package co.istad.idata.feature.auth;

import co.istad.idata.feature.auth.dto.AuthResponse;
import co.istad.idata.feature.auth.dto.LoginRequest;
import co.istad.idata.feature.auth.dto.RefreshTokenRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    AuthResponse login(@RequestBody @Valid LoginRequest loginRequest){
        return authService.login(loginRequest);
    }

    @PostMapping("/refresh")
    AuthResponse refresh(@RequestBody @Valid RefreshTokenRequest refreshTokenRequest){
        return authService.refresh(refreshTokenRequest);
    }

}
