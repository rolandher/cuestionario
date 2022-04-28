package com.sofka.cuestionario.service;

import com.sofka.cuestionario.model.Jugador;

import java.util.List;


public interface JugadorService
{
	void save(Jugador jugador);

	List<Jugador> getJugadores();
}
