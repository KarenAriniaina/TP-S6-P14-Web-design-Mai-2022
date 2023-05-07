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
  Image           text NOT NULL,
  PRIMARY KEY (idArticle)
);

CREATE TABLE Categorie (
  idCategorie varchar(50) NOT NULL, 
  Designation varchar(100) NOT NULL UNIQUE, 
  PRIMARY KEY (idCategorie)
);

CREATE TABLE CategorieUne (
  idCU      varchar(50) NOT NULL, 
  idCategorie varchar(50) NOT NULL, 
  PRIMARY KEY (idCU)
);

-- CREATE TABLE PhotosArticle (
--   idPA      varchar(50) NOT NULL, 
--   idArticle varchar(50) NOT NULL, 
--   Image     text NOT NULL, 
--   isUne     int4 NOT NULL, --1:oui 2:non
--   PRIMARY KEY (idPA)
-- );

CREATE TABLE Utilisateur (
  idUtilisateur varchar(50) NOT NULL, 
  Nom           varchar(100) NOT NULL, 
  Prenom        varchar(100) NOT NULL, 
  Login         varchar(100) NOT NULL UNIQUE, 
  Mdp           varchar(100) NOT NULL, 
  PRIMARY KEY (idUtilisateur)
);

-- alter table Utilisateur add column Type int4 not null; 1: admin 2:auteur
-- alter table Utilisateur drop column type;  
-- alter table article add column Image text NOT NULL;

ALTER TABLE Article ADD CONSTRAINT FKArticle851240 FOREIGN KEY (idCategorie) REFERENCES Categorie (idCategorie);
ALTER TABLE PhotosArticle ADD CONSTRAINT FKPhotosArti294980 FOREIGN KEY (idArticle) REFERENCES Article (idArticle);
ALTER TABLE Article ADD CONSTRAINT FKArticle393759 FOREIGN KEY (idAuteur) REFERENCES Utilisateur (idUtilisateur);
ALTER TABLE CategorieUne ADD CONSTRAINT FKCategorieU475747 FOREIGN KEY (idCategorie) REFERENCES Categorie (idCategorie);


INSERT INTO Utilisateur(idUtilisateur, Nom, Prenom, Login, Mdp,type) VALUES ('Utilisateur_'||nextval('s_Utilisateur'), 'Rakotomalala', 'Ariniaina Karen', 'Karen123', md5('KarenKely'),1);
INSERT INTO Utilisateur(idUtilisateur, Nom, Prenom, Login, Mdp,type) VALUES ('Utilisateur_'||nextval('s_Utilisateur'), 'Ralijaona', 'Tolotra Fanomezantsoa', 'Tolotra123', md5('TolotraKely'),2);
INSERT INTO Utilisateur(idUtilisateur, Nom, Prenom, Login, Mdp,type) VALUES ('Utilisateur_'||nextval('s_Utilisateur'), 'Rakotomalala', 'Sandy Liantsoa', 'Liantsoa123', md5('LiantsoaKely'),2);


CREATE OR REPLACE VIEW v_NbrArctileCategorie AS
  SELECT idCategorie,COUNT(idCategorie) as Nombre FROM Article GROUP BY idCategorie;


CREATE OR REPLACE VIEW v_Categorie AS
  SELECT C.*,
  CASE
      WHEN V.Nombre is NULL THEN 0::Integer
      ELSE V.Nombre::Integer
  END
  FROM Categorie C LEFT JOIN v_NbrArctileCategorie V ON V.idCategorie=C.idCategorie;

INSERT INTO Categorie VALUES ('Categorie_'||nextval('s_Categorie'),'Machine Learning');
INSERT INTO Categorie VALUES ('Categorie_'||nextval('s_Categorie'),'Robotics and Automation');
INSERT INTO Categorie VALUES ('Categorie_'||nextval('s_Categorie'),'IA trends');

CREATE OR REPLACE VIEW v_CategorieUne AS
  SELECT CU.*,Ca.Designation AS DesignationCategorie FROM CategorieUne CU JOIN Categorie Ca ON (Cu.idCategorie=Ca.idCategorie);

CREATE OR REPLACE VIEW v_Article AS
  SELECT A.*,C.Designation as DesignationCategorie,
  CASE 
    WHEN AU.idArticle is NULL THEN 0::INTEGER
    ELSE 1::INTEGER
    END as isUne
  FROM Article A JOIN Categorie C ON A.idCategorie=C.idCategorie
  LEFT JOIN ArticleUne AU ON (AU.idArticle=A.idArticle) ;

-- SELECT * FROM v_Article WHERE idCategorie='' AND Titre like '%r%' AND DatePublication<= '' AND DatePublication>='' AND idAuteur='' ORDER BY DatePublication DESC;

CREATE TABLE ArticleUne (
  idArticleUne varchar(50) NOT NULL, 
  idArticle    varchar(50) NOT NULL, 
  PRIMARY KEY (idArticleUne)
);

CREATE SEQUENCE s_ArticleUne INCREMENT BY 1 NO MINVALUE NO MAXVALUE START WITH 1

INSERT INTO ArticleUne VALUES ('ArticleUne_'||nextval('s_ArticleUne'),'Article_2');
