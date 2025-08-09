package net.lotte.chamomile.admin.user.domain;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import net.lotte.chamomile.module.database.audit.TimeAuthorLog;
import net.lotte.chamomile.module.database.history.annotation.MaskingColumn;
import net.lotte.chamomile.module.database.history.annotation.PrimaryKey;
import net.lotte.chamomile.module.logging.annotation.PrivacyDTO;
import net.lotte.chamomile.module.mask.MaskingType;
import net.lotte.chamomile.module.mask.PatternMasking;

/**
 * <pre>
 * 사용자 관련 VO 도메인 객체.
 * </pre>
 *
 * @author TaehoPark
 * @version 3.0
 * @Modification <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023-09-20     TaehoPark            최초 생성
 *
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 * @since 2023-09-20
 */
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class UserVO extends TimeAuthorLog implements PrivacyDTO {
    @NotBlank
    @Size(min = 5, max = 50)
    @Schema(description = "사용자 ID", example = "admin")
    @Column(name = "USER_ID", nullable = false)
    @PrimaryKey(column = "USER_ID")
    private String userId; /* 사용자 ID */
    @Size(max = 255)
    @Email
    @NotBlank
    @Schema(description = "사용자 이메일", example = "test@naver.com")
    @PatternMasking(type = MaskingType.EMAIL)
    @MaskingColumn(column = "USER_EMAIL")
    private String userEmail; /* 사용자 이메일 */
    @Size(max = 14)
    //@Pattern(regexp = "^$|^[0-9]{10,11}$", message = "휴대폰 번호 형식이 아닙니다.")
    @Schema(description = "사용자 휴대폰 번호", example = "010-1111-2222")
    @PatternMasking(type = MaskingType.PHONE_NUMBER)
    @MaskingColumn(column = "USER_MOBILE")
    private String userMobile; /* 사용자 휴대폰 번호 */
    @Size(max = 100)
    @NotBlank
    @Schema(description = "사용자 이름", example = "홍길동")
    @PatternMasking(type = MaskingType.NAME)
    @MaskingColumn(column = "USER_NAME")
    private String userName; /* 사용자 이름 */
    @Size(max = 255)
    @Schema(description = "사용자 닉네임", example = "홍길동")
    private String userNick; /* 사용자 닉네임 */

    @ToString.Exclude
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String userPwd; /* 사용자 비밀번호 */ // NOTE: 패스워드에 대한 자릿수 체크는 여기에서 안한다.
    private String userPrevPwd; /* 기존 비밀번호 */
    private String userPwdCheck; /* 신규 비밀번호 */

    @Schema(description = "사용자 메시지", example = " ")
    private String userMsg; /* 사용자 메시지 */
    @Size(max = 255)
    @Schema(description = "사용자 설명", example = " ")
    private String userDesc; /* 사용자 설명 */
    @Size(max = 16)
    @Schema(description = "사용자 상태", example = "업무")
    private String userStatCd; /* 사용자 상태 코드 */ //'업무', '휴가', '자리비움'
    @Size(max = 50)
    @Schema(description = "사용자 SNS ID", example = "test")
    private String userSnsId; /* 사용자 SNS ID */
    @Size(max = 1)
    @Schema(hidden = true)
    private String accountNonLock; /* 사용자계정잠김여부 */
    @NotBlank
    @Schema(description = "계정 시작일", example = "20000101")
    private String accountStartDt; /* 계정 시작일 */
    @NotBlank
    @Schema(description = "계정 종료일", example = "20300101")
    private String accountEndDt; /* 계정 종료일 */
    @NotBlank
    @Schema(description = "비밀번호 만료일", example = "20300101")
    private String passwordExpireDt; /* 비밀번호 만료일 */
    @Pattern(regexp = "^(0|1)$", message = "참/거짓은 0/1로 구분 합니다.")
    private String useYn; /* 사용 여부 */
    private String flag;
    private String pwChange;
    private Boolean valid;
    @Schema(description = "사용자 그룹")
    private String userGroups;  /* 사용자 그룹 */

    // 모바일 기능 추가
    @Schema(description = "보유단말수")
    private String userDeviceCount;  /* 보유단말수*/
    @Schema(description = "기기관리여부")
    private String mdmYn;  /* 기기관리여부 */

    @Override
    public String getPrivacyUserID() {
        return this.userId;
    }

    @Override
    public String getPrivacyFieldList() {
        return "이메일, 이름, 닉네임";
    }
}
