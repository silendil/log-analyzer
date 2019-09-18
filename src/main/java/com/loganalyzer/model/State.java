package com.loganalyzer.model;

public enum State {
    STARTED("STARTED"), FINISHED("FINISHED");

    private String name;

    State(String name){
        this.name = name;
    }

    public static State byName(String name){
        for(State value : values()){
            if (value.name.equals(name))
                return value;
        }
        return null;
    }
}
