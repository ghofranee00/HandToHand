package com.example.HandToHand.repository.Admin;


import com.example.HandToHand.entite.Donneur;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DonneurRepository extends JpaRepository<Donneur, Long> {
    // Tu peux ajouter des méthodes personnalisées ici si nécessaire
    Optional<Donneur> findByEmail(String email);

}

