package org.mehulsuthar.pm.patientservice.mapper;

import org.mehulsuthar.pm.patientservice.dto.PatientRequestDTO;
import org.mehulsuthar.pm.patientservice.dto.PatientResponseDTO;
import org.mehulsuthar.pm.patientservice.model.Patient;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

public class PatientMapper {
    public static PatientResponseDTO toDTO(Patient patient) {
        PatientResponseDTO patientDTO = new PatientResponseDTO();

        patientDTO.setId(patient.getId().toString());
        patientDTO.setName(patient.getName());
        patientDTO.setAddress(patient.getAddress());
        patientDTO.setEmail(patient.getEmail());
        patientDTO.setDateOfBirth(patient.getDateOfBirth().toString());

        return patientDTO;
    }

    public static Patient toEntity(PatientRequestDTO dto) {
        Patient patient = new Patient();

        patient.setName(dto.getName());
        patient.setAddress(dto.getAddress());
        patient.setEmail(dto.getEmail());
        patient.setDateOfBirth(LocalDate.parse(dto.getDateOfBirth()));
        patient.setRegisterDate(LocalDate.parse(dto.getRegisteredDate()));

        return patient;
    }
}
