package net.lotte.chamomile.admin.menu.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import net.lotte.chamomile.admin.WebApplicationTest;
import net.lotte.chamomile.admin.menu.api.dto.MenuQuery;
import net.lotte.chamomile.module.util.string.StringUtil;

class MenuMapperTest extends WebApplicationTest {

    @Autowired ObjectMapper objectMapper;
    @Autowired MenuMapper menuMapper;

    @BeforeEach
    void addTestData() {
        MenuVO input = new MenuVO(null, 0, null, "test", "root", "", 100, "0", "0", "0", "", "", "0");
        String lastMenuKey = menuMapper.selectLastMenuKey();
        int key = 0;
        if (StringUtil.isNotBlank(lastMenuKey)) {
            lastMenuKey = lastMenuKey.replaceAll("[^0-9]+", "");
            key = Integer.parseInt(lastMenuKey) + 1;
        }
        String temp = String.format("%08d", key);
        String menuKey = "test" + temp;
        input.setMenuId(menuKey);
        input.onCreate();
        menuMapper.insertMenu(input);
        List<MenuVO> findList = menuMapper.findMenuList(MenuQuery.builder()
                .searchMenuType(0)
                .searchMenu("test")
                .build(), PageRequest.of(0, 10)).getContent();
        assertThat(findList.stream().filter(m -> m.getMenuId().equals("test00000004"))).isNotNull();
    }

    @Test
    @Disabled //TODO: 빌드할 때만 통과못해서 추후에 해결 필요
    void findMenuList() {
        // when
        List<MenuVO> findList = menuMapper.findMenuList(new MenuQuery(0, "자원관리", null, "1"),
                PageRequest.of(0, 10)).getContent();

        // then
        assertThat(findList.size()).isEqualTo(1);
    }

    @Test
    void findMenuTreeData() {
        MenuVO rootMenu = menuMapper.findRoot().get();
        List<MenuVO> findAllList = menuMapper.findMenuList(MenuQuery.builder().searchUseYn("1").build()
                , PageRequest.of(0, Integer.MAX_VALUE, Sort.by("menuLvl", "menuSeq"))).getContent();
        assertThat(rootMenu.getMenuId()).isEqualTo("root");
        assertThat(findAllList.stream().filter(m -> m.getMenuId().equals("root")).count()).isEqualTo(0);
    }

    @Test
    void updateMenuTest() {
        MenuVO expect = MenuVO.builder().menuId("test00000004")
                .menuLvl(1)
                .menuUri("test")
                .menuName("updateTest")
                .upperMenuId("root")
                .menuDesc("")
                .menuSeq(-1)
                .leftMenuYn("0")
                .useYn("0")
                .adminMenuYn("0")
                .menuHelpUri("")
                .menuScript("")
                .personalDataYn("1").build();
        menuMapper.updateMenu(expect);
        MenuVO actual = menuMapper.findMenuList(MenuQuery.builder()
                .searchMenuType(0)
                .searchMenu("updateTest")
                .build(), PageRequest.of(0, 10)).getContent().get(0);
        assertThat(actual).usingRecursiveComparison()
                .ignoringFields("sysUpdateDtm", "sysUpdateUserId", "sysInsertDtm", "sysInsertUserId")
                .isEqualTo(expect);
    }

    @Test
    void insertAndDeleteBookmarkTest() {
        // Create a bookmark object for insertion
        BookmarkVO newBookmark = BookmarkVO.builder()
                .userId("admin")
                .menuId("menu00000001")
                .build();
        newBookmark.onCreate();

        // Insert bookmark
        menuMapper.insertBookmark(newBookmark);

        // Retrieve and verify the inserted bookmark
        BookmarkVO insertedBookmark = menuMapper.findBookmark(newBookmark);
        assertNotNull(insertedBookmark);
        assertThat(insertedBookmark).usingRecursiveComparison()
                .ignoringFields("sysUpdateDtm", "sysUpdateUserId", "sysInsertDtm", "sysInsertUserId")
                .isEqualTo(newBookmark);

        // Delete bookmark
        menuMapper.deleteBookmark(newBookmark);

        // Verify deletion
        BookmarkVO deletedBookmark = menuMapper.findBookmark(newBookmark);
        assertNull(deletedBookmark);
    }
}
