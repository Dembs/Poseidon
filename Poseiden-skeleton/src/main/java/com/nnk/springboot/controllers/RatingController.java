package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
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
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @RequestMapping("/rating/list")
    public String home(Model model)
    {
        // find all Rating, add to model
        List<Rating> ratings = ratingService.findAll();
        model.addAttribute("ratings", ratings);
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        //check data valid and save to db, after saving return Rating list
        if (result.hasErrors()) {
            return "rating/add";
        }
        try {
            ratingService.save(rating);
            return "redirect:/rating/list";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Erreur lors de l'enregistrement du rating: " + e.getMessage());
            return "rating/add";
        }
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        //get Rating by Id and to model then show to the form
        Optional<Rating> rating = ratingService.findById(id);
        if (rating.isEmpty()) {
            model.addAttribute("errorMessage", "Rating non trouvé avec l'id: " + id);
            return "redirect:/rating/list";
        }

        model.addAttribute("rating", rating.get());
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {
        // check required fields, if valid call service to update Rating and return Rating list
        if (result.hasErrors()) {
            rating.setId(id);
            return "rating/update";
        }

        try {
            rating.setId(id);
            ratingService.update(rating);
            return "redirect:/rating/list";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Erreur lors de la mise à jour du rating: " + e.getMessage());
            return "rating/update";
        }
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        // Find Rating by Id and delete the Rating, return to Rating list
        try {
            ratingService.findById(id)
                         .orElseThrow(() -> new IllegalArgumentException("Rating invalide avec l'id: " + id));

            ratingService.delete(id);
            return "redirect:/rating/list";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Erreur lors de la suppression du rating: " + e.getMessage());
            return "redirect:/rating/list";
        }
    }
}
