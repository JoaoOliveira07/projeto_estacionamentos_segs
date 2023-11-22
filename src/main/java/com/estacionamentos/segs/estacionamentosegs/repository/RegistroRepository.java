package com.estacionamentos.segs.estacionamentosegs.repository;

import com.estacionamentos.segs.estacionamentosegs.entity.Registro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistroRepository extends JpaRepository<Registro, Integer> {
}
