package co.istad.idata.security;

import co.istad.idata.util.KeyUtil;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.web.SecurityFilterChain;

import java.util.UUID;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final KeyUtil keyUtil;

    @Bean
    JwtAuthenticationProvider jwtAuthenticationProvider(@Qualifier("refreshJwtDecoder")JwtDecoder refreshJwtDecoder){

        JwtAuthenticationProvider provider = new JwtAuthenticationProvider(refreshJwtDecoder);

        return provider;

    }

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider(){

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);

        return provider;

    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {

        security
                .authorizeHttpRequests(request -> request
                                .requestMatchers("api/v1/register/**").permitAll()
                                .requestMatchers(HttpMethod.POST,"api/v1/auth/**").permitAll()
                                .requestMatchers("api/v1/users/**").permitAll()
                                .requestMatchers("api/v1/review/**").permitAll()
                                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                                .requestMatchers("api/v1/rest-api/**").permitAll()
                                .requestMatchers("api/v1/medias/**").permitAll()
                                .requestMatchers("api/v1/definition/**").permitAll()
                                .requestMatchers("api/v1/user-data/**").permitAll()
                                .anyRequest()
                                .authenticated()
                );

        // security mechanism
        security.oauth2ResourceServer(jwt -> jwt
                .jwt(Customizer.withDefaults()));

        // disable CSRF
        security.csrf(AbstractHttpConfigurer::disable);

        // change to stateless
        security.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return security.build();

    }

    @Primary
    @Bean
    JWKSource<SecurityContext> jwkSource() {
        RSAKey rsaKey = new RSAKey.Builder(keyUtil.getAccessTokenPublicKey())
                .privateKey(keyUtil.getAccessTokenPrivateKey())
                .keyID(UUID.randomUUID().toString())
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector
                .select(jwkSet);
    }

    @Primary
    @Bean
    JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }

    @Primary
    @Bean
    JwtDecoder jwtDecoder() throws JOSEException {
        return NimbusJwtDecoder
                .withPublicKey(keyUtil.getAccessTokenPublicKey())
                .build();
    }

    // JWT REFRESH TOKEN =====================================

    @Bean("refreshJwkSource")
    JWKSource<SecurityContext> refreshJwkSource() {
        RSAKey rsaKey = new RSAKey.Builder(keyUtil.getRefreshTokenPublicKey())
                .privateKey(keyUtil.getRefreshTokenPrivateKey())
                .keyID(UUID.randomUUID().toString())
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector
                .select(jwkSet);
    }

    @Bean("refreshJwtEncoder")
    JwtEncoder refreshJwtEncoder(@Qualifier("refreshJwkSource") JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean("refreshJwtDecoder")
    JwtDecoder refreshJwtDecoder() throws JOSEException {
        return NimbusJwtDecoder
                .withPublicKey(keyUtil.getRefreshTokenPublicKey())
                .build();
    }

}
