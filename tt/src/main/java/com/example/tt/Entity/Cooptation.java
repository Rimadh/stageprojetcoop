package com.example.tt.Entity;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "Cooptations")
public class Cooptation {
    @Id
    private String idCooptation;
    @NotBlank
    private Date dateSoumission;
    @NotBlank
    private String statut; // "SOUMIS", "EN_REVUE", "VALIDE", "REJETE"
    private String commentaire;
    @NotBlank
    private CooptationStatus etat;
@NotBlank
private String CandidatNom;
@NotBlank
private String CandidatPrenom;
//    @NotBlank
//    private String managerEmail;
    @DBRef
    private Consultant consultant;

    @DBRef
    private Candidat candidat;


    // Constructeurs
    public Cooptation() {
        this.dateSoumission = new Date();
        this.statut = "SOUMIS";
    }

    public String getIdCooptation() {
        return idCooptation;
    }

    public void setIdCooptation(String idCooptation) {
        this.idCooptation = idCooptation;
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

    public CooptationStatus getEtat() {
        return etat;
    }

    public void setEtat(CooptationStatus etat) {
        this.etat = etat;
    }

    public Consultant getConsultant() {
        return consultant;
    }

    public void setConsultant(Consultant consultant) {
        this.consultant = consultant;
    }

    public Candidat getCandidat() {
        return candidat;
    }

    public void setCandidat(Candidat candidat) {
        this.candidat = candidat;
    }

//    public String getManagerEmail() {
//        return managerEmail;
//    }




}
