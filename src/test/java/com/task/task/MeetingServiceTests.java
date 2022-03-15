package com.task.task;

import com.task.task.meeting.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@SpringBootTest
public class MeetingServiceTests {
    private MeetingService testMeetingService;
    private MeetingRepository testMeetingRepository;
    private MeetingServiceTests()  {
        testMeetingRepository= new MeetingRepository(true);
        testMeetingService = new MeetingService(testMeetingRepository);
    }
    @Test
    void testCreateMeeting() throws ParseException {
        testMeetingService.createMeeting("meeting","Person","test description", Category.SHORT, Type.LIVE,
                new SimpleDateFormat("yyyy-MM-dd").parse("2022-03-16"),new SimpleDateFormat("yyyy-MM-dd").parse("2022-03-17"));
        Assertions.assertThat(testMeetingRepository.getAll().size()).isEqualTo(1);
    }
    @Test
    void testDeleteMeeting() throws ParseException {
        testMeetingService.createMeeting("meeting","Person","test description", Category.SHORT, Type.LIVE,
                new SimpleDateFormat("yyyy-MM-dd").parse("2022-03-16"),new SimpleDateFormat("yyyy-MM-dd").parse("2022-03-17"));
        testMeetingService.deleteMeeting("meeting","Person");
        Assertions.assertThat(testMeetingRepository.getAll().size()).isEqualTo(0);
    }
    @Test
    void testAddPerson() throws ParseException {
        testMeetingService.createMeeting("meeting1","Person","test description", Category.SHORT, Type.LIVE,
                new SimpleDateFormat("yyyy-MM-dd").parse("2022-03-16"),new SimpleDateFormat("yyyy-MM-dd").parse("2022-03-17"));
        testMeetingService.addPerson("meeting1","person2");

        Assertions.assertThat(testMeetingRepository.getAll().get(0).getPeopleInMeeting().size()).isEqualTo(2);
    }
    @Test
    void testRemovePerson() throws ParseException {
        testMeetingService.createMeeting("meeting2","Person","test description", Category.SHORT, Type.LIVE,
                new SimpleDateFormat("yyyy-MM-dd").parse("2022-03-16"),new SimpleDateFormat("yyyy-MM-dd").parse("2022-03-17"));
        testMeetingService.addPerson("meeting2","person2");
        testMeetingService.removePerson("meeting2","person2");

        Assertions.assertThat(testMeetingRepository.getAll().get(0).getPeopleInMeeting().size()).isEqualTo(1);

        testMeetingService.removePerson("meeting2","Person");
        Assertions.assertThat(testMeetingRepository.getAll().get(0).getPeopleInMeeting().size()).isEqualTo(1);
    }
    @Test
    void testGetFilteredMeetings() throws ParseException {
        testMeetingService.createMeeting("meeting1","Person","test description", Category.SHORT, Type.LIVE,
                new SimpleDateFormat("yyyy-MM-dd").parse("2022-03-16"),new SimpleDateFormat("yyyy-MM-dd").parse("2022-03-17"));
        testMeetingService.createMeeting("meeting2","Person","test description2", Category.SHORT, Type.LIVE,
                new SimpleDateFormat("yyyy-MM-dd").parse("2022-03-16"),new SimpleDateFormat("yyyy-MM-dd").parse("2022-03-17"));

        List<Meeting> filteredList = testMeetingService.getFilteredMeetings("description2", null,null,null,
                null,null,0);

        Assertions.assertThat(filteredList.size()).isEqualTo(1);

    }

}
