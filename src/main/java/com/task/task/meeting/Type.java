package com.task.task.meeting;

public enum Type {

    LIVE("Live"),
    INPERSON("InPerson");
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
