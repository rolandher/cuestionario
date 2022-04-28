package com.sofka.cuestionario.service;

import com.sofka.cuestionario.model.Categoria;
import com.sofka.cuestionario.model.Ronda;

import java.util.List;


public interface RondaService
{
	List<Ronda> getRondas();

	void save(Ronda ronda);
}
