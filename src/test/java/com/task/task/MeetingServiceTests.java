package com.task.task;

import com.task.task.meeting.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@TestPropertySource(locations = "classpath:./test.properties")
@SpringBootTest
public class MeetingServiceTests {
    @Autowired private MeetingService testMeetingService;
    @Autowired private MeetingRepository testMeetingRepository;
    private MeetingServiceTests()  { }

    @Test
    void testCreateMeeting() throws ParseException {

        int amountBefore = testMeetingRepository.getAll().size();

        testMeetingService.createMeeting("meeting","Person","test description", Category.SHORT, Type.LIVE,
                new SimpleDateFormat("yyyy-MM-dd").parse("2022-03-16"),new SimpleDateFormat("yyyy-MM-dd").parse("2022-03-17"));

        Assertions.assertThat(testMeetingRepository.getAll().size()).isEqualTo(amountBefore+1);
    }
    @Test
    void testDeleteMeeting() throws ParseException {

        int amountBefore = testMeetingRepository.getAll().size();

        testMeetingService.createMeeting("meeting","Person","test description", Category.SHORT, Type.LIVE,
                new SimpleDateFormat("yyyy-MM-dd").parse("2022-03-16"),new SimpleDateFormat("yyyy-MM-dd").parse("2022-03-17"));

        testMeetingService.deleteMeeting("meeting","Person");

        Assertions.assertThat(testMeetingRepository.getAll().size()).isEqualTo(amountBefore);
    }
    @Test
    void testAddPerson() throws ParseException {

        testMeetingRepository.getAll().clear();

        testMeetingService.createMeeting("meeting1","Person","test description", Category.SHORT, Type.LIVE,
                new SimpleDateFormat("yyyy-MM-dd").parse("2022-03-16"),new SimpleDateFormat("yyyy-MM-dd").parse("2022-03-17"));

        testMeetingService.addPerson("meeting1","person2");

        Assertions.assertThat(testMeetingRepository.getAll().get(0).getPeopleInMeeting().size()).isEqualTo(2);
    }
    @Test
    void testRemovePerson() throws ParseException {
        testMeetingService.createMeeting("meeting3","Person","test description", Category.SHORT, Type.LIVE,
                new SimpleDateFormat("yyyy-MM-dd").parse("2022-03-16"),new SimpleDateFormat("yyyy-MM-dd").parse("2022-03-17"));

        testMeetingService.addPerson("meeting3","person2");

        testMeetingService.removePerson("meeting3","person2");

        int index = testMeetingRepository.getAll().size()-1;

        Assertions.assertThat(testMeetingRepository.getAll().get(index).getPeopleInMeeting().size()).isEqualTo(1);

        testMeetingService.removePerson("meeting3","Person");

        Assertions.assertThat(testMeetingRepository.getAll().get(index).getPeopleInMeeting().size()).isEqualTo(1);
    }
    @Test
    void testGetFilteredMeetings() throws ParseException {
        testMeetingRepository.getAll().clear();

        testMeetingService.createMeeting("meeting4","Person","test description6", Category.SHORT, Type.LIVE,
                new SimpleDateFormat("yyyy-MM-dd").parse("2022-03-16"),new SimpleDateFormat("yyyy-MM-dd").parse("2022-03-17"));

        testMeetingService.createMeeting("meeting5","Person2","test description7", Category.HUB, Type.LIVE,
                new SimpleDateFormat("yyyy-MM-dd").parse("2022-03-16"),new SimpleDateFormat("yyyy-MM-dd").parse("2022-03-17"));

        List<Meeting> filteredList = testMeetingService.getFilteredMeetings("description6", "Person","Short",null,
                null,null,0);

        Assertions.assertThat(filteredList.size()).isEqualTo(1);

        List<Meeting> filteredList2 = testMeetingService.getFilteredMeetings("description", null,null,null,
                null,null,0);

        Assertions.assertThat(filteredList2.size()).isEqualTo(2);

    }

}
