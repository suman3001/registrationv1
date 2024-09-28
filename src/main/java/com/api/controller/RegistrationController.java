package com.api.controller;

import com.api.entity.Registration;
import com.api.payload.RegistrationDto;
import com.api.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping
    public ResponseEntity<List<RegistrationDto>> getAllRegistrations() {
        List<RegistrationDto> dtos = registrationService.getRegistrations();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createRegistration(@Valid @RequestBody RegistrationDto registrationDto, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        RegistrationDto regDto = registrationService.createRegistration(registrationDto);
        return new ResponseEntity<>(regDto, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteRegistration(@RequestParam long id) {

        registrationService.deleteRegistration(id);

        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateRegistration(@PathVariable long id,
                                                @Valid @RequestBody RegistrationDto registrationDto,
                                                BindingResult result) {
        if (result.hasErrors()) {

            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }

        RegistrationDto updatedRegistration = registrationService.updateRegistration(id, registrationDto);
        return new ResponseEntity<>(updatedRegistration, HttpStatus.OK);
    }



    @GetMapping("/{id}")
    public ResponseEntity<RegistrationDto> getRegistrationById(@PathVariable long id) {
        RegistrationDto dto = registrationService.getRegistrationById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);


    }


}
