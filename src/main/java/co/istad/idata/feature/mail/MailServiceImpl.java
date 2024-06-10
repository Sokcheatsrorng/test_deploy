package co.istad.idata.feature.mail;

import co.istad.idata.domains.User;
import co.istad.idata.feature.mail.dto.MailRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService{


    @Override
    public void sendResetTokenEmail(String to, String resetUrl) {
        MailRequest.builder()
                .to(to)
                .subject("Password reset request")
                .text("To reset your password, click the link below:\n" + resetUrl)
                .build();
    }

    @Override
    public void sendVerificationEmail(String url, User user, JavaMailSender mailSender) throws MessagingException, UnsupportedEncodingException {
        String subject = "Email Verification";
        String senderName = "iData Service";
        String mailContent = "<p> Hi, "+ user.getFirstName()+ ", </p>"+
                "<p>Thank you for registering with us,"+"" +
                "Please, follow the link below to complete your registration.</p>"+
                "<a href=\"" +url+ "\">Verify your email to activate your account</a>"+
                "<p> Thank you <br> Users Registration Portal Service";
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("idata.cstad.co@gmail.com", senderName);
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }

    @Override
    public void sendPasswordResetVerificationEmail(String url, User user, JavaMailSender mailSender) throws MessagingException, UnsupportedEncodingException {
        String subject = "Password Reset Request Verification";
        String senderName = "User Registration Portal Service";
        String mailContent = "<p> Hi, "+ user.getFirstName()+ ", </p>"+
                "<p><b>You recently requested to reset your password,</b>"+"" +
                "Please, follow the link below to complete the action.</p>"+
                "<a href=\"" +url+ "\">Reset password</a>"+
                "<p> Users Registration Portal Service";
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("idata.cstad.co@gmail.com", senderName);
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }

}
