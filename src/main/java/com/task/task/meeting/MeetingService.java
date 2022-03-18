package com.task.task.meeting;

import org.hibernate.boot.internal.DefaultCustomEntityDirtinessStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MeetingService {
    @Autowired
    private MeetingRepository meetingRepository;
    public MeetingService(MeetingRepository repository){
        meetingRepository = repository;
    }
    public void createMeeting(String name, String responsiblePerson, String description, Category category, Type type, Date startDate, Date endDate){
        Meeting meeting = new Meeting(name,responsiblePerson, description,category,type,startDate,endDate);
        meetingRepository.addMeeting(meeting);
    }

    //returns true if successfully deleted
    //returns false if can not find the meeting
    public boolean deleteMeeting(String name, String responsiblePerson){
        List<Meeting> meetingList = meetingRepository.getAll();
        for(Meeting meeting : meetingList){
            if(meeting.getName().equals(name) && meeting.getResponsiblePerson().equals(responsiblePerson)){
                meetingList.remove(meeting);
                meetingRepository.removeMeeting(meeting);
                return true;
            }
        }
        return false;
    }
    public AddPersonResult addPerson(String meetingName, String personName){
        List<Meeting> meetingList = meetingRepository.getAll();
        for(Meeting meeting : meetingList){
            if(meeting.getName().equals(meetingName)){
                if(meeting.getPeopleInMeeting().contains(personName)){
                    return AddPersonResult.PERSON_ALREADY_IN_THE_MEETING;
                }else{
                    meeting.getPeopleInMeeting().add(personName);
                    meetingRepository.saveMeetings();
                    return AddPersonResult.ADD_SUCCESSFUL;
                }
            }
        }
        return AddPersonResult.NO_SUCH_MEETING;
    }
    public RemovePersonResult removePerson(String meetingName, String personName){
        List<Meeting> meetingList = meetingRepository.getAll();

        for(Meeting meeting : meetingList){

            if(meeting.getName().equals(meetingName)){

                if(meeting.getResponsiblePerson().equals(personName)){
                    return RemovePersonResult.PERSON_IS_RESPONSIBLE_FOR_MEETING;
                }else{
                    meeting.getPeopleInMeeting().remove(personName);
                    meetingRepository.saveMeetings();
                    return RemovePersonResult.REMOVED_SUCCESSFULLY;
                }
            }
        }
        return RemovePersonResult.NO_SUCH_MEETING;
    }

    public List<Meeting> getFilteredMeetings(String description, String responsiblePerson,
                                             String category, String type, Date startDate, Date endDate,int attendees) {


        List<Meeting> toReturn = new ArrayList<>();

        List<Meeting> meetingList = meetingRepository.getAll();
        for(Meeting meeting : meetingList){

            if(description != null && !meeting.getDescription().toLowerCase().contains(description.toLowerCase())){
                continue;
            }
            if(responsiblePerson != null && !meeting.getResponsiblePerson().equals(responsiblePerson)){
                continue;
            }
            if(category != null && !meeting.getCategory().getName().equals(category)){
                continue;
            }
            if(type != null && !meeting.getType().getName().equals(type)){
                continue;
            }
            if(startDate != null && meeting.getStartDate().before(startDate)){
                continue;
            }
            if(endDate != null && meeting.getEndDate().after(endDate)){
                continue;
            }
            if(meeting.getPeopleInMeeting().size() < attendees){
                continue;
            }

            toReturn.add(meeting);
        }
        return toReturn;
    }
}
