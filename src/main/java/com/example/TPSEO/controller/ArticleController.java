/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.TPSEO.controller;

import com.example.TPSEO.modele.Article;
import com.example.TPSEO.modele.Categorie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Ari
 */
@Controller
public class ArticleController {
    
    @GetMapping("/")
    public String GoToInsertionArticle(HttpSession session, HttpServletRequest request, Model model) throws Exception {
        Categorie[] lc = new Categorie().ListeCategorie();
        model.addAttribute("lc", lc);
        return "InsertionArticle";
    }
    
    @GetMapping("/Articles")
    public String ListeArticles(HttpSession session, HttpServletRequest request, Model model) throws Exception {
        Article[] la = new Article().ListeArticle();
        model.addAttribute("la", la);
        return "ListeArticle";
    }
    
    @PostMapping("/Article")
    public String InsertionArticle(@RequestParam("Titre") String Titre,
            @RequestParam("Resume") String Resume,
            @RequestParam("idCategorie") String idCategorie,
            @RequestParam("Contenu") String Contenu,
            HttpSession session, Model model) throws Exception {
        Article a = new Article();
        String response = "";
        try {
            a.setContenu(Contenu);
            a.setIdCategorie(idCategorie);
            a.setResume(Resume);
            a.setTitre(Titre);
            a.CreateArticle();
            response = "reussi";
        } catch (Exception e) {
            response = e.getMessage();
        }
        model.addAttribute("response", response);
        Categorie[] lc = new Categorie().ListeCategorie();
        model.addAttribute("lc", lc);
        return "InsertionArticle";
    }
    
    @GetMapping("/Article/{slug}")
    public String getArticle(@PathVariable("slug") String Slug,
            HttpSession session, Model model) throws Exception {
        Article a = new Article();
        String[] split = Slug.split("-");
        String id = split[split.length - 1];
        String response = "";
        try {
            a.setIdArticle("Article_" + id);
            a.getArticle();
        } catch (Exception e) {
            response = e.getMessage();
        }
        model.addAttribute("response", response);
        model.addAttribute("a", a);
        return "DetailArticle";
    }
}
