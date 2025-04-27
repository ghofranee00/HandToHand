package com.example.HandToHand.repository.Admin;

import com.example.HandToHand.entite.DemandeInscription;  // Importation manquante
import com.example.HandToHand.repository.Admin.DonneurRepository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DemandeInscriptionRepository extends JpaRepository<DemandeInscription, Long> {
    DemandeInscription findByEmail(String email);
}
