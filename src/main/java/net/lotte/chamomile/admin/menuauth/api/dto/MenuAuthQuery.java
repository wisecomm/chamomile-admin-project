package net.lotte.chamomile.admin.menuauth.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * <pre>
 * 메뉴 권한 관리 HTTP READ 요청 객체.
 * </pre>
 *
 * @author chaelynJang
 * @version 3.0
 * @Modification <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023-10-16     chaelynJang            최초 생성
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 * @since 2023-10-16
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuAuthQuery {
    private String searchMenuId;
    private String searchRoleId;

    // /menu-auth/detail
    private String searchRole;
    private String searchRoleStartDt;
    private String searchRoleEndDt;
    private String searchStartDt;
    private String searchEndDt;
    private String searchUseYn;

    public MenuAuthQuery(String searchMenuId, String searchRoleId) {
        this.searchMenuId = searchMenuId;
        this.searchRoleId = searchRoleId;
    }
}
