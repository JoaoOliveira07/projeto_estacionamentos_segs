package com.estacionamentos.segs.estacionamentosegs.service;

import com.estacionamentos.segs.estacionamentosegs.entity.Registro;
import com.estacionamentos.segs.estacionamentosegs.entity.Veiculo;
import com.estacionamentos.segs.estacionamentosegs.repository.RegistroRepository;
import com.estacionamentos.segs.estacionamentosegs.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class RegistroService {

    public RegistroRepository registroRepository;

    public VeiculoRepository veiculoRepository;

    public void cadastrarEntrada(RegistroDTO registroDTO, VeiculoDTO veiculoDTO) {
        Veiculo veiculo = veiculoRepository.findByPlaca(veiculoDTO.getPlaca());

        if (veiculo == null) {
            veiculo = new Veiculo();
            veiculo.setPlaca(veiculoDTO.getPlaca());
            veiculoRepository.save(veiculo);
        }

        Registro registro = new Registro();
        registro.setVeiculos(veiculo);
        registro.setEntrada(registroDTO.getEntrada());

        registroRepository.save(registro);
    }

    public void cadastrarSaida(RegistroDTO registroDTO, VeiculoDTO veiculoDTO) {
        Registro registro = registroRepository.encontrarRegistrosAtivosPorPlaca(veiculoDTO.getPlaca());

        if (registro.getSaida() == null) {
            registro.setSaida(registroDTO.getSaida());
            registroRepository.save(registro);
        }

    }

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

}
