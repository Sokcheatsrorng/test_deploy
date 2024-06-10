package co.istad.idata.feature.user;

import co.istad.idata.base.BasedMessage;
import co.istad.idata.domains.User;
import co.istad.idata.feature.user.dto.PasswordResetRequest;
import co.istad.idata.feature.user.dto.UpdatePasswordRequest;
import co.istad.idata.feature.user.registration.RegistrationRequest;
import co.istad.idata.feature.user.dto.UserResponse;
import co.istad.idata.feature.user.dto.UserUpdateRequest;
import co.istad.idata.feature.user.registration.token.VerificationToken;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

public interface UserService {

    // Create a new user
    UserResponse registerUser(RegistrationRequest createRequest);

    // Update a user
    UserResponse updateUserById(Long id, UserUpdateRequest updateRequest);

    // Find all users
    List<UserResponse> findAll(String userName);

    // Find user by id
    UserResponse findUserById(Long id);

    // Delete a user
    void deleteUserById(Long id);

    // Update user password
    BasedMessage updatePassword(UpdatePasswordRequest passwordRequest);

    // (disable, enable, block) a user by uuid
    BasedMessage disableByUuid(String uuid);
    BasedMessage enableByUuid(String uuid);
    BasedMessage blockByUuid(String uuid);

    User findByEmailAndPasswordResetToken(String email, String passwordToken);

    // Save email verification token
    void saveUserVerificationToken(User theUser, String token, VerificationToken.TokenType type);

    // Save reset token
    void savePasswordResetToken(User theUser, String resetToken, VerificationToken.TokenType type);

    String validateVerificationToken(String verificationToken);

    String validatePasswordResetToken(String resetToken);

    Optional<User> findByEmail(String email);

    void resetUserPassword(PasswordResetRequest passwordResetRequest);

    String applicationUrl(HttpServletRequest request);

    String passwordResetEmailLink(User user, String applicationUrl, String passwordResetToken, JavaMailSender mailSender) throws MessagingException, UnsupportedEncodingException;

    User getUserByUuidAndEmail(String uuid, String email);
}
