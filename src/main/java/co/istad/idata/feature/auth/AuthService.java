package co.istad.idata.feature.auth;

import co.istad.idata.feature.auth.dto.AuthResponse;
import co.istad.idata.feature.auth.dto.LoginRequest;
import co.istad.idata.feature.auth.dto.RefreshTokenRequest;

public interface AuthService {

    AuthResponse login(LoginRequest loginRequest);

    AuthResponse refresh(RefreshTokenRequest refreshTokenRequest);

}
