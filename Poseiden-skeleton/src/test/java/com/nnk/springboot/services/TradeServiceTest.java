package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TradeServiceTest {

    @Mock
    private TradeRepository tradeRepository;

    @InjectMocks
    private TradeService tradeService;

    private Trade trade1;
    private Trade trade2;

    @BeforeEach
    public void setUp() {
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
    public void testFindAll() {
        when(tradeRepository.findAll()).thenReturn(Arrays.asList(trade1, trade2));

        List<Trade> result = tradeService.findAll();

        assertEquals(2, result.size());
        assertEquals("Account 1", result.get(0).getAccount());
        assertEquals("Account 2", result.get(1).getAccount());
        verify(tradeRepository, times(1)).findAll();
    }

    @Test
    public void testFindById() {

        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade1));

        Optional<Trade> result = tradeService.findById(1);

        assertTrue(result.isPresent());
        assertEquals("Account 1", result.get().getAccount());
        verify(tradeRepository, times(1)).findById(1);
    }


    @Test
    public void testSave() {

        Trade newTrade = new Trade("New Account", "New Type");
        newTrade.setBuyQuantity(300.0);

        when(tradeRepository.save(any(Trade.class))).thenReturn(newTrade);

        Trade result = tradeService.save(newTrade);

        assertEquals("New Account", result.getAccount());
        verify(tradeRepository, times(1)).save(newTrade);
    }

    @Test
    public void testUpdate() {
        Trade updatedTrade = new Trade("Updated Account", "Updated Type");
        updatedTrade.setTradeId(1);
        updatedTrade.setBuyQuantity(150.0);

        when(tradeRepository.existsById(1)).thenReturn(true);
        when(tradeRepository.save(any(Trade.class))).thenReturn(updatedTrade);

        Trade result = tradeService.update(updatedTrade);

        assertEquals("Updated Account", result.getAccount());
        verify(tradeRepository, times(1)).existsById(1);
        verify(tradeRepository, times(1)).save(updatedTrade);
    }

    @Test
    public void testDelete() {
        when(tradeRepository.existsById(1)).thenReturn(true);
        doNothing().when(tradeRepository).deleteById(1);

        tradeService.delete(1);

        verify(tradeRepository, times(1)).existsById(1);
        verify(tradeRepository, times(1)).deleteById(1);
    }
}