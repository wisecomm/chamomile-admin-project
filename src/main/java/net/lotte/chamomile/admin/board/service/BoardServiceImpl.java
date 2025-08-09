package net.lotte.chamomile.admin.board.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import net.lotte.chamomile.admin.board.api.dto.BoardQuery;
import net.lotte.chamomile.admin.board.domain.BoardMailVO;
import net.lotte.chamomile.admin.board.domain.BoardMapDTO;
import net.lotte.chamomile.admin.board.domain.BoardMapper;
import net.lotte.chamomile.admin.board.domain.BoardUserVO;
import net.lotte.chamomile.admin.board.domain.BoardVO;
import net.lotte.chamomile.admin.common.exception.code.AdminExceptionCode;
import net.lotte.chamomile.admin.user.api.dto.UserQuery;
import net.lotte.chamomile.admin.user.domain.UserMapper;
import net.lotte.chamomile.admin.user.domain.UserVO;
import net.lotte.chamomile.boot.configure.file.ChamomileFileProperties;
import net.lotte.chamomile.core.exception.BusinessException;
import net.lotte.chamomile.core.exception.ChamomileException;
import net.lotte.chamomile.core.exception.ChamomileExceptionCode;
import net.lotte.chamomile.module.file.util.FileUtil;
import net.lotte.chamomile.module.notification.mail.MailUtil;
import net.lotte.chamomile.module.notification.mail.MailVo;
import net.lotte.chamomile.module.util.string.StringUtil;

/**
 * <pre>
 * 게시글 관련 서비스 인터페이스 구현체.
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
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardMapper boardMapper;
    private final UserMapper userMapper;
    private final MailUtil mailUtil;
    private final DateTimeFormatter yyyyMMddFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    private final DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final ChamomileFileProperties chamomileFileProperties;

    @Override
    @Transactional(readOnly = true)
    public Page<BoardVO> getBoardList(BoardQuery request, Pageable pageable) {
        if (StringUtil.isNotBlank(request.getApplyStartDtm())) {
            LocalDate applyStartDtm = LocalDate.parse(request.getApplyStartDtm(), yyyyMMddFormatter);
            request.setApplyStartDtm(applyStartDtm.format(dtFormatter) + " 00:00");
        }
        if (StringUtil.isNotBlank(request.getApplyEndDtm())) {
            LocalDate applyEndDtm = LocalDate.parse(request.getApplyEndDtm(), yyyyMMddFormatter);
            request.setApplyEndDtm(applyEndDtm.plusDays(1).format(dtFormatter) + " 00:00");
        }
        if (StringUtil.isNotBlank(request.getInsertStartDtm())) {
            LocalDate insertStartDtm = LocalDate.parse(request.getInsertStartDtm(), yyyyMMddFormatter);
            request.setInsertStartDtm(insertStartDtm.format(dtFormatter) + " 00:00:00");
        }
        if (StringUtil.isNotBlank(request.getInsertEndDtm())) {
            LocalDate insertEndDtm = LocalDate.parse(request.getInsertEndDtm(), yyyyMMddFormatter);
            request.setInsertEndDtm(insertEndDtm.plusDays(1).format(dtFormatter) + " 00:00:00");
        }

        return boardMapper.findBoardList(request, pageable);
    }

    @Override
    public BoardVO getBoardDetail(String boardId) {
        // 조회수 증가
        boardMapper.updateBoardViewCnt(boardId);
        List<String> userIdList = boardMapper.findBoardUser(boardId);
        BoardVO board = boardMapper.findBoardDetail(boardId);
        if(!CollectionUtils.isEmpty(userIdList)) {
            board.setUserIdList(userIdList);
        }
        List<String> boardImageList = boardMapper.selectBoardImages(Long.parseLong(boardId));
        if(!CollectionUtils.isEmpty(boardImageList)) {
            board.setImageUrlList(boardImageList);
        }
        return board;
    }

    @Override
    public void createBoard(BoardVO request) {
        if(ObjectUtils.isEmpty(request)) {
            throw new BusinessException(AdminExceptionCode.ServerError);
        }
        request.onCreate();
        boardMapper.insertBoard(request);
        // 게시글 메일 알림 및 메일 받는 사람
        List<String> userIdList = request.getUserIdList();
        if(!CollectionUtils.isEmpty(userIdList)) {
            addBoardUserMap(request, userIdList);
        }
        List<String> imageUrlList = request.getImageUrlList();
        if(!CollectionUtils.isEmpty(imageUrlList)) {
            addBoardImageList(request, imageUrlList);
        }
    }

    @Override
    public void deleteBoard(Long boardId) {
        // 게시글 받는 사람 삭제
        boardMapper.deleteBoardUserMap(boardId);
        List<String> boardImageUrlList = boardMapper.selectBoardImages(boardId);
        if(!CollectionUtils.isEmpty(boardImageUrlList)) {
            // 게시글 이미지 정보 삭제
            removeImages(boardImageUrlList);
            boardMapper.deleteBoardImageMap(boardId);
        }
        // 게시글 삭제
        boardMapper.deleteBoard(boardId);
    }

    @Transactional
    @Override
    public void deleteBoards(List<BoardVO> boards) {
        boards.forEach(board -> deleteBoard(board.getBoardId()));
    }

    @Override
    public void updateBoard(BoardVO request) {
        request.onUpdate();
        boardMapper.updateBoard(request);
        // 게시글 받는 사람 삭제
        boardMapper.deleteBoardUserMap(request.getBoardId());
        List<String> userIdList = request.getUserIdList();
        if(!CollectionUtils.isEmpty(userIdList)) {
            addBoardUserMap(request, userIdList);
        }
        List<String> imageUrlList = request.getImageUrlList();
        if(!CollectionUtils.isEmpty(imageUrlList)) {
            addBoardImageList(request, imageUrlList);
        }
    }

    @Override
    public void sendEmail(BoardMailVO request) {
        String userId = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        UserQuery userQuery = new UserQuery();
        userQuery.setUserId(userId);
        UserVO userVO = userMapper.findUserDetail(userQuery)
                .orElseThrow(() -> new ChamomileException(ChamomileExceptionCode.EMAIL_SEND_ERROR));

        List<String> userIdList = request.getUserIdList();
        List<String> userEmailList = boardMapper.findUserEmail(userIdList);

        MailVo mailVo = new MailVo.MailVoBuilder()
                .setFrom(userVO.getUserEmail())
                .setCharset("UTF-8")
                .setSubject(request.getTitle())
                .setMsg(request.getBody())
                .addTo(userEmailList.toArray(new String[userEmailList.size()]))
                .build();

        mailUtil.send(mailVo);
    }

    @Override
    public Page<BoardVO> getNoticeBoardList(Pageable pageable) {
        String currentDtm = LocalDate.now().format(dtFormatter) + " 00:00";
        return boardMapper.findNoticeBoardList(currentDtm, pageable);
    }

    @Override
    public void removeBoardImages(Long boardId, List<String> removeImageList) {
        if(!CollectionUtils.isEmpty(removeImageList)) {
            // 게시글 이미지 정보 삭제
            removeImages(removeImageList);
            List<BoardMapDTO> list = getBoardImageMapList(boardId, removeImageList);
            boardMapper.deleteBoardImages(list);
        }
    }

    @Override
    public void removeImages(List<String> imageUrlList) {
        for (String imageUrl : imageUrlList) {
            FileUtil.removeFile(chamomileFileProperties.getRepositoryPath() + "/" + imageUrl.split("/")[0]); // --> /files/해시코드
        }
    }

    @Override
    public Page<BoardUserVO> getUserList(String boardId, Pageable pageable) {
        return boardMapper.findUserList(boardId, pageable);
    }

    private void addBoardUserMap(BoardVO request, List<String> userIdList) {
        List<BoardMapDTO> list = new ArrayList<>();
        long boardId = request.getBoardId();
        for(String userId : userIdList) {
            BoardMapDTO boardUserMapDto = BoardMapDTO.builder()
                    .boardId(boardId)
                    .userId(userId)
                    .build();
            list.add(boardUserMapDto);
        }
        boardMapper.insertBoardUserMap(list);
    }

    private void addBoardImageList(BoardVO request, List<String> imageUrlList) {
        List<BoardMapDTO> list = getBoardImageMapList(request.getBoardId(), imageUrlList);
        boardMapper.insertBoardImage(list);
    }

    private static List<BoardMapDTO> getBoardImageMapList(long boardId, List<String> imageUrlList) {
        List<BoardMapDTO> list = new ArrayList<>();
        for(String imageUrl : imageUrlList) {
            BoardMapDTO boardUserMapDto = BoardMapDTO.builder()
                    .boardId(boardId)
                    .imageUrl(imageUrl)
                    .build();
            list.add(boardUserMapDto);
        }
        return list;
    }
}
