package net.lotte.chamomile.admin.board.domain;

import lombok.Builder;
import lombok.Data;

/**
 * <pre>
 * 게시글 받는 사람 사용자 데이터 DTO 도메인 객체.
 * </pre>
 *
 * @author chaelynJang
 * @since 2024-03-29
 * @version 3.1
 * @Modification
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2024-03-29    chaelynJang            최초 생성
 *
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 */
@Data
@Builder
public class BoardUserVO {
    private String userId;
    private String userName;
    private String useYn;
}
