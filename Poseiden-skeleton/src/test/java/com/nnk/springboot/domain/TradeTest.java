package com.nnk.springboot.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TradeTest {

    @Test
    public void testTradeCreation() {
        // Given
        Trade trade = new Trade();
        trade.setAccount("Account Test");
        trade.setType("Type Test");
        trade.setBuyQuantity(100.0);
        trade.setSellQuantity(50.0);
        trade.setBuyPrice(10.5);
        trade.setSellPrice(11.5);
        trade.setBenchmark("Benchmark Test");
        trade.setSecurity("Security Test");
        trade.setStatus("Status Test");
        trade.setTrader("Trader Test");
        trade.setBook("Book Test");

        // Then
        assertEquals("Account Test", trade.getAccount());
        assertEquals("Type Test", trade.getType());
        assertEquals(100.0, trade.getBuyQuantity());
        assertEquals(50.0, trade.getSellQuantity());
        assertEquals(10.5, trade.getBuyPrice());
        assertEquals(11.5, trade.getSellPrice());
        assertEquals("Benchmark Test", trade.getBenchmark());
        assertEquals("Security Test", trade.getSecurity());
        assertEquals("Status Test", trade.getStatus());
        assertEquals("Trader Test", trade.getTrader());
        assertEquals("Book Test", trade.getBook());
    }


}