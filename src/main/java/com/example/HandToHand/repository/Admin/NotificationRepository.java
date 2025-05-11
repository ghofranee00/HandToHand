package com.example.HandToHand.repository.Admin;


import com.example.HandToHand.entite.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    // Tu peux ajouter des méthodes spécifiques pour filtrer par destinataire ou rôle
    List<Notification> findByDestinataireId(Long destinataireId);
    List<Notification> findByDestinataireRole(String destinataireRole);
}
