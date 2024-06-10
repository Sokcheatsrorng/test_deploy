package co.istad.idata.feature.mail.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.stereotype.Service;


@Builder
public record MailRequest(

        @NotBlank(message = "Email is required")
        String to,

        @NotBlank(message = "Subject is required")
        String subject,

        String text

) {
}
