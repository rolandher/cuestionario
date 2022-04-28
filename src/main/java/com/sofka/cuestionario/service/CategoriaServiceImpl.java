package com.sofka.cuestionario.service;

import com.sofka.cuestionario.model.Categoria;
import com.sofka.cuestionario.model.Jugador;
import com.sofka.cuestionario.repository.CategoriaRepository;
import com.sofka.cuestionario.repository.JugadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoriaServiceImpl implements CategoriaService
{
	@Autowired
	private CategoriaRepository categoriaRepository;

	@Override
	public void save(Categoria categoria)
	{
		categoriaRepository.save(categoria);
	}

	@Override
	public List<Categoria> getCategorias()
	{
		return categoriaRepository.findAll();
	}
}
