package com.nnk.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Contrôleur gérant les opérations liées à l'authentification.
 */
@Controller
public class LoginController {

    /**
     * Affiche la page de connexion avec les messages appropriés.
     * @param error Paramètre indiquant une erreur de connexion
     * @param logout Paramètre indiquant une déconnexion
     * @param expired Paramètre indiquant une session expirée
     * @param model Le modèle Spring MVC
     * @return Le nom de la vue à afficher
     */
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error,
                        @RequestParam(required = false) String logout,
                        @RequestParam(required = false) String expired,
                        Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password");
        }
        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully");
        }
        if (expired != null) {
            model.addAttribute("expired", "Your session has expired. Please log in again");
        }
        return "login";
    }

    /**
     * Gère l'accès refusé et redirige vers la page 403.
     * @param model Le modèle Spring MVC
     * @return Le nom de la vue à afficher
     */
    @GetMapping("/403")
    public String accessDenied(Model model) {
        model.addAttribute("errorMsg", "You are not authorized to access this page");
        return "403";
    }
}