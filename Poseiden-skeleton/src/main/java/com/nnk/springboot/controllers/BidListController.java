package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class BidListController {

    @Autowired
    private BidListService bidListService;

    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
        //call service find all bids to show to the view
        List<BidList> bidLists = bidListService.findAllBidLists();
        model.addAttribute("bidLists", bidLists);
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {

        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        //check data valid and save to db, after saving return bid list
        if (result.hasErrors()) {
            return "bidList/add";
        }

        try {
            bidListService.saveBidList(bid);
            return "redirect:/bidList/list";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error saving bid: " + e.getMessage());
            return "bidList/add";
        }
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        //get Bid by Id and to model then show to the form
        BidList bidList = bidListService.findById(id)
                                        .orElseThrow(() -> new IllegalArgumentException("Invalid bid Id:" + id));

        model.addAttribute("bidList", bidList);
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model) {
        //check required fields, if valid call service to update Bid and return list Bid
        if (result.hasErrors()) {
            bidList.setBidListId(id);
            return "bidList/update";
        }

        try {
            // Mettre à jour l'ID et sauvegarder
            bidList.setBidListId(id);
            bidListService.updateBidList(bidList);
            return "redirect:/bidList/list";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error updating bid: " + e.getMessage());
            return "bidList/update";
        }
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        //Find Bid by Id and delete the bid, return to Bid list
        try {
            BidList bidList = bidListService.findById(id)
                                            .orElseThrow(() -> new IllegalArgumentException("Invalid bid Id:" + id));

            bidListService.deleteBidList(id);
            return "redirect:/bidList/list";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error deleting bid: " + e.getMessage());
            return "redirect:/bidList/list";
        }
    }
}
