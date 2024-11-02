package com.example.inicial1.entities;

import jakarta.persistence.Entity;
import lombok.*;

import java.io.Serializable;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder

public class Dna extends Base implements Serializable {

    private String dna;
    private boolean isMutant;

}

