package net.lotte.chamomile.admin.menutree;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "parent")
public class MenuNode {
    private String key;
    private String label;
    private String menuId;
    private String menuName;
    private boolean hasRole;
    private List<String> from;
    private List<MenuNode> children;
    @JsonIgnore
    private MenuNode parent;

    public MenuNode(MenuHierarchyVO node) {
        this.key = node.getParentMenuId();
        this.label = node.getParentMenuName();
        this.menuId = node.getParentMenuId();
        this.menuName = node.getParentMenuName();
        this.children = new ArrayList<>();
        this.from = new ArrayList<>();
    }

    public MenuNode(String roleId, String roleName, boolean hasRole) {
        this.key = roleId;
        this.label = roleName;
        this.menuId = roleId;
        this.menuName = roleName;
        this.hasRole = hasRole;
        this.from = new ArrayList<>();
        this.children = new ArrayList<>();
    }

}
