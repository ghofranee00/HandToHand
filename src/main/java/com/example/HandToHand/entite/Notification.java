package com.example.HandToHand.entite;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    private boolean lue = false;

    private LocalDateTime dateEnvoi = LocalDateTime.now();

    private String destinataireRole; // "ADMIN" ou "DONNEUR"

    private Long destinataireId; // l'id du donneur si c'est un donneur, sinon null (ou admin id si tu as une entité admin plus tard)

    public Long getId() {
        return id;
    }

    public Notification(Long id, String message, boolean lue, LocalDateTime dateEnvoi, String destinataireRole, Long destinataireId) {
        this.id = id;
        this.message = message;
        this.lue = lue;
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

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isLue() {
        return lue;
    }

    public void setLue(boolean lue) {
        this.lue = lue;
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
// Getters & Setters
}
