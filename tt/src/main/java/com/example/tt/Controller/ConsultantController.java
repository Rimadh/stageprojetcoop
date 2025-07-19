package com.example.tt.Controller;

import com.example.tt.Entity.Consultant;
import com.example.tt.Service.ConsultantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/consultants")
@CrossOrigin(origins = "http://localhost:4200") // adapte si besoin
public class ConsultantController {

    @Autowired
    private ConsultantService consultantService;

    @PostMapping
    public ResponseEntity<Consultant> createConsultant(@RequestBody Consultant consultant) {
        Consultant saved = consultantService.createConsultant(consultant);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<Consultant>> getAllConsultants() {
        return ResponseEntity.ok(consultantService.getAllConsultants());
    }

    @GetMapping("/{idConsultant}")
    public ResponseEntity<Consultant> getConsultantById(@PathVariable String idConsultant) {
        return consultantService.getConsultantById(idConsultant)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{idConsultant}")
    public ResponseEntity<Consultant> updateConsultant(
            @PathVariable String idConsultant,
            @RequestBody Consultant consultant) {
        Consultant updated = consultantService.updateConsultant(idConsultant, consultant);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{idConsultant}")
    public ResponseEntity<Void> deleteConsultant(@PathVariable String idConsultant) {
        consultantService.deleteConsultant(idConsultant);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-email")
    public ResponseEntity<Consultant> getConsultantByEmail(@RequestParam String email) {
        return consultantService.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/manager-email")
    public ResponseEntity<String> updateManagerEmail(
            @PathVariable String id,
            @RequestParam("managerEmail") String managerEmail) {

        Consultant updated = consultantService.updateManagerEmail(id, managerEmail);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("✅ Email du manager mis à jour avec succès !");
    }

    // Méthode pour récupérer l'email depuis le JWT Azure (plus fiable)
    private String getCurrentUserEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) auth.getPrincipal();

            // Affiche tous les claims dans les logs pour debug
            System.out.println("JWT claims : " + jwt.getClaims());

            // Essaye plusieurs clés courantes
            String email = jwt.getClaimAsString("preferred_username");
            if (email == null) email = jwt.getClaimAsString("email");
            if (email == null) email = jwt.getClaimAsString("upn");
            if (email == null) email = jwt.getSubject(); // fallback sur sub

            System.out.println("Email extrait du JWT : " + email);

            return email;
        }
        return auth.getName();
    }



    // Endpoint : obtenir info consultant connecté + manager
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentConsultant() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // Vérification de l'authentification
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            return ResponseEntity.status(401).body("Utilisateur non authentifié");
        }

        // Extraction de l'email depuis le JWT ou le principal
        String email = null;
        Object principal = auth.getPrincipal();
        if (principal instanceof Jwt) {
            Jwt jwt = (Jwt) principal;
            email = jwt.getClaimAsString("preferred_username"); // claim selon Azure AD
        } else if (principal instanceof String) {
            email = (String) principal;
        }

        if (email == null) {
            return ResponseEntity.status(401).body("Email utilisateur introuvable dans le token");
        }

        Optional<Consultant> consultantOpt = consultantService.findByEmail(email);
        if (consultantOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Consultant non trouvé");
        }

        return ResponseEntity.ok(consultantOpt.get());
    }



    // Endpoint : mettre à jour le manager du consultant connecté
    @PutMapping("/me/manager")
    public ResponseEntity<?> updateManagerEmail(@RequestBody String newManagerEmail) {
        String email = getCurrentUserEmail();
        if (email == null) {
            return ResponseEntity.status(401).body("Utilisateur non authentifié");
        }
        Optional<Consultant> consultantOpt = consultantService.findByEmail(email);
        if (consultantOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Consultant non trouvé");
        }
        Consultant consultant = consultantOpt.get();
        consultant.setManagerEmail(newManagerEmail);
        consultantService.save(consultant);
        return ResponseEntity.ok("Manager mis à jour avec succès");
    }
}
