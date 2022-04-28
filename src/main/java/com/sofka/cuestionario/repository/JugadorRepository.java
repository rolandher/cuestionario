package com.sofka.cuestionario.repository;

import com.sofka.cuestionario.model.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface JugadorRepository extends JpaRepository<Jugador, Long>
{
}
