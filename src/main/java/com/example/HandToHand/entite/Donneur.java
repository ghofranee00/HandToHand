package com.example.HandToHand.entite;
import jakarta.validation.constraints.*;

import jakarta.persistence.*;

@Entity
public class Donneur  {

    @Column(nullable = false, unique = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public String getRole() {
        return role;
    }

    private final String role = "DONNEUR"; // valeur fixe

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

    @Pattern(regexp = "^\\+?\\d{9,15}$", message = "Le numéro de téléphone doit être valide (ex: +21612345678)")
    private String tel;


    // Getters et setters
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

}

