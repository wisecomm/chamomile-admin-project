package net.lotte.chamomile.admin.loggingconfig.service;

import org.apache.logging.log4j.spi.StandardLevel;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.lotte.chamomile.core.exception.ChamomileException;
import net.lotte.chamomile.module.database.pageable.PagingUtil;
import net.lotte.chamomile.module.logging.config.AppenderInfoVO;
import net.lotte.chamomile.module.logging.config.Log4j2ConfigUtil;
import net.lotte.chamomile.module.logging.config.LoggerInfoVO;


@Slf4j
@Service
@RequiredArgsConstructor
public class LogConfigServiceImpl implements LogConfigService {

    public Page<LoggerInfoVO> getLoggerList(Pageable pageable) {
        return PagingUtil.listToPage(Log4j2ConfigUtil.getLoggerList(), pageable);
    }


    public Page<AppenderInfoVO> getAppenderList(Pageable pageable) {
        return PagingUtil.listToPage(Log4j2ConfigUtil.getAppenderList(), pageable);
    }


    public void saveLoggerLevel(String name, String level) {
        Log4j2ConfigUtil.changeLogLevel(name, StandardLevel.valueOf(level.toUpperCase()));
    }


    public String readLog4j2XmlFile() throws ChamomileException {
        return Log4j2ConfigUtil.readLog4j2XmlFile();
    }

    public void writeLog4j2XmlFile(String document) throws ChamomileException {
        Log4j2ConfigUtil.writeLog4j2XmlFile(document);
    }

}
