package net.lotte.chamomile.admin.authtree;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "parent")
public class AuthNode {
    private String key;
    private String roleId;
    private String roleName;
    private boolean hasRole;
    private String roleStartDt;
    private String roleEndDt;
    private String useYn;
    private List<String> from;
    private List<AuthNode> children;
    @JsonIgnore
    private AuthNode parent;
    private String parentId;

    public AuthNode(RoleHierarchyVO node) {
        this.key = node.getParentRoleId();
        this.roleId = node.getParentRoleId();
        this.roleName = node.getParentRoleName();
        this.roleStartDt = node.getRoleStartDt();
        this.roleEndDt = node.getRoleEndDt();
        this.useYn = node.getUseYn();
        this.children = new ArrayList<>();
        this.from = new ArrayList<>();
    }

    public AuthNode(String roleId, String roleName, boolean hasRole, String roleStartDt, String roleEndDt, String useYn) {
        this.key = roleId;
        this.roleId = roleId;
        this.roleName = roleName;
        this.hasRole = hasRole;
        this.roleStartDt = roleStartDt;
        this.roleEndDt = roleEndDt;
        this.useYn = useYn;
        this.from = new ArrayList<>();
        this.children = new ArrayList<>();
    }

}
