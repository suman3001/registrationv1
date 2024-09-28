package com.api.service;

import com.api.entity.Registration;
import com.api.exception.ResourceNotFoundException;
import com.api.payload.RegistrationDto;
import com.api.repository.RegistrationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final ModelMapper modelMapper;

    // Constructor-based injection for RegistrationRepository and ModelMapper
    @Autowired
    public RegistrationService(RegistrationRepository registrationRepository, ModelMapper modelMapper) {
        this.registrationRepository = registrationRepository;
        this.modelMapper = modelMapper;
    }

    // Get all registrations and map them to DTOs
    public List<RegistrationDto> getRegistrations() {
        List<Registration> registrations = registrationRepository.findAll();
        List<RegistrationDto> dtso = registrations.stream()
                .map(this::mapToDto)  // Using method reference for cleaner code
                .collect(Collectors.toList());

        return dtso;
    }

    // Create a new registration
    public RegistrationDto createRegistration(RegistrationDto registrationDto) {
        Registration registration = mapToEntity(registrationDto);
        Registration savedEntity = registrationRepository.save(registration);
        return mapToDto(savedEntity);
    }

    // Delete a registration by ID
    public void deleteRegistration(long id) {
        registrationRepository.deleteById(id);
    }

    // Update Registration
    public RegistrationDto updateRegistration(Long id, RegistrationDto registrationDto) {
        Registration registration = registrationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found"));

            registration.setName(registrationDto.getName());

            registration.setEmail(registrationDto.getEmail());

            registration.setMobile(registrationDto.getMobile());


        Registration updatedRegistration = registrationRepository.save(registration);
        return mapToDto(updatedRegistration);
    }

    // Convert RegistrationDto to Registration entity
    private Registration mapToEntity(RegistrationDto registrationDto) {
        return modelMapper.map(registrationDto, Registration.class);
    }

    // Convert Registration entity to RegistrationDto
    private RegistrationDto mapToDto(Registration registration) {
        return modelMapper.map(registration, RegistrationDto.class);
    }

    public RegistrationDto getRegistrationById(long id) {
        Registration registration = registrationRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Record not found")
        );
        return mapToDto(registration);
    }
}
