package com.example.HandToHand.entite;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class DemandeInscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;

    @Email(message = "Email invalide")
    @NotBlank(message = "L'email est obligatoire")
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    private String pwd;

    @NotBlank(message = "La confirmation du mot de passe est obligatoire")
    private String pwdconf;

    private String image;

    @NotBlank(message = "Le CIN est obligatoire")
    private String cin;

    @Min(value = 18, message = "L'âge minimum est 18 ans")
    private int age;

    @Positive(message = "Le numéro de téléphone doit être positif")
    private int tel;

    // Le champ approuvee pour savoir si la demande a été validée
    private boolean approuvee;

    // Getters et setters
    // Validation des mots de passe dans la classe
    @Transient
    public boolean isPwdMatching() {
        return pwd != null && pwd.equals(pwdconf);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public boolean isApprouvee() {
        return approuvee;
    }

    public void setApprouvee(boolean approuvee) {
        this.approuvee = approuvee;
    }
}
