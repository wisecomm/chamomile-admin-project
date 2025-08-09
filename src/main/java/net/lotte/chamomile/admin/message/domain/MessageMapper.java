package net.lotte.chamomile.admin.message.domain;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.lotte.chamomile.admin.message.api.dto.LanguageQuery;
import net.lotte.chamomile.admin.message.api.dto.LanguageUpdate;
import net.lotte.chamomile.admin.message.api.dto.MessageQuery;
import net.lotte.chamomile.module.database.mybatis.batch.BatchRequest;

/**
 * <pre>
 * 메시지 관련 Mapper.
 * </pre>
 *
 * @author chaelynJang
 * @version 3.0
 * @Modification <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023-10-05     chaelynJang            최초 생성
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 * @since 2023-10-05
 */
@Mapper
public interface MessageMapper {
    Page<MessageVO> findMessageList(MessageQuery request, Pageable pageable);

    List<MessageVO> findMessageDetail(MessageQuery request);

    Optional<Boolean> existsByKey(List<MessageVO> request);

    void insertMessage(List<MessageVO> request, BatchRequest batchRequest);

    void deleteMessage(List<MessageVO> request);

    void deleteMessageForInsert(MessageVO messageVO);

    int languageCnt(LanguageVO request);

    int languageMessageCnt(LanguageVO request);

    void insertLanguage(LanguageVO request);

    List<LanguageVO> findLanguageList();

    List<LanguageVO> findLanguageDetail(LanguageQuery request);

    List<MessageVO> findMessageListByKey(List<MessageVO> request);

    Page<MessageVO> findAlertMessageList(Map<String, Object> map, Pageable pageable);

    void updateLanguage(LanguageUpdate languageVO);

    void updateMessageLanguage(LanguageUpdate language);

    void deleteLanguage(List<LanguageVO> request, BatchRequest batchRequest);
}
