package net.lotte.chamomile.admin.resource.domain;

import org.mybatis.spring.SqlSessionTemplate;

import net.lotte.chamomile.core.context.SpringAppContext;
import net.lotte.chamomile.core.exception.ChamomileException;
import net.lotte.chamomile.core.exception.ChamomileExceptionCode;
import net.lotte.chamomile.module.excel.handler.ExcelStreamResultHandler;

public class BatchExcelMapper {

    private final SqlSessionTemplate sqlSessionTemplate;
    private final ExcelStreamResultHandler handler;
    private final String sqlMapperFullyQualifiedMethodName;

    public BatchExcelMapper(ExcelStreamResultHandler handler, String sqlMapperFullyQualifiedMethodName) {
        this.sqlSessionTemplate = SpringAppContext.getInstance().getApplicationContext().getBean(SqlSessionTemplate.class);
        this.sqlMapperFullyQualifiedMethodName = sqlMapperFullyQualifiedMethodName;
        this.handler = handler;
    }

    public void writeExcelFileOnHttpResponse() {
        try {
            sqlSessionTemplate.select(sqlMapperFullyQualifiedMethodName, null, handler);
            handler.close();
        } catch (ChamomileException var6) {
            ChamomileException ex = var6;
            throw new ChamomileException(ChamomileExceptionCode.DATABASE_EXCEPTION, "Exception executing in select mapper and ExcelStreamHandler", ex);
        }
    }
}
