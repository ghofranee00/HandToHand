package com.example.HandToHand.Service;

import com.example.HandToHand.entite.Donneur;

import java.util.List;
import java.util.Map;

public interface DonneurService {

    Map<String, Object> loginDonneur(String email, String pwd);
    //void logoutDonneur(String token); // Si tu choisis de gérer la révocation des tokens
    Map<String, Object> getProfileFromToken(String token); // ➕ ajoute cette ligne
    List<Donneur> getAllDonneurs(); // Méthode ajoutée pour récupérer tous les donneurs acceptés

    void logout(String token);

    boolean isTokenBlacklisted(String token);

    Map<String, Object> getProfileByEmail(String email);

    String getEmailFromToken(String token);
}
