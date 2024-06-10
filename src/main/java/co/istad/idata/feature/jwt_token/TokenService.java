package co.istad.idata.feature.jwt_token;

import co.istad.idata.feature.auth.dto.AuthResponse;
import org.springframework.security.core.Authentication;

public interface TokenService {

    AuthResponse createToken(Authentication auth);
    String createAccessToken(Authentication auth);
    String createRefreshToken(Authentication auth);

}
