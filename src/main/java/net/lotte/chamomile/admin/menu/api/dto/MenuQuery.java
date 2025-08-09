package net.lotte.chamomile.admin.menu.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 메뉴 관련 HTTP READ 요청 객체.
 * </pre>
 *
 * @author chaelynJang
 * @version 3.0
 * @Modification <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023-09-26     chaelynJang            최초 생성
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 * @since 2023-09-26
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuQuery {
    private Integer searchMenuType;
    private String searchMenu;
    private String searchUseYn;
    private String searchUpperMenuId;
    private String searchMenuHelpUri;

    public MenuQuery(Integer searchMenuType, String searchMenu, String searchMenuUri, String searchUseYn) {
        this.searchMenuType = searchMenuType;
        this.searchMenu = searchMenu;
        this.searchUseYn = searchUseYn;
    }

}
