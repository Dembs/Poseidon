package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {

    @Mock
    private RatingRepository ratingRepository;

    @InjectMocks
    private RatingService ratingService;

    private Rating rating1;
    private Rating rating2;

    @BeforeEach
    public void setUp() {
        rating1 = new Rating("Moodys Rating 1", "Sand P Rating 1", "Fitch Rating 1", 10);
        rating1.setId(1);

        rating2 = new Rating("Moodys Rating 2", "Sand P Rating 2", "Fitch Rating 2", 20);
        rating2.setId(2);
    }

    @Test
    public void testFindAll() {
        when(ratingRepository.findAll()).thenReturn(Arrays.asList(rating1, rating2));

        List<Rating> ratings = ratingService.findAll();

        assertEquals(2, ratings.size());
        assertEquals(rating1, ratings.get(0));
        assertEquals(rating2, ratings.get(1));
        verify(ratingRepository, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        when(ratingRepository.findById(1)).thenReturn(Optional.of(rating1));

        Optional<Rating> result = ratingService.findById(1);

        assertTrue(result.isPresent());
        assertEquals(rating1, result.get());
        verify(ratingRepository, times(1)).findById(1);
    }

    @Test
    public void testFindById_NotFound() {

        when(ratingRepository.findById(3)).thenReturn(Optional.empty());

        Optional<Rating> result = ratingService.findById(3);

        assertFalse(result.isPresent());
        verify(ratingRepository, times(1)).findById(3);
    }

    @Test
    public void testSave() {
        Rating newRating = new Rating("New Moodys", "New SandP", "New Fitch", 30);

        Rating savedRating = new Rating("New Moodys", "New SandP", "New Fitch", 30);
        savedRating.setId(3);

        when(ratingRepository.save(newRating)).thenReturn(savedRating);

        Rating result = ratingService.save(newRating);

        assertEquals(savedRating, result);
        verify(ratingRepository, times(1)).save(newRating);
    }

    @Test
    public void testUpdate() {
        Rating updatedRating = new Rating("Updated Moodys", "Updated SandP", "Updated Fitch", 15);
        updatedRating.setId(1);

        when(ratingRepository.save(updatedRating)).thenReturn(updatedRating);

        Rating result = ratingService.update(updatedRating);

        assertEquals(updatedRating, result);
        verify(ratingRepository, times(1)).save(updatedRating);
    }

    @Test
    public void testDelete() {
        doNothing().when(ratingRepository).deleteById(1);

        ratingService.delete(1);

        verify(ratingRepository, times(1)).deleteById(1);
    }
}