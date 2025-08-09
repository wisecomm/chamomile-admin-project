package net.lotte.chamomile.admin.board.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 게시글 관련 HTTP READ 요청 객체.
 * </pre>
 *
 * @author chaelynJang
 * @version 3.0
 * @Modification <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023-11-09     chaelynJang            최초 생성
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 * @since 2023-11-09
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardQuery {
    private String boardType;
    private String searchType;
    private String searchValue;
    private String searchUseYn;
    private String applyStartDtm;
    private String applyEndDtm;
    private String insertStartDtm;
    private String insertEndDtm;

    // private String title;
    // private String sysInsertUserId;
}
