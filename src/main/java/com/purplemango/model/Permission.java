package com.purplemango.model;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.spring.data.firestore.Document;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Indexed;

@Data
@Document(collectionName = "permissions")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Permission {
        @DocumentId String permissionId;
        String name;
        StatusType status;
}
