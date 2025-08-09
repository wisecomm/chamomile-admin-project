package net.lotte.chamomile.admin.board.domain;

import lombok.Builder;
import lombok.Data;

/**
 * <pre>
 * 게시글 관련 데이터(유저 ID, 게시글 이미지 url 등) 매핑 DTO 도메인 객체.
 * </pre>
 *
 * @author chaelynJang
 * @since 2024-02-21
 * @version 3.0
 * @Modification
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2024-02-21    chaelynJang            최초 생성
 *
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 */
@Data
@Builder
public class BoardMapDTO {
    private Long boardId;
    private String userId;
    private String imageUrl;
}
