package icurriculum.global.verification.converter;

import icurriculum.global.verification.dto.response.MailResponseDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static icurriculum.global.verification.dto.response.MailResponseDTO.*;

@Component
public class EmailConverter {

    public MailSend toMailSendResponse(String comment, Boolean status) {
        return MailSend.builder()
                .responseComment(comment)
                .status(status)
                .timeStamp(LocalDateTime.now())
                .build();
    }

    public MailVerify toMailVerifyResponse(Boolean check, String email) {
        return MailVerify.builder()
                .check(check)
                .email(email)
                .build();
    }
}
