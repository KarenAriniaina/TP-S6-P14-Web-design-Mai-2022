/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.TPSEO.modele;

import com.example.TPSEO.dao.Connexion;
import com.example.TPSEO.dao.ObjetBDD;
import com.example.TPSEO.outil.Slug;

import java.sql.Connection;

/**
 *
 * @author Ari
 */
public class CategorieUne extends ObjetBDD {

    private String idCU;
    private String idCategorie;
    private String DesignationCategorie;

    public CategorieUne() {
        this.setPrimaryKey("idCU");
    }

    public String getIdCU() {
        return idCU;
    }

    public void setIdCU(String idCU) {
        this.idCU = idCU;
    }

    public String getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(String idCategorie) throws Exception {
        if (idCategorie == null || idCategorie.equalsIgnoreCase("") || !idCategorie.startsWith("Categorie_")) {
            throw new Exception("Categorie non choisi");
        }
        this.idCategorie = idCategorie;
    }

    public String getDesignationCategorie() {
        return DesignationCategorie;
    }

    public void setDesignationCategorie(String DesignationCategorie) {
        this.DesignationCategorie = DesignationCategorie;
    }
    
    public String getSlug(int page){
        String id=this.getIdCategorie().replace("Categorie_", "");
        String s=this.getDesignationCategorie()+" "+id+" "+Integer.toString(page);
        return Slug.makeSlug(s);
    }

    public CategorieUne[] ListeCategorieUne(Connection con) throws Exception {
        boolean nullve = false;
        try {
            if (con == null) {
                con = Connexion.getConnection();
                nullve = true;
            }
            this.setNomTable("v_CategorieUne");
            ObjetBDD[] lu = this.Find(con);
            CategorieUne[] lcu = new CategorieUne[lu.length];
            if (lu.length != 0) {
                System.arraycopy(lu, 0, lcu, 0, lu.length);
            }
            return lcu;
        } catch (Exception e) {
            throw e;
        } finally {
            if (nullve && con != null) {
                con.close();
            }
        }
    }

    public void getCategorieUne(Connection con) throws Exception {
        boolean nullve = false;
        try {
            if (con == null) {
                con = Connexion.getConnection();
                nullve = true;
            }
            this.setNomTable("v_CategorieUne");
            ObjetBDD[] lu = this.Find(con);
            if (lu != null && lu.length != 0) {
                CategorieUne c = (CategorieUne) lu[0];
                this.setIdCategorie(c.getIdCategorie());
                this.setIdCU(c.getIdCU());
                this.setDesignationCategorie(c.getDesignationCategorie());
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (nullve && con != null) {
                con.close();
            }
        }
    }

    public void AjouterCategorieUne(String idCategorie) throws Exception {
        Connection con = null;
        try {
            con = Connexion.getConnection();
            this.setIdCategorie(idCategorie);
            this.getCategorieUne(con);
            if (this.getIdCU() != null) {
                throw new Exception("Categorie deja a la une");
            }
            CategorieUne[] lcu = new CategorieUne().ListeCategorieUne(con);
            System.err.println(lcu.length);
            if (lcu.length == 3) {
                System.err.println("tsy ato");
                throw new Exception("Categorie a la une deja pleine");
            }
            this.setNomTable("CategorieUne");
            this.Create(con);
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }

    public void SupprimerCategorieUne(String idCU) throws Exception {
        Connection con = null;
        try {
            con = Connexion.getConnection();
            this.setNomTable("CategorieUne");
            this.setIdCU(idCU);
            this.Delete(con);
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }

}
