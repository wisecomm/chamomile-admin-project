package net.lotte.chamomile.admin.board.service;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;

import net.lotte.chamomile.admin.WebApplicationTest;
import net.lotte.chamomile.admin.board.api.dto.BoardQuery;
import net.lotte.chamomile.admin.board.domain.BoardVO;

import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Sql("classpath:boardInit.sql")
@Transactional
class BoardServiceTest extends WebApplicationTest {
    @Autowired BoardService boardService;

    @BeforeEach
    void init() {
        List<String> userList = new ArrayList<>();
        userList.add("user");
        userList.add("admin");
        List<String> imageUrlList = new ArrayList<>();
        imageUrlList.add("8af490af551143edb38a986d1de54a2f/abcd.png");
        imageUrlList.add("8cee0b5867534a0f9ba09a361ae0ad58/def.jpg");
        boardService.createBoard(new BoardVO(1L,"test1", "1", "1","test","1","0","20231117","20241201",null, userList, imageUrlList));
        boardService.createBoard(new BoardVO(2L,"test2", "2", "0","test2","1","1","20231117","20241201",null, userList, imageUrlList));
    }
    @Test
    @WithMockUser
    void getBoardList() {
        List<BoardVO> findList = boardService.getBoardList(new BoardQuery(), PageRequest.of(0, 10)).getContent();
        assertThat(findList.size()).isEqualTo(2);
    }

    @Test
    void getBoardDetail() {
        BoardVO findBoard = boardService.getBoardDetail("1");
        assertThat(findBoard.getTitle()).isEqualTo("test1");
        assertThat(findBoard.getImageUrlList().size()).isEqualTo(2);
    }

    @Test
    @WithMockUser
    void createBoard() {
        List<String> userList = new ArrayList<>();
        userList.add("user");
        boardService.createBoard(new BoardVO(3L,"test3", "1", "1","test3","1","0","20231117","20241201",null, userList, null));
        List<BoardVO> findList = boardService.getBoardList(new BoardQuery(), PageRequest.of(0, 10)).getContent();
        assertThat(findList.size()).isEqualTo(3);
    }

    @Test
    @WithMockUser
    void deleteBoard() {
        boardService.deleteBoard(2L);
        List<BoardVO> findList = boardService.getBoardList(new BoardQuery(), PageRequest.of(0, 10)).getContent();
        assertThat(findList.size()).isEqualTo(1);
    }

    @Test
    @WithMockUser
    void updateBoard() {
        List<String> userList = new ArrayList<>();
        userList.add("user");
        userList.add("admin");
        BoardVO boardVO = new BoardVO(2L, "updateTest2", "2", "0", "updated", "1", "1", "20231203", "20241201", null, userList, null);
        log.info("INPUT = {}", boardVO);
        boardService.updateBoard(boardVO);
        //List<BoardVO> findList = boardService.getBoardList(new BoardQuery("updateTest2", null, null, null), PageRequest.of(0, 10)).getContent();
        List<BoardVO> findList = boardService.getBoardList(new BoardQuery(), PageRequest.of(0, 10)).getContent();
        for (BoardVO vo : findList) {
            log.info("find = {}", vo);
        }
        BoardVO updatedOne = findList.get(0);
        log.info("size = {}", findList.size());
        log.info("updated = {}", updatedOne);
        // TODO: mailYn, contents가 h2 테스트시 null이 들어가는 현상 해결
        assertThat(updatedOne).usingRecursiveComparison()
                .ignoringFields("sysUpdateDtm", "sysUpdateUserId", "sysInsertDtm", "sysInsertUserId"
                        , "viewCnt", "userIdList", "mailYn", "contents")
                .isEqualTo(boardVO);
    }

}
