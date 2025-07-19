package com.example.tt.Controller;


import com.example.tt.Service.AuthenticationService;
import com.example.tt.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins ="http://localhost:4200")
public class AuthController {
@Autowired
    private  AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    // Inscription classique (email/mot de passe)


    // Récupérer le profil Azure AD (SSO)
    @GetMapping("/azure/profile")
    public Map<String, Object> getAzureProfile(OAuth2AuthenticationToken token) {
        return authenticationService.getAzureUserProfile(token);
    }

    // Créer un utilisateur dans votre DB après connexion Azure AD
    @GetMapping("/azure/sync")
    public User syncAzureUser(OAuth2AuthenticationToken token) {
        return authenticationService.createUserFromAzure(token);
    }

    // Vérifier un rôle Azure AD (ex: ADMIN)
    @GetMapping("/azure/check-role/{role}")
    public boolean checkRole(OAuth2AuthenticationToken token, @PathVariable String role) {
        return authenticationService.hasAzureRole(token, role);
    }

    @GetMapping("/userinfo")
    public Map<String, Object> getUserInfo(@AuthenticationPrincipal Jwt jwt) {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("username", jwt.getClaimAsString("preferred_username"));
        userInfo.put("email", jwt.getClaimAsString("email"));
        userInfo.put("name", jwt.getClaimAsString("name"));
        userInfo.put("roles", jwt.getClaimAsStringList("roles")); // si roles dans le token
        return userInfo;
    }

}