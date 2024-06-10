package co.istad.idata.feature.auth.dto;

public record AuthResponse(

        String type,

        String accessToken,

        String refreshToken

) {
}
