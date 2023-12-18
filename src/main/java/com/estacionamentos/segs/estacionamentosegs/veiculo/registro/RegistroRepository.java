package com.estacionamentos.segs.estacionamentosegs.veiculo.registro;

import com.estacionamentos.segs.estacionamentosegs.veiculo.Veiculo;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RegistroRepository {
    List<Registro> findAll();
    Registro encontrarRegistrosAtivosPorPlaca(@Param("placa") String placa);
    void deleteByVeiculos(@Param("veiculo") Veiculo veiculo);
    List<RegistroVeiculoProjection> findAllByFilter(String filter, String orderBy);

    Registro save(Registro registro);



}

