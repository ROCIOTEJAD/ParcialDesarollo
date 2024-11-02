package com.example.inicial1.controllers;

import com.example.inicial1.dtos.*;
import com.example.inicial1.services.DnaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mutant")
public class DnaController {
    private final DnaService dnaService;

    // Constructor para inyectar el servicio
    public DnaController(DnaService dnaService) {

        this.dnaService = dnaService;
    }

    @PostMapping
    public ResponseEntity<DnaResponse> checkMutant(@Valid @RequestBody DnaRequest dnaRequest) {
        boolean isMutant = dnaService.analyzeDna(dnaRequest.getDna());
        DnaResponse dnaResponse = new DnaResponse(isMutant);

        if (isMutant) {
            // Devuelve la respuesta con el objeto DnaResponse
            return ResponseEntity.status(HttpStatus.OK).body(dnaResponse);
        } else {
            // Devuelve la respuesta con el objeto DnaResponse
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(dnaResponse);
        }

    }

    @GetMapping
    public ResponseEntity<String> testEndpoint() {
        return ResponseEntity.ok("El endpoint /mutant está funcionando");
    }
}


