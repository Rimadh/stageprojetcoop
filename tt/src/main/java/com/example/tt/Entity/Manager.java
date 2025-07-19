package com.example.tt.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "managers")
public class Manager {
    @Id
    private String idManager;
    @NotBlank
    private String nom;
    @NotBlank
    private String prenom;
    @NotBlank
    private String email;
    @NotBlank
    private String departement;
    @NotBlank
    private String teamsChannelId;
    @NotBlank
    private String profilePicturePath;
    @DBRef(lazy = true)
    @JsonManagedReference
    private List<Consultant> consultants;
    // Constructeurs
    public Manager() {}

    public Manager(String idManager, String nom, String prenom, String email, String departement, String teamsChannelId, String profilePicturePath, List<Consultant> consultants) {
        this.idManager = idManager;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.departement = departement;
        this.teamsChannelId = teamsChannelId;
        this.profilePicturePath = profilePicturePath;
        this.consultants = consultants;
    }

    public String getIdManager() {
        return idManager;
    }

    public void setIdManager(String idManager) {
        this.idManager = idManager;
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

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public String getTeamsChannelId() {
        return teamsChannelId;
    }

    public void setTeamsChannelId(String teamsChannelId) {
        this.teamsChannelId = teamsChannelId;
    }

    public String getProfilePicturePath() {
        return profilePicturePath;
    }

    public void setProfilePicturePath(String profilePicturePath) {
        this.profilePicturePath = profilePicturePath;
    }

    public List<Consultant> getConsultants() {
        return consultants;
    }

    public void setConsultants(List<Consultant> consultants) {
        this.consultants = consultants;
    }
}