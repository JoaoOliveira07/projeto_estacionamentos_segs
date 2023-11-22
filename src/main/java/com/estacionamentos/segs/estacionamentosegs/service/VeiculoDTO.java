package com.estacionamentos.segs.estacionamentosegs.service;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VeiculoDTO {

    private int id;
    private String placa;
    private String modelo;
    private int ano;
    private String tipo;
}
