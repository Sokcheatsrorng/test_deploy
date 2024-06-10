package co.istad.idata.feature.review.dto;

import jakarta.validation.constraints.NotNull;

public record ReviewUpdateRequest(
        String description,
        @NotNull(message = "user id is required")
        Long userId
) {
}
