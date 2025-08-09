package net.lotte.chamomile.admin.menutree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MenuHierarchyTree {
    private List<MenuNode> topNodes;

    public MenuHierarchyTree(List<MenuHierarchyVO> hierarchyList) {
        this.topNodes = new ArrayList<>();
        buildTree(hierarchyList);
    }

    public void mapToLeftTreeData(List<MenuTreeVO> responses) {
        traverseTree(node -> {
            for (MenuTreeVO menuTreeVO : responses) {
                if (node.getMenuId().equals(menuTreeVO.getMenuId())) {
                    node.setMenuName(menuTreeVO.getMenuName());
                    break;
                }
            }
        });
    }

    public void mapToRightTreeData(List<MenuTreeVO> responses) {
        traverseTree(node -> {
            for (MenuTreeVO menuTreeVO : responses) {
                if (node.getMenuId().equals(menuTreeVO.getMenuId())) {
                    node.setMenuName(menuTreeVO.getMenuName());
                    node.setHasRole(true);
                    // addDescendants(node);
                    break;
                }
            }
        });
    }

    /**
     * mapToRightTreeData 반대방향(child의 hasRole이 true일 경우 parent의 hasRole도 true)
     */
    public void mapToRightTreeDataAsc(List<MenuTreeVO> responses) {
        traverseTree(node -> {
            for (MenuTreeVO menuTreeVO : responses) {
                if (node.getMenuId().equals(menuTreeVO.getMenuId())) {
                    node.setMenuName(menuTreeVO.getMenuName());
                    node.setHasRole(true);
                    node.getParent().setHasRole(true);
                    break;
                }
            }
        });
    }

    private void addDescendants(MenuNode node) {
        for (MenuNode child : node.getChildren()) {
            child.setHasRole(true);
            addDescendants(child);
        }
    }

    public void mapToRightGroupData(List<MenuTreeVO> responses) {
        traverseTree(node -> {
            for (MenuTreeVO resource : responses) {
                if (node.getMenuId().equals(resource.getMenuId())) {
                    node.setMenuName(resource.getMenuName());
                    // node.setHasRole(true);
                    if (!node.getFrom().contains(resource.getGroupName())) {
                        node.getFrom().add(resource.getGroupName());
                    }
                    addGroupToDescendants(node, resource.getGroupName());
                    break;
                }
            }
        });
    }

    private void addGroupToDescendants(MenuNode node, String groupName) {
        for (MenuNode child : node.getChildren()) {
            if (!child.getFrom().contains(groupName)) {
                child.getFrom().add(groupName);
            }
            addGroupToDescendants(child, groupName);
        }
    }

    // 트리 순회 (깊이 우선 탐색)
    public void traverseTree(Consumer<MenuNode> action) {
        for (MenuNode node : topNodes) {
            traverseTree(node, action);
        }
    }

    private void traverseTree(MenuNode node, Consumer<MenuNode> action) {
        action.accept(node);
        for (MenuNode child : node.getChildren()) {
            traverseTree(child, action);
        }
    }

    public MenuNode findNodeByRoleId(String roleId) {
        for (MenuNode node : topNodes) {
            MenuNode found = findNodeByRoleId(node, roleId);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    private MenuNode findNodeByRoleId(MenuNode node, String menuId) {
        if (node.getMenuId().equals(menuId)) {
            return node;
        }
        for (MenuNode child : node.getChildren()) {
            MenuNode found = findNodeByRoleId(child, menuId);
            if (found != null) {
                return found;
            }
        }
        return null;
    }


    private void buildTree(List<MenuHierarchyVO> hierarchyList) {
        Map<String, MenuNode> nodeMap = new LinkedHashMap<>();

        // 모든 노드를 맵에 저장
        for (MenuHierarchyVO vo : hierarchyList) {
            nodeMap.putIfAbsent(vo.getParentMenuId(), new MenuNode(vo));
            if (vo.getChildMenuId() != null && !nodeMap.containsKey(vo.getChildMenuId())) {
                nodeMap.put(vo.getChildMenuId(), new MenuNode(vo.getChildMenuId(), vo.getChildMenuName(), false));
            }
        }

        // 자식 노드 연결 및 부모 설정
        for (MenuHierarchyVO vo : hierarchyList) {
            MenuNode parentNode = nodeMap.get(vo.getParentMenuId());
            MenuNode childNode = nodeMap.get(vo.getChildMenuId());

            if (parentNode != null && childNode != null) {
                parentNode.getChildren().add(childNode);
                childNode.setParent(parentNode);
            }
        }

        // 최상위 노드 찾기
        for (MenuNode node : nodeMap.values()) {
            if (isTopNode(node, nodeMap)) {
                this.topNodes.add(node);
            }
        }

        // 순환 참조 탐지
        for (MenuNode topNode : topNodes) {
            detectCycle(topNode, new HashSet<>());
        }

    }

    private boolean isTopNode(MenuNode node, Map<String, MenuNode> nodeMap) {
        for (MenuNode possibleParent : nodeMap.values()) {
            if (possibleParent.getChildren().contains(node)) {
                return false;
            }
        }
        return true;
    }

    private void detectCycle(MenuNode node, Set<String> visited) {
        if (visited.contains(node.getMenuId())) {
            throw new IllegalStateException("Circular reference detected in role hierarchy");
        }

        visited.add(node.getMenuId());
        for (MenuNode child : node.getChildren()) {
            detectCycle(child, new HashSet<>(visited));
        }
    }
}
