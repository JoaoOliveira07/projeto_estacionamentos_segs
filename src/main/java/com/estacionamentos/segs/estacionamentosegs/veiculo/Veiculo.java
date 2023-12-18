package com.estacionamentos.segs.estacionamentosegs.veiculo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "veiculos")
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "placa_veiculo")
    private String placa;
    @Column(name = "modelo_veiculo")
    private String modelo;
    @Column(name = "tipo_veiculo")
    private String tipo;

    private LocalDateTime deleteTimeStamp;

    public Veiculo(String placa, String modelo, String tipo) {
        this.placa = placa;
        this.modelo = modelo;
        this.tipo = tipo;
    }
}