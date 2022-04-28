package com.sofka.cuestionario.service;

import com.sofka.cuestionario.model.Categoria;
import com.sofka.cuestionario.model.Ronda;
import com.sofka.cuestionario.repository.CategoriaRepository;
import com.sofka.cuestionario.repository.RondaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RondaServiceImpl implements RondaService
{
	@Autowired
	private RondaRepository rondaRepository;

	@Override
	public void save(Ronda ronda)
	{
		rondaRepository.save(ronda);
	}

	@Override
	public List<Ronda> getRondas()
	{
		return rondaRepository.findAll();
	}
}
