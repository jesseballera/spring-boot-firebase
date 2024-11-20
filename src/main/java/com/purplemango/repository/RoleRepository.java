package com.purplemango.repository;

import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;
import com.purplemango.model.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends FirestoreReactiveRepository<Role> {
}
