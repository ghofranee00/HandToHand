package com.example.HandToHand.repository.Admin;
import com.example.HandToHand.entite.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // Récupérer les notifications par rôle (ex. ADMIN)
    List<Notification> findByDestinataireRole(String role);

    // Récupérer les notifications d’un utilisateur spécifique (ex. un donneur)
    List<Notification> findByDestinataireIdAndDestinataireRole(Long destinataireId, String role);
    List<Notification> findAll();  // récupère toutes les notifications

    // Récupérer les non vues
}
