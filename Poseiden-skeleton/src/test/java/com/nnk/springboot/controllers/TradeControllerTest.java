package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
import org.junit.jupiter.api.BeforeEach;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class TradeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TradeService tradeService;

    @InjectMocks
    private TradeController tradeController;

    private Trade trade1;
    private Trade trade2;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(tradeController).build();

        trade1 = new Trade("Account 1", "Type 1");
        trade1.setTradeId(1);
        trade1.setBuyQuantity(100.0);
        trade1.setSellQuantity(50.0);

        trade2 = new Trade("Account 2", "Type 2");
        trade2.setTradeId(2);
        trade2.setBuyQuantity(200.0);
        trade2.setSellQuantity(150.0);
    }

    @Test
    public void testHome() throws Exception {
        when(tradeService.findAll()).thenReturn(Arrays.asList(trade1, trade2));

        mockMvc.perform(get("/trade/list"))
               .andExpect(status().isOk())
               .andExpect(view().name("trade/list"))
               .andExpect(model().attributeExists("trades"))
               .andExpect(model().attribute("trades", Arrays.asList(trade1, trade2)));

        verify(tradeService, times(1)).findAll();
    }

    @Test
    public void testAddTradeForm() throws Exception {
        mockMvc.perform(get("/trade/add"))
               .andExpect(status().isOk())
               .andExpect(view().name("trade/add"))
               .andExpect(model().attributeExists("trade"));
    }

    @Test
    public void testValidate() throws Exception {
        Trade validTrade = new Trade("Test Account", "Test Type");
        validTrade.setBuyQuantity(300.0);

        mockMvc.perform(post("/trade/validate")
                       .flashAttr("trade", validTrade))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/trade/list"));

        verify(tradeService, times(1)).save(any(Trade.class));
    }

    @Test
    public void testShowUpdateForm() throws Exception {
        when(tradeService.findById(1)).thenReturn(Optional.of(trade1));

        mockMvc.perform(get("/trade/update/1"))
               .andExpect(status().isOk())
               .andExpect(view().name("trade/update"))
               .andExpect(model().attributeExists("trade"))
               .andExpect(model().attribute("trade", trade1));

        verify(tradeService, times(1)).findById(1);
    }

    @Test
    public void testUpdateTrade() throws Exception {
        Trade updatedTrade = new Trade("Updated Account", "Updated Type");
        updatedTrade.setTradeId(1);
        updatedTrade.setBuyQuantity(150.0);

        mockMvc.perform(post("/trade/update/1")
                       .flashAttr("trade", updatedTrade))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/trade/list"));

        verify(tradeService, times(1)).update(any(Trade.class));
    }

    @Test
    public void testDeleteTrade() throws Exception {
        when(tradeService.findById(1)).thenReturn(Optional.of(trade1));
        doNothing().when(tradeService).delete(1);

        mockMvc.perform(get("/trade/delete/1"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/trade/list"));

        verify(tradeService, times(1)).findById(1);
        verify(tradeService, times(1)).delete(1);
    }
}