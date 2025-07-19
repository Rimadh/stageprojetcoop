package com.example.tt.Repository;

import com.example.tt.Entity.Role;
import com.example.tt.Entity.User;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, Long> {
    Optional<User> findByEmail(String Email);
    User findByRole (Role role);


}
