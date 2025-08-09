package net.lotte.chamomile.admin.menu.service;

import lombok.extern.slf4j.Slf4j;

import net.lotte.chamomile.admin.WebApplicationTest;

import net.lotte.chamomile.admin.menu.api.dto.MenuQuery;
import net.lotte.chamomile.admin.menu.domain.MenuComponentVO;
import net.lotte.chamomile.admin.menu.domain.MenuVO;

import net.lotte.chamomile.admin.resource.domain.ResourceVO;
import net.lotte.chamomile.admin.resource.service.ResourceService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@Transactional
class MenuServiceTest extends WebApplicationTest {
    @Autowired MenuService menuService;
    @Autowired
    ResourceService resourceService;

    @Test
    void createMenu() {
        MenuVO request = MenuVO.builder()
                .menuId("testMenuId")
                .menuLvl(100)
                .menuUri("/test/test")
                .menuName("testMenu")
                .upperMenuId("root")
                .menuDesc("test")
                .menuSeq(-1)
                .menuHelpUri("test")
                .menuScript("test")
                .leftMenuYn("1")
                .useYn("1")
                .adminMenuYn("1")
                .build();
        menuService.createMenu(request);

        MenuVO findMenu = menuService.findMenuList(new MenuQuery(null, "testMenu", null, null),
                PageRequest.of(0, 10)).getContent().get(0);
        assertThat(findMenu).isNotNull();

        // ResourceVO findResource = resourceService.getResource(findMenu.getMenuId());
        // assertThat(findResource).isNotNull();
        // assertThat(findResource.getResourceId()).isEqualTo(findMenu.getMenuId());
        // assertThat(findResource.getResourceUri()).isEqualTo(findMenu.getMenuUri());
        // assertThat(findResource.getSecurityOrder()).isEqualTo(99998);
    }

    @Test
    void checkComponentIdTest() {
        assertThat(menuService.checkComponentId("menu00000101", "button1")).isTrue();
        assertThat(menuService.checkComponentId("menu00000101", "abcd")).isFalse();
    }

    @Test
    void findComponentListTest() {
        List<MenuComponentVO> findList = menuService.findComponentList("menu00000101");
        assertThat(findList.size()).isEqualTo(2);
    }

    @Test
    void insertComponentTest() {
        MenuComponentVO insertVO = new MenuComponentVO("menu00000101", "test1", "/test", "testName", "testDesc", "1", 0);
        MenuComponentVO insertVO2 = new MenuComponentVO("menu00000101", "test2", "/test2", "test2Name", "test2Desc", "1", 1);
        List<MenuComponentVO> list = new ArrayList<>();
        list.add(insertVO);
        list.add(insertVO2);
        menuService.createComponents(list);
        assertThat(menuService.checkComponentId("menu00000101", "test1")).isTrue();
        assertThat(menuService.checkComponentId("menu00000101", "test2")).isTrue();
    }

    @Test
    void updateComponentTest() {
        MenuComponentVO updateOne = new MenuComponentVO("menu00000101", "button1", "/update-test", "updateName", "updatedDesc", "1", 0);
        List<MenuComponentVO> list = new ArrayList<>();
        list.add(updateOne);
        menuService.updateComponents(list);
        MenuComponentVO findOne = menuService.findComponentList("menu00000101").get(0);
        assertThat(findOne).usingRecursiveComparison()
                .ignoringFields("componentName","sysUpdateDtm", "sysUpdateUserId", "sysInsertDtm", "sysInsertUserId")
                .isEqualTo(updateOne);
    }

    @Test
    void deleteComponentTest() {
        MenuComponentVO deleteVO1 = new MenuComponentVO("menu00000101", "button1");
        MenuComponentVO deleteVO2 = new MenuComponentVO("menu00000101", "button2");
        List<MenuComponentVO> list = new ArrayList<>();
        list.add(deleteVO1);
        list.add(deleteVO2);
        menuService.deleteComponents(list);
        assertThat(menuService.findComponentList("menu00000101")).isEmpty();
    }
}
