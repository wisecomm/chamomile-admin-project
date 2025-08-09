package net.lotte.chamomile.admin.message.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import net.lotte.chamomile.admin.common.exception.code.AdminExceptionCode;
import net.lotte.chamomile.admin.message.api.dto.LanguageQuery;
import net.lotte.chamomile.admin.message.api.dto.LanguageUpdate;
import net.lotte.chamomile.admin.message.api.dto.MessageQuery;
import net.lotte.chamomile.admin.message.domain.LanguageVO;
import net.lotte.chamomile.admin.message.domain.MessageExcelUploadVO;
import net.lotte.chamomile.admin.message.domain.MessageMapper;
import net.lotte.chamomile.admin.message.domain.MessageVO;
import net.lotte.chamomile.core.exception.BusinessException;
import net.lotte.chamomile.module.database.audit.TimeAuthorLog;
import net.lotte.chamomile.module.database.mybatis.batch.BatchRequest;

/**
 * <pre>
 * 메시지 관련 서비스 구현체.
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
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageMapper messageMapper;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "message", condition = "#pageable.getPageSize() >= 0x7fffffff")
    public Page<MessageVO> getMessageList(MessageQuery request, Pageable pageable) {
        return messageMapper.findMessageList(request, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MessageVO> getMessageDetail(MessageQuery request) {
        return messageMapper.findMessageDetail(request);
    }

    @Override
    @CacheEvict(value = "message", allEntries = true)
    public void createMessage(List<MessageVO> request) {
        Optional<Boolean> exists = messageMapper.existsByKey(request);
        if(exists.isPresent()) {
            throw new DuplicateKeyException("Duplicate key exception");
        } else {
            request.forEach(TimeAuthorLog::onCreate);
            messageMapper.insertMessage(request, new BatchRequest(1000));
        }
    }

    @Override
    @CacheEvict(value = "message", allEntries = true)
    public void deleteMessage(List<MessageVO> request) {
        messageMapper.deleteMessage(request);
    }

    @Override
    @CacheEvict(value = "message", allEntries = true)
    public void updateMessage(List<MessageVO> request) {
        // 전체 삭제 후 재등록(코드값 조건)
        messageMapper.deleteMessageForInsert(request.get(0));
        request.forEach(TimeAuthorLog::onCreate);
        messageMapper.insertMessage(request, new BatchRequest(1000));
    }

    @Override
    public void createLanguage(LanguageVO request) {
        if(messageMapper.languageCnt(request) == 0) {
            request.onCreate();
            messageMapper.insertLanguage(request);
        } else {
            throw new DuplicateKeyException("Duplicate key exception");
        }
    }

    @Override
    @Transactional
    public void updateLanguage(List<LanguageUpdate> request) {
        // 변경 대상 중에 변경 전 코드와 중복이 있는지 확인
        for (LanguageUpdate language : request) {
            for (LanguageUpdate comp : request) {
                if ((!language.getLanguageCode().equals(comp.getLanguageCode()) && !language.getCountryCode().equals(comp.getCountryCode())) // 자기자신이 아니고
                        && (language.getLanguageCode().equals(comp.getPrevLanguageCode()) && language.getCountryCode().equals(comp.getPrevCountryCode()))) {
                    throw new DuplicateKeyException("Duplicate key on Update target exception");
                }
            }
        }

        for (LanguageUpdate language : request) {
            if((!language.getPrevLanguageCode().equals(language.getLanguageCode())
                            || !language.getPrevCountryCode().equals(language.getCountryCode()))
                    && messageMapper.languageCnt(language.toLanguageVO()) > 0) {
                throw new DuplicateKeyException("Duplicate key exception");
            }
            language.onUpdate();

            // 메시지의 언어/국가코드 일괄 수정
            messageMapper.updateMessageLanguage(language);

            messageMapper.updateLanguage(language);
        }
    }

    @Override
    public List<LanguageVO> getLanguageList() {
        return messageMapper.findLanguageList();
    }

    @Override
    public List<LanguageVO> getLanguageDetail(LanguageQuery request) {
        return messageMapper.findLanguageDetail(request);
    }

    @Override
    @CacheEvict(value = "message", allEntries = true)
    public void createBulkMessage(List<MessageExcelUploadVO> list) {
        List<MessageVO> checkList = new ArrayList<>();
        List<MessageVO> insertList = new ArrayList<>();
        for(MessageExcelUploadVO vo : list) {
            MessageVO checkVO = new MessageVO();
            BeanUtils.copyProperties(vo, checkVO);
            checkList.add(checkVO);
        }
        List<MessageVO> alreadyExistList = messageMapper.findMessageListByKey(checkList);
        for (MessageVO insertVO : checkList) {
            if(alreadyExistList.stream()
                    .noneMatch(e ->
                            e.getCode().equals(insertVO.getCode()) &&
                            e.getCountryCode().equals(insertVO.getCountryCode()) &&
                            e.getLanguageCode().equals(insertVO.getLanguageCode()))) {
                insertVO.onCreate();
                insertList.add(insertVO);
            }
        }
        messageMapper.insertMessage(insertList, new BatchRequest(1000));
    }

    @Override
    public Page<MessageVO> getAlertMessageList(MessageQuery request, Pageable pageable) {
        request.setSearchRegYn("0");
        return messageMapper.findMessageList(request, pageable);
    }

    @Override
    public List<LanguageVO> hasMessage(List<LanguageVO> request) {
        if (CollectionUtils.isEmpty(request)) {
            throw new BusinessException(AdminExceptionCode.ServerError);
        }

        return request.stream()
                .filter(vo -> vo.getLanguageCode() != null && vo.getCountryCode() != null)
                .filter(vo -> messageMapper.languageMessageCnt(vo) > 0)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteLanguage(List<LanguageVO> request) {
        if (CollectionUtils.isEmpty(request)) {
            throw new BusinessException(AdminExceptionCode.ServerError);
        }

        if (!hasMessage(request).isEmpty()) {
            throw new BusinessException(AdminExceptionCode.BadRequest);
        }

        request.stream()
                .filter(vo -> vo.getLanguageCode() == null || vo.getCountryCode() == null)
                        .findFirst()
                                .ifPresent(vo -> {
                                    throw new BusinessException(AdminExceptionCode.DataNotFoundError);
                                });

        BatchRequest batchRequest = new BatchRequest(1000);

        messageMapper.deleteLanguage(request, batchRequest);
    }
}
