package com.purplemango.model;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.spring.data.firestore.Document;

@Document(collectionName = "users")
public record User(
        @DocumentId String username,
        String firstName,
        String lastName,
        String email,
        String password,
        Role role,
        StatusType status) { }
