package com.example.HandToHand.Controller;


import com.example.HandToHand.Service.NotificationService;
import com.example.HandToHand.entite.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {


    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public List<Notification> getAllNotifications() {
        return notificationService.getAllNotifications();
    }
}