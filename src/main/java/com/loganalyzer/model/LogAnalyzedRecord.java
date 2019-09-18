package com.loganalyzer.model;


import java.io.Serializable;

public class LogAnalyzedRecord implements Serializable {
    private String id;
    private long duration;
    private String host;
    private String type;

    public boolean getAlert(){
        return duration > 4;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
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

    public LogAnalyzedRecord(LogRecord startEvent, LogRecord finishEvent){
        if (startEvent.getId().equals(finishEvent.getId())){
            id = startEvent.getId();
            duration = finishEvent.getTimestamp() - startEvent.getTimestamp();
            host = startEvent.getHost() != null ? startEvent.getHost() : finishEvent.getHost();
            type = startEvent.getType() != null ? startEvent.getType() : finishEvent.getType();
        }
    }
}
