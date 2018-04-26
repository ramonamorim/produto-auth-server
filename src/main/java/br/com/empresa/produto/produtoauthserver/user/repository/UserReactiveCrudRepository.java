package br.com.empresa.produto.produtoauthserver.user.repository;

import org.bson.types.ObjectId;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import br.com.empresa.produto.produtoauthserver.model.user.User;
import reactor.core.publisher.Mono;

@Repository
public interface UserReactiveCrudRepository extends ReactiveCrudRepository<User, ObjectId> {
	
	Mono<UserDetails> findByUsername(String username);
	Mono<User> findUserbyUsername(String username);

}
