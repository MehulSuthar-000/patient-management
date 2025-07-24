package org.mehulsuthar.pm.patientservice.service;

import org.mehulsuthar.pm.patientservice.dto.PatientRequestDTO;
import org.mehulsuthar.pm.patientservice.dto.PatientResponseDTO;
import org.mehulsuthar.pm.patientservice.exception.EmailAlreadyExistsException;
import org.mehulsuthar.pm.patientservice.exception.PatientNotFoundException;
import org.mehulsuthar.pm.patientservice.mapper.PatientMapper;
import org.mehulsuthar.pm.patientservice.model.Patient;
import org.mehulsuthar.pm.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        // an email address must be unique
        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("A patient with this email already exists "
                    + patientRequestDTO.getEmail());
        }

        Patient newPatient = patientRepository.save(
                PatientMapper.toEntity(patientRequestDTO));


        return PatientMapper.toDTO(newPatient);
    }

    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO) {
        Patient patient = patientRepository.findById(id).orElseThrow(
                ()-> new PatientNotFoundException("Patient not found with ID: "+ id));

        if (patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(), id)) {
            throw new EmailAlreadyExistsException("A patient with this email already exists "
                    + patientRequestDTO.getEmail());
        }

        patient.setEmail(patientRequestDTO.getEmail());
        patient.setName(patientRequestDTO.getName());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));

        Patient updatedPatient = patientRepository.save(patient);
        return PatientMapper.toDTO(updatedPatient);
    }


    public void deletePatient(UUID id) {
        patientRepository.deleteById(id);
    }

}
