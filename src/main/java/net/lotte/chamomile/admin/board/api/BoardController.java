package net.lotte.chamomile.admin.board.api;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.lotte.chamomile.admin.board.api.dto.BoardQuery;
import net.lotte.chamomile.admin.board.domain.BoardMailVO;
import net.lotte.chamomile.admin.board.domain.BoardUserVO;
import net.lotte.chamomile.admin.board.domain.BoardVO;
import net.lotte.chamomile.admin.board.service.BoardService;
import net.lotte.chamomile.boot.configure.file.ChamomileFileProperties;
import net.lotte.chamomile.module.file.entity.FileMetadataInfoEntity;
import net.lotte.chamomile.module.file.util.FileDownloader;
import net.lotte.chamomile.module.file.util.FileUploader;
import net.lotte.chamomile.module.security.jwt.util.JwtTokenUtils;
import net.lotte.chamomile.module.web.variable.ChamomileResponse;

/**
 * <pre>
 * Admin 게시글 관련 REST API Controller.
 * </pre>
 *
 * @ClassName   : BoardController.java
 * @Description : Admin 게시글 관련(CRUD 등) REST API Controller.
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
@Slf4j
@RestController
@RequestMapping("/chmm/board")
@RequiredArgsConstructor
public class BoardController implements BoardControllerDoc {

    private final BoardService boardService;
    private final FileDownloader fileDownloader;
    private final JwtTokenUtils jwtTokenUtils;
    private final ChamomileFileProperties fileProperties;


    @GetMapping("/list")
    public ChamomileResponse<Page<BoardVO>> getBoardList(BoardQuery request, Pageable pageable) {
        return new ChamomileResponse<>(boardService.getBoardList(request, pageable));
    }

    @GetMapping("/detail/{boardId}")
    public ChamomileResponse<BoardVO> getBoardDetail(@PathVariable String boardId) {
        return new ChamomileResponse<>(boardService.getBoardDetail(boardId));
    }

    @PostMapping("/create")
    public ChamomileResponse<Void> createBoard(@Validated @RequestBody BoardVO request) {
        boardService.createBoard(request);
        return new ChamomileResponse<>();
    }

    @GetMapping("/user/list")
    public ChamomileResponse<Page<BoardUserVO>> getUserList(@RequestParam String boardId) {
        return new ChamomileResponse<>(boardService.getUserList(boardId, PageRequest.of(0, Integer.MAX_VALUE)));
    }

    @PostMapping("/delete/{boardId}")
    public ChamomileResponse<Void> deleteBoard(@PathVariable Long boardId) {
        boardService.deleteBoard(boardId);
        return new ChamomileResponse<>();
    }

    @PostMapping("/delete")
    public ChamomileResponse<Void> deleteBoards(@RequestBody List<BoardVO> request) {
        boardService.deleteBoards(request);
        return new ChamomileResponse<>();
    }

    @PostMapping("/update")
    public ChamomileResponse<Void> updateBoard(@RequestBody BoardVO request) {
        boardService.updateBoard(request);
        return new ChamomileResponse<>();
    }

    @PostMapping("/send-email")
    public ChamomileResponse<Void> sendEmail(@RequestBody BoardMailVO request) {
        boardService.sendEmail(request);
        return new ChamomileResponse<>();
    }

    @GetMapping("/notice/list")
    public ChamomileResponse<List<BoardVO>> getNoticeBoardList() {
        return new ChamomileResponse<>(boardService.getNoticeBoardList(PageRequest.of(0, 4)).getContent());
    }

    @PostMapping(value = "/image/upload", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ChamomileResponse<String> imageUpload(@RequestParam MultipartFile file) {
        FileUploader fileUploader = new FileUploader(fileProperties.getRepositoryPath(),
                "jpg,jpeg,png",fileProperties.getMaxSize(),fileProperties.getDirectoryDateNameFormat());
        FileMetadataInfoEntity uploadedFileVo = fileUploader.fileUpload(file);
        String imageUrl = uploadedFileVo.getFileMetaDataCode();
        return new ChamomileResponse<>(imageUrl);
    }

    @GetMapping("/image/download/{code}/{fileName}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable String code, @PathVariable String fileName) {
        return fileDownloader.fileDownload(code, fileName);
    }

    @GetMapping("/image/download/auth/{code}/{fileName}")
    public ResponseEntity<byte[]> downloadImageAuth(@PathVariable String code, @PathVariable String fileName, @CookieValue String token) {
        jwtTokenUtils.validateToken(token);
        return fileDownloader.fileDownload(code, fileName);
    }

    @PostMapping("/image/remove/{boardId}")
    public ChamomileResponse<Void> removeBoardImages(@PathVariable Long boardId, @RequestBody List<String> removeImageList) {
        boardService.removeBoardImages(boardId, removeImageList);
        return new ChamomileResponse<>();
    }

    @PostMapping("/image/remove")
    public ChamomileResponse<Void> removeImages(@RequestBody List<String> imageUrlList) {
        boardService.removeImages(imageUrlList);
        return new ChamomileResponse<>();
    }


}
