package com.sofka.cuestionario.service;

import com.sofka.cuestionario.model.Jugador;
import com.sofka.cuestionario.repository.JugadorRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class JugadorServiceImpl implements JugadorService
{
	private JugadorRepository jugadorRepository;

	public JugadorServiceImpl(JugadorRepository jugadorRepository)
	{
		this.jugadorRepository = jugadorRepository;
	}

	@Override
	public void save(Jugador jugador)
	{
		jugadorRepository.save(jugador);
	}

	@Override
	public List<Jugador> getJugadores()
	{
		return jugadorRepository.findAll();
	}
}
