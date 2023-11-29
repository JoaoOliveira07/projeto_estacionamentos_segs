package com.estacionamentos.segs.estacionamentosegs.repository;

import com.estacionamentos.segs.estacionamentosegs.entity.Registro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RegistroRepository extends JpaRepository<Registro, Integer> {
    @Query("SELECT r FROM Registro r INNER JOIN Veiculo v on v.id = r.veiculos.id WHERE v.placa = :placa AND r.saida is null")
    Registro encontrarRegistrosAtivosPorPlaca(@Param("placa") String placa);
}
