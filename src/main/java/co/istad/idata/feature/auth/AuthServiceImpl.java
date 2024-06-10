package co.istad.idata.feature.auth;

import co.istad.idata.feature.auth.dto.AuthResponse;
import co.istad.idata.feature.auth.dto.LoginRequest;
import co.istad.idata.feature.auth.dto.RefreshTokenRequest;
import co.istad.idata.feature.jwt_token.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final TokenService tokenService;

    @Override
    public AuthResponse login(LoginRequest loginRequest) {

        Authentication auth = new UsernamePasswordAuthenticationToken(
                loginRequest.email(),
                loginRequest.password()
        );

        auth = daoAuthenticationProvider.authenticate(auth);

        return tokenService.createToken(auth);

    }

    @Override
    public AuthResponse refresh(RefreshTokenRequest refreshTokenRequest) {

        Authentication auth = new BearerTokenAuthenticationToken(
                refreshTokenRequest.refreshToken()
        );

        auth = jwtAuthenticationProvider.authenticate(auth);

        return tokenService.createToken(auth);

    }
}
