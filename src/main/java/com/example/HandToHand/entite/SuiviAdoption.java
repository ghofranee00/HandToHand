package com.example.HandToHand.entite;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
public class SuiviAdoption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    public SuiviAdoption(Long id, StatutAdoption statutAdoption, String raisonAnnulation, Orphelin orphelin, Donneur donneur, LocalDateTime dateAdoption, String notesSuivi, LocalDateTime dateAnnulation) {
        this.id = id;
        this.statutAdoption = statutAdoption;
        this.raisonAnnulation = raisonAnnulation;
        this.orphelin = orphelin;
        this.donneur = donneur;
        this.dateAdoption = dateAdoption;
        this.notesSuivi = notesSuivi;
        this.dateAnnulation = dateAnnulation;
    }

    @Enumerated(EnumType.STRING)
    private StatutAdoption statutAdoption;
    private String raisonAnnulation;

    public StatutAdoption getStatutAdoption() {
        return statutAdoption;
    }

    public String getRaisonAnnulation() {
        return raisonAnnulation;
    }

    public void setRaisonAnnulation(String raisonAnnulation) {
        this.raisonAnnulation = raisonAnnulation;
    }

    @ManyToOne
    @JoinColumn(name = "orphelin_id", nullable = false)
    private Orphelin orphelin;  // Clé étrangère vers l'Orphelin

    @ManyToOne
    @JoinColumn(name = "donneur_id", nullable = false)
    private Donneur donneur;  // Clé étrangère vers le Donneur



    @CreationTimestamp
    private LocalDateTime dateAdoption;

    public String getNotesSuivi() {
        return notesSuivi;
    }

    public void setNotesSuivi(String notesSuivi) {
        this.notesSuivi = notesSuivi;
    }

    public LocalDateTime getDateAdoption() {
        return dateAdoption;
    }

    public void setDateAdoption(LocalDateTime dateAdoption) {
        this.dateAdoption = dateAdoption;
    }

    public Donneur getDonneur() {
        return donneur;
    }

    public void setDonneur(Donneur donneur) {
        this.donneur = donneur;
    }

    public Orphelin getOrphelin() {
        return orphelin;
    }

    public void setOrphelin(Orphelin orphelin) {
        this.orphelin = orphelin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(columnDefinition = "TEXT")
    private String notesSuivi;

    public void setStatutAdoption(StatutAdoption statutAdoption) {
        this.statutAdoption = statutAdoption;
    }
    @UpdateTimestamp
    private LocalDateTime dateAnnulation;

    public LocalDateTime getDateAnnulation() {
        return dateAnnulation;
    }

    public void setDateAnnulation(LocalDateTime dateAnnulation) {
        this.dateAnnulation = dateAnnulation;
    }

        // ...
        public SuiviAdoption() {
        }


    // Getters et Setters
}
