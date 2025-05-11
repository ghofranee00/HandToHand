package com.example.HandToHand.repository.Admin;
import com.example.HandToHand.entite.StatutAdoption;
import com.example.HandToHand.entite.SuiviAdoption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface SuiviAdoptionRepository extends JpaRepository<SuiviAdoption, Long> {

    // Méthode pour récupérer les suivis d'adoption par Orphelin
    List<SuiviAdoption> findByOrphelinIdo(Long Ido);

    // Méthode pour récupérer les suivis d'adoption par Donneur
    List<SuiviAdoption> findByDonneurId(Long donneurId);
}
