package com.sofka.cuestionario.repository;

import com.sofka.cuestionario.model.Jugador;
import com.sofka.cuestionario.model.Opciones;
import com.sofka.cuestionario.model.Pregunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OpcionesRepository extends JpaRepository<Opciones, Long>
{
    List<Opciones> findByIdQuestion(int idPregunta);
}
