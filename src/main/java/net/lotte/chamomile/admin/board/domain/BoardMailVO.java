package net.lotte.chamomile.admin.board.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * <pre>
 * 게시글 메일 관련 VO 도메인 객체.
 * </pre>
 *
 * @author chaelynJang
 * @since 2023-11-16
 * @version 3.0
 * @Modification
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023-11-16     chaelynJang            최초 생성
 *
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 */
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class BoardMailVO {
    private List<String> userIdList;
    //private String[] email; /* email 주소들 */
    private String title; /* email 제목 */
    private String body; /* email 본문 */
}
