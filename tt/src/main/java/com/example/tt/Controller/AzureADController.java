//package com.example.tt.Controller;
//
//import com.example.tt.Service.AzureADService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/azure-ad")
//public class AzureADController {
//
//    @Autowired
//    private AzureADService azureADService;
//
//    @GetMapping("/manager-email/{userId}")
//    public String getManagerEmail(@PathVariable String userId) {
//        return azureADService.getManagerEmail(userId);
//    }
//}