package co.istad.idata.feature.mail.dto;

import lombok.Builder;

@Builder
public record MailResponse(

        String subject,

        String senderName,

        String content

) {
}
