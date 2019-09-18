package com.loganalyzer.model;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class LogRecord {
    private static ObjectMapper mapper = new ObjectMapper();

    private String id;
    private State state;
    private long timestamp;
    private String host;
    private String type;

    public void setState(String stateName){
        this.state = State.byName(stateName);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public State getState() {
        return state;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setState(State state){
        this.state = state;
    }

    public static LogRecord generate(String record) throws IOException {
        return mapper.readValue(record, LogRecord.class);
    }

}
