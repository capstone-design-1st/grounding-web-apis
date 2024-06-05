package org.example.first.groundingwebapis.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.example.first.groundingwebapis.entity.User;
import org.example.first.groundingwebapis.exception.UserErrorResult;
import org.example.first.groundingwebapis.security.UserPrincipal;
import org.example.first.groundingwebapis.service.MailService;
import org.example.first.groundingwebapis.service.SmsService;
import org.example.first.groundingwebapis.service.UserService;
import org.example.first.groundingwebapis.service.VerificationService;
import org.example.first.groundingwebapis.dto.*;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    //private final SmsService smsService;
    private final VerificationService verificationService;
    private final MailService mailService;
    private final ObjectMapper objectMapper;
    private final StringRedisTemplate redisTemplate;

    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(@Valid @RequestBody UserDto.LoginRequestDto loginRequestDto) {

        if (loginRequestDto.getEmail() == null || loginRequestDto.getPassword() == null) {
            log.info("필수값 누락");
            UserErrorResult userErrorResult = UserErrorResult.REQUIRED_VALUE;
            ResponseDto responseDto = ResponseDto.builder().error(userErrorResult.getMessage()).build();

            return ResponseEntity.status(userErrorResult.getHttpStatus()).body(responseDto);
        }
        if (!userService.userExistsByEmail(loginRequestDto.getEmail())) {
            log.info("존재하지 않는 사용자");
            UserErrorResult userErrorResult = UserErrorResult.USER_NOT_FOUND;
            ResponseDto responseDto = ResponseDto.builder().error(userErrorResult.getMessage()).build();
            return ResponseEntity.status(userErrorResult.getHttpStatus()).body(responseDto);
        }
        if (!userService.userExistsByEmailAndPassword(loginRequestDto.getEmail(), loginRequestDto.getPassword())) {
            log.info("비밀번호 불일치");
            UserErrorResult userErrorResult = UserErrorResult.USER_NOT_FOUND;
            ResponseDto responseDto = ResponseDto.builder().error(userErrorResult.getMessage()).build();

            return ResponseEntity.status(userErrorResult.getHttpStatus()).body(responseDto);
        }


        UserDto.LoginResponseDto loginResponseDto = userService.login(loginRequestDto);

        log.info("로그인 성공");

        ResponseDto responseDto = ResponseDto.builder()
                .payload(objectMapper.convertValue(loginResponseDto, Map.class))
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseDto); //200
    }

    @PostMapping
    public ResponseEntity<ResponseDto> signUp(@Valid @RequestBody UserDto.
            SignUpRequestDto signUpRequestDto) throws Exception {
        try {
            if (signUpRequestDto.getEmail() == null || signUpRequestDto.getPassword() == null || signUpRequestDto.getName() == null || signUpRequestDto.getPhoneNumber() == null) {
                log.info("필수값 누락");
                UserErrorResult userErrorResult = UserErrorResult.REQUIRED_VALUE;
                ResponseDto responseDto = ResponseDto.builder().error(userErrorResult.getMessage()).build();

                return ResponseEntity.status(userErrorResult.getHttpStatus()).body(responseDto);
            }

            if (userService.userExistsByName(signUpRequestDto.getEmail())) {
                log.info("이미 존재하는 닉네임");
                UserErrorResult userErrorResult = UserErrorResult.DUPLICATED_NAME;
                ResponseDto responseDto = ResponseDto.builder().error(userErrorResult.getMessage()).build();

                return ResponseEntity.status(userErrorResult.getHttpStatus()).body(responseDto);
            }

//            if (!verificationService.isVerified(signUpRequestDto.getPhoneNumber())){
//                log.info("검증되지 않은 전화번호");
//                UserErrorResult userErrorResult = UserErrorResult.UNVERIFIED_PHONE_NUMBER;
//                ResponseDto responseDto = ResponseDto.builder().error(userErrorResult.getMessage()).build();
//
//                return ResponseEntity.status(userErrorResult.getHttpStatus()).body(responseDto);
//            }


            if (userService.userExistsByEmail(signUpRequestDto.getEmail())) {
                log.info("이미 존재하는 이메일");
                UserErrorResult userErrorResult = UserErrorResult.DUPLICATED_EMAIL;

                ResponseDto responseDto = ResponseDto.builder()
                        .error(userErrorResult.getMessage())
                        .build();

                return ResponseEntity.status(userErrorResult.getHttpStatus()).body(responseDto);
            }

            UserDto.SignUpResponseDto signUpResponseDto = userService.signUp(signUpRequestDto);

            ResponseDto responseDto = ResponseDto.builder()
                    .payload(objectMapper.convertValue(signUpResponseDto, Map.class))
                    .build();

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto); //201


        } catch (Exception e) {
            log.info("회원가입 실패");
            ResponseDto responseDto = ResponseDto.builder()
                    .error("회원가입 실패")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto); //400
        }
    }

    @Operation(summary = "회원가입 이메일 중복 확인")
    @PostMapping("/sign-up/email/validation")
    public ResponseEntity<ResponseDto> checkDuplicatedEmail(@Valid @RequestBody UserDto.CheckDuplicatedEmailRequestDto checkDuplicatedEmailRequestDto) {

        String email = checkDuplicatedEmailRequestDto.getEmail();
        if (userService.userExistsByEmail(email)) {
            log.info("이메일 중복");
            UserErrorResult userErrorResult = UserErrorResult.DUPLICATED_EMAIL;

            ResponseDto responseDto = ResponseDto.builder()
                    .error(userErrorResult.getMessage())
                    .build();

            return ResponseEntity.status(userErrorResult.getHttpStatus()).body(responseDto); //409
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); //204
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping //API-102
    public ResponseEntity<ResponseDto> withdrawUser(@AuthenticationPrincipal UserPrincipal user) {

        Long userId = user.getUser().getUserId();

        userService.deleteUser(userId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); //204
    }

    @GetMapping("/{userId}/phone-number")
    public ResponseEntity<ResponseDto> getPhoneNumberWithUserId(@PathVariable Long userId) throws Exception {

        UserDto.GetPhoneNumberResponseDto getPhoneNumberResponseDto = userService.getPhoneNumber(userId);

        ResponseDto responseDto = ResponseDto.builder()
                .payload(objectMapper.convertValue(getPhoneNumberResponseDto, Map.class))
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseDto); //200
    }

    @GetMapping("/{userId}/email")
    public ResponseEntity<ResponseDto> getEmailWithUserId(@PathVariable Long userId) throws Exception {
        UserDto.GetEmailResponseDto getEmailResponseDto = userService.getEmail(userId);

        ResponseDto responseDto = ResponseDto.builder()
                .payload(objectMapper.convertValue(getEmailResponseDto, Map.class))
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseDto); //200
    }

    //email validation, verification
    @Operation(summary = "이메일 인증코드 발송")
    @PostMapping("/email/validation")
    public ResponseEntity<ResponseDto> validationEmail(@Valid @RequestBody MailDto.MailRequestDto mailRequestDto) throws JsonProcessingException, RestClientException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
        String email = mailRequestDto.getEmail();

        String verificationCode = verificationService.makeVerificationCode();

        MailDto.MailSendDto mailSendDto = MailDto.MailSendDto.builder()
                .email(email)
                .title("그라운딩 이메일 인증코드입니다.")
                .content("인증번호는 [" + verificationCode + "]입니다.")
                .build();
        mailService.sendMail(mailSendDto);

        verificationService.saveVerificationCode(email, verificationCode);
        verificationService.saveCompletionCode(email, false);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); //204
    }

    @Operation(summary = "이메일 인증코드 검증")
    @PostMapping("/email/verification")
    public ResponseEntity<ResponseDto> verificationEmail(@RequestBody MailDto.MailVerifyDto mailVerifyDto) {
        String email = mailVerifyDto.getEmail();

        if (verificationService.verifyCode(email, mailVerifyDto.getVerificationCode())) {
            log.info("인증 코드 검증 성공");
            //검증 성공 후 코드 삭제, 완료 코드 생성
            verificationService.deleteVerificationCode(email);
            verificationService.saveCompletionCode(email, true);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); //204
        } else {
            log.info("인증 코드 검증 실패");
            UserErrorResult userErrorResult = UserErrorResult.FAILED_VALIDATING_CODE;
            ResponseDto responseDto = ResponseDto.builder().error(userErrorResult.getMessage()).build();

            return ResponseEntity.status(userErrorResult.getHttpStatus()).body(responseDto);
        }
    }

    @GetMapping("/wallet")
    public ResponseEntity<ResponseDto> getWalletAddress(@AuthenticationPrincipal UserPrincipal user) {
        Long userId = user.getUser().getUserId();
        UserDto.GetWalletAddressResponseDto getWalletAddressResponseDto = userService.getWallet(userId);

        ResponseDto responseDto = ResponseDto.builder()
                .payload(objectMapper.convertValue(getWalletAddressResponseDto, Map.class))
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseDto); //200
    }
}