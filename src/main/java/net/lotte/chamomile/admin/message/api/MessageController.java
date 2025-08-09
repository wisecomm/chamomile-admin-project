package net.lotte.chamomile.admin.message.api;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.lotte.chamomile.admin.message.api.dto.LanguageQuery;
import net.lotte.chamomile.admin.message.api.dto.LanguageUpdate;
import net.lotte.chamomile.admin.message.api.dto.MessageQuery;
import net.lotte.chamomile.admin.message.domain.LanguageVO;
import net.lotte.chamomile.admin.message.domain.MessageExcelUploadVO;
import net.lotte.chamomile.admin.message.domain.MessageVO;
import net.lotte.chamomile.admin.message.service.MessageService;
import net.lotte.chamomile.module.excel.ExcelExporter;
import net.lotte.chamomile.module.excel.ExcelImporter;
import net.lotte.chamomile.module.file.util.FileUploader;
import net.lotte.chamomile.module.web.variable.ChamomileResponse;

/**
 * <pre>
 * 메시지 REST API 관련 컨트롤러.
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
@RestController
@RequestMapping(path = "/chmm/message")
@RequiredArgsConstructor
public class MessageController implements MessageControllerDoc {

    private final MessageService messageService;
    private final FileUploader fileUpload;

    @GetMapping("/list")
    public ChamomileResponse<Page<MessageVO>> getMessageList(MessageQuery request, Pageable pageable) {
        return new ChamomileResponse<>(messageService.getMessageList(request, pageable));
    }

    @GetMapping("/list/all")
    public ChamomileResponse<List<MessageVO>> getMessageAllList(MessageQuery request) {
        return new ChamomileResponse<>(messageService.getMessageList(request, PageRequest.of(0, Integer.MAX_VALUE)).getContent());
    }

    @GetMapping("/list/language/all")
    public ChamomileResponse<List<MessageVO>> getMessageAllListByLanguage(MessageQuery request) {
        if (!StringUtils.hasLength(request.getSearchCountryCode()) && !StringUtils.hasLength(request.getSearchLanguageCode())) {
            Locale locale = Locale.KOREA;
            request.setSearchCountryCode(String.valueOf(locale.getCountry()));
            request.setSearchLanguageCode(String.valueOf(locale.getLanguage()));
        }
        return new ChamomileResponse<>(messageService.getMessageList(request, PageRequest.of(0, Integer.MAX_VALUE)).getContent());
    }

    @GetMapping("/detail")
    public ChamomileResponse<List<MessageVO>> getMessageDetail(MessageQuery request) {
        return new ChamomileResponse<>(messageService.getMessageDetail(request));
    }

    @PostMapping("/create")
    public ChamomileResponse<Void> createMessage(@Validated @RequestBody List<MessageVO> request) {
        messageService.createMessage(request);
        return new ChamomileResponse<>();
    }

    @PostMapping("/delete")
    public ChamomileResponse<Void> deleteMessage(@RequestBody List<MessageVO> request) {
        messageService.deleteMessage(request);
        return new ChamomileResponse<>();
    }

    @PostMapping("/update")
    public ChamomileResponse<Void> updateMessage(@RequestBody List<MessageVO> request) {
        messageService.updateMessage(request);
        return new ChamomileResponse<>();
    }

    @PostMapping("/language/create")
    public ChamomileResponse<Void> createLanguage(@Validated @RequestBody LanguageVO request) {
        messageService.createLanguage(request);
        return new ChamomileResponse<>();
    }

    @PostMapping("/language/update")
    public ChamomileResponse<Void> updateLanguage(@RequestBody List<LanguageUpdate> request) {
        messageService.updateLanguage(request);
        return new ChamomileResponse<>();
    }

    @GetMapping("/language/list")
    public ChamomileResponse<List<LanguageVO>> getLanguageList() {
        return new ChamomileResponse<>(messageService.getLanguageList());
    }

    @GetMapping("/language/detail")
    public ChamomileResponse<List<LanguageVO>> getLanguageDetail(LanguageQuery request) {
        return new ChamomileResponse<>(messageService.getLanguageDetail(request));
    }

    @GetMapping("/excel/download")
    public ResponseEntity<byte[]> exportMenuExcel(MessageQuery request) throws Exception {
        String docName = URLEncoder.encode("message", "UTF-8");
        ExcelExporter excelExporter = new ExcelExporter(docName);
        excelExporter.addDataList(messageService.getMessageList(request
                , PageRequest.of(0, Integer.MAX_VALUE)).getContent());
        return excelExporter.toHttpResponse();
    }

    @GetMapping("/excel/sample")
    public ResponseEntity<byte[]> exportMessageTemplateExcel() throws Exception {
        List<MessageExcelUploadVO> list = new ArrayList<>();
        list.add(new MessageExcelUploadVO("코드", "언어코드", "국가코드", "해당언어 메시지"));
        String docName = URLEncoder.encode("sample_menu", "UTF-8");
        ExcelExporter excelExporter = new ExcelExporter(docName);
        excelExporter.addDataList(list);
        return excelExporter.toHttpResponse();
    }

    @PostMapping(value = "/excel/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ChamomileResponse<Void> excelUpload(@RequestParam("file") MultipartFile file) {
        List<MessageExcelUploadVO> list = new ExcelImporter().toCustomClass(file, MessageExcelUploadVO.class);
        messageService.createBulkMessage(list);
        return new ChamomileResponse<>();
    }

    @GetMapping("/alert/list")
    public ChamomileResponse<Page<MessageVO>> getAlertMessageList(MessageQuery request, Pageable pageable) {
        return new ChamomileResponse<>(messageService.getAlertMessageList(request, pageable));
    }

    @PostMapping("/language/has-message")
    public ChamomileResponse<List<LanguageVO>> hasLanguageMessage(@RequestBody List<LanguageVO> request) {
        return new ChamomileResponse<>(messageService.hasMessage(request));
    }

    @PostMapping("/language/delete")
    public ChamomileResponse<Void> deleteLanguage(@RequestBody List<LanguageVO> request) {
        messageService.deleteLanguage(request);
        return new ChamomileResponse<>();
    }
}
