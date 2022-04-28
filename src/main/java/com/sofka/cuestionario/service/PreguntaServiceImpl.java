package com.sofka.cuestionario.service;

import com.sofka.cuestionario.model.Categoria;
import com.sofka.cuestionario.model.Pregunta;
import com.sofka.cuestionario.repository.CategoriaRepository;
import com.sofka.cuestionario.repository.PreguntaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PreguntaServiceImpl implements PreguntaService
{
	@Autowired
	private PreguntaRepository preguntaRepository;

	@Override
	public void save(Pregunta pregunta)
	{
		preguntaRepository.save(pregunta);
	}

	@Override
	public List<Pregunta> getPreguntas()
	{
		return preguntaRepository.findAll();
	}

	@Override
	public List<Pregunta> getPreguntaByIdRonda(int ronda) {
		return preguntaRepository.findByIdRound(ronda);
	}


}
