package net.lotte.chamomile.admin.loggingconfig.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.lotte.chamomile.core.exception.ChamomileException;
import net.lotte.chamomile.module.logging.config.AppenderInfoVO;
import net.lotte.chamomile.module.logging.config.LoggerInfoVO;


public interface LogConfigService {

    Page<LoggerInfoVO> getLoggerList(Pageable pageable);

    Page<AppenderInfoVO> getAppenderList(Pageable pageable);


    void saveLoggerLevel(String name, String level);

    String readLog4j2XmlFile() throws ChamomileException;

    void writeLog4j2XmlFile(String document) throws ChamomileException;

}
