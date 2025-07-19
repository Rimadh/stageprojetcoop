package com.example.tt.dto;

import com.example.tt.Entity.Candidat;
import com.example.tt.Entity.CooptationStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data

public class CooptationRequestDTO {
    // Consultant (coopteur) fields
    private String consultantId;

    // Candidat (coopté) fields
    private String genre;
    private String prenom;
    private String nom;
    private String statut;
    private Date dateNaissance;
    private String telephone;
    private String email;
    private String villeResidence;
    private boolean cguAcceptees;
    private String cvFileName;

    // Cooptation fields
    private String commentaire;

    public CooptationRequestDTO() {
    }

    public CooptationRequestDTO(String consultantId, String genre, String prenom, String nom, Date dateNaissance, String telephone, String email, String villeResidence, boolean cguAcceptees, String commentaire) {
        this.consultantId = consultantId;
        this.genre = genre;
        this.prenom = prenom;
        this.nom = nom;
        this.dateNaissance = dateNaissance;
        this.telephone = telephone;
        this.email = email;
        this.villeResidence = villeResidence;
        this.cguAcceptees = cguAcceptees;
        this.commentaire = commentaire;
    }

    public String getConsultantId() {
        return consultantId;
    }

    public void setConsultantId(String consultantId) {
        this.consultantId = consultantId;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVilleResidence() {
        return villeResidence;
    }

    public void setVilleResidence(String villeResidence) {
        this.villeResidence = villeResidence;
    }

    public boolean isCguAcceptees() {
        return cguAcceptees;
    }

    public void setCguAcceptees(boolean cguAcceptees) {
        this.cguAcceptees = cguAcceptees;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getCvFileName() {
        return cvFileName;
    }

    public void setCvFileName(String cvFileName) {
        this.cvFileName = cvFileName;
    }
}