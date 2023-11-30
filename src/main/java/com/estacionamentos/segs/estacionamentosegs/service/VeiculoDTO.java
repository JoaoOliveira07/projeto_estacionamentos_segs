package com.estacionamentos.segs.estacionamentosegs.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VeiculoDTO {

    private int id;
    private String placa;
    private String modelo;
    private int ano;
    private String tipo;


    public VeiculoDTO(String placa, String modelo, int ano, String tipo) {
        this.placa = placa;
        this.modelo = modelo;
        this.ano = ano;
        this.tipo = tipo;
    }
}
