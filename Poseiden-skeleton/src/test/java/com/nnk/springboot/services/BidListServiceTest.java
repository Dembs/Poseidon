package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BidListServiceTest {

    @Mock
    private BidListRepository bidListRepository;

    @InjectMocks
    private BidListService bidListService;

    @Test
    public void findAllBidListsTest() {
        List<BidList> list = List.of(
                new BidList("account1", "type1", 10.0),
                new BidList("account2", "type2", 20.0));
        when(bidListRepository.findAll()).thenReturn(list);

        List<BidList> result = bidListService.findAllBidLists();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(bidListRepository, times(1)).findAll();
    }

    @Test
    public void findByIdTest() {
        BidList bidList = new BidList("Test account", "Test type", 100.0);
        when(bidListRepository.findById(1)).thenReturn(Optional.of(bidList));

        Optional<BidList> result = bidListService.findById(1);

        assertTrue(result.isPresent());
        assertEquals("Test account", result.get().getAccount());
    }

    @Test
    public void saveBidListTest() {
        BidList bidList = new BidList("Test account", "Test type", 100.0);
        when(bidListRepository.save(any(BidList.class))).thenReturn(bidList);

        BidList result = bidListService.saveBidList(bidList);

        assertNotNull(result);
        assertEquals("Test account", result.getAccount());
        verify(bidListRepository, times(1)).save(bidList);
    }

    @Test
    public void updateBidListTest() {
        BidList bidList = new BidList("Test account", "Test type", 100.0);
        bidList.setBidListId(1);

        when(bidListRepository.save(any(BidList.class))).thenReturn(bidList);

        BidList updatedBidList = new BidList("Updated account", "Updated type", 200.0);
        updatedBidList.setBidListId(1);

        BidList result = bidListService.updateBidList(updatedBidList);

        assertNotNull(result);
        assertEquals(1, result.getBidListId());
        verify(bidListRepository, times(1)).save(any(BidList.class));
    }

    @Test
    public void deleteBidListTest() {
        doNothing().when(bidListRepository).deleteById(1);

        bidListService.deleteBidList(1);

        verify(bidListRepository, times(1)).deleteById(1);
    }

    @Test
    public void findByIdNotFoundTest() {
        when(bidListRepository.findById(999)).thenReturn(Optional.empty());

        Optional<BidList> result = bidListService.findById(999);

        assertFalse(result.isPresent());
    }
}