/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.TPSEO.modele;

import com.example.TPSEO.dao.Connexion;
import com.example.TPSEO.dao.ObjetBDD;
import com.example.TPSEO.outil.Util;
import java.sql.Connection;

/**
 *
 * @author Ari
 */
public class Utilisateur extends ObjetBDD {

    private String idUtilisateur;
    private String Nom;
    private String Prenom;
    private String Login;
    private String Mdp;
    private int Type; // 1: admin 2:auteur

    public String getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(String idUtilisateur) throws Exception {
        if (idUtilisateur == null || idUtilisateur.equalsIgnoreCase("") || !idUtilisateur.startsWith("Utilisateur_")) {
            throw new Exception("Utilisateur non choisi");
        }
        this.idUtilisateur = idUtilisateur;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String Nom) throws Exception {
        if (Nom == null || Nom.equalsIgnoreCase("")) {
            throw new Exception("Nom vide");
        }
        this.Nom = Nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String Prenom) throws Exception {
        if (Prenom == null || Prenom.equalsIgnoreCase("")) {
            throw new Exception("Prenom vide");
        }
        this.Prenom = Prenom;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String Login) throws Exception {
        if (Login == null || Login.equalsIgnoreCase("")) {
            throw new Exception("Login vide");
        }
        this.Login = Login;
    }

    public String getMdp() {
        return Mdp;
    }

    public void setMdp(String Mdp) throws Exception {
        if (Mdp == null || Mdp.equalsIgnoreCase("")) {
            throw new Exception("Mot de passe vide");
        }
        this.Mdp = Mdp;
    }

    public int getType() {
        return Type;
    }

    public void setType(int Type) {
        this.Type = Type;
    }

    public Utilisateur() {
        this.setNomTable("Utilisateur");
        this.setPrimaryKey("idUtilisateur");
    }

    public void SignIn(String Login, String Mdp) throws Exception {
        Connection con = null;
        try {
            con = Connexion.getConnection();
            this.setLogin(Login);
            this.setMdp(Mdp);
            this.setMdp(Util.getMd5(Mdp));
            this.getUtilisateur(con);
            if (this.getNom() == null) {
                throw new Exception("Aucun utilisateur trouvé");
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }

    public void getUtilisateur(Connection con) throws Exception {
        boolean nullve = false;
        try {
            if (con == null) {
                con = Connexion.getConnection();
                nullve = true;
            }
            ObjetBDD[] lu = this.Find(con);
            if (lu != null && lu.length != 0) {
                Utilisateur u = (Utilisateur) lu[0];
                this.setIdUtilisateur(u.getIdUtilisateur());
                this.setLogin(u.getLogin());
                this.setNom(u.getNom());
                this.setPrenom(u.getPrenom());
                this.setType(u.getType());
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (nullve && con != null) {
                con.close();
            }
        }
    }

    public void CreateAuteur(String Nom, String Prenom, String Login, String Mdp) throws Exception {
        Connection con = null;
        try {
            con = Connexion.getConnection();
            this.setLogin(Login);
            this.getUtilisateur(con);
            if (this.getNom() != null) {
                throw new Exception("Login déjà utilisé");
            }
            this.setMdp(Mdp);
            this.setMdp(Util.getMd5(Mdp));
            this.setNom(Nom);
            this.setPrenom(Prenom);
            this.setType(2);
            this.Create(con);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }
}
