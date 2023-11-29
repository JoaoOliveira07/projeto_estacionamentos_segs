package com.estacionamentos.segs.estacionamentosegs.service;

import com.estacionamentos.segs.estacionamentosegs.entity.Registro;
import com.estacionamentos.segs.estacionamentosegs.entity.Veiculo;
import com.estacionamentos.segs.estacionamentosegs.repository.RegistroRepository;
import com.estacionamentos.segs.estacionamentosegs.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EstacionamentoService {

    //    @Autowired
//    private RegistroRepository registroRepository;
    @Autowired
    private RegistroRepository registroRepository;

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


    public List<Veiculo> findAll() {
        return veiculoRepository.findAllByOrderByModeloAsc();
    }

    public Veiculo findById(int theId) {
        Optional<Veiculo> result = veiculoRepository.findById(theId);

        Veiculo theVeiculo = null;

        if (result.isPresent()) {
            theVeiculo = result.get();
        }
        else {
            // we didn't find the employee
            throw new RuntimeException("Não foi encontrado ID- " + theId);
        }

        return theVeiculo;
    }

    public void save(Veiculo theVeiculo) {
        veiculoRepository.save(theVeiculo);
    }

    public void deleteById(int theId) {
        veiculoRepository.deleteById(theId);
    }

    public void cadastrarVeiculo(VeiculoDTO veiculoDTO) {
        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca(veiculoDTO.getPlaca());
        veiculo.setModelo(veiculoDTO.getModelo());
        veiculo.setAno(veiculoDTO.getAno());
        veiculo.setTipo(veiculoDTO.getTipo());

        // Salvar o veículo no banco de dados
        veiculoRepository.save(veiculo);
    }


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

}