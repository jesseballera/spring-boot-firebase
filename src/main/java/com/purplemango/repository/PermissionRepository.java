package com.purplemango.repository;

import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;
import com.purplemango.model.Permission;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PermissionRepository extends FirestoreReactiveRepository<Permission> {
    Mono<Permission> findByName(String permissionName);
}
