package com.estacionamentos.segs.estacionamentosegs.repository;

import com.estacionamentos.segs.estacionamentosegs.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VeiculoRepository extends JpaRepository<Veiculo, Integer> {
    Veiculo findByPlaca(String placa);

    List<Veiculo> findAllByOrderByModeloAsc();
}
