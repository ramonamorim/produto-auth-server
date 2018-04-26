package br.com.empresa.produto.produtoauthserver.mongo;

import java.util.Arrays;
import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import br.com.empresa.produto.produtoauthserver.model.user.User;
import br.com.empresa.produto.produtoauthserver.user.repository.UserReactiveCrudRepository;
import reactor.core.publisher.Flux;

@Component
public class MongoRunner {
	private final UserReactiveCrudRepository userReactiveCrudRepository;

	public MongoRunner(UserReactiveCrudRepository userReactiveCrudRepository) {
		Assert.notNull(userReactiveCrudRepository, "userReactiveCrudRepository cannot be null");
		this.userReactiveCrudRepository = userReactiveCrudRepository;
	}

	@Bean
	public CommandLineRunner initDatabase() {
		Flux<User> people = Flux.just(new User(new ObjectId(), "jdev", "Joe", "Developer", "dev@transempiric.com",
				"{noop}dev", Arrays.asList("ROLE_ADMIN"), true, new Date()));

		return args -> {
			this.userReactiveCrudRepository.deleteAll().thenMany(userReactiveCrudRepository.saveAll(people))
					.blockLast();
		};
	}
}
