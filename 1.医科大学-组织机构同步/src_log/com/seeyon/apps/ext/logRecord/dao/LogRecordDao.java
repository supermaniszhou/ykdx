package com.seeyon.apps.ext.logRecord.dao;

import com.seeyon.apps.ext.logRecord.po.LogRecord;
import com.seeyon.ctp.util.FlipInfo;

import java.util.List;
import java.util.Map;

public interface LogRecordDao {
    void saveLogRecord(LogRecord logRecord);

    void updateLogRecord(LogRecord logRecord);

    FlipInfo selectAllPage(FlipInfo flipInfo, Map<String, Object> params);
}
