package net.lotte.chamomile.admin.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import net.lotte.chamomile.admin.authuser.domain.AuthUserVO;

@Mapper
public interface UserHistoryMapper {
    List<UserHistoryVO> findUserHistory(String userId);
}
