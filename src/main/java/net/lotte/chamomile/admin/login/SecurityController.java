package net.lotte.chamomile.admin.login;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PSource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.lotte.chamomile.admin.common.exception.code.AdminExceptionCode;
import net.lotte.chamomile.admin.user.api.dto.UserQuery;
import net.lotte.chamomile.admin.user.domain.UserVO;
import net.lotte.chamomile.admin.user.service.UserService;
import net.lotte.chamomile.core.exception.BusinessException;
import net.lotte.chamomile.core.exception.ChamomileException;
import net.lotte.chamomile.core.exception.ChamomileExceptionCode;
import net.lotte.chamomile.module.logging.LoggingUtils;
import net.lotte.chamomile.module.logging.type.UserAccessActionType;
import net.lotte.chamomile.module.security.SecurityService;
import net.lotte.chamomile.module.security.jwt.vo.JwtRequest;
import net.lotte.chamomile.module.security.login.JdbcLoginService;
import net.lotte.chamomile.module.security.password.decoder.PasswordDecoder;
import net.lotte.chamomile.module.security.userdetails.DefaultUser;
import net.lotte.chamomile.module.security.userdetails.JdbcUserDetailsService;
import net.lotte.chamomile.module.web.variable.ChamomileResponse;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/chmm/security")
public class SecurityController implements SecurityControllerDoc {

    private final SecurityService securityService;
    private final UserService userService;
    private final JdbcUserDetailsService jdbcUserDetailsService;
    private final JdbcLoginService jdbcLoginService;
    private final PasswordEncoder passwordEncoder;
    private final PasswordDecoder passwordDecoder;

    @PostMapping("/pre-login")
    public ChamomileResponse preLogin(@RequestBody JwtRequest jwtRequest) throws Exception {
        authenticate(jwtRequest.getUsername(),passwordDecoder.decrypt(jwtRequest.getPassword()));
        UserQuery userQuery = new UserQuery();
        userQuery.setUserId(jwtRequest.getUsername());
        UserVO userVO =  userService.getUserDetail(userQuery);
        return new ChamomileResponse<>(userVO);
    }


    @PostMapping("/jwt/authenticate")
    public ChamomileResponse<Map<String, String>> authenticate(@RequestBody JwtRequest jwtRequest) throws Exception {
        return new ChamomileResponse<>();
    }

    @PostMapping("/jwt/refresh-token")
    public ChamomileResponse<Map<String, String>> refreshToken(HttpServletRequest request) throws Exception {
        return new ChamomileResponse<>();
    }

    @GetMapping("/jwt/secret-key")
    public ChamomileResponse<String> secretKey() throws InvalidKeySpecException, NoSuchAlgorithmException {

        SecureRandom secureRandom = new SecureRandom(); // 스레드 안전한 랜덤 생성기
        Base64.Encoder base64Encoder = Base64.getUrlEncoder(); // URL 안전한 인코딩에 사용
        String originalPassword = generateKey(32, secureRandom, base64Encoder);
        byte[] salt = new byte[16]; // You should use a unique and secure salt for each user!

        // Generate derived key
        KeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, 65536, 512);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] derivedKey = factory.generateSecret(spec).getEncoded();

        if (StringUtils.isNotBlank(Base64.getEncoder().encodeToString(derivedKey))) {
            return new ChamomileResponse<>(Base64.getEncoder().encodeToString(derivedKey));
        }
        return new ChamomileResponse<>(ChamomileExceptionCode.SERVER_ERROR, "");
    }

    public static String generateKey(int length, SecureRandom secureRandom, Base64.Encoder base64Encoder) {
        byte[] randomBytes = new byte[length];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
    @GetMapping("/jwt/rsa-password")
    public ChamomileResponse<String> encrypt(String plainText) throws Exception {
        String encryptedText = null;
        String publicKeyConfig = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA7n9T9y2rflLzbudMjm+PslBSQTGGCPaySE5ayghbr6lRZ4QDA+WTf2SDxjBWh91J7C2NkTQ6FSHHxdFemvHBIKZtACNOBgsQaO7Q0Sh5nrl34XSVrq53hMIHC7OmP98Wqjqdz6c6BQFicyefGZffOtyG4eBjWtu0oAd0/wW902X0sVnzn/g30V6r1IvvE3ov8Nq49W2F7g1goh1IMPtay2lSvc1259vviV1ONOsqyzmrDY+q9kkEW6bXckZH53Soihhgf+bUA36qagV7EbQKorNwNHYyICHZRRxiFem7Y/vlYWIOamJNSdlPHRdl4epAPMHDVvRxGpteNWkVbr3ybQIDAQAB";

        try {
            // 평문으로 전달받은 공개키를 사용하기 위해 공개키 객체 생성
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] bytePublicKey = Base64.getDecoder().decode(publicKeyConfig.getBytes());
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(bytePublicKey);
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

            // 만들어진 공개키 객체로 암호화 설정
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
            OAEPParameterSpec oaepParameterSpecJCE = new OAEPParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey, oaepParameterSpecJCE);

            // 평문을 암호문으로 변환하는 과정
            byte[] plainBytes = plainText.getBytes();
            byte[] encryptedBytes = cipher.doFinal(plainBytes);
            encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new BusinessException(AdminExceptionCode.ServerError);
        }
        return new ChamomileResponse<>(encryptedText);
    }

    public void authenticate(String loginId,String password) throws AuthenticationException {
            try {

                LoggingUtils.userAccessLogging(loginId, UserAccessActionType.LOGIN_ATTEMPT);
                // 입력받은 userID로 사용자 정보(UserDetails)를 조회하여 반환한다. (사용자정보, 사용자권한 사용자그룹권한)
                DefaultUser userDetails = (DefaultUser) jdbcUserDetailsService.loadUserByUsername(loginId);

                checkCredentials(password, userDetails);
                checkAccountStatus(userDetails);

                LoggingUtils.userAccessLogging(loginId, UserAccessActionType.PRE_LOGIN_SUCCESS);
            } catch (ChamomileException ex) {
                throw new ChamomileException((ex).getFrameworkCode(), ex.getMessage()).setHttpStatus(401);
            } catch (Exception ex) {
                throw new ChamomileException(ChamomileExceptionCode.UNAUTHORIZED, ex.getMessage(), ex).setHttpStatus(401);
            }
    }


    private void checkCredentials(String password, DefaultUser userDetails) throws Exception {
            // 비밀번호 체크
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                LoggingUtils.userAccessLogging(userDetails.getUsername(), UserAccessActionType.PASSWORD_MISMATCH);
                jdbcLoginService.lockProcessAccount(userDetails.getUsername());
                throw new ChamomileException(ChamomileExceptionCode.ID_OR_PASSWORD_NOT_MATCH);
            }
        }
        private void checkAccountStatus(DefaultUser userDetails) {
            // 계정 만료
            if (!userDetails.isAccountNonExpired()) {
                LoggingUtils.userAccessLogging(userDetails.getUsername(), UserAccessActionType.ACCOUNT_EXPIRED);
                throw new ChamomileException(ChamomileExceptionCode.USER_EXPIRED);
            }

            if (!userDetails.isAccountNonLocked()) {
                LoggingUtils.userAccessLogging(userDetails.getUsername(), UserAccessActionType.ACCOUNT_LOCKED);
                throw new ChamomileException(ChamomileExceptionCode.USER_LOCKED);
            }

            if (!userDetails.isCredentialsNonExpired()) {
                LoggingUtils.userAccessLogging(userDetails.getUsername(), UserAccessActionType.ACCOUNT_DISABLED);
                throw new ChamomileException(ChamomileExceptionCode.USER_PASSWORD_EXPIRED);
            }

            if (!userDetails.isEnabled()) {
                LoggingUtils.userAccessLogging(userDetails.getUsername(), UserAccessActionType.CREDENTIALS_EXPIRED);
                throw new ChamomileException(ChamomileExceptionCode.USER_AUTHENTICATION);
            }
    }

}

