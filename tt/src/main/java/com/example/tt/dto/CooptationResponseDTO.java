package com.example.tt.dto;

import com.example.tt.Entity.CooptationStatus;
import lombok.Data;

import java.util.Date;

@Data
public class CooptationResponseDTO {
    private String id;
    private Date dateSoumission;
    private String statut;
    private String commentaire;
    private String consultantNom;
    private String consultantPrenom;
    private String candidatNom;
    private String candidatPrenom;
    private String candidatEmail;
    private String CvFileName;
    private String managerEmail;

    public CooptationResponseDTO() {
    }

    public CooptationResponseDTO(String id, Date dateSoumission, String statut, String commentaire, String consultantNom, String consultantPrenom, String candidatNom, String candidatPrenom, String candidatEmail, String cvFileName, String managerEmail) {
        this.id = id;
        this.dateSoumission = dateSoumission;
        this.statut = statut;
        this.commentaire = commentaire;
        this.consultantNom = consultantNom;
        this.consultantPrenom = consultantPrenom;
        this.candidatNom = candidatNom;
        this.candidatPrenom = candidatPrenom;
        this.candidatEmail = candidatEmail;
        CvFileName = cvFileName;
        this.managerEmail = managerEmail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDateSoumission() {
        return dateSoumission;
    }

    public void setDateSoumission(Date dateSoumission) {
        this.dateSoumission = dateSoumission;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getConsultantNom() {
        return consultantNom;
    }

    public void setConsultantNom(String consultantNom) {
        this.consultantNom = consultantNom;
    }

    public String getConsultantPrenom() {
        return consultantPrenom;
    }

    public void setConsultantPrenom(String consultantPrenom) {
        this.consultantPrenom = consultantPrenom;
    }

    public String getCandidatNom() {
        return candidatNom;
    }

    public void setCandidatNom(String candidatNom) {
        this.candidatNom = candidatNom;
    }

    public String getCandidatPrenom() {
        return candidatPrenom;
    }

    public void setCandidatPrenom(String candidatPrenom) {
        this.candidatPrenom = candidatPrenom;
    }

    public String getCandidatEmail() {
        return candidatEmail;
    }

    public void setCandidatEmail(String candidatEmail) {
        this.candidatEmail = candidatEmail;
    }

    public String getCvFileName() {
        return CvFileName;
    }

    public void setCvFileName(String cvFileName) {
        CvFileName = cvFileName;
    }

    public String getManagerEmail() {
        return managerEmail;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }
}