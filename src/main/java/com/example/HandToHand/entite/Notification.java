package com.example.HandToHand.entite;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Donneur getDonneur() {
        return donneur;
    }

    public void setDonneur(Donneur donneur) {
        this.donneur = donneur;
    }

    @ManyToOne
    @JoinColumn(name = "donneur_id")  // Relation avec l'entité Donneur
    private Donneur donneur;

    private String message;

    private String expediteurRole;
    private Long expediteurId;

    public String getExpediteurRole() {
        return expediteurRole;
    }

    public void setExpediteurRole(String expediteurRole) {
        this.expediteurRole = expediteurRole;
    }

    public Long getExpediteurId() {
        return expediteurId;
    }

    public void setExpediteurId(Long expediteurId) {
        this.expediteurId = expediteurId;
    }

    private LocalDateTime dateEnvoi = LocalDateTime.now();

    private String destinataireRole; // "ADMIN" ou "DONNEUR"

    private Long destinataireId; // l'id du donneur si c'est un donneur, sinon null (ou admin id si tu as une entité admin plus tard)

    public Long getId() {
        return id;
    }


    public Notification(Long id, Donneur donneur, String message, String expediteurRole, Long expediteurId, LocalDateTime dateEnvoi, String destinataireRole, Long destinataireId) {
        this.id = id;
        this.donneur = donneur;
        this.message = message;
        this.expediteurRole = expediteurRole;
        this.expediteurId = expediteurId;
        this.dateEnvoi = dateEnvoi;
        this.destinataireRole = destinataireRole;
        this.destinataireId = destinataireId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }
    public Notification(String s, Donneur donneur, String admin) {
        // Constructeur sans arguments
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public Notification() {
        // initialisation des propriétés
    }



    public LocalDateTime getDateEnvoi() {
        return dateEnvoi;
    }

    public void setDateEnvoi(LocalDateTime dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }

    public String getDestinataireRole() {
        return destinataireRole;
    }

    public void setDestinataireRole(String destinataireRole) {
        this.destinataireRole = destinataireRole;
    }

    public Long getDestinataireId() {
        return destinataireId;
    }

    public void setDestinataireId(Long destinataireId) {
        this.destinataireId = destinataireId;
    }

    public void setDate(LocalDateTime now) {
    }

    public Long getDonneurId() {
        return null;
    }


// Getters & Setters
}
