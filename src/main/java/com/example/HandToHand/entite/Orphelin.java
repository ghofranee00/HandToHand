package com.example.HandToHand.entite;

import jakarta.persistence.*;

@Entity

public class Orphelin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ido;
    private String Nom;
    private String Prenom;
    private Long age;
    private String Origine;
    private String Personnalite;
    private String Scolarité;
    private String Passions;
    private String Langues;
    private String Situation;
    private String image;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    // Constructeur sans argument
    public Orphelin() {
        // Le constructeur sans argument est nécessaire pour JPA
    }

    // Constructeur avec arguments (existant)
    public Orphelin(Long ido, String nom, String prenom, Long age, String origine, String personnalite, String scolarité, String passions, String langues, String situation, String image) {
        this.ido = ido;
        Nom = nom;
        Prenom = prenom;
        this.age = age;
        Origine = origine;
        Personnalite = personnalite;
        Scolarité = scolarité;
        Passions = passions;
        Langues = langues;
        Situation = situation;
        this.image = image;
    }

    public Long getIdo() {
        return ido;
    }

    public void setIdo(Long ido) {
        this.ido = ido;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getOrigine() {
        return Origine;
    }

    public void setOrigine(String origine) {
        Origine = origine;
    }

    public String getPersonnalite() {
        return Personnalite;
    }

    public void setPersonnalite(String personnalite) {
        Personnalite = personnalite;
    }

    public String getScolarité() {
        return Scolarité;
    }

    public void setScolarité(String scolarité) {
        Scolarité = scolarité;
    }

    public String getPassions() {
        return Passions;
    }

    public void setPassions(String passions) {
        Passions = passions;
    }

    public String getLangues() {
        return Langues;
    }

    public void setLangues(String langues) {
        Langues = langues;
    }

    public String getSituation() {
        return Situation;
    }

    public void setSituation(String situation) {
        Situation = situation;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
// Getters et setters...
}
