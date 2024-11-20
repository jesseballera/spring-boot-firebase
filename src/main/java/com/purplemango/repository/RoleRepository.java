package com.purplemango.repository;

import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;
import com.purplemango.model.Role;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface RoleRepository extends FirestoreReactiveRepository<Role> {

    Mono<Role> findByRoleId(String name);
    Mono<Role> findByName(String name);
}
