package com.estacionamentos.segs.estacionamentosegs.service;

import com.estacionamentos.segs.estacionamentosegs.entity.Registro;
import com.estacionamentos.segs.estacionamentosegs.entity.Veiculo;
import com.estacionamentos.segs.estacionamentosegs.repository.RegistroRepository;
import com.estacionamentos.segs.estacionamentosegs.repository.VeiculoRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EstacionamentoService {

    @Autowired
    private RegistroRepository registroRepository;

    @Autowired
    private VeiculoRepository veiculoRepository;

//    public void calcularValor() {
//        if (entrada != null && saida != null) {
//            Duration duracao = Duration.between(entrada, saida);
//            long horas = duracao.toHours();
//
//            if (horas <= 1) {
//                valor = 2.0;
//            } else if (horas <= 2) {
//                valor = 3.0;
//            } else if (horas <= 3) {
//                valor = 5.0;
//            } else {
//                valor = 5.0 + (horas - 3) * 2.0;
//            }
//        }
//    }
}