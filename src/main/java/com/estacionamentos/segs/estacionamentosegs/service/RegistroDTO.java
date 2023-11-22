package com.estacionamentos.segs.estacionamentosegs.service;

import com.estacionamentos.segs.estacionamentosegs.entity.Veiculo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistroDTO {

    private int id;
    private Veiculo veiculos;
    private LocalDateTime entrada;
    private LocalDateTime saida;
    private double valor;

}
