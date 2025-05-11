package com.example.HandToHand.entite;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.time.LocalDateTime;
@Entity
public class DemandeAdoption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "donneur_id", nullable = false)
    private Donneur donneur;

    @ManyToOne
    @JoinColumn(name = "orphelin_id", nullable = false)
    private Orphelin orphelin;

    @Column(nullable = false)
    private Double revenuMensuel;

    @NotBlank(message = "La profession ne peut pas être vide.")
    private String profession;

    @Column(name = "nombre_membres_famille", nullable = false)
    private Integer nombreMembresFamille;

    @Column(columnDefinition = "TEXT")
    private String maladies;

    @Column(nullable = false, length = 50)
    private String etatCivil;

    private Boolean aDesEnfants = false;

    @Column(nullable = false, length = 50)
    private String typeHabitation;

    @Column(nullable = false)
    private Integer heuresTravailSemaine;

    @Column(length = 100)
    private String formationParentale;

    @Column(columnDefinition = "TEXT")
    private String historiqueMariage;

    private Boolean antecedentsJudiciaires = false;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String preferencesAdoption;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String motivation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutDemandeAdoption statut = StatutDemandeAdoption.EN_ATTENTE;

    @CreationTimestamp
    private LocalDateTime dateCreation;

    @UpdateTimestamp
    private LocalDateTime dateMiseAJour;

    @Column(columnDefinition = "TEXT")
    private String notesAdministration;

    // Getters et Setters

    public void accepterDemande() {
        this.statut = StatutDemandeAdoption.ACCEPTEE;
        // Déplace l'adoption vers la table de suivi
    }

    public void refuserDemande() {
        this.statut = StatutDemandeAdoption.REFUSEE;
    }

    public Donneur getDonneur() {
        return donneur;
    }
    public Orphelin getOrphelin() {
        return orphelin;
    }
    public DemandeAdoption() {
        // Aucun code spécifique nécessaire ici, cela permet à JPA de créer une instance par défaut
    }
    public DemandeAdoption(Long id, Donneur donneur, Orphelin orphelin, Double revenuMensuel, String profession, Integer nombreMembresFamille, String maladies, String etatCivil, Boolean aDesEnfants, String typeHabitation, Integer heuresTravailSemaine, String formationParentale, String historiqueMariage, Boolean antecedentsJudiciaires, String preferencesAdoption, String motivation, StatutDemandeAdoption statut, LocalDateTime dateCreation, LocalDateTime dateMiseAJour, String notesAdministration) {
        this.id = id;
        this.donneur = donneur;
        this.orphelin = orphelin;
        this.revenuMensuel = revenuMensuel;
        this.profession = profession;
        this.nombreMembresFamille = nombreMembresFamille;
        this.maladies = maladies;
        this.etatCivil = etatCivil;
        this.aDesEnfants = aDesEnfants;
        this.typeHabitation = typeHabitation;
        this.heuresTravailSemaine = heuresTravailSemaine;
        this.formationParentale = formationParentale;
        this.historiqueMariage = historiqueMariage;
        this.antecedentsJudiciaires = antecedentsJudiciaires;
        this.preferencesAdoption = preferencesAdoption;
        this.motivation = motivation;
        this.statut = StatutDemandeAdoption.EN_ATTENTE;  // Initialisation du statut par défaut
        this.dateCreation = dateCreation;
        this.dateMiseAJour = dateMiseAJour;
        this.notesAdministration = notesAdministration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDonneur(Donneur donneur) {
        this.donneur = donneur;
    }

    public void setOrphelin(Orphelin orphelin) {
        this.orphelin = orphelin;
    }

    public Double getRevenuMensuel() {
        return revenuMensuel;
    }

    public void setRevenuMensuel(Double revenuMensuel) {
        this.revenuMensuel = revenuMensuel;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public Integer getNombreMembresFamille() {
        return nombreMembresFamille;
    }

    public void setNombreMembresFamille(Integer nombreMembresFamille) {
        this.nombreMembresFamille = nombreMembresFamille;
    }

    public String getMaladies() {
        return maladies;
    }

    public void setMaladies(String maladies) {
        this.maladies = maladies;
    }

    public String getEtatCivil() {
        return etatCivil;
    }

    public void setEtatCivil(String etatCivil) {
        this.etatCivil = etatCivil;
    }

    public Boolean getaDesEnfants() {
        return aDesEnfants;
    }

    public void setaDesEnfants(Boolean aDesEnfants) {
        this.aDesEnfants = aDesEnfants;
    }

    public String getTypeHabitation() {
        return typeHabitation;
    }

    public void setTypeHabitation(String typeHabitation) {
        this.typeHabitation = typeHabitation;
    }

    public Integer getHeuresTravailSemaine() {
        return heuresTravailSemaine;
    }

    public void setHeuresTravailSemaine(Integer heuresTravailSemaine) {
        this.heuresTravailSemaine = heuresTravailSemaine;
    }

    public String getFormationParentale() {
        return formationParentale;
    }

    public void setFormationParentale(String formationParentale) {
        this.formationParentale = formationParentale;
    }

    public String getHistoriqueMariage() {
        return historiqueMariage;
    }

    public void setHistoriqueMariage(String historiqueMariage) {
        this.historiqueMariage = historiqueMariage;
    }

    public Boolean getAntecedentsJudiciaires() {
        return antecedentsJudiciaires;
    }

    public void setAntecedentsJudiciaires(Boolean antecedentsJudiciaires) {
        this.antecedentsJudiciaires = antecedentsJudiciaires;
    }

    public String getPreferencesAdoption() {
        return preferencesAdoption;
    }

    public void setPreferencesAdoption(String preferencesAdoption) {
        this.preferencesAdoption = preferencesAdoption;
    }

    public String getMotivation() {
        return motivation;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }

    public StatutDemandeAdoption getStatut() {
        return statut;
    }

    public void setStatut(StatutDemandeAdoption statut) {
        this.statut = statut;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDateTime getDateMiseAJour() {
        return dateMiseAJour;
    }

    public void setDateMiseAJour(LocalDateTime dateMiseAJour) {
        this.dateMiseAJour = dateMiseAJour;
    }

    public String getNotesAdministration() {
        return notesAdministration;
    }

    public void setNotesAdministration(String notesAdministration) {
        this.notesAdministration = notesAdministration;
    }
}