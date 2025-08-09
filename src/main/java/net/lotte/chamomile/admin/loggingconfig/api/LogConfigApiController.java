package net.lotte.chamomile.admin.loggingconfig.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.lotte.chamomile.admin.loggingconfig.service.LogConfigService;
import net.lotte.chamomile.module.logging.config.AppenderInfoVO;
import net.lotte.chamomile.module.logging.config.LoggerInfoVO;
import net.lotte.chamomile.module.web.variable.ChamomileResponse;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/chmm/logging-config")
public class LogConfigApiController implements LogConfigApiControllerDoc {

    private final LogConfigService logConfigService;


    @GetMapping("/logger")
    public ChamomileResponse<Page<LoggerInfoVO>> getLoggerList(Pageable pageable) {
        return new ChamomileResponse<>(logConfigService.getLoggerList(pageable));
    }

    @GetMapping("/appender")
    public ChamomileResponse<Page<AppenderInfoVO>> getAppenderList(Pageable pageable) {
        return new ChamomileResponse<>(logConfigService.getAppenderList(pageable));
    }

    @PostMapping("/logger/update")
    public ChamomileResponse<Void> updateLoggerLevel(@RequestBody LoggerInfoVO loggerInfoVO) {
        logConfigService.saveLoggerLevel(loggerInfoVO.getName(), loggerInfoVO.getLevel().toString());
        return new ChamomileResponse<>();
    }


    @GetMapping("/detail")
    public ChamomileResponse<String> getConfigDetail() {
        String log4j2xml = logConfigService.readLog4j2XmlFile();
        return new ChamomileResponse<>(log4j2xml);
    }


    @PostMapping("/update")
    public ChamomileResponse<Void> updateConfig(@RequestBody String document) {
        logConfigService.writeLog4j2XmlFile(document);
        return new ChamomileResponse<>();
    }

}
