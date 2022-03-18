package com.task.task.meeting;

public enum Category {

    CODE_MONKEY("CodeMonkey"),
    HUB("Hub"),
    SHORT("Short"),
    TEAM_BUILDING("TeamBuilding");
    String name;

    Category(String name){
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public static Category getByName(String name){
        for(Category category:values()){
            if(category.name.equals(name)){
                return category;
            }
        }
        return null;
    }
}
