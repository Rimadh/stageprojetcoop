package com.example.tt.Repository;

import com.example.tt.Entity.Consultant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConsultantRepository extends MongoRepository<Consultant,String> {

    Optional<Consultant> findByEmail(String email);
}
