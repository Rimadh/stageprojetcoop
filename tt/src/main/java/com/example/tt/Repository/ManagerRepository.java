package com.example.tt.Repository;


import com.example.tt.Entity.Manager;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ManagerRepository  extends MongoRepository<Manager, String> {



    boolean existsByEmail(String email);

    Optional<Manager> findByEmail(String email);

    List<Manager> findByDepartement(String departement);
}
