package net.lotte.chamomile.admin.authgroup.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import org.springframework.web.multipart.MultipartFile;

import net.lotte.chamomile.admin.authgroup.api.AuthGroupController;
import net.lotte.chamomile.module.database.audit.TimeAuthorLog;

/**
 * <pre>
 * 권한 그룹 엑셀용 VO.
 * </pre>
 *
 * @author MoonHKLee
 * @since 2023-11-01
 * @version 3.0
 * @see AuthGroupController#excelUpload(MultipartFile)
 * @Modification
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023-11-01     MoonHKLee            최초 생성
 *
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AuthGroupExcelVO extends TimeAuthorLog {
    private String groupId;
    private String roleId;
    private String useYn;
}
