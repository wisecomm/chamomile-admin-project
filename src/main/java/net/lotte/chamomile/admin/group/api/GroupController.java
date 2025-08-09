package net.lotte.chamomile.admin.group.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.lotte.chamomile.admin.group.api.dto.GroupQuery;
import net.lotte.chamomile.admin.group.domain.GroupExcelVO;
import net.lotte.chamomile.admin.group.domain.GroupVO;
import net.lotte.chamomile.admin.group.service.GroupService;
import net.lotte.chamomile.admin.groupuser.api.dto.GroupUserResponse;
import net.lotte.chamomile.admin.groupuser.domain.GroupUserVO;
import net.lotte.chamomile.admin.groupuser.service.GroupUserService;
import net.lotte.chamomile.module.excel.ExcelExporter;
import net.lotte.chamomile.module.excel.ExcelImporter;
import net.lotte.chamomile.module.file.util.FileUploader;
import net.lotte.chamomile.module.web.variable.ChamomileResponse;

/**
 * <pre>
 * 그룹 컨트롤러.
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
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/chmm/group")
public class GroupController implements GroupControllerDoc {
    private final GroupService groupService;
    private final FileUploader fileUploader;
    private final GroupUserService groupUserService;
    
    @GetMapping(value = "/list")
    public ChamomileResponse<Page<GroupVO>> getGroupList(GroupQuery request, Pageable pageable) {
    	Page<GroupVO> result = null;
    	
    	// 240905 임지훈 - 검색조건 입력 시 부모노드를 조회하지 못해 트리를 생성하지 못하는 문제 해결
    	if (!StringUtils.isEmpty(request.getSearchGroupId())   || 
			!StringUtils.isEmpty(request.getSearchGroupName()) || 
			!StringUtils.isEmpty(request.getSearchUseYn())
		) {
    		List<GroupVO> groups = new ArrayList<GroupVO>();
    		Map<String, GroupVO> groupsMap = new HashMap<>();
    		getGroupListParent(request, pageable, groups, groupsMap, true);
    		
    		result = new PageImpl<>(groups, pageable, groups.size());
    	} else {
    		result = groupService.getGroupList(request, pageable);
    	}
    	
        // 240801 임지훈 - 그룹유저정보 조회여부 추가
        if (request.getIncludeUserYn() != null && request.getIncludeUserYn()) {
        	List<GroupVO> groups = new ArrayList<>(result.getContent());

            Map<String, GroupVO> groupVOMap = new HashMap<>();
        	result.getContent().forEach(groupVO -> {
                if (!groupVOMap.containsKey(groupVO.getGroupId())) {
                    groupVOMap.put(groupVO.getGroupId(), groupVO);
                }
            });

        	// 그룹유저 조회
        	List<GroupVO> topLevelGroups = groups.stream().filter(groupVO -> groupVO.getParentGroupId() == null)
								        				  .collect(Collectors.toList());
        	List<GroupVO> groupUsers     = new ArrayList<>();
            List<String> parentGroups = new ArrayList<>();

        	for (GroupVO topLevelGroup : topLevelGroups) {
        		GroupUserResponse topLevelGroupUsers = groupUserService.getGroupUser(topLevelGroup.getGroupId());
        		
        		for (GroupUserVO groupUser : topLevelGroupUsers.getReturnList()) {
        			GroupVO parentGroup  = groupVOMap.get(groupUser.getGroupId());

                    parentGroups.clear();
        			parentGroups.add(groupUser.getGroupId());
        			if (parentGroup != null) {
        				parentGroups.addAll(parentGroup.getParentGroupIds());
        			}
        			
        			GroupVO group = GroupVO.builder()
										.groupId       (groupUser.getUserId())
										.groupName     (String.format("%s(%s)", groupUser.getUserName(), groupUser.getUserId()))
										.groupDesc     (groupUser.getUserName())
										.parentGroupId (groupUser.getGroupId())
										.parentGroupIds(parentGroups)
										.includedUserYn("1")
										.build();
        			
        			groupUsers.add(group);
        		}
        	}
        	groups.addAll(groupUsers);

        	// 미그룹유저 조회
        	Page<GroupUserVO> unMappedGroupUsers = groupUserService.getUnMappedUser("", pageable);
        	
        	for (GroupUserVO groupUser : unMappedGroupUsers.getContent()) {
    			GroupVO group = GroupVO.builder()
									.groupId       (groupUser.getUserId())
									.groupName     (String.format("%s(%s)", groupUser.getUserName(), groupUser.getUserId()))
									.groupDesc     (groupUser.getUserName())
									.parentGroupId (groupUser.getGroupId())
									.includedUserYn("1")
									.build();
    			
    			groups.add(group);
    		}
        	
        	result = new PageImpl<>(groups, pageable, result.getTotalElements() + unMappedGroupUsers.getTotalElements());
        }
        
        return new ChamomileResponse<>(result);
    }
    
    private void getGroupListParent(GroupQuery request, Pageable pageable, List<GroupVO> groups, Map<String, GroupVO> groupsMap, boolean isHeader) {
    	List<GroupVO> groupList = new ArrayList<>();
    	
    	if (isHeader) {
    		groupList = groupService.getGroupList(request, pageable).getContent();
    	} else {
    		List<GroupVO> group = groupService.getGroupListWithId(request.getSearchGroupId());
    		
    		if (group != null) {
    			groupList.addAll(group);
    		}
    	}
    	
    	for (GroupVO group : groupList) {
    		groups.add(group);
    		groupsMap.put(group.getGroupId(), group);
    		
    		for (String parent : group.getParentGroupIds()) {
    			if (!groupsMap.containsKey(parent)) {
    				getGroupListParent(GroupQuery.builder().searchGroupId(parent).build(), pageable, groups, groupsMap, false);
    			}
    		}
    	}
    }

    @GetMapping(value = "/detail")
    public ChamomileResponse<GroupVO> getGroup(@RequestParam String groupId) {
        GroupVO result = groupService.getGroup(groupId);
        return new ChamomileResponse<>(result);
    }

    @GetMapping(value = "/check")
    public ChamomileResponse<Boolean> getGroupCheck(@RequestParam String groupId) {
        Boolean result = groupService.getGroupCheck(groupId);
        return new ChamomileResponse<>(result);
    }

    @PostMapping(value = "/create")
    public ChamomileResponse<Void> createGroup(@RequestBody @Validated GroupVO request) {
        groupService.createGroup(request);
        return new ChamomileResponse<>();
    }

    @PostMapping(value = "/update")
    public ChamomileResponse<Void> updateGroup(@RequestBody @Validated GroupVO request) {
        groupService.updateGroup(request);
        return new ChamomileResponse<>();
    }

    @PostMapping(value = "/delete")
    public ChamomileResponse<Void> deleteGroup(@RequestBody List<GroupVO> request) {
        groupService.deleteGroup(request);
        return new ChamomileResponse<>();
    }

    @PostMapping(value = "/excel/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ChamomileResponse<Void> excelUpload(@RequestParam("file") MultipartFile file) {
        List<GroupExcelVO> list = new ExcelImporter()
                .toCustomClass(file, GroupExcelVO.class);
        groupService.createGroup(list);
        return new ChamomileResponse<>();
    }

    @GetMapping("/excel/download")
    public ResponseEntity<byte[]> excelDownload(GroupQuery request, Pageable pageable) throws IOException {
        Page<GroupExcelVO> result = groupService.getGroupListExcel(request, pageable);
        ExcelExporter excelExporter = new ExcelExporter("group");
        excelExporter.addDataList(result.toList());
        return excelExporter.toHttpResponse();
    }

    @GetMapping("/excel/sample")
    public ResponseEntity<byte[]> excelSample() throws IOException {
        List<GroupExcelVO> list = new ArrayList<>();
        list.add(new GroupExcelVO());
        ExcelExporter excelExporter = new ExcelExporter("sample_group");
        excelExporter.addDataList(list);
        return excelExporter.toHttpResponse();
    }
}

