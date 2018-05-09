package br.com.empresa.produto.produtoauthserver.web;

import static java.lang.String.format;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.empresa.produto.produtoauthserver.exceptions.UserServiceException;
import br.com.empresa.produto.produtoauthserver.models.user.User;
import br.com.empresa.produto.produtoauthserver.mongo.repository.UserReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/api/rest/user", produces = { APPLICATION_JSON_UTF8_VALUE })
public class UserRestController {

	private UserReactiveCrudRepository repo;
	public UserRestController(UserReactiveCrudRepository repo) {
		this.repo = repo;
	};

	//@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(method = GET)
	public Mono<ResponseEntity<List<User>>> allUsers() {

		return repo.findAll().collectList()
			.filter(users -> users.size() > 0)
			.map(users -> ResponseEntity.ok(users))
			.defaultIfEmpty(noContent().build());
	}

	/**
	 * Create a new user.
	 * 
	 * @param newUser
	 *            The user to create.
	 * 
	 * @return HTTP 201, the header Location contains the URL of the created
	 *         user.
	 */
	@RequestMapping(method = POST, consumes = { APPLICATION_JSON_UTF8_VALUE })
	public Mono<ResponseEntity<?>> addUser(@RequestBody @Valid User newUser) {

		return Mono.justOrEmpty(newUser.getId())
				.flatMap(id -> repo.existsById(id))
				.defaultIfEmpty(Boolean.FALSE)
				.flatMap(exists -> {

					if (exists) {
						throw new UserServiceException(HttpStatus.BAD_REQUEST,
							"User already exists, to update an existing user use PUT instead.");
					}

					return repo.save(newUser).map(saved -> {
						return created(URI.create(format("/users/%s", saved.getId()))).build();
					});
				});
	}


	@RequestMapping(method = PUT, value = "/{id}", consumes = { APPLICATION_JSON_UTF8_VALUE })
	public Mono<ResponseEntity<?>> updateUser(@PathVariable @NotNull String id,
			@RequestBody @Valid User userToUpdate) {

		return repo.existsById(id).flatMap(exists -> {

			if (!exists) {
				throw new UserServiceException(HttpStatus.BAD_REQUEST,
					"User does not exist, to create a new user use POST instead.");
			}

			return repo.save(userToUpdate).then(Mono.just(noContent().build()));
		});
	}

	/**
	 * Delete a user.
	 * <p>
	 * This method is idempotent, if it's called multiples times with the same
	 * id then the first call will delete the user and subsequent calls will
	 * be silently ignored.
	 * 
	 * @param id
	 *            The id of the user to delete.
	 * @return HTTP 204
	 */
	@RequestMapping(method = DELETE, value = "/{id}")
	public Mono<ResponseEntity<?>> deleteUser(@PathVariable @NotNull String id) {

		final Mono<ResponseEntity<?>> noContent = Mono.just(noContent().build());

		return repo.existsById(id)
			.filter(Boolean::valueOf) // Delete only if user exists
			.flatMap(exists -> repo.deleteById(id).then(noContent))
			.switchIfEmpty(noContent);
	}

	@PostMapping("/create")
	Mono<Void> create(@RequestBody Publisher<User> userStream) {
		return this.repo.saveAll(userStream).then();
	}

	@GetMapping("/list")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	Flux<User> list() {
		return this.repo.findAll();
	}

	@GetMapping("/{id}")
	Mono<User> findById(@PathVariable String id) {
		return this.repo.findById(id);
	}
}
