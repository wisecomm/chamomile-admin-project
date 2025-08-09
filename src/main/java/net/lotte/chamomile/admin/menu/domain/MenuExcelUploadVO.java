package net.lotte.chamomile.admin.menu.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <pre>
 * 메뉴 엑셀 업로드 관련 VO 도메인 객체.
 * </pre>
 *
 * @author chaelynJang
 * @version 3.0
 * @Modification <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023-10-18     chaelynJang            최초 생성
 *
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 * @since 2023-10-18
 */
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class MenuExcelUploadVO {
    private Integer menuLvl;
    private String upperMenuId;
    private String menuName;
    private String menuDesc;
    private Integer menuSeq;
    private String menuUri;
    private String adminMenuYn;
    private String leftMenuYn;
    private String useYn;
    private String menuHelpUri;
    private String menuScript;
    private String personalDataYn;
}
