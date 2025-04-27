package com.example.HandToHand.entite;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String pwd;
    private String pwdconf;
    private String image;
    @OneToMany(mappedBy = "admin")
    private List<Orphelin> orphelinList;
    @OneToMany(mappedBy = "admin")
    private List<Donation> donationList;

    public Admin(Long id, String nom, String prenom, String pwd, String pwdconf, String image) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.pwd = pwd;
        this.pwdconf = pwdconf;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPwdconf() {
        return pwdconf;
    }

    public void setPwdconf(String pwdconf) {
        this.pwdconf = pwdconf;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
