package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TradeService {

    private final TradeRepository tradeRepository;

    @Autowired
    public TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    public List<Trade> findAll() {
        return tradeRepository.findAll();
    }

    public Optional<Trade> findById(Integer id) {
        return tradeRepository.findById(id);
    }

    public Trade save(Trade trade) {
        return tradeRepository.save(trade);
    }

    public Trade update(Trade trade) {
        if (trade.getTradeId() == null) {
            throw new IllegalArgumentException("Trade ID cannot be null for update operation");
        }

        // Verify that the trade exists
        if (!tradeRepository.existsById(trade.getTradeId())) {
            throw new IllegalArgumentException("Trade with ID " + trade.getTradeId() + " not found");
        }

        return tradeRepository.save(trade);
    }

    public void delete(Integer id) {
        if (!tradeRepository.existsById(id)) {
            throw new IllegalArgumentException("Trade with ID " + id + " not found");
        }
        tradeRepository.deleteById(id);
    }
}