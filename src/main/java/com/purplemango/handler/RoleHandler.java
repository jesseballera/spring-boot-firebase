package com.purplemango.handler;

import com.purplemango.model.Role;
import com.purplemango.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.ResponseEntity.ok;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleHandler {
    RoleRepository roleRepository;

    // retrieve all roles
    public Mono<ServerResponse> all(ServerRequest request) {
        return ServerResponse.ok().body(roleRepository.findAll(), Role.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    // retrieve a role by id
    public Mono<ServerResponse> findById(ServerRequest request) {
        return ServerResponse.ok().body(roleRepository.findByRoleId(request.pathVariable("id")), Role.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    // retrieve a role by name
    public Mono<ServerResponse> findByName(ServerRequest request) {
        return ServerResponse.ok().body(roleRepository.findByName(request.queryParam("name").get()), Role.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    // create role
    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(Role.class)
               .flatMap(role -> ServerResponse.ok().body(roleRepository.save(role), Role.class))
               .switchIfEmpty(ServerResponse.badRequest().build());
    }

    // update role
    public Mono<ServerResponse> update(ServerRequest request) {
        return request.bodyToMono(Role.class)
               .flatMap(role -> roleRepository.findByRoleId(request.pathVariable("id"))
                        .flatMap(existingRole -> {
                            existingRole.setName(role.getName());
                            return ServerResponse.ok().body(roleRepository.save(existingRole), Role.class);
                        }))
               .switchIfEmpty(ServerResponse.notFound().build());
    }

    // delete role
    public Mono<ServerResponse> delete(ServerRequest request) {
        return roleRepository
                .deleteById(request.pathVariable("id"))
                .then(ServerResponse.noContent().build());
    }
}
