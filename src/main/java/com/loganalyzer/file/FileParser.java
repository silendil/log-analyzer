package com.loganalyzer.file;

import com.loganalyzer.model.LogRecord;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FileParser {
    private String filePath;

    public FileParser(String filePath){
        this.filePath = filePath;
    }

    public List<LogRecord> parseFile(){
        List<LogRecord> result = new LinkedList<>();
        try (Scanner scanner = new Scanner(new FileInputStream(filePath))){
            Map<String, LogRecord> records = new HashMap<>();
            while (scanner.hasNext()){
                String line = scanner.nextLine();
                result.add(LogRecord.generate(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
