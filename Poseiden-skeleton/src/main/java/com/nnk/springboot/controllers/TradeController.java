package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @RequestMapping("/trade/list")
    public String home(Model model)
    {
        // find all Trade, add to model
        model.addAttribute("trades", tradeService.findAll());
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Trade bid) {
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        // check data valid and save to db, after saving return Trade list
        if (result.hasErrors()) {
            return "trade/add";
        }

        try {
            tradeService.save(trade);
            model.addAttribute("trades", tradeService.findAll());
            return "redirect:/trade/list";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error saving trade: " + e.getMessage());
            return "trade/add";
        }
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // get Trade by Id and to model then show to the form
        try {
            Trade trade = tradeService.findById(id)
                                      .orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
            model.addAttribute("trade", trade);
            return "trade/update";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error finding trade: " + e.getMessage());
            model.addAttribute("trades", tradeService.findAll());
            return "trade/list";
        }
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) {
        // check required fields, if valid call service to update Trade and return Trade list
        if (result.hasErrors()) {
            trade.setTradeId(id);
            return "trade/update";
        }

        try {
            trade.setTradeId(id);
            tradeService.update(trade);
            model.addAttribute("trades", tradeService.findAll());
            return "redirect:/trade/list";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error updating trade: " + e.getMessage());
            trade.setTradeId(id);
            return "trade/update";
        }
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        // Find Trade by Id and delete the Trade, return to Trade list
        try {
            Trade trade = tradeService.findById(id)
                                      .orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
            tradeService.delete(id);
            model.addAttribute("trades", tradeService.findAll());
            return "redirect:/trade/list";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error deleting trade: " + e.getMessage());
            model.addAttribute("trades", tradeService.findAll());
            return "trade/list";
        }
    }
}
