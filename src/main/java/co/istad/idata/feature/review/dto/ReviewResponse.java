package co.istad.idata.feature.review.dto;

import co.istad.idata.domains.User;
import co.istad.idata.feature.user.dto.UserResponse;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

public record ReviewResponse(

        String description,

        UserResponse user

) {
}
