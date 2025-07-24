package org.mehulsuthar.pm.patientservice.service;

import org.mehulsuthar.pm.patientservice.dto.PatientResponseDTO;
import org.mehulsuthar.pm.patientservice.mapper.PatientMapper;
import org.mehulsuthar.pm.patientservice.model.Patient;
import org.mehulsuthar.pm.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientService {
    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDTO> getPatients() {
        List<Patient> patients = patientRepository.findAll();


        return patients.stream()
                .map(PatientMapper::toDTO)
                .toList();
    }
}
