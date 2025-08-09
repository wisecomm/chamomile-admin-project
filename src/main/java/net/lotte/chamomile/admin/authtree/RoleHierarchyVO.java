package net.lotte.chamomile.admin.authtree;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RoleHierarchyVO {
    private String parentRoleId;
    private String parentRoleName;
    private String childRoleId;
    private String childRoleName;
    private String roleStartDt;
    private String roleEndDt;
    private String useYn;

    public RoleHierarchyVO(String parentRoleId, String childRoleId) {
        this.parentRoleId = parentRoleId;
        this.childRoleId = childRoleId;
    }
}
