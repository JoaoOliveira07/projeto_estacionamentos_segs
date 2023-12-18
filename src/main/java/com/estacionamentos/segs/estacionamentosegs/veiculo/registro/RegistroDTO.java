package com.estacionamentos.segs.estacionamentosegs.veiculo.registro;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistroDTO {

    private int id;
    private String placa;
    @DateTimeFormat(pattern = "dd/MM/yyyy, HH:mm:ss")
    private LocalDateTime entrada;
    @DateTimeFormat(pattern = "dd/MM/yyyy, HH:mm:ss")
    private LocalDateTime saida;
    private double valor;

    public RegistroDTO(String placa, LocalDateTime entrada, LocalDateTime saida) {
        this.placa = placa;
        this.entrada = entrada;
        this.saida = saida;
    }

}
