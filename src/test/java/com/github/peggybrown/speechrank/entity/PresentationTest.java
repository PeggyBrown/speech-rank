package com.github.peggybrown.speechrank.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PresentationTest {

    Presentation presentation;
    @BeforeEach
    void setUp() {
        presentation =  new Presentation();
    }

    @Test
    void shouldAddComment() {
        //given
        Comment comment = new Comment();
        comment.setComment("Great video!");
        //when
        presentation.addComment(comment);
        //then
        assertThat(presentation.getComments().get(0)).isEqualTo(comment);
    }

    @Test
    void shouldAddRate() {
        //given
        Rate rate = setRateToBeAdded(4);
        //when
        Double value = presentation.addRate(rate);
        //then
        assertThat(new Double(4.0)).isEqualTo(value);
    }

    @Test
    void shouldReturnAverageValueOfRates() {
        //given
        Rate rate = setRateToBeAdded(4);
        Rate rate2 = setRateToBeAdded(5);
        //when
        presentation.addRate(rate);
        Double value = presentation.addRate(rate2);
        //then
        assertThat(new Double(4.5)).isEqualTo(value);

    }

    private Rate setRateToBeAdded(int val){
        Rate rate = new Rate();
        rate.setRate(val);
        return rate;
    }
}
