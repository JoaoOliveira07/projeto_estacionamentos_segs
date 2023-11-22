package com.estacionamentos.segs.estacionamentosegs.repository;

import com.estacionamentos.segs.estacionamentosegs.entity.Registro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistroRepository extends JpaRepository<Registro, Integer> {
}
