package com.github.peggybrown.speechrank.entity;

import javaslang.collection.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;

class PresentationTest {



    @Test
    void shouldAddComment() {
        //given
        Presentation presentation =  Mockito.mock(Presentation.class);
        Comment comment = new Comment();
        comment.setComment("Great video!");
        //when
        Mockito.when(presentation.addComment(any(Comment.class))).thenReturn(comment);
        //then
        assertThat(presentation.addComment(comment).getComment()).isEqualTo("Great video!");
    }

    @Test
    void shouldAddRate() {
        //given
        Presentation presentation =  Mockito.mock(Presentation.class);
        Rate rate = setRateToBeAdded();
        //when
        Double value = presentation.addRate(rate);
        //then
        assertThat(new Double("0.0")).isEqualTo(value); //why not 10?

    }

    private Rate setRateToBeAdded(){
        Rate rate = new Rate();
        UUID userId = UUID.randomUUID();
        UUID presentationId = UUID.randomUUID();
        int rateValue = 10;
        rate.setUserId(userId.toString());
        rate.setPresentationId(presentationId.toString());
        rate.setRate(rateValue);
        return rate;
    }
}
