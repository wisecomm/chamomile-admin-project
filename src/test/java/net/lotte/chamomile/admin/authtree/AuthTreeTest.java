package net.lotte.chamomile.admin.authtree;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AuthTreeTest {
    @Test
    @DisplayName("노드가 올바르게 들어갔는지 테스트")
    void testGenerateNodes() {
        //given
        List<RoleHierarchyVO> list = new ArrayList<>();
        list.add(new RoleHierarchyVO("node1","node2"));
        list.add(new RoleHierarchyVO("node2","node3"));
        list.add(new RoleHierarchyVO("node2","node4"));
        list.add(new RoleHierarchyVO("node5","node6"));
        list.add(new RoleHierarchyVO("node6","node7"));
        list.add(new RoleHierarchyVO("node8","node9"));

        //given
        List<AuthTreeVO> authUsers = new ArrayList<>();
        authUsers.add(new AuthTreeVO("user1","node1","1","노드1","group01","그룹01"));
        authUsers.add(new AuthTreeVO("user1","node2","1","노드2","group02","그룹02"));


        //when
        RoleHierarchyTree tree = new RoleHierarchyTree(list);

        //then
        assertThat(tree.getTopNodes()).hasSize(3);
        assertThat(tree.getTopNodes().get(0).getRoleId()).isEqualTo("node1");
        assertThat(tree.getTopNodes().get(0).getChildren().get(0).getRoleId()).isEqualTo("node2");
        assertThat(tree.getTopNodes().get(0).getChildren().get(0).getChildren().get(0).getRoleId()).isEqualTo("node3");
        assertThat(tree.getTopNodes().get(0).getChildren().get(0).getChildren().get(1).getRoleId()).isEqualTo("node4");
        assertThat(tree.getTopNodes().get(1).getRoleId()).isEqualTo("node5");
        assertThat(tree.getTopNodes().get(1).getChildren().get(0).getRoleId()).isEqualTo("node6");
        assertThat(tree.getTopNodes().get(1).getChildren().get(0).getChildren().get(0).getRoleId()).isEqualTo("node7");
        assertThat(tree.getTopNodes().get(2).getRoleId()).isEqualTo("node8");
        assertThat(tree.getTopNodes().get(2).getChildren().get(0).getRoleId()).isEqualTo("node9");

        //when
        List<AuthTreeVO> unusedList = new ArrayList<>();
        unusedList.add(new AuthTreeVO("노드1","node1"));
        unusedList.add(new AuthTreeVO("노드2","node2"));
        unusedList.add(new AuthTreeVO("노드3","node3"));
        unusedList.add(new AuthTreeVO("노드8","node8"));
        unusedList.add(new AuthTreeVO("노드9","node9"));
        tree.mapToLeftTreeData(unusedList);

        List<AuthTreeVO> usedList = new ArrayList<>();
        usedList.add(new AuthTreeVO("노드4","node4"));
        usedList.add(new AuthTreeVO("노드5","node5"));
        usedList.add(new AuthTreeVO("노드6","node6"));
        usedList.add(new AuthTreeVO("노드7","node7"));
        tree.mapToRightTreeData(usedList);
        tree.mapToRightGroupData(authUsers);

        //then
        assertThat(tree.findNodeByRoleId("node1").getRoleName()).isEqualTo("노드1");
        assertThat(tree.findNodeByRoleId("node1").isHasRole()).isFalse();
        assertThat(tree.findNodeByRoleId("node2").getRoleName()).isEqualTo("노드2");
        assertThat(tree.findNodeByRoleId("node2").isHasRole()).isFalse();
        assertThat(tree.findNodeByRoleId("node3").getRoleName()).isEqualTo("노드3");
        assertThat(tree.findNodeByRoleId("node3").isHasRole()).isFalse();
        assertThat(tree.findNodeByRoleId("node4").getRoleName()).isEqualTo("노드4");
        assertThat(tree.findNodeByRoleId("node4").isHasRole()).isTrue();
        assertThat(tree.findNodeByRoleId("node5").getRoleName()).isEqualTo("노드5");
        assertThat(tree.findNodeByRoleId("node5").isHasRole()).isTrue();
        assertThat(tree.findNodeByRoleId("node6").getRoleName()).isEqualTo("노드6");
        assertThat(tree.findNodeByRoleId("node6").isHasRole()).isTrue();
        assertThat(tree.findNodeByRoleId("node7").getRoleName()).isEqualTo("노드7");
        assertThat(tree.findNodeByRoleId("node7").isHasRole()).isTrue();
        assertThat(tree.findNodeByRoleId("node8").getRoleName()).isEqualTo("노드8");
        assertThat(tree.findNodeByRoleId("node8").isHasRole()).isFalse();
        assertThat(tree.findNodeByRoleId("node9").getRoleName()).isEqualTo("노드9");
        assertThat(tree.findNodeByRoleId("node9").isHasRole()).isFalse();

        //then
        assertThat(tree.findNodeByRoleId("node1").getFrom()).containsOnly("그룹01");
        assertThat(tree.findNodeByRoleId("node2").getFrom()).containsOnly("그룹01","그룹02");
        assertThat(tree.findNodeByRoleId("node3").getFrom()).containsOnly("그룹01","그룹02");
        assertThat(tree.findNodeByRoleId("node4").getFrom()).containsOnly("그룹01","그룹02");

    }

    @Test
    @DisplayName("노드가 올바르게 들어갔는지 테스트 - 반대로")
    void testGenerateNodesReverse() {
        //given
        List<RoleHierarchyVO> list = new ArrayList<>();
        list.add(new RoleHierarchyVO("node1","node2"));
        list.add(new RoleHierarchyVO("node2","node3"));
        list.add(new RoleHierarchyVO("node2","node4"));
        list.add(new RoleHierarchyVO("node5","node6"));
        list.add(new RoleHierarchyVO("node6","node7"));
        list.add(new RoleHierarchyVO("node8","node9"));

        //given
        List<AuthTreeVO> authUsers = new ArrayList<>();
        authUsers.add(new AuthTreeVO("user1","node1","1","노드1","group01","그룹01"));
        authUsers.add(new AuthTreeVO("user1","node2","1","노드2","group02","그룹02"));


        //when
        RoleHierarchyTree tree = new RoleHierarchyTree(list);

        //then
        assertThat(tree.getTopNodes()).hasSize(3);
        assertThat(tree.getTopNodes().get(0).getRoleId()).isEqualTo("node1");
        assertThat(tree.getTopNodes().get(0).getChildren().get(0).getRoleId()).isEqualTo("node2");
        assertThat(tree.getTopNodes().get(0).getChildren().get(0).getChildren().get(0).getRoleId()).isEqualTo("node3");
        assertThat(tree.getTopNodes().get(0).getChildren().get(0).getChildren().get(1).getRoleId()).isEqualTo("node4");
        assertThat(tree.getTopNodes().get(1).getRoleId()).isEqualTo("node5");
        assertThat(tree.getTopNodes().get(1).getChildren().get(0).getRoleId()).isEqualTo("node6");
        assertThat(tree.getTopNodes().get(1).getChildren().get(0).getChildren().get(0).getRoleId()).isEqualTo("node7");
        assertThat(tree.getTopNodes().get(2).getRoleId()).isEqualTo("node8");
        assertThat(tree.getTopNodes().get(2).getChildren().get(0).getRoleId()).isEqualTo("node9");

        //when
        List<AuthTreeVO> unusedList = new ArrayList<>();
        unusedList.add(new AuthTreeVO("노드1","node1"));
        unusedList.add(new AuthTreeVO("노드2","node2"));
        unusedList.add(new AuthTreeVO("노드3","node3"));
        unusedList.add(new AuthTreeVO("노드7","node7"));
        unusedList.add(new AuthTreeVO("노드8","node8"));
        unusedList.add(new AuthTreeVO("노드9","node9"));
        tree.mapToLeftTreeData(unusedList);

        List<AuthTreeVO> usedList = new ArrayList<>();
        usedList.add(new AuthTreeVO("노드4","node4"));
        usedList.add(new AuthTreeVO("노드5","node5"));
        usedList.add(new AuthTreeVO("노드6","node6"));
        tree.mapToRightTreeDataReverse(usedList);
        tree.mapToRightGroupData(authUsers);

        //then
        assertThat(tree.findNodeByRoleId("node1").getRoleName()).isEqualTo("노드1");
        assertThat(tree.findNodeByRoleId("node1").isHasRole()).isTrue();
        assertThat(tree.findNodeByRoleId("node2").getRoleName()).isEqualTo("노드2");
        assertThat(tree.findNodeByRoleId("node2").isHasRole()).isTrue();
        assertThat(tree.findNodeByRoleId("node3").getRoleName()).isEqualTo("노드3");
        assertThat(tree.findNodeByRoleId("node3").isHasRole()).isFalse();
        assertThat(tree.findNodeByRoleId("node4").getRoleName()).isEqualTo("노드4");
        assertThat(tree.findNodeByRoleId("node4").isHasRole()).isTrue();
        assertThat(tree.findNodeByRoleId("node5").getRoleName()).isEqualTo("노드5");
        assertThat(tree.findNodeByRoleId("node5").isHasRole()).isTrue();
        assertThat(tree.findNodeByRoleId("node6").getRoleName()).isEqualTo("노드6");
        assertThat(tree.findNodeByRoleId("node6").isHasRole()).isTrue();
        assertThat(tree.findNodeByRoleId("node7").getRoleName()).isEqualTo("노드7");
        assertThat(tree.findNodeByRoleId("node7").isHasRole()).isFalse();
        assertThat(tree.findNodeByRoleId("node8").getRoleName()).isEqualTo("노드8");
        assertThat(tree.findNodeByRoleId("node8").isHasRole()).isFalse();
        assertThat(tree.findNodeByRoleId("node9").getRoleName()).isEqualTo("노드9");
        assertThat(tree.findNodeByRoleId("node9").isHasRole()).isFalse();

        //then
        assertThat(tree.findNodeByRoleId("node1").getFrom()).containsOnly("그룹01");
        assertThat(tree.findNodeByRoleId("node2").getFrom()).containsOnly("그룹01","그룹02");
        assertThat(tree.findNodeByRoleId("node3").getFrom()).containsOnly("그룹01","그룹02");
        assertThat(tree.findNodeByRoleId("node4").getFrom()).containsOnly("그룹01","그룹02");

    }

}
