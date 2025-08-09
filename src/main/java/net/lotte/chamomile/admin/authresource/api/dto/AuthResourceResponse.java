package net.lotte.chamomile.admin.authresource.api.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.NoArgsConstructor;

import net.lotte.chamomile.admin.authresource.api.dto.UsableAuthResourceResponse;
import net.lotte.chamomile.admin.authtree.AuthTreeVO;
import net.lotte.chamomile.admin.authtree.RoleHierarchyTree;
import net.lotte.chamomile.admin.resource.domain.ResourceVO;

/**
 * <pre>
 * 리소스 권한 반환 DTO.
 * </pre>
 *
 * @author MoonHKLee
 * @since 2023-11-09
 * @version 3.0
 * @Modification
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023-11-09     MoonHKLee            최초 생성
 *
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 */
@Data
@NoArgsConstructor
public class AuthResourceResponse {
    private ResourceVO authResourceVO;
    private List<UsableAuthResourceResponse> authResourceLeftList;
    private List<UsableAuthResourceResponse> authResourceList;
    private RoleHierarchyTree tree;

    public AuthResourceResponse(ResourceVO authResourceVO, List<AuthTreeVO> left, List<AuthTreeVO> right, RoleHierarchyTree tree) {
        this.authResourceVO = authResourceVO;
        this.authResourceLeftList = left
                .stream()
                .map(v -> new UsableAuthResourceResponse(v.getRoleId(),v.getRoleName()))
                .collect(Collectors.toList());
        this.authResourceList = right
                .stream()
                .map(v -> new UsableAuthResourceResponse(v.getRoleId(),v.getRoleName()))
                .collect(Collectors.toList());
        this.tree = tree;
    }
}
