package com.sofka.cuestionario.repository;

import com.sofka.cuestionario.model.Categoria;
import com.sofka.cuestionario.model.Pregunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreguntaRepository extends JpaRepository<Pregunta, Long>
{
    List<Pregunta> findByIdRound(int idRound);
}
