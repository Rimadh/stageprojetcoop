package com.example.tt.Controller;


import com.example.tt.Service.CooptationService;
import com.example.tt.dto.CooptationRequestDTO;
import com.example.tt.dto.CooptationResponseDTO;
import com.example.tt.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/cooptations")
@CrossOrigin(origins = "http://localhost:4200")
public class CooptationController {

    @Autowired
    private CooptationService cooptationService;

    @Autowired
    public CooptationController(CooptationService cooptationService) {
        this.cooptationService = cooptationService;
    }

    // ... (tes autres méthodes ici)

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CooptationResponseDTO> updateCooptation(
            @PathVariable String id,
            @RequestParam("genre") String genre,
            @RequestParam("prenom") String prenom,
            @RequestParam("nom") String nom,
            @RequestParam("email") String email,
            @RequestParam("telephone") String telephone,
            @RequestParam("villeResidence") String villeResidence,
            @RequestParam(value = "commentaire", required = false) String commentaire,
            @RequestParam(value = "dateNaissance", required = false) String dateNaissance,
            @RequestParam(value = "cv", required = false) MultipartFile cvFile) {

        try {
            CooptationRequestDTO dto = new CooptationRequestDTO();
            dto.setGenre(genre);
            dto.setPrenom(prenom);
            dto.setNom(nom);
            dto.setEmail(email);
            dto.setTelephone(telephone);
            dto.setVilleResidence(villeResidence);
            dto.setCommentaire(commentaire);

            // Gérer la dateNaissance si fournie
            if (dateNaissance != null && !dateNaissance.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date parsedDate = sdf.parse(dateNaissance);
                dto.setDateNaissance(parsedDate);
            }

            CooptationResponseDTO response = cooptationService.updateCooptation(id, dto, cvFile);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Erreur lors de la mise à jour de la cooptation", e);
            return ResponseEntity.status(500).build();
        }
    }


    @PostMapping(value = "/{id}/cv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CooptationResponseDTO> uploadCv(
            @PathVariable String id,
            @RequestParam("cv") MultipartFile file) {
        return ResponseEntity.ok(cooptationService.uploadCv(id, file));
    }

    @GetMapping("/{id}/cv/filename")
    public ResponseEntity<String> getCvFileName(@PathVariable String id) {
        return ResponseEntity.ok(cooptationService.getCvFileName(id));
    }

    // Méthode pour récupérer le fichier CV
    @GetMapping("/{id}/cv")
    public ResponseEntity<Resource> getCvFile(@PathVariable String id, HttpServletRequest request) {
        Resource resource = cooptationService.getCvFile(id);

        // Essayer de déterminer le type de contenu
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (Exception e) {
            log.info("Impossible de déterminer le type du fichier, utilisation de 'application/octet-stream'");
        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        // Retourner le fichier avec header Content-Disposition pour téléchargement
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createCooptation(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam("genre") String genre,
            @RequestParam("prenom") String prenom,
            @RequestParam("nom") String nom,
            @RequestParam("email") String email,
            @RequestParam("telephone") String telephone,
            @RequestParam("dateNaissance") String dateNaissance,
            @RequestParam("villeResidence") String villeResidence,
            @RequestParam("commentaire") String commentaire,
            @RequestParam("cguAcceptees") String cguAccepteesStr,
            @RequestParam(value = "cv", required = false) MultipartFile cvFile
    ) {
        try {
            String consultantId = jwt.getSubject();
            log.info("🔐 Consultant connecté : {}", consultantId);

            if (cvFile != null && !cvFile.isEmpty()) {
                log.info("📄 Nom fichier : {}", cvFile.getOriginalFilename());
                log.info("📦 Taille : {}", cvFile.getSize());
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = sdf.parse(dateNaissance);

            CooptationRequestDTO dto = new CooptationRequestDTO();
            dto.setConsultantId(consultantId);
            dto.setGenre(genre);
            dto.setPrenom(prenom);
            dto.setNom(nom);
            dto.setEmail(email);
            dto.setTelephone(telephone);
            dto.setVilleResidence(villeResidence);
            dto.setCommentaire(commentaire);
            dto.setCguAcceptees(Boolean.parseBoolean(cguAccepteesStr));
            dto.setDateNaissance(parsedDate);

            CooptationResponseDTO response = cooptationService.createCooptation(dto, cvFile);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body("❌ Erreur backend : " + e.getMessage());
        }
    }
    @GetMapping
    public ResponseEntity<List<CooptationResponseDTO>> getAllCooptations() {
        List<CooptationResponseDTO> cooptations = cooptationService.getAllCooptations(); // ta méthode de service
        return ResponseEntity.ok(cooptations);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCooptation(@PathVariable String id) {
        cooptationService.deleteCooptation(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }




    ////////////

    @PostMapping("/{id}/notify-manager")
    public ResponseEntity<String> notifyManager(@PathVariable String id) {
        try {
            CooptationResponseDTO cooptation = cooptationService.getCooptationById(id);

            // Remplacer par votre logique de récupération du manager
            String managerEmail = "manager@example.com"; // Temporaire - à remplacer

            cooptationService.notifyManagerByEmail(
                    managerEmail,
                    cooptation.getCandidatNom(),
                    cooptation.getCandidatPrenom()
            );

            return ResponseEntity.ok("Notification envoyée avec succès");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Erreur: " + e.getMessage());
        }
    }


}
