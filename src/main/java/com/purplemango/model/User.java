package com.purplemango.model;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.spring.data.firestore.Document;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collectionName = "users")
public class User {
    @DocumentId String id;
    String username;
    String firstName;
    String lastName;
    String email;
    String password;
    Role role;
    StatusType status;
}
