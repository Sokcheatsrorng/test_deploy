package co.istad.idata.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

import java.util.Arrays;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("OAuth2", new SecurityScheme()
                                .type(SecurityScheme.Type.OAUTH2)
                                .flows(new OAuthFlows()
                                        .authorizationCode(new OAuthFlow()
                                                .authorizationUrl("https://example.com/oauth/authorize")
                                                .tokenUrl("https://example.com/oauth/token")
                                                .scopes(new Scopes()
                                                        .addString("read", "Grants read access")
                                                        .addString("write", "Grants write access")
                                                        .addString("admin", "Grants access to admin operations"))))))
                .security(Arrays.asList(
                        new SecurityRequirement().addList("OAuth2", Arrays.asList("read", "write"))
                ))
                .info(new Info()
                        .title("API Documentation")
                        .version("3.1.0")
                        .description("API documentation"));
    }
}



