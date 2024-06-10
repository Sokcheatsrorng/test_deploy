package co.istad.idata.event.listener;

import co.istad.idata.domains.User;
import co.istad.idata.event.RegistrationCompleteEvent;
import co.istad.idata.feature.mail.MailService;
import co.istad.idata.feature.user.UserService;
import co.istad.idata.feature.user.registration.token.VerificationToken;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class RegistrationCompleteEventListener
        implements ApplicationListener<RegistrationCompleteEvent> {

    private final MailService mailService;
    private final UserService userService;
    private final JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {

        // 1. Get the new registered user
        User theUser = event.getUser();

        // 2. Create a verification token for the user
        String verificationToken = UUID.randomUUID().toString();

        // 3. Save the verification token for the user
        userService.saveUserVerificationToken(theUser, verificationToken, VerificationToken.TokenType.EMAIL_VERIFICATION);

        // 4. Build the verification url to be sent to the user
        String url = event.getApplicationUrl() + "/api/v1/register/verify-email?token=" + verificationToken;

        // 5. Send the email
        log.info("Click the link to verify your email: {}", url);

        try {
            mailService.sendVerificationEmail(url, theUser, mailSender);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);

        }
    }
}
