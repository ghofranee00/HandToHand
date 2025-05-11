package com.example.HandToHand.Controller;

import com.example.HandToHand.Service.suiviAdoptionService;
import com.example.HandToHand.entite.SuiviAdoption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/suivi-adoption")
public class SuiviAdoptionController {

    @Autowired
    private suiviAdoptionService suiviAdoptionService;

    @GetMapping("/all")
    public List<SuiviAdoption> getAllSuivis() {
        return suiviAdoptionService.getAllSuivis();
    }
}
