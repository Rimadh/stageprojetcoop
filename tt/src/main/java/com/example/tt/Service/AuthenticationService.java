package com.example.tt.Service;


import com.example.tt.Entity.User;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import java.util.Map;

public interface AuthenticationService {

    public User createUserFromAzure(OAuth2AuthenticationToken token);
    public boolean hasAzureRole(OAuth2AuthenticationToken token, String role);

    Map<String, Object> getAzureUserProfile(OAuth2AuthenticationToken token);
}
