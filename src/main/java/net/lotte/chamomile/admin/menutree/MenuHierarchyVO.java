package net.lotte.chamomile.admin.menutree;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MenuHierarchyVO {
    private String parentMenuId;
    private String parentMenuName;
    private String childMenuId;
    private String childMenuName;

    public MenuHierarchyVO(String parentRoleId, String childRoleId) {
        this.parentMenuId = parentRoleId;
        this.childMenuId = childRoleId;
    }
}
