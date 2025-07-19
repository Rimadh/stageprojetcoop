package com.example.tt.Service;



import com.example.tt.Entity.Manager;

import java.util.List;
import java.util.Optional;

public interface IManager {

    Manager createManager(Manager manager);
    Manager updateManager(String id, Manager manager);
    List<Manager> getAllManagers();
    Optional<Manager> getManagerById(String idManager);
    Optional<Manager> getManagerByEmail(String email);
    List<Manager> getManagersByDepartement(String departement);
    void deleteManager(String idManager);
    boolean existsByEmail(String email);
}
