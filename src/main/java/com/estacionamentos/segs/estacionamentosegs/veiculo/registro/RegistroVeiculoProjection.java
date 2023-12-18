package com.estacionamentos.segs.estacionamentosegs.veiculo.registro;


import com.estacionamentos.segs.estacionamentosegs.veiculo.Veiculo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroVeiculoProjection {

    Registro registro;
    Veiculo veiculo;
    String status;


}