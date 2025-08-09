package net.lotte.chamomile.admin.authhierarchy.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


/**
 * <pre>
 * 권한 상하 관계 관련 VO 도메인 객체.
 * </pre>
 *
 * @author TaehoPark
 * @since 2023-10-06
 * @version 3.0
 * @Modification
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023-10-06     TaehoPark            최초 생성
 *
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 */
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class AuthHierarchyExcelUploadVO {
    /** 케모마일 권한 정보 **/
    private String parentRoleId;
    private String childRoleId; /* 하위 Role ID */

}
