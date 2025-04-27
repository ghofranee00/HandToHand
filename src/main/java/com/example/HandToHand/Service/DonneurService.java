package com.example.HandToHand.Service;

import com.example.HandToHand.entite.Donneur;

import java.util.Optional;

public interface DonneurService {
    //void ajouterDonneur(Donneur donneur);
    //Donneur login(String email, String pwd);
    Optional<Donneur> findByEmail(String email);

}
