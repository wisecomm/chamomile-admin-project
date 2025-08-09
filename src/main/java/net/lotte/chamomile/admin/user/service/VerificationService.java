package net.lotte.chamomile.admin.user.service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import net.lotte.chamomile.admin.user.api.dto.UserQuery;
import net.lotte.chamomile.admin.user.domain.UserMapper;
import net.lotte.chamomile.admin.user.domain.UserVO;
import net.lotte.chamomile.admin.user.domain.VerificationToken;
import net.lotte.chamomile.admin.user.domain.VerificationTokenMapper;
import net.lotte.chamomile.core.exception.ChamomileException;
import net.lotte.chamomile.core.exception.ChamomileExceptionCode;
import net.lotte.chamomile.module.notification.mail.MailUtil;
import net.lotte.chamomile.module.notification.mail.MailVo;

@Service
@RequiredArgsConstructor
public class VerificationService {
    private final VerificationTokenMapper tokenRepository;
    private final UserMapper userMapper;
    private final MailUtil mailUtil;

    @Value("${chmm.admin.mail.from}")
    private String mailFromAddress;


    public void isUserExist(String userId, String email){
        UserQuery userQuery = new UserQuery();
        userQuery.setUserId(userId);

        UserVO userVo = userMapper.findUserDetail(userQuery)
                .orElseThrow(() -> new ChamomileException(ChamomileExceptionCode.USER_NOT_FOUND));

        if(!email.equals(userVo.getUserEmail()))
            throw new ChamomileException(ChamomileExceptionCode.USER_NOT_FOUND);
    }

    public void sendVerificationCodeEmail(String userId,String email,LocalDateTime requestTime) throws ChamomileException {

        Random random = new Random();

        // 100000부터 999999 사이의 랜덤 숫자를 생성
        int sixDigitNumber = 100000 + random.nextInt(900000);
        String token = ""+sixDigitNumber;
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUserId(userId);
        verificationToken.setEmail(email);
        verificationToken.setExpiryDate(requestTime);

        tokenRepository.save(token,verificationToken);

        MailVo mailVo = new MailVo.MailVoBuilder()
                .setFrom(mailFromAddress)
                .setCharset("UTF-8")
                .setSubject("이메일 인증 코드 발송 안내")
                .setMsg("인증 코드: " + token)
                .addTo(email)
                .build();
        mailUtil.send(new MailVo[]{mailVo});
    }

    public void checkVerifyCode(String token) throws ChamomileException{
        VerificationToken verificationToken = tokenRepository.findByToken(token);

        if (verificationToken == null) {
            throw new ChamomileException(ChamomileExceptionCode.VERIFICATION_CODE_NOT_FOUND).setHttpStatus(200);
        }
    }

    public String checkVerifyCode(String email, String token, LocalDateTime requestTime) throws ChamomileException{
        VerificationToken verificationToken = tokenRepository.findByToken(token);

        if (verificationToken == null || !email.equals(verificationToken.getEmail())) {
            throw new ChamomileException(ChamomileExceptionCode.VERIFICATION_CODE_NOT_FOUND).setHttpStatus(200);
        }

        if(!verificationToken.getExpiryDate().isBefore(requestTime)) {
            throw new ChamomileException(ChamomileExceptionCode.VERIFICATION_CODE_EXPIRED).setHttpStatus(200);
        }
        return verificationToken.getUserId();
    }

    public String sendTemporaryPassword(String email) {
        String password = UUID.randomUUID().toString().substring(0,10);

        MailVo mailVo = new MailVo.MailVoBuilder()
                .setFrom(mailFromAddress)
                .setCharset("UTF-8")
                .setSubject("임시 비밀번호 발송 안내")
                .setMsg("임시 비밀번호: " + password)
                .addTo(email)
                .build();
        mailUtil.send(new MailVo[]{mailVo});

        return password;
    }
}
