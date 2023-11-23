package com.estacionamentos.segs.estacionamentosegs.service;

import com.estacionamentos.segs.estacionamentosegs.entity.Veiculo;
import com.estacionamentos.segs.estacionamentosegs.repository.RegistroRepository;
import com.estacionamentos.segs.estacionamentosegs.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class EstacionamentoService {

//    @Autowired
//    private RegistroRepository registroRepository;

    @Autowired
    private VeiculoRepository veiculoRepository;

//    private void calcularValor(RegistroDTO registro) {
//        LocalDateTime entrada = registro.getEntrada();
//        LocalDateTime saida = registro.getSaida();
//
//        if (entrada != null && saida != null) {
//            Duration duracao = Duration.between(entrada, saida);
//            long horas = duracao.toHours();
//
//            if (horas <= 1) {
//                registro.setValor(2.0);
//            } else if (horas <= 2) {
//                registro.setValor(3.0);
//            } else if (horas <= 3) {
//                registro.setValor(5.0);
//            } else {
//                registro.setValor(5.0 + (horas - 3) * 2.0);
//            }
//        }
//    }

    public void cadastrarVeiculo(VeiculoDTO veiculoDTO) {
        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca(veiculoDTO.getPlaca());
        veiculo.setModelo(veiculoDTO.getModelo());
        veiculo.setAno(veiculoDTO.getAno());
        veiculo.setTipo(veiculoDTO.getTipo());

        // Salvar o veículo no banco de dados
        veiculoRepository.save(veiculo);
    }
}