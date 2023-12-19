package com.estacionamentos.segs.estacionamentosegs.veiculo.registro;

import com.estacionamentos.segs.estacionamentosegs.veiculo.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RegistroRepositoryJpa extends JpaRepository<Registro, Integer> {

    @Query("SELECT r FROM Registro r INNER JOIN Veiculo v on v.id = r.veiculos.id WHERE v.placa = :placa AND r.saida is null AND v.deleteTimeStamp is null")
    Registro encontrarRegistrosAtivosPorPlaca(@Param("placa") String placa);

}
