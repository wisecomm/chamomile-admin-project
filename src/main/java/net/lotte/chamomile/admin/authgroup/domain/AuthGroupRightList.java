package net.lotte.chamomile.admin.authgroup.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import net.lotte.chamomile.admin.authgroup.api.dto.AuthGroupDetailResponse;
import net.lotte.chamomile.admin.authhierarchy.domain.AuthHierarchyVO;

/**
 * <pre>
 * 권한 그룹 적용 리스트.
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
public class AuthGroupRightList {

    private final Queue<String> queue;

    List<AuthGroupDetailResponse> rightList;

    List<AuthHierarchyVO> list;

    List<AuthGroupDetailResponse> roleList;

    public AuthGroupRightList(List<AuthHierarchyVO> list, List<AuthGroupDetailResponse> roleList) {
        this.queue = new LinkedList<>();
        this.roleList = roleList;
        this.rightList = new ArrayList<>();
        this.list = list;
    }

    public List<AuthGroupDetailResponse> getList() {
        Set<AuthHierarchyVO> setList = new HashSet<>();
        for (AuthGroupDetailResponse vo0 : roleList) {
            queue.add(vo0.getVal());
            AuthGroupDetailResponse vo1 = new AuthGroupDetailResponse();
            vo1.setVal(vo0.getVal());
            vo1.setText(vo0.getText());
            vo1.setFixed("false");
            rightList.add(vo1);
        }

        // 가지고 있는 것보다 상위 권한 조회
        while (!queue.isEmpty()) {
            int qSize = queue.size();
            for (int i = 0; i < qSize; i++) {
                String currentRoleId = queue.poll();
                for (AuthHierarchyVO nextVo : list) {
                    String nextParentRoleId = nextVo.getParentRoleId();
                    String nextChildRoleId = nextVo.getChildRoleId();
                    if (!nextParentRoleId.equals(currentRoleId)) continue;
                    if (setList.contains(nextVo)) continue;
                    queue.add(nextChildRoleId);
                    setList.add(nextVo);
                }
            }
        }
        ArrayList<AuthHierarchyVO> resultList = new ArrayList<>(setList);
        resultList.stream()
                .map(AuthGroupDetailResponse::of)
                .forEach(rightList::add);
        return rightList;
    }
}
