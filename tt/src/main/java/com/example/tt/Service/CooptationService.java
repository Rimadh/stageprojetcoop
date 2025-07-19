package com.example.tt.Service;

import com.example.tt.Entity.*;
import com.example.tt.Repository.*;
import com.example.tt.dto.*;
import com.example.tt.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
public class CooptationService implements ICooptation {

    @Autowired
    private CooptationRepository cooptationRepository;

    @Autowired
    private CandidatRepository candidatRepository;

    @Autowired
    private ConsultantRepository consultantRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private CandidatService candidatService;
    @Autowired
    private JavaMailSender mailSender;
//    @Autowired
//    private AzureADService azureADService;


    @Autowired
    public CooptationService(CooptationRepository cooptationRepository,
                             CandidatRepository candidatRepository,
                             ConsultantRepository consultantRepository,
                             FileStorageService fileStorageService,
                             CandidatService candidatService) {
        this.cooptationRepository = cooptationRepository;
        this.candidatRepository = candidatRepository;
        this.consultantRepository = consultantRepository;
        this.fileStorageService = fileStorageService;
        this.candidatService = candidatService;
    }

    // Dans CooptationService.java
    @Override
    public void notifyManagerByEmail(String managerEmail, String candidatNom, String candidatPrenom) {
        if (managerEmail == null) {
            throw new IllegalStateException("Aucun email manager disponible");
        }

        String subject = "🆕 Nouveau candidat à valider : " + candidatNom;
        String body = String.format(
                "Bonjour,\n\nUn nouveau candidat a été soumis.\n\nNom : %s\nPrénom : %s\n\nMerci de valider son dossier.",
                candidatNom,
                candidatPrenom
        );

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(managerEmail);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
    @Override
    public List<CooptationResponseDTO> getAllCooptations() {
        return cooptationRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CooptationResponseDTO getCooptationById(String id) {
        Cooptation cooptation = cooptationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cooptation non trouvée"));
        return convertToDTO(cooptation);
    }




    @Override
    public List<CooptationResponseDTO> getCooptationsByStatut(String statut) {
        return cooptationRepository.findByStatut(statut).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CooptationResponseDTO createCooptation(CooptationRequestDTO requestDTO, MultipartFile cvFile)  {
        if (requestDTO.getConsultantId() == null || requestDTO.getConsultantId().isEmpty()) {
            throw new IllegalArgumentException("Consultant ID est requis");
        }
        if (requestDTO.getNom() == null || requestDTO.getNom().isEmpty()) {
            throw new IllegalArgumentException("Nom du candidat est requis");
        }
        if (requestDTO.getPrenom() == null || requestDTO.getPrenom().isEmpty()) {
            throw new IllegalArgumentException("Prénom du candidat est requis");
        }
        if (requestDTO.getEmail() == null || requestDTO.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email du candidat est requis");
        }

        Consultant consultant = consultantRepository.findById(requestDTO.getConsultantId())
                .orElseGet(() -> {
                    Consultant newConsultant = new Consultant();
                    newConsultant.setId(requestDTO.getConsultantId());
                    newConsultant.setNom("AutoCréé");
                    newConsultant.setPrenom("AutoCréé");
                    newConsultant.setEmail("autocreated@example.com");
                    return consultantRepository.save(newConsultant);
                });

//        // Récupération du manager depuis Azure AD
//        String managerEmail = azureADService.getManagerEmail(consultant.getId());
//        if (managerEmail == null) {
//            throw new RuntimeException("Impossible de déterminer le manager pour ce consultant");
//        }

        Candidat candidat = new Candidat();
        candidat.setGenre(requestDTO.getGenre());
        candidat.setPrenom(requestDTO.getPrenom());
        candidat.setNom(requestDTO.getNom());
        candidat.setDateNaissance(requestDTO.getDateNaissance());
        candidat.setTelephone(requestDTO.getTelephone());
        candidat.setEmail(requestDTO.getEmail());

        if (cvFile != null && !cvFile.isEmpty()) {
            if (cvFile.getSize() > 5 * 1024 * 1024) {
                throw new IllegalArgumentException("La taille du fichier CV ne doit pas dépasser 5MB");
            }
            String fileName = fileStorageService.storeFile(cvFile);
            candidat.setCvFileName(fileName);
        } else if (requestDTO.getCvFileName() != null && !requestDTO.getCvFileName().isEmpty()) {
            candidat.setCvFileName(requestDTO.getCvFileName());
        }

        Candidat savedCandidat = candidatService.saveCandidat(candidat);

        Cooptation cooptation = new Cooptation();
        cooptation.setConsultant(consultant);
        cooptation.setCandidat(savedCandidat);
        cooptation.setCommentaire(requestDTO.getCommentaire());
        cooptation.setStatut("SOUMIS");
        cooptation.setEtat(CooptationStatus.EN_ATTENTE);
      // cooptation.setManagerEmail(managerEmail); // Ajout de l'email du manager
        Cooptation savedCooptation = cooptationRepository.save(cooptation);
       // notifyManagerByEmail(managerEmail, savedCandidat.getNom(), savedCandidat.getPrenom());
        return convertToDTO(savedCooptation);
    }

    @Override
    @Transactional
    public CooptationResponseDTO updateCooptation(String id, CooptationRequestDTO dto, MultipartFile cvFile) {
        Cooptation existing = cooptationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cooptation non trouvée"));

        // Récupérer le candidat lié
        Candidat candidat = existing.getCandidat();
        if (candidat == null) {
            throw new ResourceNotFoundException("Candidat lié à cette cooptation non trouvé");
        }

        // Mettre à jour les champs du candidat
        candidat.setGenre(dto.getGenre());
        candidat.setPrenom(dto.getPrenom());
        candidat.setNom(dto.getNom());
        candidat.setEmail(dto.getEmail());
        candidat.setTelephone(dto.getTelephone());
        candidat.setDateNaissance(dto.getDateNaissance());

        // Gérer le commentaire sur la cooptation
        existing.setCommentaire(dto.getCommentaire());

        // Gérer le fichier CV
        if (cvFile != null && !cvFile.isEmpty()) {
            if (cvFile.getSize() > 5 * 1024 * 1024) {
                throw new IllegalArgumentException("La taille du fichier CV ne doit pas dépasser 5MB");
            }
            String fileName = fileStorageService.storeFile(cvFile);
            candidat.setCvFileName(fileName);
        }

        // Sauvegarder le candidat
        candidatRepository.save(candidat);

        // Sauvegarder la cooptation
        Cooptation updated = cooptationRepository.save(existing);

        return convertToDTO(updated);
    }


    @Override
    @Transactional
    public void deleteCooptation(String id) {
        Cooptation cooptation = cooptationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cooptation non trouvée"));

        if (cooptation.getCandidat() != null) {
            candidatRepository.delete(cooptation.getCandidat());
        }
        cooptationRepository.delete(cooptation);
    }

    @Override
    public List<CooptationResponseDTO> getCooptationsByConsultant(String consultantId) {
        return cooptationRepository.findByConsultantId(consultantId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CooptationResponseDTO updateStatus(String id, String newStatus) {
        Cooptation cooptation = cooptationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cooptation non trouvée"));

        cooptation.setStatut(newStatus);
        return convertToDTO(cooptationRepository.save(cooptation));
    }

    @Override
    public CooptationResponseDTO uploadCv(String id, MultipartFile cvFile) {
        Cooptation cooptation = cooptationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cooptation non trouvée"));

        if (cooptation.getCandidat() == null) {
            throw new ResourceNotFoundException("Candidat non trouvé pour cette cooptation");
        }

        if (cvFile == null || cvFile.isEmpty()) {
            throw new IllegalArgumentException("Fichier CV vide");
        }

        String cvFileName = fileStorageService.storeFile(cvFile);
        cooptation.getCandidat().setCvFileName(cvFileName);
        candidatRepository.save(cooptation.getCandidat());

        return convertToDTO(cooptationRepository.save(cooptation));
    }

    @Override
    public String getCvFileName(String id) {
        Cooptation cooptation = cooptationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cooptation non trouvée"));

        if (cooptation.getCandidat() == null || cooptation.getCandidat().getCvFileName() == null) {
            throw new ResourceNotFoundException("CV non trouvé");
        }

        return cooptation.getCandidat().getCvFileName();
    }


    @Override
    public Resource getCvFile(String id) {
        String fileName = getCvFileName(id);
        return fileStorageService.loadFile(fileName);
    }

    private CooptationResponseDTO convertToDTO(Cooptation cooptation) {
        CooptationResponseDTO dto = new CooptationResponseDTO();
        dto.setId(cooptation.getIdCooptation());
        dto.setDateSoumission(cooptation.getDateSoumission());
        dto.setStatut(cooptation.getStatut());
        dto.setCommentaire(cooptation.getCommentaire());
      //  dto.setManagerEmail(cooptation.getManagerEmail()); // Ajouté
        if (cooptation.getConsultant() != null) {
            dto.setConsultantNom(cooptation.getConsultant().getNom());
            dto.setConsultantPrenom(cooptation.getConsultant().getPrenom());
        }

        if (cooptation.getCandidat() != null) {
            dto.setCandidatNom(cooptation.getCandidat().getNom());
            dto.setCandidatPrenom(cooptation.getCandidat().getPrenom());
            dto.setCandidatEmail(cooptation.getCandidat().getEmail());
            dto.setCvFileName(cooptation.getCandidat().getCvFileName());
        }

        return dto;
    }
}