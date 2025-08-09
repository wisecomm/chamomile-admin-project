package net.lotte.chamomile.admin.grouphierarchy.domain;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * 그룹 상하관계 VO.
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
@Slf4j
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class GroupHierarchyVO {

    @NotNull(message = "부모 그룹 ID는 필수 입력값 입니다.")
    private String parentGroupId;

    @NotNull(message = "자식 그룹 ID는 필수 입력값 입니다.")
    private String childGroupId;

    @JsonIgnore
    private int order;

    public GroupHierarchyVO(String parentGroupId, String childGroupId) {
        this.parentGroupId = parentGroupId;
        this.childGroupId = childGroupId;
    }

    /** Cycle 체크 **/
    public void cycleCheck(List<GroupHierarchyVO> allList) {
        Set<String> groupIdSet = new HashSet<>();
        groupIdSet.add(childGroupId);

        Queue<String> queue = new LinkedList<>();
        queue.add(childGroupId);

        while (!queue.isEmpty()) {
            String currentGroupId = queue.poll();
            if (currentGroupId.equals(parentGroupId)) {
                log.debug("ParentGroupID : {}, ChildGroupID : {} is Cycle.", parentGroupId, childGroupId);
                throw new RuntimeException("Cycle Error");
            }

            // vo를 하나씩 꺼낸다
            for (GroupHierarchyVO nextVo : allList) {
                String nextParentGroupId = nextVo.getParentGroupId();
                String nextChildGroupId = nextVo.getChildGroupId();

                if (parentGroupId.equals(nextParentGroupId) && childGroupId.equals(nextChildGroupId)) { /* cycle */
                    log.debug("ParentGroupID : {}, ChildGroupID : {} is Cycle.", parentGroupId, childGroupId);
                    throw new RuntimeException("Cycle Error");
                }

                if (!nextParentGroupId.equals(currentGroupId)) continue; // next vo의 parent가 현재 vo인 경우 넘긴다
                if (groupIdSet.contains(nextChildGroupId)) continue; // 방문했다면 넘긴다

                queue.add(nextChildGroupId);
                groupIdSet.add(nextChildGroupId);
            }
        }
        log.debug("ParentGroupID : {}, ChildGroupID : {} isn't Cycle.", parentGroupId, childGroupId);
    }
}
