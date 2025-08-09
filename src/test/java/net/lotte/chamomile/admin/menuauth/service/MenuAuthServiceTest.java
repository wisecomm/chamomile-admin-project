package net.lotte.chamomile.admin.menuauth.service;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.lotte.chamomile.admin.menuauth.api.dto.MenuAuthQuery;

import net.lotte.chamomile.admin.menuauth.domain.MenuComponentRoleVO;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import net.lotte.chamomile.admin.WebApplicationTest;
import net.lotte.chamomile.admin.auth.api.dto.AuthQuery;
import net.lotte.chamomile.admin.menu.api.dto.MenuQuery;
import net.lotte.chamomile.admin.menu.service.MenuService;
import net.lotte.chamomile.admin.menuauth.domain.MenuAuthVO;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
class MenuAuthServiceTest extends WebApplicationTest {
    @Autowired MenuAuthService menuAuthService;
    @Autowired MenuService menuService;
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Test
    void updateMenuAuthTest() {
        menuAuthService.updateMenuAuth("menu00000001", Arrays.asList("ROLE_ADMIN_ID", "ROLE_DEFAULT_ID"));
        List<MenuAuthVO> findList = menuAuthService.getMenuAuthExcelList(new MenuQuery(), PageRequest.of(0, 10)).getContent();
        for (MenuAuthVO menuAuthVO : findList) {
            log.info("vo = {}, {}", menuAuthVO.getMenuId(), menuAuthVO.getRoleId());
        }
        assertThat(findList.stream().filter(m -> m.getMenuId().equals("menu00000001"))).size().isEqualTo(2);
    }

    @Test
    void updateAuthMenuTest() {
        menuAuthService.updateAuthMenu("ROLE_ADMIN_ID", Arrays.asList("menu00000001", "menu00000002"));
        List<MenuAuthVO> findList = menuAuthService.getAuthMenuExcelList(new AuthQuery(), PageRequest.of(0, 10)).getContent();
        for (MenuAuthVO menuAuthVO : findList) {
            log.info("vo = {}, {}", menuAuthVO.getRoleId(), menuAuthVO.getMenuId());
        }
        assertThat(findList.stream().filter(m -> m.getRoleId().equals("ROLE_ADMIN_ID"))).size().isEqualTo(2);
    }

    @Test
    void getMenuComponentsByRole() {
        // 존재하지 않는  menuId
        List<MenuComponentRoleVO> findNoDataList = menuAuthService.getMenuComponentsByRole(new MenuAuthQuery("noMenuData", "ROLE_ADMIN_ID"));
        assertThat(findNoDataList.size()).isEqualTo(0);

        List<MenuComponentRoleVO> findList = menuAuthService.getMenuComponentsByRole(new MenuAuthQuery("menu00000101", "ROLE_ADMIN_ID"));
        assertThat(findList.stream().filter(e -> e.getComponentId().equals("button1")).findAny().get().isHasRole()).isTrue();
        assertThat(findList.stream().filter(e -> e.getComponentId().equals("button2")).findAny().get().isHasRole()).isFalse();
    }

    @Test
    void saveMenuComponentRoleTest() {
        List<String> list = new ArrayList<>();
        list.add("button3");
        list.add("button4");
        list.add("button5");
        menuAuthService.saveMenuComponentRole("menu00000101", "ROLE_ADMIN_ID", list);
        String sql = "select menu_id, component_id, role_id from chmm_menu_component_role_map where menu_id = :menuId and role_id=:roleId";
        Map<String, Object> param = new HashMap<>();
        param.put("menuId", "menu00000101");
        param.put("roleId", "ROLE_ADMIN_ID");
        List<MenuComponentRoleVO> findList = jdbcTemplate.query(sql, param, rowMapper());

        for (MenuComponentRoleVO menuComponentRoleVO : findList) {
            log.info("#### find= {}", menuComponentRoleVO);
        }
        assertThat(findList.size()).isEqualTo(3);
        assertThat(findList.stream().anyMatch(e -> e.getComponentId().equals("button1"))).isFalse();
        assertThat(findList.stream().anyMatch(e -> e.getComponentId().equals("button3"))).isTrue();
        assertThat(findList.stream().anyMatch(e -> e.getComponentId().equals("button4"))).isTrue();
        assertThat(findList.stream().anyMatch(e -> e.getComponentId().equals("button5"))).isTrue();
    }

    private RowMapper<MenuComponentRoleVO> rowMapper() {
        return BeanPropertyRowMapper.newInstance(MenuComponentRoleVO.class);
    }
}
