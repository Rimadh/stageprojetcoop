package com.example.tt.Controller;


import com.example.tt.Entity.Candidat;
import com.example.tt.Entity.Consultant;
import com.example.tt.Service.CandidatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/candidats")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CandidatController {
@Autowired
    private  CandidatService candidatService;

    @PostMapping
    public ResponseEntity<?> createCandidat(
            @RequestBody Map<String, Object> request) {

        try {
            // Extraction des paramètres
            String consultantId = (String) request.get("consultantId");
            String managerId = (String) request.get("managerId");

            // Validation
            if (consultantId == null || managerId == null) {
                throw new IllegalArgumentException("consultantId et managerId sont obligatoires");
            }

            // Création du candidat
            Candidat candidat = new Candidat();
            candidat.setNom((String) request.get("nom"));
            candidat.setPrenom((String) request.get("prenom"));
            // ... autres champs si nécessaire

            return ResponseEntity.ok(
                    candidatService.createCandidat(candidat, consultantId, managerId)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping
    public List<Candidat> getAll() {
        return candidatService.getAllCandidats();
    }

    @GetMapping("/{id}")
    public Candidat getById(@PathVariable String id) {
        return candidatService.getCandidatById(id);
    }



    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        candidatService.deleteCandidat(id);
    }
}
