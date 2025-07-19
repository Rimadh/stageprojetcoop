package com.example.tt.Service;

import com.example.tt.Entity.Manager;

import com.example.tt.Repository.ManagerRepository;
import com.example.tt.exception.ResourceAlreadyExistsException;
import com.example.tt.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ManagerService implements IManager {
@Autowired
    private  ManagerRepository managerRepository;



    @Override
    public Manager createManager(Manager manager) {

        return managerRepository.save(manager);
    }

    @Override
    public Manager updateManager(String id, Manager manager) {
        return managerRepository.findById(id)
                .map(existingManager -> {
                    if (!existingManager.getEmail().equals(manager.getEmail())){
                        if (managerRepository.existsByEmail(manager.getEmail())) {
                            throw new ResourceAlreadyExistsException("Email " + manager.getEmail() + " already in use");
                        }
                    }
                    existingManager.setNom(manager.getNom());
                    existingManager.setPrenom(manager.getPrenom());
                    existingManager.setEmail(manager.getEmail());
                    existingManager.setDepartement(manager.getDepartement());
                    existingManager.setTeamsChannelId(manager.getTeamsChannelId());
                    existingManager.setProfilePicturePath(manager.getProfilePicturePath());
                    existingManager.setConsultants(manager.getConsultants());
                    return managerRepository.save(existingManager);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Manager not found with id " + id));
    }

    @Override
    public List<Manager> getAllManagers() {
        return managerRepository.findAll();
    }

    @Override
    public Optional<Manager> getManagerById(String idManager) {
        return managerRepository.findById(idManager);
    }

    @Override
    public Optional<Manager> getManagerByEmail(String email) {
        return managerRepository.findByEmail(email);
    }

    @Override
    public List<Manager> getManagersByDepartement(String departement) {
        return managerRepository.findByDepartement(departement);
    }

    @Override
    public void deleteManager(String idManager) {
        if (!managerRepository.existsById(idManager)) {
            throw new ResourceNotFoundException("Manager not found with id " + idManager);
        }
        managerRepository.deleteById(idManager);
    }

    @Override
    public boolean existsByEmail(String email) {
        return managerRepository.existsByEmail(email);
    }
}