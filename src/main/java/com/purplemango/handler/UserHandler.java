package com.purplemango.handler;

import com.purplemango.model.User;
import com.purplemango.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserHandler {
    UserRepository userRepository;

    // retrieve all users
    public Mono<ServerResponse> all(ServerRequest request) {
        return ServerResponse.ok().body(userRepository.findAll(), User.class);
    }

    // retrieve a user by id
    public Mono<ServerResponse> findById(ServerRequest request) {
        return ServerResponse.ok().body(userRepository.findById(request.pathVariable("id")), User.class);
    }

    // retrieve a user by username
    public Mono<ServerResponse> findByUsername(ServerRequest request) {
        return ServerResponse.ok().body(userRepository.findByUsername(request.queryParam("username").get()), User.class);
    }

    // create user
    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(User.class)
               .flatMap(user -> ServerResponse.ok().body(userRepository.save(user), User.class));
    }

    // update user
    public Mono<ServerResponse> update(ServerRequest request) {
        return request.bodyToMono(User.class)
               .flatMap(user -> userRepository.findById(request.pathVariable("id"))
                        .flatMap(existingUser -> {
                            existingUser.setUsername(user.getUsername());
                            existingUser.setEmail(user.getEmail());
                            existingUser.setFirstName(user.getFirstName());
                            existingUser.setLastName(user.getLastName());
                            existingUser.setRole(user.getRole());
                            existingUser.setPassword(user.getPassword());
                            return ServerResponse.ok().body(userRepository.save(existingUser), User.class);
                        }));
    }

    // delete user
    public Mono<ServerResponse> delete(ServerRequest request) {
        return userRepository
               .deleteById(request.pathVariable("id"))
               .then(ServerResponse.noContent().build());
    }
}
