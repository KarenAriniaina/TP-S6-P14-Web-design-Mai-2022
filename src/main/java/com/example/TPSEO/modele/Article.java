/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.TPSEO.modele;

import com.example.TPSEO.dao.Connexion;
import com.example.TPSEO.dao.ObjetBDD;
import java.sql.Connection;

/**
 *
 * @author Ari
 */
public class Article extends ObjetBDD {

    private String idArticle;
    private String idCategorie;
    private String DesignationCategorie;
    private String Resume;
    private String Contenu;
    private String Titre;

    public String getDesignationCategorie() {
        return DesignationCategorie;
    }

    public void setDesignationCategorie(String DesignationCategorie) {
        this.DesignationCategorie = DesignationCategorie;
    }

    public String getTitre() {
        return Titre;
    }

    public void setTitre(String Titre) throws Exception {
        if (Titre.equalsIgnoreCase("")) {
            throw new Exception("Titre vide");
        }
        this.Titre = Titre;
    }

    public String getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(String idArticle) throws Exception {
        if (idArticle.equalsIgnoreCase("Article_") || idArticle.equalsIgnoreCase("")) {
            throw new Exception("Aucune article saisie");
        }
        this.idArticle = idArticle;
    }

    public String getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(String idCategorie) throws Exception {
        if (idCategorie.equalsIgnoreCase("Categorie_") || idCategorie.equalsIgnoreCase("")) {
            throw new Exception("Aucune Categorie saisie");
        }
        this.idCategorie = idCategorie;
    }

    public String getResume() {
        return Resume;
    }

    public void setResume(String Resume) throws Exception {
        if (Resume.equalsIgnoreCase("")) {
            throw new Exception("Resume vide");
        }
        this.Resume = Resume;
    }

    public String getContenu() {
        return Contenu;
    }

    public void setContenu(String Contenu) throws Exception {
        if (Contenu.equalsIgnoreCase("")) {
            throw new Exception("Contenu vide");
        }
        this.Contenu = Contenu;
    }

    public Article() {
        this.setNomTable("Article");
        this.setPrimaryKey("idArticle");
    }

    public Article[] ListeArticle() throws Exception {
        Article[] valiny = new Article[0];
        Connection c = null;
        try {
            c = Connexion.getConnection();
            ObjetBDD[] lo = this.Find(c);
            if (lo != null && lo.length != 0) {
                valiny = new Article[lo.length];
                System.arraycopy(lo, 0, valiny, 0, lo.length);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return valiny;
    }

    public void CreateArticle() throws Exception {
        Connection c = null;
        try {
            c = Connexion.getConnection();
            this.Create(c);
        } catch (Exception e) {
            if (c != null) {
                c.rollback();
            }
            throw e;
        } finally {
            if (c != null) {
                c.close();
            }
        }
    }

    public String getSlug() throws Exception {
        String s = this.getTitre() + " " + this.getIdArticle().replace("Article_", "");
        return Slug.makeSlug(s);
    }

    public void getArticle() throws Exception {
        Connection c = null;
        try {
            c = Connexion.getConnection();
            this.setNomTable("v_Article");
            ObjetBDD[] lo = this.Find(c);
            if (lo != null && lo.length != 0) {
                Article a = (Article) lo[0];
                this.setContenu(a.getContenu());
                this.setIdArticle(a.getIdArticle());
                this.setIdCategorie(a.getIdCategorie());
                this.setResume(a.getResume());
                this.setTitre(a.getTitre());
                this.setDesignationCategorie(a.getDesignationCategorie());
            } else {
                throw new Exception("Aucun article trouvé");
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (c != null) {
                c.close();
            }
            this.setNomTable("Article");
        }
    }
}
