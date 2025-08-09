package net.lotte.chamomile.admin.user.api.dto;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class RegisterUserDTO {
    @NotBlank
    @Size(min = 5, max = 50)
    @Schema(description = "사용자 ID", example = "admin")
    @Column(name = "USER_ID", nullable = false)
    private String userId; /* 사용자 ID */
    @Size(max = 255)
    @Email
    @NotBlank
    @Schema(description = "사용자 이메일", example = "test@naver.com")
    private String userEmail; /* 사용자 이메일 */
    @Size(max = 14)
    //@Pattern(regexp = "^$|^[0-9]{10,11}$", message = "휴대폰 번호 형식이 아닙니다.")
    @Schema(description = "사용자 휴대폰 번호", example = "010-1111-2222")
    private String userMobile; /* 사용자 휴대폰 번호 */
    @Size(max = 100)
    @NotBlank
    @Schema(description = "사용자 이름", example = "홍길동")
    private String userName; /* 사용자 이름 */

    @ToString.Exclude
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String userPwd; /* 사용자 비밀번호 */ // NOTE: 패스워드에 대한 자릿수 체크는 여기에서 안한다.
    private String verifyCode; /* 인증 코드 */
}
