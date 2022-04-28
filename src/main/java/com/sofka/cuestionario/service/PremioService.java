package com.sofka.cuestionario.service;

import com.sofka.cuestionario.model.Categoria;
import com.sofka.cuestionario.model.Premio;

import java.util.List;


public interface PremioService
{
	void save(Premio premio);

	List<Premio> getPremios();
	Premio getPremioByIdRonda(int idRonda);
}
