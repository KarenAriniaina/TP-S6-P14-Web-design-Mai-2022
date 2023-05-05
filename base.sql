CREATE SEQUENCE s_Article INCREMENT BY 1 NO MINVALUE NO MAXVALUE START WITH 1;
CREATE SEQUENCE s_Categorie INCREMENT BY 1 NO MINVALUE NO MAXVALUE START WITH 1;
CREATE SEQUENCE s_CategorieUne INCREMENT BY 1 NO MINVALUE NO MAXVALUE START WITH 1;
CREATE SEQUENCE s_PhotosArticle INCREMENT BY 1 NO MINVALUE NO MAXVALUE START WITH 1;
CREATE SEQUENCE s_Utilisateur INCREMENT BY 1 NO MINVALUE NO MAXVALUE START WITH 1;

CREATE TABLE Article (
  idArticle       varchar(50) NOT NULL, 
  idCategorie     varchar(50) NOT NULL, 
  Titre           text NOT NULL, 
  Resume          text NOT NULL, 
  Contenu         text NOT NULL, 
  DatePublication Timestamp, 
  idAuteur        varchar(50) NOT NULL, 
  PRIMARY KEY (idArticle)
);

CREATE TABLE Categorie (
  idCategorie varchar(50) NOT NULL, 
  Designation varchar(100) NOT NULL UNIQUE, 
  PRIMARY KEY (idCategorie)
);

CREATE TABLE CategorieUne (
  idCU      varchar(50) NOT NULL, 
  idArticle varchar(50) NOT NULL, 
  PRIMARY KEY (idCU)
);

CREATE TABLE PhotosArticle (
  idPA      varchar(50) NOT NULL, 
  idArticle varchar(50) NOT NULL, 
  Image     text NOT NULL, 
  PRIMARY KEY (idPA)
);

CREATE TABLE Utilisateur (
  idUtilisateur varchar(50) NOT NULL, 
  Nom           varchar(100) NOT NULL, 
  Prenom        varchar(100) NOT NULL, 
  Login         varchar(100) NOT NULL UNIQUE, 
  Mdp           varchar(100) NOT NULL, 
  Type          int4 NOT NULL, 
  PRIMARY KEY (idUtilisateur)
);

ALTER TABLE Article ADD CONSTRAINT FKArticle851240 FOREIGN KEY (idCategorie) REFERENCES Categorie (idCategorie);
ALTER TABLE PhotosArticle ADD CONSTRAINT FKPhotosArti294980 FOREIGN KEY (idArticle) REFERENCES Article (idArticle);
ALTER TABLE Article ADD CONSTRAINT FKArticle393759 FOREIGN KEY (idAuteur) REFERENCES Utilisateur (idUtilisateur);
ALTER TABLE CategorieUne ADD CONSTRAINT FKCategorieU475747 FOREIGN KEY (idArticle) REFERENCES Article (idArticle);
