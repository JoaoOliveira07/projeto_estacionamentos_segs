package com.estacionamentos.segs.estacionamentosegs.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "registro")
public class Registro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @ManyToOne
    @JoinColumn(name = "veiculo_id")
    private Veiculo veiculos;
    @Column(name = "entrada_veiculos")
    private LocalDateTime entrada;
    @Column(name = "saida_veiculos")
    private LocalDateTime saida;
    @Column(name = "campo_valor")
    private double valor;

    public Registro(){

    }

    public Registro(Veiculo veiculos, LocalDateTime entrada, LocalDateTime saida, double valor) {
        this.veiculos = veiculos;
        this.entrada = entrada;
        this.saida = saida;
    }
}
