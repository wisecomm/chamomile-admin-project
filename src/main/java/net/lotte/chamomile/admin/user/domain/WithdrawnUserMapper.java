package net.lotte.chamomile.admin.user.domain;

import java.time.LocalDateTime;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WithdrawnUserMapper {

    void insertWithdrawnUser(@Param("userId") String userId, @Param("withdrawalDate") LocalDateTime withdrawalDate);

    LocalDateTime selectWithdrawalDateByUserId(@Param("userId") String userId);

    void deleteWithdrawnUserByUserId(@Param("userId") String userId);
}
