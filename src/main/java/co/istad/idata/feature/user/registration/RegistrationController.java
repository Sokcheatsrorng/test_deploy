package co.istad.idata.feature.user.registration;

import co.istad.idata.domains.User;
import co.istad.idata.event.RegistrationCompleteEvent;
import co.istad.idata.feature.user.UserService;
import co.istad.idata.feature.user.dto.UserResponse;
import co.istad.idata.feature.user.registration.token.VerificationToken;
import co.istad.idata.feature.user.registration.token.VerificationTokenRepository;
import co.istad.idata.mapper.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/register")
public class RegistrationController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final ApplicationEventPublisher publisher;

    private final VerificationTokenRepository tokenRepository;

    @PostMapping
    public String registerUser(@RequestBody @Valid RegistrationRequest registrationRequest, final HttpServletRequest request){

        UserResponse userResponse = userService.registerUser(registrationRequest);

        User user = userMapper.fromUserResponse(userResponse);

        // publish registration event
        publisher.publishEvent(new RegistrationCompleteEvent(user, userService.applicationUrl(request)));

        return "Registration success! Please check your email to complete your registration";

    }

    @GetMapping("/verify-email")
    public String verifyEmail(@RequestParam("token") String token) {

        VerificationToken verificationToken = tokenRepository.findByTokenAndType(token, VerificationToken.TokenType.EMAIL_VERIFICATION)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Token has not been found"
                        )
                );

        if (verificationToken.getUser().getIsEmailVerified()) {

            return "This account has already been verified, please login";

        }

        String verificationResult = userService.validateVerificationToken(token);

        if (verificationResult.equalsIgnoreCase("valid")) {

            return "Email verified successfully. Now you can login";

        }

        return "Invalid verification token";

    }

}
