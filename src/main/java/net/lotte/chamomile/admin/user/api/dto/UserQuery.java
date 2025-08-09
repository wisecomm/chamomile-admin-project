package net.lotte.chamomile.admin.user.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <pre>
 * 사용자 관련 HTTP READ 요청 객체.
 * </pre>
 *
 * @author TaehoPark
 * @since 2023-09-19
 * @version 3.0
 * @Modification
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023-09-19     TaehoPark            최초 생성
 *
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 */
@Data
public class UserQuery {

    @Schema(description = "검색유형", example = "searchUserId")
    private String searchSelect;
    @Schema(description = "검색어", example = "test")
    private String searchValue;
    @Schema(description = "검색ID", example = "admin")
    private String searchUserId;
    @Schema(description = "사용자명", example = " ")
    private String searchUserName;
    @Schema(description = "검색휴대폰번호", example = "010-1234-5678")
    private String searchUserMobile;
    @Schema(description = "검색이메일", example = "abcd@lotte.net")
    private String searchUserEmail;
    @Schema(description = "사용여부", example = "1")
    private String searchUseYn;
    @Schema(description = "사용자ID", example = "admin")
    private String userId;
    @Schema(description = "하위", example = " ")
    private String hideChild;

    @Schema(description = "로그타입", example = "UserAccessActionType.LOGIN_SUCCESS")
    private String searchLogType;

    @Schema(description = "검색사용자그룹")
    private String searchUserGroup;
    @Schema(description = "모바일기능 - 기기관리여부")
    private String searchMdmYn;
    @Schema(description = "모바일기능")
    private String mobileFlag;
}
