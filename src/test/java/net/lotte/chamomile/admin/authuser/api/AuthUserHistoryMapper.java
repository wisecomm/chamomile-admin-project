package net.lotte.chamomile.admin.authuser.api;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import net.lotte.chamomile.admin.authuser.domain.AuthUserVO;

@Mapper
public interface AuthUserHistoryMapper {
    List<AuthUserVO> findAuthUserHistory(String userId);
}
