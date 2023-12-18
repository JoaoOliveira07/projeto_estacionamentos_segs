package com.estacionamentos.segs.estacionamentosegs.veiculo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VeiculoRepository extends JpaRepository<Veiculo, Integer> {

    @Query("SELECT v FROM Veiculo v WHERE LOWER(v.placa) = LOWER(:placa) and v.deleteTimeStamp is null")
    Veiculo findByPlaca(@Param("placa") String placa);

    @Override
    @Query("from Veiculo where deleteTimeStamp is null")
    List<Veiculo> findAll();



}
