package com.purplemango.handler;

import com.purplemango.model.Permission;
import com.purplemango.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionHandler {
    PermissionRepository permissionRepository;

    //retrieve all permissions
    public Mono<ServerResponse> all(ServerRequest request) {
        return ServerResponse.ok().body(permissionRepository.findAll(), Permission.class)
                .switchIfEmpty(ServerResponse.notFound().build());

    }

    //retrieve a permission by id
    public Mono<ServerResponse> findById(ServerRequest request) {
        return ServerResponse.ok().body(permissionRepository.findById(request.pathVariable("permission-id")), Permission.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    //retrieve a permission by  name
    public Mono<ServerResponse> findByName(ServerRequest request) {
        return ServerResponse.ok().body(permissionRepository.findByName(request.queryParam("permission-name").get()), Permission.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    //create permission
    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(Permission.class)
                .flatMap(permission -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(permissionRepository.save(permission), Permission.class));
    }

    //update permission
    public Mono<ServerResponse> update(ServerRequest request) {
        return request.bodyToMono(Permission.class)
                .flatMap(permission -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(permissionRepository.save(permission), Permission.class));
    }

    //delete permission
    public Mono<ServerResponse> delete(ServerRequest request) {
        return permissionRepository
                .deleteById(request.pathVariable("permissionId"))
                .then(ServerResponse.noContent().build());
    }

}
