package com.task.task.meeting;

public enum Type {

    LIVE("Live"),
    IN_PERSON("InPerson");
    String name;

    Type(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Type getByName(String name){
        for(Type type:values()){
            if(type.name.equals(name)){
                return type;
            }
        }
        return null;
    }
}
