package co.istad.idata.feature.review.dto;

import co.istad.idata.domains.User;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

public record ReviewCreateRequest(
        String description,
        @NotNull(message = "user id is required")
        Long userId

) {
}
