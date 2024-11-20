package com.purplemango.repository;

import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;
import com.purplemango.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends FirestoreReactiveRepository<User> {
}
