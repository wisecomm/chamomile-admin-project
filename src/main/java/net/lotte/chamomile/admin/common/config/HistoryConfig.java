package net.lotte.chamomile.admin.common.config;

import java.sql.PreparedStatement;

import org.slf4j.MDC;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.security.core.context.SecurityContextHolder;

import net.lotte.chamomile.module.web.utils.HttpLoggingUtils;

@Configuration
public class HistoryConfig {

    @Bean
    public PreparedStatementCreator revisionPreparedStatement() {
        return con -> {
            PreparedStatement pstmt = con.prepareStatement(
                    "INSERT INTO REVINFO (REVTSTMP, USER_ID, CLIENT_IP) VALUES (?, ?, ?)", new String[] {"REV"});
            pstmt.setLong(1, System.currentTimeMillis());
            pstmt.setString(2, SecurityContextHolder.getContext().getAuthentication().getName());
            pstmt.setString(3, MDC.get(HttpLoggingUtils.CHMM_CLIENT_IP));
            return pstmt;
        };
    }
}
