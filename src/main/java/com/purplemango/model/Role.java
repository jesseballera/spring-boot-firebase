package com.purplemango.model;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.spring.data.firestore.Document;

import java.util.Set;

@Document(collectionName = "roles")
public record Role(
        @DocumentId String roleId,
        String roleName,
        Set<Permission> permissions,
        StatusType status) { }
