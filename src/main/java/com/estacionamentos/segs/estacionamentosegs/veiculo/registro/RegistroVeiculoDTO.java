package com.estacionamentos.segs.estacionamentosegs.veiculo.registro;

import com.estacionamentos.segs.estacionamentosegs.veiculo.Veiculo;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
public class RegistroVeiculoDTO {

    private int id;
    private String placa;
    private String modelo;
    private String tipo;
    private Veiculo veiculos;
    private LocalDateTime entrada;
    private LocalDateTime saida;
    private double valor;
    private String status;


    public static RegistroVeiculoDTO builderFromProjection(RegistroVeiculoProjection projection){
        return RegistroVeiculoDTO.builder()
                .id(projection.getRegistro().getId())
                .placa(projection.getVeiculo().getPlaca())
                .modelo(projection.getVeiculo().getModelo())
                .tipo(projection.getVeiculo().getTipo())
                .entrada(projection.getRegistro().getEntrada())
                .saida(projection.getRegistro().getSaida())
                .valor(projection.getRegistro().getValor())
                .status(projection.getStatus())
                .build();
    }


}

