package com.example.HandToHand.Service;
import com.example.HandToHand.entite.SuiviAdoption;
import com.example.HandToHand.repository.Admin.SuiviAdoptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SuiviAdoptionServiceImpl implements suiviAdoptionService {

    @Autowired
    private SuiviAdoptionRepository suiviAdoptionRepository;

    // Méthode pour récupérer tous les suivis d'adoption
    @Override
    public List<SuiviAdoption> getAllSuivis() {
        return suiviAdoptionRepository.findAll(); // Récupérer tous les suivis d'adoption
    }

    // Optionnel: méthode pour récupérer les suivis d'adoption par Orphelin (si tu veux un filtre)
    public List<SuiviAdoption> getSuiviByOrphelin(Long Ido) {
        return suiviAdoptionRepository.findByOrphelinIdo(Ido); // Méthode à définir si tu veux filtrer par orphelin
    }

    // Optionnel: méthode pour récupérer les suivis d'adoption par Donneur (si tu veux un filtre)
    public List<SuiviAdoption> getSuiviByDonneur(Long donneurId) {
        return suiviAdoptionRepository.findByDonneurId(donneurId); // Méthode à définir si tu veux filtrer par donneur
    }
}
