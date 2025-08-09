package net.lotte.chamomile.admin.message.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.lotte.chamomile.admin.message.api.dto.LanguageQuery;
import net.lotte.chamomile.admin.message.api.dto.LanguageUpdate;
import net.lotte.chamomile.admin.message.api.dto.MessageQuery;
import net.lotte.chamomile.admin.message.domain.LanguageVO;
import net.lotte.chamomile.admin.message.domain.MessageExcelUploadVO;
import net.lotte.chamomile.admin.message.domain.MessageVO;

/**
 * <pre>
 * 메시지 관련 서비스.
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
public interface MessageService {
    Page<MessageVO> getMessageList(MessageQuery request, Pageable pageable);

    List<MessageVO> getMessageDetail(MessageQuery request);

    void createMessage(List<MessageVO> request);

    void deleteMessage(List<MessageVO> request);

    void updateMessage(List<MessageVO> request);

    void createLanguage(LanguageVO request);

    void updateLanguage(List<LanguageUpdate> request);

    List<LanguageVO> getLanguageList();

    List<LanguageVO> getLanguageDetail(LanguageQuery request);

    void createBulkMessage(List<MessageExcelUploadVO> request);

    Page<MessageVO> getAlertMessageList(MessageQuery request, Pageable pageable);

    List<LanguageVO> hasMessage(List<LanguageVO> request);

    void deleteLanguage(List<LanguageVO> request);
}
