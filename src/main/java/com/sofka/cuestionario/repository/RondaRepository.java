package com.sofka.cuestionario.repository;

import com.sofka.cuestionario.model.Ronda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RondaRepository extends JpaRepository<Ronda, Long>
{

}
