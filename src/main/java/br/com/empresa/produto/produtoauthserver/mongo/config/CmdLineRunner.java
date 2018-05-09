package br.com.empresa.produto.produtoauthserver.mongo.config;

import java.util.Arrays;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import br.com.empresa.produto.produtoauthserver.models.user.User;
import br.com.empresa.produto.produtoauthserver.mongo.repository.UserReactiveCrudRepository;
import reactor.core.publisher.Flux;

@Component
public class CmdLineRunner {
    private final UserReactiveCrudRepository userReactiveCrudRepository;

    public CmdLineRunner(UserReactiveCrudRepository userReactiveCrudRepository) {
        Assert.notNull(userReactiveCrudRepository, "userReactiveCrudRepository cannot be null");
        this.userReactiveCrudRepository = userReactiveCrudRepository;
    }

    @Bean
    public CommandLineRunner initDatabase() {
        Flux<User> people = Flux.just(
                new User( UUID.randomUUID().toString(), "user", "user", Arrays.asList("ROLE_ADMIN"), true)
        );

        return args -> {
            this.userReactiveCrudRepository.deleteAll().thenMany(userReactiveCrudRepository.saveAll(people)).blockLast();
        };
    }
}
