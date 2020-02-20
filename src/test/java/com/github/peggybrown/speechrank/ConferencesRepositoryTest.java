package com.github.peggybrown.speechrank;

import com.github.peggybrown.speechrank.dto.ConferenceDto;
import com.github.peggybrown.speechrank.dto.ConferenceImportDto;
import com.github.peggybrown.speechrank.dto.YearDto;
import com.github.peggybrown.speechrank.entity.Comment;
import com.github.peggybrown.speechrank.entity.Conference;
import com.github.peggybrown.speechrank.entity.Presentation;
import com.github.peggybrown.speechrank.entity.Rate;
import javaslang.collection.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ConferencesRepositoryTest {

    ConferencesRepository mockedConfRepo;
    ConferencesRepository confRepo;

    @BeforeEach
    void setUp() {
        mockedConfRepo = Mockito.mock(ConferencesRepository.class);
        confRepo = new ConferencesRepository("xyz");
    }
    @Test
    void shouldAddRateToSpecificPresentation() {
        //given
        Rate rate = setRate(5);
        //when
        doNothing().when(mockedConfRepo).add(isA(Rate.class));
        mockedConfRepo.add(rate);
        //then
        verify(mockedConfRepo, times(1)).add(rate);
    }

    @Test
    void shouldAddCommentToSpecificPresentation() {
        //when
        doNothing().when(mockedConfRepo).add(isA(Comment.class));
        mockedConfRepo.add(new Comment());
        //then
        verify(mockedConfRepo, times(1)).add(new Comment());
    }

    @Test
    void shouldReturnListOfConferences() {
        //given
        java.util.List<Conference> cenferencesList = new ArrayList<>();
        javaslang.collection.List<Presentation> listOfPresentation = List.empty();
        Conference conf = new Conference(UUID.randomUUID().toString(), "DevConf", listOfPresentation);
        cenferencesList.add(conf);
        //when
        Mockito.when(mockedConfRepo.getConferences()).thenReturn(cenferencesList);
        //then
        assertThat(mockedConfRepo.getConferences()).contains(conf);
    }

    @Test
    void shouldReturnListOfYears() {
        //when
        java.util.List<YearDto> list = confRepo.getYears();
        java.util.List<String> listOfYears = list.stream()
            .map(YearDto::getYear)
            .collect(Collectors.toList());
        //then
        assertThat(listOfYears).contains("2019", "2018", "2017");
    }

    @Test
    void shouldReturnConference() {
        //given
        confRepo.importAllConferences();
        //when
        ConferenceDto actual = confRepo.getConference("21");
        //then
        assertThat(actual).hasFieldOrPropertyWithValue("name", "Boiling Frogs");
    }

    @Test
    void shouldImportConference() {
        //given
        ConferenceImportDto conf = new ConferenceImportDto();
        conf.setName("Caffeerence");
        conf.setYear("2018");
        conf.setPlaylistLink("link");
        //when
        String id = confRepo.importConference(conf);
        ConferenceDto actual = confRepo.getConference(id);
        //then
        assertThat(actual).hasFieldOrPropertyWithValue("name", "Caffeerence");
    }

    private Rate setRate(int rateVal) {
        Rate rate = new Rate();
        rate.setUserId(UUID.randomUUID().toString());
        rate.setPresentationId(UUID.randomUUID().toString());
        rate.setRate(rateVal);
        return rate;
    }
}
