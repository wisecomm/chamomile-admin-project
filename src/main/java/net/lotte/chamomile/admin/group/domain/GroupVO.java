package net.lotte.chamomile.admin.group.domain;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import net.lotte.chamomile.module.database.audit.TimeAuthorLog;

/**
 * <pre>
 * 그룹 VO.
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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class GroupVO extends TimeAuthorLog {
    @NotNull(message = "그룹 아이디는 필수값입니다.")
    @Size(min = 5, max = 50)
    private String groupId;
    @NotNull(message = "그룹 이름은 필수값입니다.")
    @Size(min = 5, max = 50)
    private String groupName;
    @Size(max = 200)
    private String groupDesc;
    @NotNull(message = "사용여부는 필수값입니다.")
    @Pattern(regexp = "^(0|1)$", message = "참/거짓은 0/1로 구분 합니다.")
    private String useYn;


    private String parentGroupId;
    private List<String> parentGroupIds;

    // 240801 임지훈 - 그룹유저정보 조회여부 추가
    private String includedUserYn;
    
    public static GroupVO id(String groupId) {
        GroupVO groupVO = new GroupVO();
        groupVO.setGroupId(groupId);
        return groupVO;
    }

}
