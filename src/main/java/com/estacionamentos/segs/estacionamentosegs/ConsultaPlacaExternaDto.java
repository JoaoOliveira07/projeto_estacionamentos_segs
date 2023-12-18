package com.estacionamentos.segs.estacionamentosegs;

import com.estacionamentos.segs.estacionamentosegs.veiculo.VeiculoApiExternaDTO;
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
public class ConsultaPlacaExternaDto {
    VeiculoApiExternaDTO data;
}
