/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.TPSEO.controller;

import com.example.TPSEO.modele.Article;
import com.example.TPSEO.modele.Categorie;
import com.example.TPSEO.modele.CategorieUne;
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
public class CategorieController {

    @PostMapping("/addCategorie")
    public String addCategorie(@RequestParam("Designation") String Designation,
            HttpSession session, Model model) throws Exception {
        if (session.getAttribute("idAdmin") == null) {
            return "redirect:/connexionBO";
        }
        String reponse = "";
        try {
            Categorie c = new Categorie();
            c.CreateCategorie(Designation);
        } catch (Exception e) {
            reponse = e.getMessage();
        }
        model.addAttribute("response", reponse);
        return "redirect:/ListCategorie?reponse=" + reponse;
    }

    @PostMapping("/ModifCategorie")
    public String ModifCategorie(@RequestParam("Designation") String Designation,
            @RequestParam("idCategorie") String idCategorie,
            HttpSession session, Model model) throws Exception {
        if (session.getAttribute("idAdmin") == null) {
            return "redirect:/connexionBO";
        }
        String reponse = "";
        try {
            Categorie c = new Categorie();
            c.ModifCategorie(Designation, idCategorie);
        } catch (Exception e) {
            reponse = e.getMessage();
        }
        model.addAttribute("response", reponse);
        return "redirect:/ListCategorie?reponse=" + reponse;
    }

    @GetMapping("/ListCategorie")
    public String ListCategorie(HttpServletRequest request, HttpSession session, Model model) throws Exception {
        if (session.getAttribute("idAdmin") == null) {
            return "redirect:/connexionBO";
        }
        String response = "";
        if (request.getParameter("reponse") != null) {
            response = request.getParameter("reponse");
        }
        try {
            Categorie[] lc = new Categorie().ListeCategorieParNombre();
            model.addAttribute("lc", lc);
            model.addAttribute("lcu", new CategorieUne().ListeCategorieUne(null));
        } catch (Exception e) {
            e.printStackTrace();
            response = e.getMessage();
            return "error";
        } finally {
            model.addAttribute("response", response);
        }
        return "ListCategorie";
    }

    @GetMapping("/ListCategorieUne")
    public String ListCategorieUne(HttpServletRequest request, HttpSession session, Model model) throws Exception {
        if (session.getAttribute("idAdmin") == null) {
            return "redirect:/connexionBO";
        }
        String response = "";
        if (request.getParameter("reponse") != null) {
            response = request.getParameter("reponse");
        }
        try {
            CategorieUne[] lc = new CategorieUne().ListeCategorieUne(null);
            Categorie[] lca = new Categorie().ListeCategorie();
            model.addAttribute("lc", lc);
            model.addAttribute("lca", lca);
            model.addAttribute("lcu", new CategorieUne().ListeCategorieUne(null));
        } catch (Exception e) {
            e.printStackTrace();
            response = e.getMessage();
            return "error";
        } finally {
            model.addAttribute("response", response);
        }
        return "ListCategorieUne";
    }

    @PostMapping("/addCategorieUne")
    public String addCategorieUne(@RequestParam("idCategorie") String idCategorie,
            HttpSession session, Model model) throws Exception {
        if (session.getAttribute("idAdmin") == null) {
            return "redirect:/connexionBO";
        }
        String reponse = "";
        try {
            CategorieUne c = new CategorieUne();
            c.AjouterCategorieUne(idCategorie);
        } catch (Exception e) {
            reponse = e.getMessage();
        }
        model.addAttribute("response", reponse);
        return "redirect:/ListCategorieUne?reponse=" + reponse;
    }

    @PostMapping("/SupprimerCategorieUne")
    public String SupprimerCategorieUne(@RequestParam("idCU") String idCU,
            HttpSession session, Model model) throws Exception {
        if (session.getAttribute("idAdmin") == null) {
            return "redirect:/connexionBO";
        }
        String reponse = "";
        try {
            CategorieUne cu = new CategorieUne();
            cu.SupprimerCategorieUne(idCU);
        } catch (Exception e) {
            reponse = e.getMessage();
        }
        model.addAttribute("response", reponse);
        return "redirect:/ListCategorieUne?reponse=" + reponse;
    }

    @GetMapping("/Categorie/{slug}")
    public String ListeArticles(@PathVariable("slug") String Slug,
            HttpSession session, Model model) throws Exception {
        try {
            Article a = new Article();
            String[] split = Slug.split("-");
            String title="";
            String link="";
            int page=0;
            if (split.length < 3) {
                model.addAttribute("response", "Page unreachable");
                return "error";
            }
            try {
                page = Integer.valueOf(split[split.length - 1]);
                int categorie = Integer.valueOf(split[split.length - 2]);
                a.setIdCategorie("Categorie_"+Integer.toString(categorie));
                Categorie c=new Categorie();
                c.setIdCategorie(a.getIdCategorie());
                c.getCategorie(null);
                for(int i=0;i<split.length-1;i++){
                    link+=split[i]+"-";
                }
                title=c.getDesignation();
            } catch (Exception e) {
                model.addAttribute("response", "Page unreachable");
                return "error";
            }
            if (session.getAttribute("idAuteur") != null) {
                a.setIdAuteur((String) session.getAttribute("idAuteur"));
            }
            Article[] la = a.ListeArticlePaginer(null,null, page);
            model.addAttribute("la", la);
            model.addAttribute("page", page);
            int nbrpage = a.NbrPageRecord(null, null, null);
            model.addAttribute("nbrpage", nbrpage);
            model.addAttribute("title", title);
            model.addAttribute("link", link);
            model.addAttribute("lcu", new CategorieUne().ListeCategorieUne(null));
            int isAdmin = 0;
            if (session.getAttribute("idAdmin") != null) {
                isAdmin = 1;
            }
            if (session.getAttribute("idAdmin") != null || session.getAttribute("idAuteur") != null) {
                model.addAttribute("isAuteur", 1);
            }
            model.addAttribute("isAdmin", isAdmin);
        } catch (Exception e) {
            model.addAttribute("response", e.getMessage());
            e.printStackTrace();
            return "error";
        }
        return "CategorieUne";
    }
}
