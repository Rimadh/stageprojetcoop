package com.example.tt.Service;

import com.example.tt.Entity.Candidat;
import com.example.tt.Entity.Consultant;
import com.example.tt.Entity.Manager;
import com.example.tt.Repository.CandidatRepository;
import com.example.tt.Repository.ConsultantRepository;
import com.example.tt.Repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CandidatService implements ICandidat {

    @Autowired
    private CandidatRepository candidatRepository;

    @Autowired
    private ConsultantRepository consultantRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Override
    @Transactional
    public Candidat createCandidat(Candidat candidat, String consultantId, String managerId) {
        // 1. Validation des paramètres
        if (candidat == null) {
            throw new IllegalArgumentException("L'objet candidat ne peut pas être null");
        }
        if (consultantId == null || consultantId.isEmpty()) {
            throw new IllegalArgumentException("L'ID du consultant est obligatoire");
        }
        if (managerId == null || managerId.isEmpty()) {
            throw new IllegalArgumentException("L'ID du manager est obligatoire");
        }

        // 2. Récupération des entités
        Consultant consultant = consultantRepository.findById(consultantId)
                .orElseThrow(() -> new RuntimeException("Consultant non trouvé avec l'ID: " + consultantId));

        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() -> new RuntimeException("Manager non trouvé avec l'ID: " + managerId));

        // 3. Vérification de la cohérence des données
        if (candidat.getNom() == null || candidat.getNom().isEmpty()) {
            throw new IllegalArgumentException("Le nom du candidat est obligatoire");
        }
        if (candidat.getPrenom() == null || candidat.getPrenom().isEmpty()) {
            throw new IllegalArgumentException("Le prénom du candidat est obligatoire");
        }

        // 4. Association des entités
        candidat.setConsultant(consultant);
        candidat.setManager(manager);

        // 5. Sauvegarde
        return candidatRepository.save(candidat);
    }

    @Override
    public List<Candidat> getAllCandidats() {
        return candidatRepository.findAll();
    }

    @Override
    public Candidat getCandidatById(String idCandidat) {
        if (idCandidat == null || idCandidat.isEmpty()) {
            throw new IllegalArgumentException("L'ID du candidat est obligatoire");
        }
        return candidatRepository.findById(idCandidat).orElse(null);
    }

    @Override
    public void deleteCandidat(String idCandidat) {
        if (idCandidat == null || idCandidat.isEmpty()) {
            throw new IllegalArgumentException("L'ID du candidat est obligatoire");
        }
        candidatRepository.deleteById(idCandidat);
    }


    public Candidat saveCandidat(Candidat candidat) {
        return candidatRepository.save(candidat);
    }
}