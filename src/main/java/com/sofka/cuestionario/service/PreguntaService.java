package com.sofka.cuestionario.service;

import com.sofka.cuestionario.model.Categoria;
import com.sofka.cuestionario.model.Pregunta;

import java.util.List;


public interface PreguntaService
{
	void save(Pregunta pregunta);

	List<Pregunta> getPreguntas();

	List<Pregunta> getPreguntaByIdRonda(int ronda);
}
