package com.example.tt.Repository;

import com.example.tt.Entity.Candidat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidatRepository extends MongoRepository<Candidat,String> {
}
