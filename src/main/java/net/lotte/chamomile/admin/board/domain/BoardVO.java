package net.lotte.chamomile.admin.board.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import net.lotte.chamomile.module.database.audit.TimeAuthorLog;

/**
 * <pre>
 * Admin 게시글 관리 REST API Mybatis CHMM_BOARD_INFO(게시물 테이블) ReturnType.
 * </pre>
 *
 * @ClassName   : BoardVO.java
 * @Description : Admin 게시글 관리 REST API Mybatis CHMM_BOARD_INFO(게시물 테이블) ReturnType.
 * @author chaelynJang
 * @since 2023.11.09
 * @version 3.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023.11.09     chaelynJang            최초 생성
 *
 *
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BoardVO extends TimeAuthorLog {
    private Long boardId;
    private String title;
    private String boardType;
    private String mailYn; // 메일발송여부
    private String contents;
    private String noticeYn; // 대시보드 노출여부
    private String publicYn; // (미사용)
    private String applyStartDtm;
    private String applyEndDtm;
    private Long viewCnt;
    private List<String> userIdList;
    private List<String> imageUrlList;
}
