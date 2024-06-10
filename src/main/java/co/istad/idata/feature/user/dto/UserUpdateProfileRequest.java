package co.istad.idata.feature.user.dto;

import jakarta.validation.constraints.NotBlank;

public record UserUpdateProfileRequest(

        @NotBlank(message = "Avatar is required")
        String avatar

) {
}
