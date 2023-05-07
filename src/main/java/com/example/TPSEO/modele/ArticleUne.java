/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.TPSEO.modele;

import com.example.TPSEO.dao.ObjetBDD;
import java.sql.Connection;

/**
 *
 * @author Ari
 */
public class ArticleUne extends ObjetBDD{
    private String idArticleUne;
    private String idArticle;

    public String getIdArticleUne() {
        return idArticleUne;
    }

    public void setIdArticleUne(String idArticleUne) {
        this.idArticleUne = idArticleUne;
    }

    public String getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(String idArticle) {
        this.idArticle = idArticle;
    }

    public ArticleUne() {
        this.setNomTable("ArticleUne");
        this.setPrimaryKey("idArticleUne");
    }
    
    public void setArticleUne(Connection con,String idArticle) throws Exception{
        this.DeleteAll(con);
        this.setIdArticle(idArticle);
        this.Create(con);
    }
    
}
