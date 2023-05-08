/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.TPSEO.controller;

import com.example.TPSEO.config.ImageService;
import com.example.TPSEO.modele.Article;
import com.example.TPSEO.modele.Categorie;
import com.example.TPSEO.modele.CategorieUne;
import com.example.TPSEO.outil.DriveServiceImpl;
import com.google.api.services.drive.model.File;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

/**
 *
 * @author Ari
 */
@Controller
public class ArticleController {

    @Autowired
    private ImageService imageService;

    @GetMapping("/AjoutArticle")
    public String GoToInsertionArticle(HttpSession session, HttpServletRequest request, Model model) throws Exception {
        if (session.getAttribute("idAdmin") == null && session.getAttribute("idAuteur") == null) {
            return "redirect:/connexionBO";
        }
        Categorie[] lc = new Categorie().ListeCategorie();
        model.addAttribute("lc", lc);
        model.addAttribute("lcu", new CategorieUne().ListeCategorieUne(null));
        int isAdmin = 0;
        if (session.getAttribute("idAdmin") != null) {
            isAdmin = 1;
        }
        if (session.getAttribute("idAdmin") != null || session.getAttribute("idAuteur") != null) {
            model.addAttribute("isAuteur", 1);
        }
        if (request.getParameter("response") != null) {
            model.addAttribute("response", request.getParameter("response"));
        }
        model.addAttribute("isAdmin", isAdmin);
        return "InsertionArticle";
    }

//    @GetMapping("/Articles")
//    public String ListeArticles(HttpSession session, HttpServletRequest request, Model model) throws Exception {
//        Article[] la = new Article().ListeArticle();
//        model.addAttribute("la", la);
//        return "ListeArticle";
//    }
    @GetMapping("/")
    public String ListeArticles(@RequestParam(name = "Titre", required = false, defaultValue = "") String Titre,
            @RequestParam(name = "t1", required = false, defaultValue = "") String t1,
            @RequestParam(name = "t2", required = false, defaultValue = "") String t2,
            @RequestParam(name = "idCategorie", required = false, defaultValue = "") String idCategorie,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            HttpSession session, Model model) throws Exception {
        try {
            Article a = new Article();
            if (Titre != null && !Titre.equalsIgnoreCase("")) {
                a.setTitre(Titre);
            }
            if (idCategorie != null && !idCategorie.equalsIgnoreCase("")) {
                a.setIdCategorie(idCategorie);
            }
            if (session.getAttribute("idAuteur") != null) {
                a.setIdAuteur((String) session.getAttribute("idAuteur"));
            }
            Article[] la = a.ListeArticlePaginer(t1, t2, page);
            model.addAttribute("la", la);
            Categorie[] lc = new Categorie().ListeCategorieParNombre();
            model.addAttribute("lc", lc);
            model.addAttribute("link", a.LinkArticle(t1, t2));
            model.addAttribute("page", page);
            int nbrpage = a.NbrPageRecord(null, t1, t2);
            System.err.println("nbr=" + nbrpage);
            model.addAttribute("nbrpage", nbrpage);
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
        return "ListeArticle";
    }

    @PostMapping("/addArticle")
    public String InsertionArticle(@RequestParam("Titre") String Titre,
            @RequestParam("Resume") String Resume,
            @RequestParam("idCategorie") String idCategorie,
            @RequestParam("Contenu") String Contenu,
            @RequestParam("DatePub") String DatePub,
            @RequestParam(name = "img", required = false) MultipartFile img,
            HttpSession session, Model model) throws Exception {
        Article a = new Article();
        String response = "";
        String idAuteur = "";
        if (session.getAttribute("idAdmin") != null) {
            idAuteur = (String) session.getAttribute("idAdmin");
        }
        if (session.getAttribute("idAuteur") != null) {
            idAuteur = (String) session.getAttribute("idAuteur");
        }
        if (idAuteur.equalsIgnoreCase("")) {
            return "redirect:/connexionBO";
        }
        try {
            a.CreateArticle(idAuteur, Titre, idCategorie, Resume, Contenu, DatePub, img);
            response = "reussi";
        } catch (Exception e) {
            response = e.getMessage();
        }
        // model.addAttribute("response", response);
        Categorie[] lc = new Categorie().ListeCategorie();
        model.addAttribute("lc", lc);
        return "redirect:/AjoutArticle?response=" + response;
    }

    @GetMapping("/Article/{slug}")
    public String getArticle(@PathVariable("slug") String Slug,
            HttpSession session, HttpServletRequest request,HttpServletResponse resp, Model model) throws Exception {
        Article a = new Article();
        String[] split = Slug.split("-");
        String id = split[split.length - 1];
        String response = "";
        if (request.getParameter("response") != null) {
            response = request.getParameter("response");
        }
        try {
            a.setIdArticle("Article_" + id);
            a.getArticle();
            File file = new DriveServiceImpl().getDriveService().files().get(a.getImage()).setFields("webViewLink").execute();
            String imageUrl = file.getWebViewLink();
            UrlResource imageResource = new UrlResource(imageUrl);
            ResourceHttpRequestHandler handler = new ResourceHttpRequestHandler();
            handler.setLocations(Collections.singletonList(imageResource));
            handler.setCacheSeconds(86400);
            // handler.handleRequest(request, resp);
            model.addAttribute("lcu", new CategorieUne().ListeCategorieUne(null));
            System.out.println("nandalo lcu");
        } catch (Exception e) {
            response = e.getMessage();
            e.printStackTrace();
        } finally {
            model.addAttribute("response", response);
            model.addAttribute("a", a);
        }
        if (session.getAttribute("idAdmin") != null || session.getAttribute("idAuteur") != null) {
            model.addAttribute("datepub", a.getDatePublication().toLocalDateTime());
            Categorie[] lc = new Categorie().ListeCategorie();
            int isAdmin = 0;
            if (session.getAttribute("idAdmin") != null) {
                isAdmin = 1;
            }
            model.addAttribute("isAuteur", 1);
            model.addAttribute("isAdmin", isAdmin);
            model.addAttribute("lc", lc);
            return "ModifArticle";
        }
        model.addAttribute("lda", a.DernierArticle3());
        return "DetailArticle";
    }

    @PostMapping("/UpdateArticle")
    public String UpdateArticle(@RequestParam("Titre") String Titre,
            @RequestParam("idArticle") String idArticle,
            @RequestParam("Resume") String Resume,
            @RequestParam("idCategorie") String idCategorie,
            @RequestParam("Contenu") String Contenu,
            @RequestParam("DatePub") String DatePub,
            @RequestParam(name = "aUne", required = false, defaultValue = "0") int aUne,
            @RequestParam(name = "img", required = false) MultipartFile img,
            HttpSession session, Model model) throws Exception {
        Article a = new Article();
        String response = "";
        String idAuteur = "";
        if (session.getAttribute("idAdmin") != null) {
            idAuteur = (String) session.getAttribute("idAdmin");
        }
        if (session.getAttribute("idAuteur") != null) {
            idAuteur = (String) session.getAttribute("idAuteur");
        }
        if (idAuteur.equalsIgnoreCase("")) {
            return "redirect:/connexionBO";
        }
        try {
            boolean aune = false;
            if (aUne == 1) {
                aune = true;
            }
            a.UpdateArticle(idArticle, aune, Titre, idCategorie, Resume, Contenu, DatePub, img);
            response = "";
        } catch (Exception e) {
            response = e.getMessage();
        }
        Categorie[] lc = new Categorie().ListeCategorie();
        model.addAttribute("lc", lc);
        return "redirect:/Article/" + a.getSlug() + "?response=" + response;
    }
}
