package com.example.inicial1.services;

import com.example.inicial1.entities.Dna;
import com.example.inicial1.repositories.DnaRepository;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Builder
@Service
public class DnaService {
    private static final int SEQUENCE_LENGTH = 4;

    @Autowired
    private DnaRepository dnaRepository;

    public boolean analyzeDna(String[] dna) {
        // Convertimos el ADN a una cadena con comas
        String dnaSequence = String.join(",", dna);

        // Verificamos si ya existe en la base de datos
        Optional<Dna> existingDna = dnaRepository.findByDna(dnaSequence);
        if (existingDna.isPresent()) {
            // Si ya fue analizado, retornar el resultado previo
            return existingDna.get().isMutant();
        }

        boolean isMutant = isMutant(dna);         // Analizamos si es mutante

        Dna dnaEntity = Dna.builder()        // Guardamos el resultado en la base de datos
                .dna(dnaSequence)
                .isMutant(isMutant)
                .build();
        dnaRepository.save(dnaEntity);

        return isMutant;
    }

    public static boolean isMutant(String[] dna) {    // Metodo que verifica si el ADN es mutante
        int n = dna.length;
        int sequenceCount = 0;

        sequenceCount += checkRows(dna, n);
        if (sequenceCount > 1) return true;

        sequenceCount += checkColumns(dna, n);
        if (sequenceCount > 1) return true;

        sequenceCount += checkDiagonals(dna, n);
        return sequenceCount > 1;

    }

    // Verificación por filas
    public static int checkRows(String[] dna, int n) {
        int sequenceCount = 0; // Contador de secuencias encontradas
        for (int i = 0; i < n; i++) { // Recorrer cada fila
            int count = 1; // Inicializamos el contador para cada fila
            for (int j = 1; j < n; j++) { // Empezamos a comparar desde el segundo carácter
                if (dna[i].charAt(j) == dna[i].charAt(j - 1)) { // Comparar caracteres adyacentes
                    count++; // Incrementar contador si son iguales
                    if (count == SEQUENCE_LENGTH) { // Si se encuentra una secuencia de 4
                        sequenceCount++; // Incrementar el contador de secuencias
                        if (sequenceCount > 1) return sequenceCount; // Si encontramos más de 1 secuencia, salir
                        count = 1; // Reiniciar el contador para seguir buscando más secuencias en la misma fila
                    }
                } else {
                    count = 1; // Reiniciar el contador si no son iguales
                }
            }
        }
        return sequenceCount;
    }


    // Verificación por columnas
    public static int checkColumns(String[] dna, int n) {
        int sequenceCount = 0;
        for (int j = 0; j < n; j++) {
            int count = 1;
            for (int i = 1; i < n; i++) {
                if (dna[i].charAt(j) == dna[i - 1].charAt(j)) {
                    count++;
                    if (count == SEQUENCE_LENGTH) {
                        sequenceCount++;
                        if (sequenceCount > 1) return sequenceCount;
                    }
                } else {
                    count = 1;
                }
            }
        }
        return sequenceCount;
    }

    // Verificación por diagonales
    public static int checkDiagonals(String[] dna, int n) {
        int sequenceCount = 0;

        // Diagonal de izquierda a derecha
        for (int i = 0; i <= n - SEQUENCE_LENGTH; i++) {
            for (int j = 0; j <= n - SEQUENCE_LENGTH; j++) {
                if (checkDiagonal(dna, i, j, 1, 1, n)) {
                    sequenceCount++;
                    if (sequenceCount > 1) return sequenceCount;
                }
            }
        }

        // Diagonal de derecha a izquierda
        for (int i = 0; i <= n - SEQUENCE_LENGTH; i++) {
            for (int j = SEQUENCE_LENGTH - 1; j < n; j++) {
                if (checkDiagonal(dna, i, j, 1, -1, n)) {
                    sequenceCount++;
                    if (sequenceCount > 1) return sequenceCount;
                }
            }
        }

        return sequenceCount;
    }

    // Verificación de una diagonal específica
    public static boolean checkDiagonal(String[] dna, int x, int y, int dx, int dy, int n) {
        char first = dna[x].charAt(y);
        for (int i = 1; i < SEQUENCE_LENGTH; i++) {
            int newX = x + i * dx;
            int newY = y + i * dy;
            if (newX >= n || newY >= n || newY < 0 || dna[newX].charAt(newY) != first) {
                return false;
            }
        }
        return true;
    }
}


