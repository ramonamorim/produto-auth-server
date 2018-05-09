package br.com.empresa.produto.produtoauthserver.mongo.repository;

import org.bson.types.ObjectId;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import br.com.empresa.produto.produtoauthserver.models.user.User;
import reactor.core.publisher.Mono;

@Repository
public interface UserReactiveCrudRepository  extends ReactiveCrudRepository<User, String> {
    Mono<UserDetails> findByUsername(String username);
    Mono<User> findUserByUsername(String username);

}
