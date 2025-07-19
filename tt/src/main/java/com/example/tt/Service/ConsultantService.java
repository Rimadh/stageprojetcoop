package com.example.tt.Service;

import com.example.tt.Entity.Consultant;
import com.example.tt.Repository.ConsultantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsultantService {

    @Autowired
    private ConsultantRepository consultantRepository;

    public Consultant createConsultant(Consultant consultant) {
        return consultantRepository.save(consultant);
    }

    public List<Consultant> getAllConsultants() {
        return consultantRepository.findAll();
    }

    public Optional<Consultant> getConsultantById(String idConsultant) {
        return consultantRepository.findById(idConsultant);
    }

    public Optional<Consultant> findByEmail(String email) {
        return consultantRepository.findByEmail(email);
    }

    public Consultant updateConsultant(String idConsultant, Consultant consultant) {
        return consultantRepository.findById(idConsultant)
                .map(existing -> {
                    consultant.setId(idConsultant);
                    return consultantRepository.save(consultant);
                })
                .orElse(null);
    }

    public void deleteConsultant(String idConsultant) {
        consultantRepository.deleteById(idConsultant);
    }

    public Consultant updateManagerEmail(String consultantId, String managerEmail) {
        return getConsultantById(consultantId)
                .map(consultant -> {
                    consultant.setManagerEmail(managerEmail);
                    return consultantRepository.save(consultant);
                }).orElse(null);
    }

    public Consultant save(Consultant consultant) {
        return consultantRepository.save(consultant);
    }
}
