package com.sofka.cuestionario.repository;

import com.sofka.cuestionario.model.Pregunta;
import com.sofka.cuestionario.model.Premio;
import com.sofka.cuestionario.model.Ronda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PremioRepository extends JpaRepository<Premio, Long>
{
    Premio findByIdRound(int idRound);
}
