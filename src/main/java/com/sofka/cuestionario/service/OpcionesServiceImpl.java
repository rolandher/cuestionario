package com.sofka.cuestionario.service;

import com.sofka.cuestionario.model.Jugador;
import com.sofka.cuestionario.model.Opciones;
import com.sofka.cuestionario.repository.OpcionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OpcionesServiceImpl implements OpcionesService
{
	@Autowired
	private OpcionesRepository opcionesRepository;
	@Override
	public void save(Opciones opciones)
	{
		opcionesRepository.save(opciones);
	}

	@Override
	public List<Opciones> getOpciones()
	{
		return opcionesRepository.findAll();
	}

	@Override
	public List<Opciones> getOpcionesByPregunta(int pregunta) {
		return opcionesRepository.findByIdQuestion(pregunta);
	}
}
