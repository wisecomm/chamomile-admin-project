package net.lotte.chamomile.admin.board.api;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import net.lotte.chamomile.admin.board.api.dto.BoardQuery;
import net.lotte.chamomile.admin.board.domain.BoardMailVO;
import net.lotte.chamomile.admin.board.domain.BoardUserVO;
import net.lotte.chamomile.admin.board.domain.BoardVO;
import net.lotte.chamomile.module.web.variable.ChamomileResponse;

/**
 * <pre>
 * Admin 게시글 관련 REST API Swagger Doc.
 * </pre>
 *
 * @ClassName   : BoardControllerDoc.java
 * @Description : Admin 게시글 관련(CRUD 등) REST API Swagger Doc.
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
@Tag(name = "캐모마일 어드민 게시글 API")
public interface BoardControllerDoc {
    @Operation(summary = "게시글 목록 요청", description = "게시글 목록을 조회합니다.")
    ChamomileResponse<Page<BoardVO>> getBoardList(BoardQuery request, Pageable pageable);

    @Operation(summary = "게시글 상세 조회 요청", description = "게시글 상세를 조회합니다.")
    public ChamomileResponse<BoardVO> getBoardDetail(String boardId);

    @Operation(summary = "게시글 생성 요청", description = "게시글을 생성합니다.")
    ChamomileResponse<Void> createBoard(BoardVO request);

    @Operation(summary = "게시글 사용자 검색 요청", description = "게시글 사용자를 검색합니다.")
    ChamomileResponse<Page<BoardUserVO>> getUserList(String boardId);

    @Operation(summary = "게시글 삭제 요청", description = "게시글을 삭제합니다.")
    ChamomileResponse<Void> deleteBoard(Long boardId);

    @Operation(summary = "게시글 수정 요청", description = "게시글을 수정합니다.")
    ChamomileResponse<Void> updateBoard(BoardVO request);

    @Operation(summary = "이메일 전송 요청", description = "게시글 알림/이메일을 받는 사람들에게 이메일을 전송합니다.")
    ChamomileResponse<Void> sendEmail(BoardMailVO request);

    @Operation(summary = "메인 화면 알림 조회 요청", description = "메인 화면 알림 게시글 목록을 조회합니다.")
    ChamomileResponse<List<BoardVO>> getNoticeBoardList();

    @Operation(summary = "게시물 이미지 업로드 요청", description = "게시물 이미지를 업로드 합니다.")
    ChamomileResponse<String> imageUpload(MultipartFile file);

    @Operation(summary = "게시물 이미지 다운로드 요청", description = "게시물 이미지를 다운로드 합니다.")
    public ResponseEntity<byte[]> downloadImage(String code, String fileName);

    @Operation(summary = "게시물 수정시 이미지 삭제 요청", description = "게시물 수정 시, 기존 이미지를 삭제합니다.")
    public ChamomileResponse<Void> removeBoardImages(Long boardId, List<String> removeImageList);

    @Operation(summary = "이미지 url 기반 삭제 요청", description = "이미지 url 정보로 이미지 파일을 삭제합니다.")
    ChamomileResponse<Void> removeImages(List<String> imageUrlList);
}
