package com.example.HandToHand.repository.Admin;

import com.example.HandToHand.entite.Orphelin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrphelinRepository extends JpaRepository<Orphelin,Long> {
    Optional<Orphelin> findById(Long ido);

}

