package com.estacionamentos.segs.estacionamentosegs.veiculo;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VeiculoDTO {

    private int id;
    private String placa;
    private String modelo;
    private String tipo;
    private LocalDateTime deleteTimeStamp;

    public VeiculoDTO(String placa, String modelo, String tipo) {
        this.placa = placa;
        this.modelo = modelo;
        this.tipo = tipo;
    }

    public static VeiculoDTO fromVeiculo(Veiculo defaultCar) {
        return VeiculoDTO.builder()
            .id(defaultCar.getId())
            .placa(defaultCar.getPlaca())
            .modelo(defaultCar.getModelo())
            .tipo(defaultCar.getTipo())
            .build();
    }
}
