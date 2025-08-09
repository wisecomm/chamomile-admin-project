package net.lotte.chamomile.admin.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import net.lotte.chamomile.module.database.audit.TimeAuthorLog;


/**
 * <pre>
 * 권한 관련 VO 도메인 객체.
 * </pre>
 *
 * @author TaehoPark
 * @since 2023-09-20
 * @version 3.0
 * @Modification
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023-09-20     TaehoPark            최초 생성
 *
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 */
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class AuthExcelUploadVO extends TimeAuthorLog {
    /** 케모마일 권한 정보 **/
    private String roleId;    //권한 아이디
    private String roleName;  //권한 명
    private String roleDesc;  //권한 설명
    private String roleStartDt;//권한 시작일자
    private String roleEndDt;  //권한 종료일자
    private String useYn; /* 사용 여부 */

}
