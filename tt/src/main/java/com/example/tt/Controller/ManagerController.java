package com.example.tt.Controller;

import com.example.tt.Entity.Manager;
import com.example.tt.Service.IManager;
import com.example.tt.Service.ManagerService;
import com.example.tt.exception.ResourceAlreadyExistsException;
import com.example.tt.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/managers")
@CrossOrigin(origins = "http://localhost:4200")
public class ManagerController {
@Autowired
    private ManagerService managerService;

    @PostMapping
    public Manager createManager(@RequestBody  Manager manager)  {
        return managerService.createManager(manager);
    }

    @GetMapping
    public ResponseEntity<List<Manager>> getAllManagers(
            @RequestParam(required = false) String departement) {

        List<Manager> managers = (departement != null && !departement.isEmpty())
                ? managerService.getManagersByDepartement(departement)
                : managerService.getAllManagers();

        return ResponseEntity.ok(managers);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getManagerById(@PathVariable String id) {
        try {
            return managerService.getManagerById(id)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/by-email/{email}")
    public ResponseEntity<?> getManagerByEmail(@PathVariable String email) {
        try {
            if (email == null || email.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Email parameter is required");
            }

            return managerService.getManagerByEmail(email)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateManager(
            @PathVariable String id,
            @Valid @RequestBody Manager manager) {

        try {
            // Additional validation
            if (manager.getEmail() == null || manager.getEmail().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Email is required");
            }

            Manager updatedManager = managerService.updateManager(id, manager);
            return ResponseEntity.ok(updatedManager);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (ResourceAlreadyExistsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManager(@PathVariable String id) {
        try {
            managerService.deleteManager(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/exists/{email}")
    public ResponseEntity<?> checkEmailExists(@PathVariable String email) {
        try {
            if (email == null || email.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Email parameter is required");
            }
            return ResponseEntity.ok(managerService.existsByEmail(email));
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }
}