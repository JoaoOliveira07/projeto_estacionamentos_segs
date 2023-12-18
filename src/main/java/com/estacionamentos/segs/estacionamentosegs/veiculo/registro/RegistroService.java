package com.estacionamentos.segs.estacionamentosegs.veiculo.registro;

import com.estacionamentos.segs.estacionamentosegs.veiculo.Veiculo;
import com.estacionamentos.segs.estacionamentosegs.veiculo.VeiculoDTO;
import com.estacionamentos.segs.estacionamentosegs.veiculo.VeiculoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;

@Service
public class RegistroService {


    @Autowired
    private RegistroRepository registroRepository;

    @Autowired
    private VeiculoService veiculoService;

    public List<Registro> findAll() {
        return registroRepository.findAll();
    }

    public List<RegistroVeiculoDTO> findAllByFilter(String filter, String orderBy){
        List<RegistroVeiculoProjection> projections = registroRepository.findAllByFilter(filter != null ? filter : "",orderBy);
        return projections.stream().map(RegistroVeiculoDTO::builderFromProjection).toList();
    }
//        mesma coisa do stream.map()
//        List<RegistroVeiculoDTO> dtos = new ArrayList<>();
//        for (RegistroVeiculoProjection projection: projections) {
//            dtos.add(RegistroVeiculoDTO.builderFromProjection(projection));
//        }
//        return dtos;

    @Transactional
    public void deleteByVeiculo(Veiculo veiculo) {
        registroRepository.deleteByVeiculos(veiculo);
    }




    @Transactional
    public void cadastrarEntrada(RegistroDTO registroDTO, VeiculoDTO veiculoDTO, String dataLocal) {
        Veiculo veiculo = veiculoService.getOrCreateVeiculo(veiculoDTO);

        // Verifica se há registros ativos (saída == null) para o veículo
        Registro registroAtivo = registroRepository.encontrarRegistrosAtivosPorPlaca(veiculo.getPlaca());

        if (registroAtivo == null) {
            // Se não houver registros ativos, cria um novo registro de entrada
            Registro registro = new Registro();
            registro.setVeiculos(veiculo);
            LocalDateTime dataEntrada = LocalDateTime.parse(dataLocal, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            registro.setEntrada(dataEntrada);
            registroRepository.save(registro);
        }
    }

    @Transactional
    public String cadastrarSaida(String placa, String saida) {
        Registro registro = registroRepository.encontrarRegistrosAtivosPorPlaca(placa);

        if (registro != null && registro.getSaida() == null) {
            LocalDateTime saidaFront = LocalDateTime.parse(saida, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
            if (registro.getEntrada().isBefore(saidaFront)) {
                registro.setSaida(saidaFront);
                registro.setValor(calcularValor(registro.getEntrada(), saidaFront));
                registroRepository.save(registro);
                return "Data Cadastrada com sucesso.";
            } else {
                throw new DataInvalidaException("Data de Saída é menor que a de entrada.");
            }
        }
        return "Erro ao cadastrar a saída.";
    }

    private double calcularValor(LocalDateTime entrada, LocalDateTime saida) {
        Duration duracao = Duration.between(entrada, saida);
        long horas = duracao.toHours();
        double valor;
        if (horas <= 1) {
            valor = 2.0;
        } else if (horas <= 2) {
            valor = 3.0;
        } else if (horas <= 3) {
            valor = 5.0;
        } else {
            valor = (5.0 + (horas - 3) * 2.0);
        }
        return valor;
    }



}