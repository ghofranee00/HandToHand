package com.example.HandToHand.Service;

import com.example.HandToHand.entite.Donneur;
import com.example.HandToHand.entite.Notification;
import com.example.HandToHand.repository.Admin.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }
    public Notification saveNotification(Notification notification) {
        if (notification.getDonneur() == null) {
            notification.setDonneur(new Donneur());  // Crée un nouvel objet Donneur si c'est nécessaire
        }
        return notificationRepository.save(notification);
    }

}
