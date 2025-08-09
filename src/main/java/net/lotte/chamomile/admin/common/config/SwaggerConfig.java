package net.lotte.chamomile.admin.common.config;

import java.util.Collections;

import org.springdoc.core.GroupedOpenApi;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * <pre>
 * 스웨거 관련 설정 파일.
 * </pre>
 *
 * @author MoonHKLee
 * @version 3.0
 * @Modification <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023-09-15     MoonHKLee            최초 생성
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 * @since 2023-09-15
 */
@Configuration
public class SwaggerConfig {

    private static final String BASE_PACKAGE = "net.lotte.chamomile.admin";
    private static final String MOBILE_BASE_PACKAGE = "net.lotte.chamomile.module.mobile.admin.controller";

    @Bean
    public OpenAPI openAPI() {
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER).name("Authorization");
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

        return new OpenAPI()
                .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
                .security(Collections.singletonList(securityRequirement));
    }

    @Bean
    public GroupedOpenApi authApi() {
        final String url = "auth";
        return GroupedOpenApi.builder()
                .group(url)
                .pathsToMatch("/chmm/" + url + "/**")
                .packagesToScan(BASE_PACKAGE + "." + url)
                .build();
    }

    @Bean
    public GroupedOpenApi authGroupApi() {
        final String url = "auth-group";
        return GroupedOpenApi.builder()
                .group(url)
                .pathsToMatch("/chmm/" + url + "/**")
                .packagesToScan(BASE_PACKAGE + ".authgroup")
                .build();
    }

    @Bean
    public GroupedOpenApi authHierarchyApi() {
        final String url = "auth-hierarchy";
        return GroupedOpenApi.builder()
                .group(url)
                .pathsToMatch("/chmm/" + url + "/**")
                .packagesToScan(BASE_PACKAGE + ".authhierarchy")
                .build();
    }

    @Bean
    public GroupedOpenApi authResourceApi() {
        final String url = "auth-resource";
        return GroupedOpenApi.builder()
                .group(url)
                .pathsToMatch("/chmm/" + url + "/**")
                .packagesToScan(BASE_PACKAGE + ".authresource")
                .build();
    }

    @Bean
    public GroupedOpenApi authUserApi() {
        final String url = "auth-user";
        return GroupedOpenApi.builder()
                .group(url)
                .pathsToMatch("/chmm/" + url + "/**")
                .packagesToScan(BASE_PACKAGE + ".authuser")
                .build();
    }

    @Bean
    public GroupedOpenApi commonCodeApi() {
        final String url = "common-code";
        return GroupedOpenApi.builder()
                .group(url)
                .pathsToMatch("/chmm/" + url + "/**")
                .packagesToScan(BASE_PACKAGE + ".commoncode")
                .build();
    }

    @Bean
    public GroupedOpenApi groupApi() {
        final String url = "group";
        return GroupedOpenApi.builder()
                .group(url)
                .pathsToMatch("/chmm/" + url + "/**")
                .packagesToScan(BASE_PACKAGE + "." + url)
                .build();
    }

    @Bean
    public GroupedOpenApi groupHierarchyApi() {
        final String url = "group-hierarchy";
        return GroupedOpenApi.builder()
                .group(url)
                .pathsToMatch("/chmm/" + url + "/**")
                .packagesToScan(BASE_PACKAGE + ".grouphierarchy")
                .build();
    }

    @Bean
    public GroupedOpenApi groupUserApi() {
        final String url = "group-user";
        return GroupedOpenApi.builder()
                .group(url)
                .pathsToMatch("/chmm/" + url + "/**")
                .packagesToScan(BASE_PACKAGE + ".groupuser")
                .build();
    }

    @Bean
    public GroupedOpenApi loginApi() {
        final String url = "/chmm/security";
        return GroupedOpenApi.builder()
                .group("login")
                .pathsToMatch(url + "/**")
                .packagesToScan(BASE_PACKAGE + ".login")
                .build();
    }

    @Bean
    public GroupedOpenApi menuApi() {
        final String url = "menu";
        return GroupedOpenApi.builder()
                .group(url)
                .pathsToMatch("/chmm/" + url + "/**")
                .packagesToScan(BASE_PACKAGE + "." + url)
                .build();
    }

    @Bean
    public GroupedOpenApi messageApi() {
        final String url = "message";
        return GroupedOpenApi.builder()
                .group(url)
                .pathsToMatch("/chmm/" + url + "/**")
                .packagesToScan(BASE_PACKAGE + "." + url)
                .build();
    }

    @Bean
    public GroupedOpenApi resourceApi() {
        final String url = "resource";
        return GroupedOpenApi.builder()
                .group(url)
                .pathsToMatch("/chmm/" + url + "/**")
                .packagesToScan(BASE_PACKAGE + "." + url)
                .build();
    }

    @Bean
    public GroupedOpenApi userApi() {
        final String url = "user";
        return GroupedOpenApi.builder()
                .group(url)
                .pathsToMatch("/chmm/" + url + "/**")
                .packagesToScan(BASE_PACKAGE + "." + url)
                .build();
    }

    @Bean
    public GroupedOpenApi menuAuthApi() {
        final String url = "menu-auth";
        final String url2 = "auth-menu";
        return GroupedOpenApi.builder()
                .group(url)
                .pathsToMatch("/chmm/" + url + "/**", "/chmm/" + url2 + "/**")
                .packagesToScan(BASE_PACKAGE + "." + url)
                .build();
    }

    @Bean
    public GroupedOpenApi loggingApi() {
        final String url = "logging";
        return GroupedOpenApi.builder()
                .group(url)
                .pathsToMatch("/chmm/" + url + "/**")
                .packagesToScan(BASE_PACKAGE + "." + url)
                .build();
    }

    @Bean
    public GroupedOpenApi loggingConfigApi() {
        final String url = "logging-config";
        return GroupedOpenApi.builder()
                .group(url)
                .pathsToMatch("/chmm/" + url + "/**")
                .packagesToScan(BASE_PACKAGE + "." + "loggingConfig")
                .build();
    }

    @Bean
    public GroupedOpenApi boardApi() {
        final String url = "board";
        return GroupedOpenApi.builder()
                .group(url)
                .pathsToMatch("/chmm/" + url + "/**")
                .packagesToScan(BASE_PACKAGE + "." + url)
                .build();
    }

    @Bean
    public GroupedOpenApi cacheApi() {
        final String url = "cache";
        return GroupedOpenApi.builder()
                .group(url)
                .pathsToMatch("/chmm/" + url + "/**")
                .packagesToScan(BASE_PACKAGE + ".common.cache")
                .build();
    }

    @Bean
    public GroupedOpenApi webpageLogApi() {
        final String url = "webpage-log";
        return GroupedOpenApi.builder()
                .group(url)
                .pathsToMatch("/chmm/" + url + "/**")
                .packagesToScan(BASE_PACKAGE + ".webpagelog")
                .build();
    }
    @Bean
    public GroupedOpenApi appApi() {
        final String url = "app";
        return GroupedOpenApi.builder()
                .group(url)
                .pathsToMatch("/chmm/admin/mobile/" + url + "/**")
                .packagesToScan(MOBILE_BASE_PACKAGE)
                .build();
    }

    @Bean
    public GroupedOpenApi appFileApi() {
        final String url = "app-file";
        return GroupedOpenApi.builder()
                .group(url)
                .pathsToMatch("/chmm/admin/mobile/" + url + "/**")
                .packagesToScan(MOBILE_BASE_PACKAGE)
                .build();
    }

    @Bean
    public GroupedOpenApi appstoreApi() {
        final String url = "appstore";
        return GroupedOpenApi.builder()
                .group(url)
                .pathsToMatch("/" + url + "/**")
                .packagesToScan(MOBILE_BASE_PACKAGE)
                .build();
    }

    @Bean
    public GroupedOpenApi appstoreAdminApi() {
        final String url = "appstore";
        return GroupedOpenApi.builder()
                .group("appstore-admin")
                .pathsToMatch("/chmm/admin/mobile/" + url + "/**")
                .packagesToScan(MOBILE_BASE_PACKAGE)
                .build();
    }

    @Bean
    public GroupedOpenApi mobileFileApi() {
        return GroupedOpenApi.builder()
                .group("mobile-file")
                .pathsToMatch("/chmm/admin/mobile/file/**")
                .packagesToScan(MOBILE_BASE_PACKAGE)
                .build();
    }

    @Bean
    public GroupedOpenApi appDeviceApi() {
        final String url = "app-device";
        return GroupedOpenApi.builder()
                .group(url)
                .pathsToMatch("/chmm/admin/mobile/" + url + "/**")
                .packagesToScan(MOBILE_BASE_PACKAGE)
                .build();
    }

    @Bean
    public GroupedOpenApi privacyPolicyApi() {
        final String url = "privacy-policy";
        return GroupedOpenApi.builder()
                .group(url)
                .pathsToMatch("/chmm/" + url + "/**")
                .packagesToScan(BASE_PACKAGE + "." + url)
                .build();
    }

}
