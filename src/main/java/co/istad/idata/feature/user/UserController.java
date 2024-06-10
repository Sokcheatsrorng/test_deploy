package co.istad.idata.feature.user;

import co.istad.idata.base.BasedMessage;
import co.istad.idata.domains.User;
import co.istad.idata.feature.user.dto.*;
import co.istad.idata.feature.user.registration.RegistrationRequest;
import co.istad.idata.feature.user.registration.token.VerificationToken;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    private final JavaMailSender mailSender;

    @GetMapping
    List<UserResponse> findAll(@RequestParam String userName){
        return userService.findAll(userName);
    }

    @GetMapping("/{id}")
    UserResponse findById(@PathVariable Long id){
        return userService.findUserById(id);
    }


    @PatchMapping("/{id}")
    UserResponse updateUser(@PathVariable Long id,
                            @RequestBody @Valid UserUpdateRequest updateRequest){
        return userService.updateUserById(id, updateRequest);
    }

    @DeleteMapping("/{id}")
    void deleteUser(@PathVariable Long id){
        userService.deleteUserById(id);
    }

    @PutMapping("/{uuid}/disable-user")
    BasedMessage disableByUuid(@PathVariable String uuid){
        return userService.disableByUuid(uuid);
    }

    @PutMapping("/{uuid}/enable-user")
    BasedMessage enableByUuid(@PathVariable String uuid){
        return userService.enableByUuid(uuid);
    }

    @PutMapping("/{uuid}/block-user")
    BasedMessage blockByUuid(@PathVariable String uuid){
        return userService.blockByUuid(uuid);
    }

    @PutMapping("/change-password")
    BasedMessage updatePassword(@RequestBody @Valid UpdatePasswordRequest request){
        return userService.updatePassword(request);
    }

    @PostMapping("/{uuid}/forget-password")
    public String resetPasswordRequest(@PathVariable String uuid, @RequestBody @Valid ForgetPasswordRequest emailRequest, final HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        User user = userService.getUserByUuidAndEmail(uuid, emailRequest.email());

        String passwordResetToken = UUID.randomUUID().toString();
        userService.savePasswordResetToken(user, passwordResetToken, VerificationToken.TokenType.PASSWORD_RESET);

        String passwordResetUrl="";

        passwordResetUrl = userService.passwordResetEmailLink(user, userService.applicationUrl(request), passwordResetToken, mailSender);

        return passwordResetUrl;
    }


    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody @Valid PasswordResetRequest passwordResetRequest,
                                @RequestParam("token") String passwordResetToken) {
        String tokenValidationResult = userService.validatePasswordResetToken(passwordResetToken);

        if (!tokenValidationResult.equalsIgnoreCase("valid")) {
            return "Invalid password reset token";
        }

        userService.resetUserPassword(passwordResetRequest);
        return "Password has been reset successfully";
    }



}
