package net.lotte.chamomile.admin.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import net.lotte.chamomile.module.database.audit.TimeAuthorLog;
import net.lotte.chamomile.module.logging.annotation.PrivacyDTO;

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
public class UserExcelUploadVO extends TimeAuthorLog implements PrivacyDTO {
    private String userId; /* 사용자 ID */
    private String userEmail; /* 사용자 이메일 */
    private String userMobile; /* 사용자 휴대폰 번호 */
    private String userName; /* 사용자 이름 */
    private String userNick; /* 사용자 닉네임 */
    private String userPwd; /* 사용자 비밀번호 */ // NOTE: 패스워드에 대한 자릿수 체크는 여기에서 안한다.
    private String userMsg; /* 사용자 메시지 */
    private String userDesc; /* 사용자 설명 */
    private String userStatCd; /* 사용자 상태 코드 */ //'업무', '휴가', '자리비움'
    private String useYn;
    private String userSnsId; /* 사용자 SNS ID */
    private String accountStartDt; /* 계정 시작일 */
    private String accountEndDt; /* 계정 종료일 */
    private String passwordExpireDt; /* 비밀번호 만료일 */

    @Override
    public String getPrivacyUserID() {
        return this.userId;
    }

    @Override
    public String getPrivacyFieldList() {
        return "이름, 이메일, 닉네임";
    }
}
