package co.istad.idata.feature.mail;

import co.istad.idata.domains.User;
import co.istad.idata.feature.mail.dto.MailRequest;
import co.istad.idata.feature.mail.dto.MailResponse;
import jakarta.mail.MessagingException;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.UnsupportedEncodingException;

public interface MailService {

    void sendResetTokenEmail(String to, String resetUrl);

    void sendVerificationEmail(String url, User user, JavaMailSender mailSender) throws MessagingException, UnsupportedEncodingException;

    void sendPasswordResetVerificationEmail(String url, User user, JavaMailSender mailSender) throws MessagingException, UnsupportedEncodingException;

}
