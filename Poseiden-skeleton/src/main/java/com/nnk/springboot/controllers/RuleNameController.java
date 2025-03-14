package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
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
public class RuleNameController {
    @Autowired
    private RuleNameService ruleNameService;

    @RequestMapping("/ruleName/list")
    public String home(Model model)
    {
        // find all RuleName, add to model
        model.addAttribute("ruleNames", ruleNameService.findAll());
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName bid) {
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        // check data valid and save to db, after saving return RuleName list
        if (result.hasErrors()) {
            return "ruleName/add";
        }
        try {
            ruleNameService.save(ruleName);
            model.addAttribute("ruleNames", ruleNameService.findAll());
            return "redirect:/ruleName/list";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error saving rule: " + e.getMessage());
            return "ruleName/add";
        }
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // get RuleName by Id and to model then show to the form
        RuleName ruleName = ruleNameService.findById(id)
                                           .orElseThrow(() -> new IllegalArgumentException("Invalid rule Id:" + id));
        model.addAttribute("ruleName", ruleName);
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model) {
        // check required fields, if valid call service to update RuleName and return RuleName list
        if (result.hasErrors()) {
            ruleName.setId(id);
            return "ruleName/update";
        }

        try {
            ruleName.setId(id);
            ruleNameService.update(ruleName);
            model.addAttribute("ruleNames", ruleNameService.findAll());
            return "redirect:/ruleName/list";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error updating rule: " + e.getMessage());
            ruleName.setId(id);
            return "ruleName/update";
        }
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        // Find RuleName by Id and delete the RuleName, return to Rule list
        try {
            ruleNameService.findById(id)
                           .orElseThrow(() -> new IllegalArgumentException("Invalid rule Id:" + id));
            ruleNameService.delete(id);
            model.addAttribute("ruleNames", ruleNameService.findAll());
            return "redirect:/ruleName/list";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error deleting rule: " + e.getMessage());
            return "ruleName/list";
        }
    }
}
