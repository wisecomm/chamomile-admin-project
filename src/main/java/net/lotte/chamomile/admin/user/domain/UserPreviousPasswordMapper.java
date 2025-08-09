package net.lotte.chamomile.admin.user.domain;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserPreviousPasswordMapper {

    void deleteOldestPassword(String userId);

    List<String> getAllPasswordsByUserId(String userId);

    void insertNewPassword(String userId, String passwordHash);
}
