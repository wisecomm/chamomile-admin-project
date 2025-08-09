package net.lotte.chamomile.admin.login;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.RequestBody;

import net.lotte.chamomile.admin.resource.api.ResourceController;
import net.lotte.chamomile.module.security.jwt.vo.JwtRequest;
import net.lotte.chamomile.module.web.variable.ChamomileResponse;

/**
 * <pre>
 * 리소스 관련 컨트롤러 스웨거 문서.
 * </pre>
 *
 * @author MoonHKLee
 * @since 2023-09-15
 * @version 3.0
 * @see ResourceController
 * @Modification
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023-09-15     MoonHKLee            최초 생성
 *
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 */
@Tag(name = "캐모마일 어드민 로그인 API")
public interface SecurityControllerDoc {
    @Operation(summary = "로그인 요청", description = "로그인 요청을합니다.")
    ChamomileResponse<Map<String, String>> authenticate(@RequestBody JwtRequest request) throws Exception;
    @Operation(summary = "리프래쉬 요청", description = "리프래쉬 토큰을 요청합니다.")
    ChamomileResponse<Map<String, String>> refreshToken(HttpServletRequest request) throws Exception;

    @Operation(summary = "RSA암호화 패스워드", description = "RSA암호화 패스워드를 요청합니다..")
    ChamomileResponse<String> encrypt(String plainText) throws Exception;

    @Operation(summary = "2팩터 pre 로그인", description = "2팩터 pre 로그인 요청을합니다.")
    public ChamomileResponse preLogin(@RequestBody JwtRequest jwtRequest) throws Exception;

}
