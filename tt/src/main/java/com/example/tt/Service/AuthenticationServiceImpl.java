package com.example.tt.Service;

import com.example.tt.Entity.Role;
import com.example.tt.Entity.User;
import com.example.tt.Repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;


import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private  UserRepository userRepository;






    @Override
    public Map<String, Object> getAzureUserProfile(OAuth2AuthenticationToken token) {
        return token.getPrincipal().getAttributes(); // {sub, name, email, ...}
    }

    @Override
    public boolean hasAzureRole(OAuth2AuthenticationToken token, String role) {
        return token.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_" + role));
    }

    // Méthode pour créer un utilisateur depuis Azure AD (ex: après première connexion SSO)
    public User createUserFromAzure(OAuth2AuthenticationToken token) {
        Map<String, Object> attributes = getAzureUserProfile(token);
        User user = new User();
        user.setEmail((String) attributes.get("email"));
        user.setNom((String) attributes.get("family_name"));
        user.setPrenom((String) attributes.get("given_name"));
        user.setRole(Role.valueOf("USER")); // Par défaut
        return userRepository.save(user);
    }

}
