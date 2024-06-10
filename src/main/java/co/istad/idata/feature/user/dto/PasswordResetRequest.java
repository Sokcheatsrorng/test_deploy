package co.istad.idata.feature.user.dto;

public record PasswordResetRequest(

        String email,

        String newPassword,

        String confirmPassword

) {
}
