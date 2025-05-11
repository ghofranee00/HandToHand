package com.example.HandToHand.repository.Admin;

import com.example.HandToHand.entite.DemandeAdoption;
import com.example.HandToHand.entite.StatutDemandeAdoption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DemandeAdoptionRepository extends JpaRepository<DemandeAdoption, Long> {
    List<DemandeAdoption> findByStatut(StatutDemandeAdoption statutDemandeAdoption);
}
