package com.estacionamentos.segs.estacionamentosegs.veiculo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@Data
public class VeiculoApiExternaDTO {

    private String placa;
    private String modelo;
}
