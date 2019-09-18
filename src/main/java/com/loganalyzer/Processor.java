package com.loganalyzer;

import com.loganalyzer.db.HSQLDBService;
import com.loganalyzer.file.FileParser;
import com.loganalyzer.model.LogAnalyzedRecord;
import com.loganalyzer.model.LogRecord;
import com.loganalyzer.model.State;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Processor {
    private FileParser parser;
    private HSQLDBService service;


    public Processor(FileParser parser) {
        this.parser = parser;
        this.service = new HSQLDBService();
    }

    public boolean process() {
        Map<String, LogRecord> records = new HashMap<>();
        if (parser.parseFile().isEmpty())
            return false;
        for (LogRecord record : parser.parseFile()) {
            if (records.containsKey(record.getId())
                    && records.get(record.getId()).getState() != record.getState()) {
                LogAnalyzedRecord event;
                if (record.getState() == State.STARTED) {
                    event = new LogAnalyzedRecord(record, records.get(record.getId()));
                } else {
                    event = new LogAnalyzedRecord(records.get(record.getId()), record);
                }
                service.writeDetails(event);
            } else {
                records.put(record.getId(), record);
            }
        }
        return true;
    }

    public List<String> readData() {
        return service.readDetails();
    }
}
