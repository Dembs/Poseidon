package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
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
import java.util.Optional;


@Controller
public class CurveController {
    @Autowired
    private CurvePointService curvePointService;

    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
        //find all Curve Point, add to model
        List<CurvePoint> curvePoints = curvePointService.findAll();
        model.addAttribute("curvePoints", curvePoints);
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint bid) {
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        //check data valid and save to db, after saving return Curve list
        if (result.hasErrors()) {
            return "curvePoint/add";
        }

        try {
            curvePointService.save(curvePoint);
            return "redirect:/curvePoint/list";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error saving curve point: " + e.getMessage());
            return "curvePoint/add";
        }
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        //get CurvePoint by Id and to model then show to the form
        Optional<CurvePoint> curvePoint = curvePointService.findById(id);
        if (curvePoint.isEmpty()) {
            model.addAttribute("errorMessage", "Curve point not found with id: " + id);
            return "redirect:/curvePoint/list";
        }

        model.addAttribute("curvePoint", curvePoint.get());
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) {
        // check required fields, if valid call service to update Curve and return Curve list
        if (result.hasErrors()) {
            curvePoint.setId(id);
            return "curvePoint/update";
        }

        try {
            curvePoint.setId(id);
            curvePointService.update(curvePoint);
            return "redirect:/curvePoint/list";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error updating curve point: " + e.getMessage());
            return "curvePoint/update";
        }
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        //Find Curve by Id and delete the Curve, return to Curve list
        try {
            curvePointService.findById(id)
                             .orElseThrow(() -> new IllegalArgumentException("Invalid curve point Id:" + id));

            curvePointService.delete(id);
            return "redirect:/curvePoint/list";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error deleting curve point: " + e.getMessage());
            return "redirect:/curvePoint/list";
        }
    }
}
