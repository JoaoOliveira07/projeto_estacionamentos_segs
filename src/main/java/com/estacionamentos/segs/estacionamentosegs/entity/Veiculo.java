package com.estacionamentos.segs.estacionamentosegs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

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
    @Column(name = "ano_veiculo")
    private int ano;
    @Column(name = "tipo_veiculo")
    private String tipo;

}