package com.nnk.springboot.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RatingTests {

    @Test
    public void testRatingCreation() {
        // Given
        Rating rating = new Rating();
        rating.setMoodysRating("Moodys Rating");
        rating.setSandPRating("Sand P Rating");
        rating.setFitchRating("Fitch Rating");
        rating.setOrderNumber(10);

        // Then
        assertEquals("Moodys Rating", rating.getMoodysRating());
        assertEquals("Sand P Rating", rating.getSandPRating());
        assertEquals("Fitch Rating", rating.getFitchRating());
        assertEquals(10, rating.getOrderNumber());
    }
}