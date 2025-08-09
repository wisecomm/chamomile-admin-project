package net.lotte.chamomile.admin.board.domain;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.lotte.chamomile.admin.board.api.dto.BoardQuery;

/**
 * <pre>
 * 게시글 관련 Mapper.
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
@Mapper
public interface BoardMapper {
    Page<BoardVO> findBoardList(BoardQuery request, Pageable pageable);

    void insertBoard(BoardVO request);

    void insertBoardUserMap(List<BoardMapDTO> list);

    void updateBoardViewCnt(String boardId);

    BoardVO findBoardDetail(String boardId);

    List<String> findBoardUser(String boardId);

    void deleteBoardUserMap(Long boardId);

    void deleteBoard(Long boardId);

    void updateBoard(BoardVO request);

    List<String> findUserEmail(List<String> list);

    Page<BoardVO> findNoticeBoardList(String currentDtm, Pageable pageable);

    void insertBoardImage(List<BoardMapDTO> list);

    void deleteBoardImageMap(Long boardId);

    List<String> selectBoardImages(Long boardId);

    void deleteBoardImages(List<BoardMapDTO> list);

    Page<BoardUserVO> findUserList(String boardId, Pageable pageable);
}
