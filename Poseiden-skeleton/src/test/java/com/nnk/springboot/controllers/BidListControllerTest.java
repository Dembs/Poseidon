package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class BidListControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BidListService bidListService;

    @InjectMocks
    private BidListController controller;

    private BidList bidList;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        bidList = new BidList("Account Test", "Type Test", 10.00);
        bidList.setBidListId(1);
    }

    @Test
    public void testHome() throws Exception {
        // Given
        when(bidListService.findAllBidLists()).thenReturn(Arrays.asList(bidList));

        // When & Then
        mockMvc.perform(get("/bidList/list"))
               .andExpect(status().isOk())
               .andExpect(view().name("bidList/list"))
               .andExpect(model().attributeExists("bidLists"));

        verify(bidListService).findAllBidLists();
    }

    @Test
    public void testAddBidForm() throws Exception {
        mockMvc.perform(get("/bidList/add"))
               .andExpect(status().isOk())
               .andExpect(view().name("bidList/add"));
    }

    @Test
    public void testValidate() throws Exception {
        when(bidListService.saveBidList(any(BidList.class))).thenReturn(bidList);

        mockMvc.perform(post("/bidList/validate")
                       .param("account", "Test Account")
                       .param("type", "Test Type")
                       .param("bidQuantity", "10.0"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/bidList/list"));

        verify(bidListService).saveBidList(any(BidList.class));
    }

    @Test
    public void testValidateInvalid() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                       .param("account", "")
                       .param("type", "")
                       .param("bidQuantity", "10.0"))
               .andExpect(status().isOk())
               .andExpect(view().name("bidList/add"))
               .andExpect(model().hasErrors());

        verify(bidListService, never()).saveBidList(any());
    }

    @Test
    public void testShowUpdateForm() throws Exception {
        when(bidListService.findById(1)).thenReturn(Optional.of(bidList));

        mockMvc.perform(get("/bidList/update/1"))
               .andExpect(status().isOk())
               .andExpect(view().name("bidList/update"))
               .andExpect(model().attributeExists("bidList"));

        verify(bidListService).findById(1);
    }

    @Test
    public void testUpdateBid() throws Exception {
        when(bidListService.updateBidList(any(BidList.class))).thenReturn(bidList);

        mockMvc.perform(post("/bidList/update/1")
                       .param("bidListId", "1")
                       .param("account", "Updated Account")
                       .param("type", "Updated Type")
                       .param("bidQuantity", "20.0"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/bidList/list"));

        verify(bidListService).updateBidList(any(BidList.class));
    }

    @Test
    public void testUpdateBidInvalid() throws Exception {
        mockMvc.perform(post("/bidList/update/1")
                       .param("bidListId", "1")
                       .param("account", "")
                       .param("type", "")
                       .param("bidQuantity", "20.0"))
               .andExpect(status().isOk())
               .andExpect(view().name("bidList/update"))
               .andExpect(model().hasErrors());

        verify(bidListService, never()).updateBidList(any());
    }

    @Test
    public void testDeleteBid() throws Exception {
        when(bidListService.findById(1)).thenReturn(Optional.of(bidList));
        doNothing().when(bidListService).deleteBidList(1);

        mockMvc.perform(get("/bidList/delete/1"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/bidList/list"));

        verify(bidListService).findById(1);
        verify(bidListService).deleteBidList(1);
    }

    @Test
    public void testDeleteBidError() throws Exception {
        when(bidListService.findById(1)).thenReturn(Optional.of(bidList));
        doThrow(new RuntimeException("Error deleting bid")).when(bidListService).deleteBidList(1);

        mockMvc.perform(get("/bidList/delete/1"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/bidList/list"));

        verify(bidListService).findById(1);
        verify(bidListService).deleteBidList(1);
    }
}