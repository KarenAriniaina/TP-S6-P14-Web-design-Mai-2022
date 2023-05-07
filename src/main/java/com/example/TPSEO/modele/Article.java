/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.TPSEO.modele;

import com.example.TPSEO.outil.Slug;
import com.example.TPSEO.dao.Connexion;
import com.example.TPSEO.dao.ObjetBDD;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

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
    private Timestamp DatePublication;
    private String Image;
    private String idAuteur;
    private int isUne;

    public int getIsUne() {
        return isUne;
    }

    public void setIsUne(int isUne) {
        this.isUne = isUne;
    }

    public String getIdAuteur() {
        return idAuteur;
    }

    public void setIdAuteur(String idAuteur) throws Exception {
        if (idAuteur == null || idAuteur.equalsIgnoreCase("")) {
            throw new Exception("Auteur non connu");
        }
        this.idAuteur = idAuteur;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String Image) throws Exception {
        if (Image == null || Image.equalsIgnoreCase("")) {
            throw new Exception("Image vide");
        }
        this.Image = Image;
    }

    public void CreerPhoto(MultipartFile file) throws Exception {
        String name = "";
        try {
            if (file.isEmpty()) {
                throw new Exception("Photos is empty");
            }
            if (file != null && !file.getOriginalFilename().equalsIgnoreCase("")) {
                name = file.getOriginalFilename();
                if (!name.endsWith(".jpeg") && !name.endsWith(".JPEG")
                        && !name.endsWith(".jpg") && !name.endsWith(".JPG")
                        && !name.endsWith(".png") && !name.endsWith(".PNG")
                        && !name.endsWith(".gif") && !name.endsWith(".GIF")
                        && !name.endsWith(".BMP") && !name.endsWith(".bmp")
                        && !name.endsWith(".tiff") && !name.endsWith(".TIFF")
                        && !name.endsWith(".WEBP") && !name.endsWith(".webp")
                        && !name.endsWith(".SVG") && !name.endsWith(".svg")) {
                    throw new Exception("Veuillez saisir une image");
                }
            }
            
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            Path uploadPath = Paths.get("src/main/resources/static/photos/").toAbsolutePath().normalize();
            // Normalize the file name
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            String newFileName = UUID.randomUUID().toString() + "." + StringUtils.getFilenameExtension(fileName);
            // Copy file to the target location (replace existing file with the same name)
            Path targetLocation = uploadPath.resolve(newFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            this.setImage(newFileName);
//            Path uploadPath = Paths.get("src/main/resources/static/photos/");
//            uploadPath = Paths.get("src/main/resources/static/photos/" + name);
//            Files.copy(file.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Timestamp getDatePublication() {
        return DatePublication;
    }

    public void setDatePublication(Timestamp DatePublication) throws Exception {
        if (DatePublication.after(Timestamp.valueOf(LocalDateTime.now()))) {
            throw new Exception("Date superieur a current date");
        }
        this.DatePublication = DatePublication;
    }

    public void setDatePublication(String Date) throws Exception {
        Timestamp t = Timestamp.valueOf(LocalDateTime.now());
        if (Date.equalsIgnoreCase("")) {
            throw new Exception("Date vide");
        }
        try {
            LocalDateTime dateTime = LocalDateTime.parse(Date);
            long timestamp = dateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
            t = new Timestamp(timestamp);
        } catch (Exception e) {
            throw new Exception("Format date incorrect");
        }
        this.setDatePublication(t);
    }

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

    public void CreateArticle(String idAuteur, String Titre, String idCategorie, String Resume, String Contenu, String DatePub, MultipartFile img) throws Exception {
        Connection c = null;
        try {
            c = Connexion.getConnection();
            this.setIdAuteur(idAuteur);
            this.setContenu(Contenu);
            this.setTitre(Titre);
            this.setIdCategorie(idCategorie);
            this.setResume(Resume);
            this.setDatePublication(DatePub);
            this.CreerPhoto(img);
            this.Create(c);
        } catch (Exception e) {
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
                this.setDatePublication(a.getDatePublication());
                this.setImage(a.getImage());
                this.setIdAuteur(a.getIdAuteur());
                this.setIsUne(a.getIsUne());
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

    public String getSQLPagination(String t1, String t2) throws Exception {
        String sql = "";
        if (t1 != null && !t1.equalsIgnoreCase("")) {
            try {
                this.setDatePublication(t1);
            } catch (Exception e) {
                throw new Exception("Format date debut invalide");
            }
            sql += " AND DatePublication >='" + t1 + "'";
        }
        if (t2 != null && !t2.equalsIgnoreCase("")) {
            try {
                this.setDatePublication(t2);
            } catch (Exception e) {
                throw new Exception("Format date fin invalide");
            }
            sql += " AND DatePublication <='" + t2 + "'";
        }
        if (Titre != null && !Titre.equalsIgnoreCase("")) {
            sql += " AND LOWER(Titre) like LOWER('%" + Titre + "%') ";
        }
        if (idAuteur != null && !idAuteur.equalsIgnoreCase("")) {
            sql += " AND idAuteur='" + idAuteur + "'";
        }
        if (idCategorie != null && !idCategorie.equalsIgnoreCase("")) {
            sql += " AND idCategorie='" + idCategorie + "'";
        }
        return sql;
    }

    public int NbrPageRecord(Connection con, String t1, String t2) throws Exception {
        int valiny = 0;
        boolean nullve = false;
        Statement st = null;
        ResultSet sett = null;
        try {
            if (con == null) {
                con = Connexion.getConnection();
                nullve = true;
            }
            String sql = "SELECT COUNT (*)::Integer as nbr FROM v_Article WHERE 1=1 " + this.getSQLPagination(t1, t2);
            System.err.println(sql);
            st = con.createStatement();
            sett = st.executeQuery(sql);
            if (sett.next()) {
                int nbr = sett.getInt("nbr");
                valiny = nbr;
                System.err.println(valiny);
                valiny /= 6;
                if (valiny == 0 || (nbr % 6) != 0) {
                    valiny++;
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (sett != null) {
                sett.close();
            }
            if (st != null) {
                st.close();
            }
            if (nullve == true) {
                con.close();
            }
        }
        return valiny;
    }

    public Article[] ListeArticlePaginer(String t1, String t2, int page) throws Exception {
        Article[] la = new Article[0];
        Connection con = null;
        try {
            con = Connexion.getConnection();
//            int nbrPage=this.NbrPageRecord(con, t1, t2);
            int numpage = (6 * page) - 6;
            String sql = "SELECT * FROM v_Article WHERE 1=1 " + this.getSQLPagination(t1, t2) + " ORDER BY isUne DESC LIMIT 6 OFFSET " + numpage;
            System.err.println(sql);
            ObjetBDD[] lo = this.Find(con, sql);
            la = new Article[lo.length];
            if (lo.length != 0) {
                System.arraycopy(lo, 0, la, 0, lo.length);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return la;
    }

    public Article[] DernierArticle3() throws Exception {
        Article[] la = new Article[0];
        Connection con = null;
        try {
            con = Connexion.getConnection();
            String sql = "SELECT * FROM v_Article ORDER BY DatePublication DESC LIMIT 3";
            ObjetBDD[] lo = this.Find(con, sql);
            la = new Article[lo.length];
            if (lo.length != 0) {
                System.arraycopy(lo, 0, la, 0, lo.length);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return la;
    }

    public String LinkArticle(String t1, String t2) {
        String link = "";
        int nbr = 0;
        if (t1 != null && !t1.equalsIgnoreCase("")) {
            link += "t1=" + t1;
            nbr++;
        }
        if (t2 != null && !t2.equalsIgnoreCase("")) {
            if (nbr > 0) {
                link += "&";
            }
            link += "t2=" + t2;
        }
        if (Titre != null && !Titre.equalsIgnoreCase("")) {
            if (nbr > 0) {
                link += "&";
            }
            link += "Titre=" + Titre;
        }
        if (idCategorie != null && !idCategorie.equalsIgnoreCase("")) {
            if (nbr > 0) {
                link += "&";
            }
            link += "idCategorie=" + idCategorie;
        }
        return link;
    }

    public void UpdateArticle(String idArticle, boolean aUne, String Titre, String idCategorie, String Resume, String Contenu, String DatePub, MultipartFile img) throws Exception {
        Connection c = null;
        try {
            c = Connexion.getConnection();
            this.setIdArticle(idArticle);
            this.setContenu(Contenu);
            this.setTitre(Titre);
            this.setIdCategorie(idCategorie);
            this.setResume(Resume);
            if (img != null && !img.getOriginalFilename().equalsIgnoreCase("")) {
                this.CreerPhoto(img);
            }
            this.setDatePublication(DatePub);
            this.Update(c);
            if (aUne == true) {
                ArticleUne au = new ArticleUne();
                au.setArticleUne(c, idArticle);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (c != null) {
                c.close();
            }
        }
    }
}
