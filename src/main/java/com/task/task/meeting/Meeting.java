package com.task.task.meeting;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Meeting {
    private String name;
    private String responsiblePerson;
    private String description;
    private Category category;
    private Type type;
    private Date startDate;
    private Date endDate;
    private List<String> peopleInMeeting;
    public Meeting(){

    }
    public Meeting(String name, String responsiblePerson, String description, Category category, Type type, Date startDate, Date endDate) {
        this.name = name;
        this.responsiblePerson = responsiblePerson;
        this.description = description;
        this.category = category;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.peopleInMeeting = new ArrayList();
        this.peopleInMeeting.add(responsiblePerson);
    }
    public String getName() {
        return name;
    }

    public String getResponsiblePerson() {
        return responsiblePerson;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public Type getType() {
        return type;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<String> getPeopleInMeeting() {
        return peopleInMeeting;
    }
}
