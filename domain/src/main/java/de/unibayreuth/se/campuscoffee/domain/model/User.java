package de.unibayreuth.se.campuscoffee.domain.model;

import edu.umd.cs.findbugs.annotations.Nullable;
import lombok.Data;
import org.springframework.lang.NonNull;
import java.time.LocalDateTime;
import java.io.Serial;
import java.io.Serializable;

/**
 * Domain class that stores the user metadata.
 */
@Data
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Nullable
    private Long id; // null when the user has not been created yet
    //  Implement user domain class. Hint: the @Nullable annotations are important for the Lombok @Data annotation (see https://projectlombok.org/features/Data).

    @Nullable
    private LocalDateTime createdAt;

    @Nullable
    private LocalDateTime updatedAt;

    @NonNull
    private String username;

    @NonNull
    private String email;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;
}
