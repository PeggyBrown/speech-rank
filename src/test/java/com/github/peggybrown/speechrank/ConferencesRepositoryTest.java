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

    ConferencesRepository confRepo;

    @BeforeEach
    void setUp() {
        confRepo = Mockito.mock(ConferencesRepository.class);
    }
    @Test
    void shouldAddRateToSpecificPresentation() {
        //when
        doNothing().when(confRepo).add(isA(Rate.class));
        Rate rate = setRate(5);
        confRepo.add(rate);
        //then
        verify(confRepo, times(1)).add(rate);
    }

    @Test
    void shouldAddCommentToSpecificPresentation() {
        //when
        doNothing().when(confRepo).add(isA(Comment.class));
        confRepo.add(new Comment());
        //then
        verify(confRepo, times(1)).add(new Comment());
    }

    @Test
    void shouldReturnListOfConferences() {
        //given
        java.util.List<Conference> cenferencesList = new ArrayList<>();
        javaslang.collection.List<Presentation> listOfPresentation = List.empty();
        Conference conf = new Conference(UUID.randomUUID().toString(), "DevConf", listOfPresentation);
        cenferencesList.add(conf);
        //when
        Mockito.when(confRepo.getConferences()).thenReturn(cenferencesList);
        //then
        assertThat(confRepo.getConferences()).contains(conf);
    }

    @Test
    void shouldReturnListOfYears() {
        //given
        ConferencesRepository confRepo = new ConferencesRepository("xyz");
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
        ConferencesRepository confRepo = new ConferencesRepository("xyz");
        confRepo.importAllConferences();
        //when
        ConferenceDto actual = confRepo.getConference("21");
        //then
        assertThat(actual).hasFieldOrPropertyWithValue("name", "Boiling Frogs");
    }

    @Test
    void shouldImportConference() {
        //given
        ConferencesRepository confRepo = new ConferencesRepository("xyz");
        confRepo.importAllConferences();
        //when
        ConferenceImportDto conf = new ConferenceImportDto();
        conf.setName("Caffeerence");
        conf.setYear("2018");
        conf.setPlaylistLink("link");

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
