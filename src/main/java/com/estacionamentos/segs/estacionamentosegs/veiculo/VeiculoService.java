package com.estacionamentos.segs.estacionamentosegs.veiculo;

import com.estacionamentos.segs.estacionamentosegs.ConsultaPlacaExternaDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    public List<Veiculo> findAll() {
        return veiculoRepository.findAll();
    }


    public Veiculo findByPlaca(String placa) {
        return veiculoRepository.findByPlaca(placa);
    }

    public Veiculo findById(int theId) {
        Optional<Veiculo> result = veiculoRepository.findById(theId);

        if (result.isEmpty()) {
            throw new RuntimeException("Não foi encontrado ID- " + theId);
        }
        return result.get();
    }
    public Veiculo getOrCreateVeiculo(VeiculoDTO veiculoDTO) {
        Veiculo veiculo = veiculoRepository.findByPlaca(veiculoDTO.getPlaca());
        if (veiculo == null) {
            veiculo = new Veiculo();
            veiculo.setPlaca(veiculoDTO.getPlaca().trim());
            veiculo.setModelo(veiculoDTO.getModelo());
            veiculo.setTipo(veiculoDTO.getTipo().trim());
            if (veiculo.getPlaca() != null && veiculo.getModelo() != null && veiculo.getTipo() != null) {
                veiculo = veiculoRepository.save(veiculo);
            }
        }
        return veiculo;
    }

    @Transactional
    public void deleteByPlaca(String placa) {
        Veiculo veiculo = veiculoRepository.findByPlaca(placa);

        if (veiculo != null) {
            veiculo.setDeleteTimeStamp(LocalDateTime.now());
        }
    }


    public void save(Veiculo theVeiculo) {
        veiculoRepository.save(theVeiculo);
    }



    public Veiculo getVeiculoByPlaca(String placa) {
        if (!placa.matches("^([A-Z]{3}[0-9]{1}[A-Z]{1}[0-9]{2})|([A-Z]{3}[0-9]{4})$")) {
            // Placa inválida, você pode lançar uma exceção, retornar null ou lidar de outra forma
            throw new IllegalArgumentException("Placa inválida");
        }
        Veiculo veiculo = veiculoRepository.findByPlaca(placa);

        if(veiculo != null){
            return veiculo;
        }

        return queryPlaca(placa);
    }

    private Veiculo queryPlaca(String placa){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ConsultaPlacaExternaDto> resp =
                restTemplate.getForEntity(String.format("https://api.brabocar.com.br/api/Vehicle/%s", placa), ConsultaPlacaExternaDto.class);

        if(resp.getStatusCode().equals(HttpStatusCode.valueOf(200))){
            Veiculo veiculo = new Veiculo();
            veiculo.setPlaca(placa);
            veiculo.setModelo(resp.getBody().getData().getModelo());
            return veiculo;
        }

        return null;
    }

}