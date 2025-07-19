package com.example.tt.Entity;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.Date;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "candidats")
public class Candidat {

    @Id
    private String idCandidat;

    @NotBlank
    private String nom;

    @NotBlank
    private String prenom;

    @NotBlank @Email
    @Indexed(unique = true)
    private String email;

    @NotBlank

    private String telephone;

    @NotBlank

    private String posteSouhaite;

    @NotBlank
    private String cvFileName;

    @NotBlank
    private String genre;

    @NotBlank
    private Date DateNaissance;

    @NotBlank
    private String cv;
    @DBRef

    private Consultant consultant;

    @DBRef

    private Manager manager;

    public Candidat() {
    }

    public Candidat(String idCandidat, String nom, String prenom, String email, String telephone, String posteSouhaite, String cvFileName, String genre, Consultant consultant, Manager manager) {
        this.idCandidat = idCandidat;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.posteSouhaite = posteSouhaite;
        this.cvFileName = cvFileName;
        this.genre = genre;
        this.consultant = consultant;
        this.manager = manager;
    }

    public String getIdCandidat() {
        return idCandidat;
    }

    public void setIdCandidat(String idCandidat) {
        this.idCandidat = idCandidat;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPosteSouhaite() {
        return posteSouhaite;
    }

    public void setPosteSouhaite(String posteSouhaite) {
        this.posteSouhaite = posteSouhaite;
    }

    public String getCvFileName() {
        return cvFileName;
    }

    public void setCvFileName(String cvFileName) {
        this.cvFileName = cvFileName;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Consultant getConsultant() {
        return consultant;
    }

    public void setConsultant(Consultant consultant) {
        this.consultant = consultant;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public Date getDateNaissance() {
        return DateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        DateNaissance = dateNaissance;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }
}