package com.example.tt.Service;


import com.example.tt.dto.CooptationRequestDTO;
import com.example.tt.dto.CooptationResponseDTO;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ICooptation {

        List<CooptationResponseDTO> getAllCooptations();
        public CooptationResponseDTO getCooptationById(String id) ;
        List<CooptationResponseDTO> getCooptationsByStatut(String statut);
        CooptationResponseDTO createCooptation(CooptationRequestDTO requestDTO, MultipartFile cvFile);
        public CooptationResponseDTO updateCooptation(String id, CooptationRequestDTO dto, MultipartFile cvFile);
        void deleteCooptation(String id);
        public void notifyManagerByEmail(String cooptationId, String candidatNom, String candidatPrenom);
        List<CooptationResponseDTO> getCooptationsByConsultant(String consultantId);
        CooptationResponseDTO updateStatus(String id, String newStatus);
        CooptationResponseDTO uploadCv(String id, MultipartFile cvFile);
        String getCvFileName(String id);
        Resource getCvFile(String id);
}