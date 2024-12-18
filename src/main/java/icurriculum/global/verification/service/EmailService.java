package icurriculum.global.verification.service;

import icurriculum.global.verification.converter.EmailConverter;
import icurriculum.global.verification.dto.response.MailResponseDTO;
import icurriculum.global.verification.entity.EmailVerification;
import icurriculum.global.verification.repository.EmailVerificationRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static icurriculum.global.verification.dto.response.MailResponseDTO.*;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final EmailConverter emailConverter;
    private final EmailVerificationRepository emailVerificationRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    private final String SUBJECT = "[ICURRICULUM] 인하대학교 학생 인증 메일입니다.";

    public String makeVerificationCode() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(999999));
    }
    public String getRedisKey(String email) {
        return "Email:" + email;
    }

    @Transactional
    public MailSend sendVerificationEmail(String email, String code) {
        try {
            EmailVerification verification = new EmailVerification(email, code);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);

            messageHelper.setTo(email);
            messageHelper.setSubject(SUBJECT);
            String htmlContent = getVerificationMessage(code);
            messageHelper.setText(htmlContent, true);

            if (emailVerificationRepository.existsById(getRedisKey(email))) { //redis에 email이 있으면 삭제
                emailVerificationRepository.deleteById(getRedisKey(email));
            }
            emailVerificationRepository.save(verification); //Redis에 인증 코드 저장
            mailSender.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
            return emailConverter.toMailSendResponse("메일 전송에 실패했습니다.", false);
        }
        return emailConverter.toMailSendResponse("메일 전송에 성공했습니다.", true);
    }

    @Transactional
    public MailSend integratedProcee(String email) {
        String code = makeVerificationCode();
        return sendVerificationEmail(email, code);
    }

    public String getVerificationMessage(String verificationCode) {
        StringBuffer verificationMessage = new StringBuffer();
        verificationMessage
                .append("<h1 style = 'text-align: center;'>[ICURRICULUM] 인증메일</h1>");
        verificationMessage
                .append("<h3 style ='text-align: center;'>인증코드 : <strong style='font-size: 32px; letter-spacing: 8px;'>")
                .append(verificationCode)
                .append("</strong></h3>");
        return verificationMessage.toString();
    }

    public MailVerify verifyCode(String email, String code) {
        Optional<EmailVerification> verificationObject = emailVerificationRepository.findById(email);
        boolean check = false;
        if (verificationObject.isPresent()) {
            EmailVerification verification = verificationObject.get();
            if (verification.getVerificationCode().equals(code)) {
                emailVerificationRepository.deleteById(email);
                check = true;
            }
        }
        return emailConverter.toMailVerifyResponse(check, email);
    }

    public Long getTTL(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }
}
