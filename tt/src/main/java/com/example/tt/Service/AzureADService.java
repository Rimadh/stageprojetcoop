//package com.example.tt.Service;
//
//import com.azure.identity.ClientSecretCredential;
//import com.azure.identity.ClientSecretCredentialBuilder;
//import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
//import com.microsoft.graph.models.DirectoryObject;
//import com.microsoft.graph.models.User;
//import com.microsoft.graph.requests.GraphServiceClient;
//import lombok.extern.slf4j.Slf4j;
//import okhttp3.Request;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//@Slf4j
//@Service
//public class AzureADService {
//
//    @Value("${azure.activedirectory.client-id}")
//    private String clientId;
//
//    @Value("${azure.activedirectory.client-secret}")
//    private String clientSecret;
//
//    @Value("${azure.activedirectory.tenant-id}")
//    private String tenantId;
//
//    public GraphServiceClient<Request> getGraphClient() {
//        ClientSecretCredential credential = new ClientSecretCredentialBuilder()
//                .clientId(clientId)
//                .clientSecret(clientSecret)
//                .tenantId(tenantId)
//                .build();
//
//        return GraphServiceClient.builder()
//                .authenticationProvider(new TokenCredentialAuthProvider(credential))
//                .buildClient();
//    }
//
//    // Dans AzureADService.java
//    public String getManagerEmail(String userId) {
//        try {
//            User manager = (User) getGraphClient().users(userId).manager()
//                    .buildRequest()
//                    .select("mail,userPrincipalName")
//                    .get();
//
//            if (manager == null) {
//                log.warn("Aucun manager trouvé pour l'utilisateur {}", userId);
//                return "default.manager@example.com"; // Email par défaut
//            }
//            return manager.mail != null ? manager.mail : manager.userPrincipalName;
//        } catch (Exception e) {
//            log.error("Erreur lors de la récupération du manager pour l'utilisateur {}", userId, e);
//            return "default.manager@example.com"; // Email par défaut
//        }
//    }
//}