package com.task.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.task.meeting.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class MeetingController {
    @Autowired
    private MeetingService meetingService;
    @RequestMapping("/create_meeting")
    public String createMeeting(HttpServletRequest request, String name, String responsiblePerson, String description, String category, String type,
                                String startDate, String endDate) throws ParseException {

        Category categoryEnum = Category.getByName(category);

        if(categoryEnum == null){
            return "Incorrect category.";
        }

        Type typeEnum = Type.getByName(type);

        if(typeEnum == null){
            return "Incorrect type.";
        }

        Date dateStart = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
        Date dateEnd = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);

        meetingService.createMeeting(name,responsiblePerson,description,categoryEnum,typeEnum,dateStart,dateEnd);

        return "Meeting created.";
    }
    @RequestMapping("/delete_meeting")
    public String deleteMeeting(HttpServletRequest request, String meetingName, String responsiblePerson) {

        if(meetingService.deleteMeeting(meetingName,responsiblePerson)){
            return "Meeting deleted.";
        }else{
            return "Could not find meeting.";
        }
    }
    @RequestMapping("/add_person")
    public String addPersonToMeeting(HttpServletRequest request, String meetingName, String person) {

        AddPersonResult result = meetingService.addPerson(meetingName, person);

        if (result.equals(AddPersonResult.ADD_SUCCESSFUL)) {
            return "Person added.";
        }else if (result.equals(AddPersonResult.PERSON_ALREADY_IN_THE_MEETING)) {
            return "Person with such name exists in the meeting.";
        }else if (result.equals(AddPersonResult.NO_SUCH_MEETING)) {
            return "Meeting with name '"+meetingName+"' does not exist.";
        }

        return "";
    }
    @RequestMapping("/remove_person")
    public String removePersonFromMeeting(HttpServletRequest request, String meetingName, String person) {

        RemovePersonResult result = meetingService.removePerson(meetingName, person);

        if (result.equals(RemovePersonResult.REMOVED_SUCCESSFULLY)) {
            return "Person removed.";
        }else if (result.equals(RemovePersonResult.PERSON_IS_RESPONSIBLE_FOR_MEETING)) {
            return "Person is responsible for the meeting.";
        }else if (result.equals(RemovePersonResult.NO_SUCH_MEETING)) {
            return "Meeting with name '"+meetingName+"' does not exist.";
        }
        return "";
    }

    @RequestMapping("/get_all_meetings")
    public String getAllMeetings(HttpServletRequest request, String description, String responsiblePerson,
                                 String category, String type,  String startDate, String endDate, @RequestParam(value = "attendees", defaultValue = "0") int attendees) throws ParseException, JsonProcessingException {

        Date dateStart = null;
        Date dateEnd = null;

        if(startDate!=null){
            dateStart = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
        }
        if(endDate!=null){
            dateEnd = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
        }

       List<Meeting> filteredMeetings  = meetingService.getFilteredMeetings(description, responsiblePerson,
               category, type, dateStart,dateEnd, attendees);

        ObjectMapper objectMapper = new ObjectMapper();

       return objectMapper.writeValueAsString(filteredMeetings);
    }
}
