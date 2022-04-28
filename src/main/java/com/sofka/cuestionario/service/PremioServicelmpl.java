package com.sofka.cuestionario.service;

import com.sofka.cuestionario.model.Categoria;
import com.sofka.cuestionario.model.Premio;
import com.sofka.cuestionario.model.Ronda;
import com.sofka.cuestionario.repository.PremioRepository;
import com.sofka.cuestionario.repository.RondaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PremioServicelmpl implements PremioService
{
	@Autowired
	private PremioRepository premioRepository;

	@Override
	public void save(Premio premio)
	{
		premioRepository.save(premio);
	}

	@Override
	public List<Premio> getPremios()
	{
		return premioRepository.findAll();
	}

	@Override
	public Premio getPremioByIdRonda(int idRonda) {
		return premioRepository.findByIdRound(idRonda);
	}
}
