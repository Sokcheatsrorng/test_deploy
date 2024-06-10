package co.istad.idata.feature.user;

import co.istad.idata.base.BasedMessage;
import co.istad.idata.domains.Role;
import co.istad.idata.domains.User;
import co.istad.idata.feature.mail.MailService;
import co.istad.idata.feature.user.dto.PasswordResetRequest;
import co.istad.idata.feature.user.dto.UpdatePasswordRequest;
import co.istad.idata.feature.user.registration.RegistrationRequest;
import co.istad.idata.feature.user.dto.UserResponse;
import co.istad.idata.feature.user.dto.UserUpdateRequest;
import co.istad.idata.feature.user.registration.token.VerificationToken;
import co.istad.idata.feature.user.registration.token.VerificationTokenRepository;
import co.istad.idata.mapper.UserMapper;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final UserMapper userMapper;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse registerUser(RegistrationRequest createRequest) {

        if (userRepository.existsByEmail(createRequest.email())){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email is already existed"
            );
        }

        if (!createRequest.password().equals(createRequest.confirmedPassword())){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Password does not match"
            );
        }

        User user = userMapper.fromUserCreateRequest(createRequest);
        user.setUuid(UUID.randomUUID().toString());
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCredentialsNonExpired(true);
        user.setIsEmailVerified(false);
        user.setIsBlocked(false);
        user.setIsDeleted(false);

        List<Role> roles = new ArrayList<>();
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Role user has not been found"
                        ));

        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);

        return userMapper.toUserResponse(user);

    }

    @Override
    public UserResponse updateUserById(Long id, UserUpdateRequest updateRequest) {

        User existedUser = userRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User has not been found"
                        )
                );

        existedUser.setUsername(updateRequest.username());
        existedUser.setFirstName(updateRequest.firstName());
        existedUser.setLastName(updateRequest.lastName());

        userRepository.save(existedUser);

        return userMapper.toUserResponse(existedUser);
    }

    @Override
    public List<UserResponse> findAll(String userName) {

        List<User> users = userRepository.findAll()
                .stream().filter(
                        user -> user.getUsername().contains(userName)
                ).collect(Collectors.toList());

        return userMapper.toUserResponseList(users);
    }

    @Override
    public UserResponse findUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User has not been found"
                        )
                );

        return userMapper.toUserResponse(user);

    }

    @Transactional
    @Override
    public void deleteUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User has not been found"
                        )
                );

        verificationTokenRepository.deleteAllByUser(user);

        userRepository.delete(user);

    }

    @Transactional
    @Override
    public BasedMessage disableByUuid(String uuid) {

        userRepository.disableByUuid(uuid);

        return BasedMessage.builder()
                .message("User has been disabled")
                .build();
    }

    @Transactional
    @Override
    public BasedMessage enableByUuid(String uuid) {

        userRepository.enableByUuid(uuid);

        return BasedMessage.builder()
                .message("User has been enabled")
                .build();
    }

    @Transactional
    @Override
    public BasedMessage blockByUuid(String uuid){

        userRepository.blockByUuid(uuid);

        return BasedMessage.builder()
                .message("User has been blocked")
                .build();

    }
    @Override
    public void saveUserVerificationToken(User theUser, String token, VerificationToken.TokenType type) {

        VerificationToken verificationToken = new VerificationToken(token, theUser, VerificationToken.TokenType.EMAIL_VERIFICATION);

        verificationTokenRepository.save(verificationToken);

    }

    @Override
    public void savePasswordResetToken(User theUser, String resetToken, VerificationToken.TokenType type) {

        var passwordResetToken = new VerificationToken(resetToken, theUser, VerificationToken.TokenType.PASSWORD_RESET);

        verificationTokenRepository.save(passwordResetToken);

    }

    @Override
    public BasedMessage updatePassword(UpdatePasswordRequest passwordRequest) {

        if (!userRepository.existsByPassword(passwordRequest.oldPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Wrong password! Please try again."
            );
        }

        if (!passwordRequest.newPassword().equals(passwordRequest.confirmPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Password does not match, Please try again."
            );
        }

        User user = userRepository.findAll()
                .stream()
                .filter(user1 -> user1.getPassword().equals(passwordRequest.oldPassword()))
                .findFirst().orElseThrow();

        user.setPassword(passwordEncoder.encode(passwordRequest.newPassword()));

        userRepository.save(user);

        return BasedMessage.builder()
                .message("Password has been changed successfully.")
                .build();
    }

    @Override
    public String validateVerificationToken(String verificationToken) {

        VerificationToken token = verificationTokenRepository.findByTokenAndType(verificationToken, VerificationToken.TokenType.EMAIL_VERIFICATION)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Token has not been found"
                        )
                );

        User user = token.getUser();

        Calendar calendar = Calendar.getInstance();

        if ((token.getExpiresAt().getTime() - calendar.getTime().getTime()) <= 0) {

            verificationTokenRepository.delete(token);

            return "Token already expired";
        }
        user.setIsEmailVerified(true);

        userRepository.save(user);

        return "valid";
    }

    @Override
    public String validatePasswordResetToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken == null || verificationToken.getIdUsed()) {
            return "invalid";
        }
        return "valid";
    }


    @Override
    public User findByEmailAndPasswordResetToken(String email, String passwordToken) {

        VerificationToken existedToken = verificationTokenRepository.findByTokenAndType(passwordToken, VerificationToken.TokenType.PASSWORD_RESET)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Token has not been found"
                        )
                );

        //        return Optional.ofNullable(verificationTokenRepository.findByTokenAndType(passwordToken, VerificationToken.TokenType.PASSWORD_RESET).get().getUser());
        return existedToken.getUser();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void resetUserPassword(PasswordResetRequest passwordResetRequest) {

        if (!passwordResetRequest.newPassword().equals(passwordResetRequest.confirmPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Password is not match, please try again"
            );
        }

        User existsUser = userRepository.findByEmail(passwordResetRequest.email())
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User has not been found"
                        )
                );

        if (passwordResetRequest.newPassword().equals(existsUser.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "New password can't be same as old password"
            );
        }

        existsUser.setPassword(passwordEncoder.encode(passwordResetRequest.newPassword()));

        userRepository.save(existsUser);

    }

    @Override
    public String applicationUrl(HttpServletRequest request) {

        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

    }

    @Override
    public String passwordResetEmailLink(User user, String applicationUrl, String passwordResetToken, JavaMailSender mailSender) throws MessagingException, UnsupportedEncodingException {
        String url = applicationUrl + "/api/v1/users/reset-password?token=" + passwordResetToken;

        mailService.sendPasswordResetVerificationEmail(url, user, mailSender);

        log.info("Click the link to reset your password: {}", url);

        return url;
    }

    @Override
    public User getUserByUuidAndEmail(String uuid, String email) {
        return userRepository.findByEmailAndUuid(email, uuid).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User has not been found"
                )
        );
    }
}
