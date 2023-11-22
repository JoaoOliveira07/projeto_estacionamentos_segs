package com.estacionamentos.segs.estacionamentosegs.service;

import com.estacionamentos.segs.estacionamentosegs.entity.Veiculo;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class RegistroDTO {

    private int id;
    private Veiculo veiculos;
    private LocalDateTime entrada;
    private LocalDateTime saida;
    private double valor;

}
