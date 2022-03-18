package com.task.task.meeting;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MeetingRepository {
    private List<Meeting> meetingList = new ArrayList<>();
    ///@Value( "${storage.filename}" )
    private String fileName = "meetings.txt";
    public MeetingRepository(boolean test){

    }
    public MeetingRepository() throws IOException {
        File file = new File(fileName);
        if(file.exists()){
            String content = new String(Files.readAllBytes(Paths.get(fileName)));
            ObjectMapper objectMapper = new ObjectMapper();
            meetingList = objectMapper.readValue(content,new TypeReference<List<Meeting>>(){});
        }
    }
    public void addMeeting(Meeting meeting){
        meetingList.add(meeting);
        saveMeetings();
    }
    public void removeMeeting(Meeting meeting){
        meetingList.remove(meeting);
        saveMeetings();
    }
    public void saveMeetings(){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonString = objectMapper.writeValueAsString(meetingList);
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(jsonString);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public List<Meeting> getAll(){
        return meetingList;
    }
}
