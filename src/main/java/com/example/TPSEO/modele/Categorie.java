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
public class Categorie extends ObjetBDD {

    private String idCategorie;
    private String Designation;

    public String getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(String idCategorie) {
        this.idCategorie = idCategorie;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String Designation) {
        this.Designation = Designation;
    }

    public Categorie() {
        this.setPrimaryKey("idCategorie");
        this.setNomTable("Categorie");
    }

    public Categorie[] ListeCategorie() throws Exception {
        Categorie[] valiny = new Categorie[0];
        Connection c = null;
        try {
            c = Connexion.getConnection();
            ObjetBDD[] lo = this.Find(c);
            if (lo != null && lo.length != 0) {
                valiny = new Categorie[lo.length];
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
}
