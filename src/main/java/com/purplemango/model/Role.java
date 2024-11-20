package com.purplemango.model;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.spring.data.firestore.Document;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collectionName = "roles")
public class Role{
    @DocumentId String roleId;
    String name;
    List<Permission> permissions;
    StatusType status;
}
