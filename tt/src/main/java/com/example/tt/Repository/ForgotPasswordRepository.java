package com.example.tt.Repository;

import com.example.tt.Entity.ForgotPassword;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ForgotPasswordRepository extends MongoRepository<ForgotPassword, Integer> {


}
