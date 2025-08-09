package net.lotte.chamomile.admin.authtree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import net.lotte.chamomile.admin.authuser.domain.AuthUserVO;

@Getter
@NoArgsConstructor
@ToString
public class RoleHierarchyTree {
    private List<AuthNode> topNodes;

    public RoleHierarchyTree(List<RoleHierarchyVO> hierarchyList) {
        this.topNodes = new ArrayList<>();
        buildTree(hierarchyList);
    }

    public void mapToLeftTreeData(List<AuthTreeVO> responses) {
        traverseTree(node -> {
            for (AuthTreeVO authUser : responses) {
                if (node.getRoleId().equals(authUser.getRoleId())) {
                    node.setRoleName(authUser.getRoleName());
                    break;
                }
            }
        });
    }

    public void mapToRightTreeData(List<AuthTreeVO> responses) {
        traverseTree(node -> {
            for (AuthTreeVO authUser : responses) {
                if (node.getRoleId().equals(authUser.getRoleId())) {
                    node.setRoleName(authUser.getRoleName());
                    node.setHasRole(true);
                    addDesc(node);
                    break;
                }
            }
        });
    }

    /**
     * mapToRightTreeData 반대방향(child의 hasRole이 true일 경우 parent의 hasRole도 true)
     */
    public void mapToRightTreeDataReverse(List<AuthTreeVO> responses) {
        traverseTreeReverse(node -> {
            for (AuthTreeVO authUser : responses) {
                if (node.getRoleId().equals(authUser.getRoleId())) {
                    node.setRoleName(authUser.getRoleName());
                    node.setHasRole(true);
                    addAsc(node);
                    break;
                }
            }
        });
    }

    private void addDesc(AuthNode node) {
        for (AuthNode child : node.getChildren()) {
            child.setHasRole(true);
            addDesc(child);
        }
    }
    private void addAsc(AuthNode node) {
        if (node.getParent() != null) {
            AuthNode parent = node.getParent();
            parent.setHasRole(true);
            addAsc(parent);
        }
    }
    public void mapToLeftGroupData(List<AuthUserVO> responses) {
        traverseTree(node -> {
            for (AuthUserVO authUser : responses) {
                if (node.getRoleId().equals(authUser.getRoleId())) {
                    node.setRoleName(authUser.getRoleName());
                    break;
                }
            }
        });
    }
    public void mapToRightGroupData(List<AuthTreeVO> responses) {
        traverseTree(node -> {
            for (AuthTreeVO resource : responses) {
                if (node.getRoleId().equals(resource.getRoleId())) {
                    node.setRoleName(resource.getRoleName());
                    if (!node.getFrom().contains(resource.getGroupName())) {
                        node.getFrom().add(resource.getGroupName());
                    }
                    addGroupToDescendants(node, resource.getGroupName());
                    break;
                }
            }
        });
    }

    private void addGroupToDescendants(AuthNode node, String groupName) {
        for (AuthNode child : node.getChildren()) {
            if (!child.getFrom().contains(groupName)) {
                child.getFrom().add(groupName);
            }
            addGroupToDescendants(child, groupName);
        }
    }

    // 트리 순회 (깊이 우선 탐색)
    private void traverseTree(Consumer<AuthNode> action) {
        for (AuthNode node : topNodes) {
            traverseTree(node, action);
        }
    }

    private void traverseTreeReverse(Consumer<AuthNode> action) {
        for (AuthNode node : topNodes) {
            traverseTreeReverse(node, action);
        }
    }

    private void traverseTree(AuthNode node, Consumer<AuthNode> action) {
        action.accept(node);
        for (AuthNode child : node.getChildren()) {
            traverseTree(child, action);
        }
    }

    private void traverseTreeReverse(AuthNode node, Consumer<AuthNode> action) {
        for (AuthNode child : node.getChildren()) {
            traverseTree(child, action);
        }
        action.accept(node);
    }

    public AuthNode findNodeByRoleId(String roleId) {
        for (AuthNode node : topNodes) {
            AuthNode found = findNodeByRoleId(node, roleId);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    private AuthNode findNodeByRoleId(AuthNode node, String roleId) {
        if (node.getRoleId().equals(roleId)) {
            return node;
        }
        for (AuthNode child : node.getChildren()) {
            AuthNode found = findNodeByRoleId(child, roleId);
            if (found != null) {
                return found;
            }
        }
        return null;
    }


    private void buildTree(List<RoleHierarchyVO> hierarchyList) {
        Map<String, AuthNode> nodeMap = new LinkedHashMap<>();

        // 모든 노드를 맵에 저장
        for (RoleHierarchyVO vo : hierarchyList) {
            nodeMap.putIfAbsent(vo.getParentRoleId(), new AuthNode(vo));
            if (vo.getChildRoleId() != null && !nodeMap.containsKey(vo.getChildRoleId())) {
                nodeMap.put(vo.getChildRoleId(), new AuthNode(vo.getChildRoleId(), vo.getChildRoleName(), false, vo.getRoleStartDt(), vo.getRoleEndDt(), vo.getUseYn()));
            }
        }

        // 자식 노드 연결 및 부모 설정
        for (RoleHierarchyVO vo : hierarchyList) {
            AuthNode parentNode = nodeMap.get(vo.getParentRoleId());
            AuthNode childNode = nodeMap.get(vo.getChildRoleId());

            if (parentNode != null && childNode != null) {
                parentNode.getChildren().add(childNode);
                childNode.setParent(parentNode);
                childNode.setParentId(parentNode.getRoleId());
            }
        }

        // 최상위 노드 찾기
        for (AuthNode node : nodeMap.values()) {
            if (isTopNode(node, nodeMap)) {
                this.topNodes.add(node);
            }
        }

        // 순환 참조 탐지
        for (AuthNode topNode : topNodes) {
            detectCycle(topNode, new HashSet<>());
        }
    }
    public void addSingleRole(List<RoleHierarchyVO> singleRoleList) {
        if (singleRoleList != null) {
            for (RoleHierarchyVO vo : singleRoleList) {
                AuthNode node = new AuthNode(vo.getChildRoleId(), vo.getChildRoleName(), false, vo.getRoleStartDt(), vo.getRoleEndDt(), vo.getUseYn());
                if(findNodeByRoleId(node.getRoleId()) == null) {
                    this.topNodes.add(node);
                }
            }
        }
    }

    private boolean isTopNode(AuthNode node, Map<String, AuthNode> nodeMap) {
        for (AuthNode possibleParent : nodeMap.values()) {
            if (possibleParent.getChildren().contains(node)) {
                return false;
            }
        }
        return true;
    }

    private void detectCycle(AuthNode node, Set<String> visited) {
        if (visited.contains(node.getRoleId())) {
            throw new IllegalStateException("Circular reference detected in role hierarchy");
        }

        visited.add(node.getRoleId());
        for (AuthNode child : node.getChildren()) {
            detectCycle(child, new HashSet<>(visited));
        }
    }
}
