package co.istad.idata.feature.user.registration;

import jakarta.validation.constraints.NotBlank;

public record RegistrationRequest(

        @NotBlank(message = "Email is required")
        String email,

        @NotBlank(message = "First name is required")
        String firstName,

        @NotBlank(message = "Last name is required")
        String lastName,

        @NotBlank(message = "Username is required")
        String username,

        @NotBlank(message = "Password is required")
        String password,

        @NotBlank(message = "Confirmed password is required")
        String confirmedPassword

) {
}
