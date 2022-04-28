package com.sofka.cuestionario.service;

import com.sofka.cuestionario.model.Categoria;
import com.sofka.cuestionario.model.Jugador;

import java.util.List;


public interface CategoriaService
{
	void save(Categoria categoria);

	List<Categoria> getCategorias();
}
