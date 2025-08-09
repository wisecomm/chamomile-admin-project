package net.lotte.chamomile.admin.board.service;

import net.lotte.chamomile.admin.board.domain.BoardUserVO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.lotte.chamomile.admin.board.api.dto.BoardQuery;
import net.lotte.chamomile.admin.board.domain.BoardMailVO;
import net.lotte.chamomile.admin.board.domain.BoardVO;

import java.util.List;

/**
 * <pre>
 * 게시글 관련 서비스 인터페이스.
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
public interface BoardService {
    Page<BoardVO> getBoardList(BoardQuery request, Pageable pageable);

    BoardVO getBoardDetail(String boardId);

    void createBoard(BoardVO request);

    void deleteBoard(Long boardId);

    void deleteBoards(List<BoardVO> boards);

    void updateBoard(BoardVO request);

    void sendEmail(BoardMailVO request);

    Page<BoardVO> getNoticeBoardList(Pageable pageable);

    void removeBoardImages(Long boardId, List<String> removeImageList);

    void removeImages(List<String> imageUrlList);

    Page<BoardUserVO> getUserList(String boardId, Pageable pageable);
}
