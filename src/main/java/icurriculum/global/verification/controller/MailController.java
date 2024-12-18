package icurriculum.global.verification.controller;


import icurriculum.global.response.ApiResponse;
import icurriculum.global.response.status.ErrorStatus;
import icurriculum.global.verification.dto.request.MailRequestDTO;
import icurriculum.global.verification.dto.response.MailResponseDTO;
import icurriculum.global.verification.service.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static icurriculum.global.verification.dto.response.MailResponseDTO.*;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
@Slf4j
public class MailController {

    private final EmailService emailService;

    @PostMapping("/send")
    public ApiResponse<MailSend> mailSend(@RequestBody MailRequestDTO.MailSend request) {
        MailSend mailSend = emailService.integratedProcee(request.getEmail());
        if (!mailSend.getStatus()) {
            return ApiResponse.onFailure(ErrorStatus.MAIL_NOT_SEND, mailSend);
        }
        return ApiResponse.onSuccess(mailSend);
    }

    @PostMapping("/verify")
    public ApiResponse<MailVerify> mailVerify(@RequestBody @Valid MailRequestDTO.MailVerify request) {
        MailVerify mailVerify = emailService.verifyCode(request.getEmail(), request.getCode());
        return ApiResponse.onSuccess(mailVerify);
    }
}
