package com.example.inicial1.dtos;
import com.example.inicial1.validators.ValidDna;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class DnaRequest {
    @ValidDna
    private String[] dna;
}
