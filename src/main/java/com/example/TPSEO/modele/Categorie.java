/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.TPSEO.modele;

import com.example.TPSEO.dao.Connexion;
import com.example.TPSEO.dao.ObjetBDD;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Ari
 */
public class Categorie extends ObjetBDD {

    private String idCategorie;
    private String Designation;
    private Integer Nombre;

    public Integer getNombre() {
        return Nombre;
    }

    public void setNombre(Integer Nombre) {
        this.Nombre = Nombre;
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

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String Designation) throws Exception {
        if (Designation == null || Designation.equalsIgnoreCase("")) {
            throw new Exception("Designation categorie vide");
        }
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

    public Categorie[] ListeCategorieParNombre() throws Exception {
        Categorie[] valiny = new Categorie[0];
        Connection c = null;
        try {
            c = Connexion.getConnection();
            this.setNomTable("v_categorie");
            ObjetBDD[] lo = this.Find(c);
            if (lo != null && lo.length != 0) {
                valiny = new Categorie[lo.length];
                System.arraycopy(lo, 0, valiny, 0, lo.length);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.setNomTable("Categorie");
            if (c != null) {
                c.close();
            }
        }
        return valiny;
    }

    public void getCategorie(Connection con) throws Exception {
        boolean nullve = false;
        try {
            if (con == null) {
                con = Connexion.getConnection();
                nullve = true;
            }
            ObjetBDD[] lu = this.Find(con);
            if (lu != null && lu.length != 0) {
                Categorie c = (Categorie) lu[0];
                this.setIdCategorie(c.getIdCategorie());
                this.setDesignation(c.getDesignation());
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (nullve && con != null) {
                con.close();
            }
        }
    }

    public void CreateCategorie(String Designation) throws Exception {
        Connection con = null;
        try {
            con = Connexion.getConnection();
            this.setDesignation(Designation);
            this.getCategorie(con);
            if (this.getIdCategorie() != null) {
                throw new Exception("Designation categorie deja existante");
            }
            this.Create(con);
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }

    public void ModifCategorie(String Designation, String idCategorie) throws Exception {
        Connection con = null;
        try {
            con = Connexion.getConnection();
            Categorie ca = new Categorie();
            ca.setIdCategorie(idCategorie);
            ca.getCategorie(con);
            if (Designation.equalsIgnoreCase(ca.getDesignation())) {
                throw new Exception("Vous n'avez pas saisie une nouvelle designation pour cette catégorie");
            }
            this.setDesignation(Designation);
            this.getCategorie(con);
            if (this.getIdCategorie() != null) {
                throw new Exception("Designation categorie deja existante");
            }
            this.setIdCategorie(idCategorie);
            this.Update(con);
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }

}
