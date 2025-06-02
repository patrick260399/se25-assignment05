package de.unibayreuth.se.campuscoffee.api.dtos;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

/**
 * DTO for user metadata.
 *
 */
@Data
@Builder(toBuilder = true)
public class UserDto {

    @Nullable
    private Long id; // id is null when creating a new task
    // Implement user DTO.
    @Nullable
    private LocalDateTime createdAt; // is null when using DTO to create or update a new POS

    @Nullable
    private LocalDateTime updatedAt;

    @NotBlank
    @Pattern(regexp = "\\w+")
    private String username;

    @Email (message = "Invalid E-Mail")
    private String email;

    @NotBlank
    @Size(max = 255)
    private String firstName;

    @NotBlank
    @Size(max = 255)
    private String lastName;
}
