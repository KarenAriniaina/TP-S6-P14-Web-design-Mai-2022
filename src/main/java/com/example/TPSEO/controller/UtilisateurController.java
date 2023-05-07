/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.TPSEO.controller;

import com.example.TPSEO.modele.Article;
import com.example.TPSEO.modele.Categorie;
import com.example.TPSEO.modele.CategorieUne;
import com.example.TPSEO.modele.Utilisateur;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Ari
 */
@Controller
public class UtilisateurController {

    @GetMapping("/connexionBO")
    public String connexionBO(HttpSession session, Model model) throws Exception {
        if (session.getAttribute("idAuteur") != null) {
            return "redirect:/";
        }
        if (session.getAttribute("idAdmin") != null) {
            return "redirect:/";
        }
        model.addAttribute("message", "");
        return "connexionBO";
    }

    @GetMapping("/deconnexionBO")
    public String deconnexionBO(HttpSession session, Model model) throws Exception {
        if (session.getAttribute("idAuteur") != null) {
            session.removeAttribute("idAuteur");
        }
        if (session.getAttribute("idAdmin") != null) {
            //redirection
            session.removeAttribute("idAdmin");
        }
        model.addAttribute("message", "");
        return "redirect:/connexionBO";
    }

    @PostMapping("/connecterBO")
    public String connecterBO(@RequestParam("Login") String Login,
            @RequestParam("Mdp") String Mdp,
            HttpSession session, Model model) throws Exception {
        String response = "";
        Utilisateur u = new Utilisateur();
        try {
            u.SignIn(Login, Mdp);
            //add session
            if (u.getType() == 1) {
                session.setAttribute("idAdmin", u.getIdUtilisateur());
            } else if (u.getType() == 2) {
                session.setAttribute("idAuteur", u.getIdUtilisateur());
            }
            //redirection
        } catch (Exception e) {
            response = e.getMessage();
            return "connexionBO";
        } finally {
            model.addAttribute("response", response);
        }
        return "redirect:/";
    }

    @GetMapping("/createAuteur")
    public String createAuteur(HttpSession session, Model model) throws Exception {
        if (session.getAttribute("idAdmin") == null) {
            return "redirect:/connexionBO";
        }
        model.addAttribute("lcu", new CategorieUne().ListeCategorieUne(null));
        model.addAttribute("message", "");
        return "createAuteur";
    }

    @PostMapping("/creerAuteur")
    public String creerAuteur(@RequestParam("Login") String Login,
            @RequestParam("Mdp") String Mdp,
            @RequestParam("Nom") String Nom,
            @RequestParam("Prenom") String Prenom,
            HttpSession session, Model model) throws Exception {
        if (session.getAttribute("idAdmin") == null) {
            return "redirect:/connexionBO";
        }
        String response = "";
        Utilisateur u = new Utilisateur();
        try {
            u.CreateAuteur(Nom, Prenom, Login, Mdp);
            response = "OK";
        } catch (Exception e) {
            response = e.getMessage();
        }
        model.addAttribute("response", response);
        return "createAuteur";
    }
}
