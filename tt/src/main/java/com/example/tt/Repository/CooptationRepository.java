package com.example.tt.Repository;

import com.example.tt.Entity.Cooptation;
import com.example.tt.Entity.CooptationStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
@Repository
public interface CooptationRepository extends MongoRepository<Cooptation, String> {

    List<Cooptation> findByEtat(CooptationStatus etat);
    @Query("{ 'consultant.idConsultant': ?0 }")
    List<Cooptation> findByConsultantId(String idConsultant);

    List<Cooptation> findByStatut(String statut);
}
