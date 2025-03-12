package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class RatingControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RatingService ratingService;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private RatingController ratingController;

    private Rating rating1;
    private Rating rating2;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ratingController).build();

        rating1 = new Rating("Moodys Rating 1", "Sand P Rating 1", "Fitch Rating 1", 10);
        rating1.setId(1);

        rating2 = new Rating("Moodys Rating 2", "Sand P Rating 2", "Fitch Rating 2", 20);
        rating2.setId(2);
    }

    @Test
    public void testHome() throws Exception {

        when(ratingService.findAll()).thenReturn(Arrays.asList(rating1, rating2));

        mockMvc.perform(get("/rating/list"))
               .andExpect(status().isOk())
               .andExpect(view().name("rating/list"))
               .andExpect(model().attributeExists("ratings"))
               .andExpect(model().attribute("ratings", Arrays.asList(rating1, rating2)));

        verify(ratingService, times(1)).findAll();
    }

    @Test
    public void testAddRatingForm() throws Exception {
        mockMvc.perform(get("/rating/add"))
               .andExpect(status().isOk())
               .andExpect(view().name("rating/add"))
               .andExpect(model().attributeExists("rating"));
    }

    @Test
    public void testValidate() throws Exception {
        Rating validRating = new Rating("Moodys Rating", "Sand P Rating", "Fitch Rating", 10);

        mockMvc.perform(post("/rating/validate")
                       .flashAttr("rating", validRating))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/rating/list"));

        verify(ratingService, times(1)).save(any(Rating.class));
    }


    @Test
    public void testShowUpdateForm() throws Exception {
        when(ratingService.findById(1)).thenReturn(Optional.of(rating1));

        mockMvc.perform(get("/rating/update/1"))
               .andExpect(status().isOk())
               .andExpect(view().name("rating/update"))
               .andExpect(model().attributeExists("rating"))
               .andExpect(model().attribute("rating", rating1));

        verify(ratingService, times(1)).findById(1);
    }

    @Test
    public void testUpdateRating() throws Exception {

        mockMvc.perform(post("/rating/update/1")
                       .flashAttr("rating", rating1))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/rating/list"));

        verify(ratingService, times(1)).update(any(Rating.class));
    }

    @Test
    public void testDeleteRating() throws Exception {
        when(ratingService.findById(1)).thenReturn(Optional.of(rating1));
        doNothing().when(ratingService).delete(1);

        mockMvc.perform(get("/rating/delete/1"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/rating/list"));

        verify(ratingService, times(1)).findById(1);
        verify(ratingService, times(1)).delete(1);
    }

}