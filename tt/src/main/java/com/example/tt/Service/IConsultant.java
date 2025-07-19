package com.example.tt.Service;

import com.example.tt.Entity.Consultant;

import java.util.List;
import java.util.Optional;

public interface IConsultant {
    Consultant createConsultant(Consultant consultant);

    List<Consultant> getAllConsultants();

    Consultant getConsultantById(String idConsultant);

    Consultant updateConsultant(String idConsultant, Consultant consultant);

    void deleteConsultant(String idConsultant);

    public Consultant updateManagerEmail(String consultantId, String managerEmail);

    public Consultant getConsultantByEmail(String email);

    public Optional<Consultant> findByEmail(String email);
}



