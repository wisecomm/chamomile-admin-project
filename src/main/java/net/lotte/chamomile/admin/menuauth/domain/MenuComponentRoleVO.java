package net.lotte.chamomile.admin.menuauth.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import net.lotte.chamomile.module.database.audit.TimeAuthorLog;

@Data
@NoArgsConstructor
public class MenuComponentRoleVO extends TimeAuthorLog {
    private String menuId;
    private String componentId;
    private String componentDesc;
    private String componentUrl;
    private boolean hasRole;
    private String roleId;

    public MenuComponentRoleVO(String menuId, String componentId, String roleId) {
        this.menuId = menuId;
        this.componentId = componentId;
        this.roleId = roleId;
    }
}
