package com.estacionamentos.segs.estacionamentosegs.service;

import com.estacionamentos.segs.estacionamentosegs.entity.Veiculo;
import com.estacionamentos.segs.estacionamentosegs.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class VeiculoService {

    private VeiculoRepository veiculoRepository;
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

    public void cadastrarVeiculo(VeiculoDTO veiculoDTO) {
        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca(veiculoDTO.getPlaca());
        veiculo.setModelo(veiculoDTO.getModelo());
        veiculo.setAno(veiculoDTO.getAno());
        veiculo.setTipo(veiculoDTO.getTipo());

        // Salvar o veículo no banco de dados
        veiculoRepository.save(veiculo);
    }

    public void save(Veiculo theVeiculo) {
        veiculoRepository.save(theVeiculo);
    }
}
