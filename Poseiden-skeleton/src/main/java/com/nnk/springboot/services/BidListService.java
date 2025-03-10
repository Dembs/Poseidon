package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BidListService {

    @Autowired
    private BidListRepository bidListRepository;

    @Transactional
    public BidList saveBidList(BidList bidList) {
        return bidListRepository.save(bidList);
    }

    public List<BidList> findAllBidLists() {
        return bidListRepository.findAll();
    }

    public Optional<BidList> findById(Integer id) {
        return bidListRepository.findById(id);
    }

    @Transactional
    public BidList updateBidList(BidList bidList) {
        return bidListRepository.save(bidList);
    }

    @Transactional
    public void deleteBidList(Integer id) {
        bidListRepository.deleteById(id);
    }
}