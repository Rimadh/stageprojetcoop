package com.example.tt.Entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "users") // MongoDB collection name
public class User {

    @Id
    String idUser; // MongoDB uses String ID by default

    @NotBlank
    String nom;

    @NotBlank
    String prenom;

    @Email
    @NotBlank
    String email;

    @NotBlank
    String password;

    @NotNull
    Role role;

    @NotBlank
    String telephone;

    @NotBlank
    String adresse;

    @DBRef // MongoDB reference to another document
    ForgotPassword forgotPassword;

    public User() {
    }

    public User(String idUser, String nom, String prenom, String email, String password, Role role, String telephone, String adresse, ForgotPassword forgotPassword) {
        this.idUser = idUser;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.role = role;
        this.telephone = telephone;
        this.adresse = adresse;
        this.forgotPassword = forgotPassword;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public ForgotPassword getForgotPassword() {
        return forgotPassword;
    }

    public void setForgotPassword(ForgotPassword forgotPassword) {
        this.forgotPassword = forgotPassword;
    }
}