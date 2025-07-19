package com.example.tt.Service;

import com.example.tt.Entity.Candidat;

import java.util.List;

public interface ICandidat {
    public Candidat createCandidat(Candidat candidat, String consultantId, String managerId);
    List<Candidat> getAllCandidats();
    Candidat getCandidatById(String idCandidat);
 //   Candidat updateCandidat(String idCandidat, Candidat candidat);
    void deleteCandidat(String idCandidat);
}
