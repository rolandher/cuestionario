package com.sofka.cuestionario.service;

import com.sofka.cuestionario.model.Opciones;

import java.util.List;


public interface OpcionesService
{
	void save(Opciones opciones);

	List<Opciones> getOpciones();

	List<Opciones> getOpcionesByPregunta(int pregunta);
}
