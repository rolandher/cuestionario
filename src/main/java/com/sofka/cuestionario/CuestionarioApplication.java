package com.sofka.cuestionario;

import com.sofka.cuestionario.model.*;

import com.sofka.cuestionario.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import javax.annotation.PostConstruct;
import java.util.*;


@SpringBootApplication
public class CuestionarioApplication
{

	@Autowired
	JugadorService jugadorService;

	@Autowired
	CategoriaService categoriaService;

	@Autowired
	RondaService rondaService;

	@Autowired
	PremioService premioService;

	@Autowired
	PreguntaService preguntaService;

	@Autowired
	OpcionesService opcionesService;


	public static void main(String[] args) {
		SpringApplication.run(CuestionarioApplication.class, args);
	}

	@PostConstruct
	public void init() {

		guardarCategorias();
		guardarRondas();
		guardarPremio();
		guardarPreguntas();

		Scanner sc = new Scanner(System.in);


		int opc = 1;
		String userName;

		System.out.println("------------------------------");
		System.out.println("------JUEGO DE PREGUNTAS------");
		System.out.println("------------------------------");
		System.out.println("Por favor ingrese su nombre: ");
		userName = sc.nextLine();


		System.out.println("Bienvenido " + userName);

		Jugador jugador = Jugador.builder().name(userName).reward(0).build();

		while (opc == 1) {
			Scanner sc1 = new Scanner(System.in);
			System.out.println("Opciones: ");
			System.out.println("1 = Jugar");
			System.out.println("0 = Salir");
			try {
				opc = sc1.nextInt();
				if (opc == 1) {
					mostrarRondas(userName);
					System.out.println("Probando...");
					opc = 0;
				}
			} catch (Exception e) {
				System.out.println("Error de digitación por favor intente de nuevo"+ e);
				opc = 1;

			}
		}


		}


	private void mostrarRondas(String nombreUsuario) throws Exception {
		List<Ronda> rounds = new ArrayList<>();
		rounds = rondaService.getRondas();
		Jugador jugador = Jugador.builder().name(nombreUsuario).reward(0).build();

		for(Ronda round: rounds){
			System.out.println(round.getName());
			if(mostrarPreguntas(round.getId(), jugador))
				continue;
			else
				break;
		}

		jugadorService.save(jugador);


	}

	private boolean mostrarPreguntas(int idRound, Jugador player) throws Exception {


		Pregunta pregunta = new Pregunta();
		List<Pregunta> preguntas = preguntaService.getPreguntaByIdRonda(idRound);

		Premio premio = premioService.getPremioByIdRonda(idRound);

		Random random = new Random();
		int mostrarPregunta = 1 + random.nextInt(6-1);
		pregunta = preguntas.get(mostrarPregunta-1);

		Opciones optionToValidate = new Opciones();
		List<Opciones> opciones = opcionesService.getOpcionesByPregunta(pregunta.getId());

		for(Opciones option: opciones){
			System.out.println(option.getStatement());
		}

		System.out.println("Seleccione la alternativa correcta o 0 para salir: ");

		boolean preguntaOK = false;
		int respuestaPregunta = 0;


		while (!preguntaOK){
			try {
				Scanner sc1 = new Scanner(System.in);
				respuestaPregunta = sc1.nextInt();
				if (respuestaPregunta == 0){
					preguntaOK = true;
					return false;
				}else{
					if(respuestaPregunta == 1 || respuestaPregunta == 2 || respuestaPregunta == 3 || respuestaPregunta == 4){
						preguntaOK = true;
						optionToValidate = opciones.get(respuestaPregunta-1);
						if(optionToValidate.isOptionCorrect()){
							System.out.println("¡Pasaste a la siguiente Ronda");
							player.setReward(player.getReward()+premio.getValue());

							return true;
						}
						else{
							System.out.println("Lo siento, has perdido");
							return false;
						}
					}else{
						System.out.println("Seleccione la alternativa correcta o 0 para salir: ");
						preguntaOK = false;
					}
				}

			} catch (Exception e) {
				System.out.println("Ocurrio un error ");
				System.out.println("Seleccione la alternativa correcta o 0 para salir: "+ e);
				preguntaOK = false;
			}
		}

		return true;
	}

	private void guardarCategorias(){

		Categoria categoria = new Categoria();
		categoria.setName("Programacion1");

		Categoria categoria2 = new Categoria();
		categoria2.setName("Programacion2");

		Categoria categoria3 = new Categoria();
		categoria3.setName("Programacion3");

		Categoria categoria4 = new Categoria();
		categoria4.setName("Programacion4");

		Categoria categoria5 = new Categoria();
		categoria5.setName("Programacion5");

		List<Categoria> categoriaList = Arrays.asList(categoria, categoria2, categoria3, categoria4, categoria5);

		for (Categoria categorias: categoriaList) {
			categoriaService.save(categorias);
		}

	}
	private void guardarRondas(){

		Ronda ronda1 = new Ronda();
		ronda1.setName("Ronda1");

		Ronda ronda2 = new Ronda();
		ronda2.setName("Ronda2");

		Ronda ronda3 = new Ronda();
		ronda3.setName("Ronda3");

		Ronda ronda4 = new Ronda();
		ronda4.setName("Ronda4");

		Ronda ronda5 = new Ronda();
		ronda5.setName("Ronda5");

		List<Ronda> rondaList = Arrays.asList(ronda1, ronda2, ronda3, ronda4, ronda5);

		for (Ronda ronda: rondaList) {
			rondaService.save(ronda);
		}

	}

	private void guardarPremio(){

		Premio premio1 = new Premio();
		premio1.setValue(50);
		premio1.setIdRound(1);

		Premio premio2 = new Premio();
		premio2.setValue(100);
		premio2.setIdRound(2);

		Premio premio3 = new Premio();
		premio3.setValue(150);
		premio3.setIdRound(3);

		Premio premio4 = new Premio();
		premio4.setValue(200);
		premio4.setIdRound(4);

		Premio premio5 = new Premio();
		premio5.setValue(50);
		premio5.setIdRound(5);

		List<Premio> premioList = Arrays.asList(premio1, premio2, premio3, premio4, premio5);

		for (Premio premio: premioList) {
			premioService.save(premio);
		}

	}


	private void guardarPreguntas(){

		Pregunta pregunta1 = new Pregunta();
		pregunta1.setIdCategory(1);
		pregunta1.setIdRound(1);
		pregunta1.setName("¿Cuál es la descripción que crees que define mejor el concepto 'clase' en la programación orientada a objetos?");

		Pregunta pregunta2 = new Pregunta();
		pregunta2.setIdCategory(1);
		pregunta2.setIdRound(1);
		pregunta2.setName("¿Qué elementos crees que definen a un objeto?");

		Pregunta pregunta3 = new Pregunta();
		pregunta3.setIdCategory(1);
		pregunta3.setIdRound(1);
		pregunta3.setName("¿Qué código de los siguientes tiene que ver con la herencia?");

		Pregunta pregunta4 = new Pregunta();
		pregunta4.setIdCategory(1);
		pregunta4.setIdRound(1);
		pregunta4.setName("¿Qué significa instanciar una clase?");

		Pregunta pregunta5 = new Pregunta();
		pregunta5.setIdCategory(1);
		pregunta5.setIdRound(1);
		pregunta5.setName("En Java, ¿a qué nos estamos refiriendo si hablamos de 'Swing'?");


		Pregunta pregunta6 = new Pregunta();
		pregunta6.setIdCategory(2);
		pregunta6.setIdRound(2);
		pregunta6.setName("¿Qué es Eclipse?");

		Pregunta pregunta7 = new Pregunta();
		pregunta7.setIdCategory(2);
		pregunta7.setIdRound(2);
		pregunta7.setName("¿Qué es el bytecode en Java?");

		Pregunta pregunta8 = new Pregunta();
		pregunta8.setIdCategory(2);
		pregunta8.setIdRound(2);
		pregunta8.setName("¿Qué código asociarías a una Interfaz en Java?");

		Pregunta pregunta9 = new Pregunta();
		pregunta9.setIdCategory(2);
		pregunta9.setIdRound(2);
		pregunta9.setName("¿Qué significa sobrecargar (overload) un método?");

		Pregunta pregunta10 = new Pregunta();
		pregunta10.setIdCategory(2);
		pregunta10.setIdRound(2);
		pregunta10.setName("¿Qué es una excepción?");

		Pregunta pregunta11 = new Pregunta();
		pregunta11.setIdCategory(3);
		pregunta11.setIdRound(3);
		pregunta11.setName("¿Qué entendemos como lenguaje de programación?");

		Pregunta pregunta12 = new Pregunta();
		pregunta12.setIdCategory(3);
		pregunta12.setIdRound(3);
		pregunta12.setName("¿Qué podemos decir de un método que tiene una asignación a una variable global de clase?");

		Pregunta pregunta13 = new Pregunta();
		pregunta13.setIdCategory(3);
		pregunta13.setIdRound(3);
		pregunta13.setName("¿Qué es una variable?");

		Pregunta pregunta14 = new Pregunta();
		pregunta14.setIdCategory(3);
		pregunta14.setIdRound(3);
		pregunta14.setName("Una clase es...");

		Pregunta pregunta15 = new Pregunta();
		pregunta15.setIdCategory(3);
		pregunta15.setIdRound(3);
		pregunta15.setName("Cuando hablamos de un lenguaje compilado nos referimos a...?");


		Pregunta pregunta16 = new Pregunta();
		pregunta16.setIdCategory(4);
		pregunta16.setIdRound(4);
		pregunta16.setName("Un ciclo de control es... ");

		Pregunta pregunta17 = new Pregunta();
		pregunta17.setIdCategory(4);
		pregunta17.setIdRound(4);
		pregunta17.setName("Para una clase que tenga métodos get y set pero con atributos privados, ¿qué pilar de la programación se está aplicando?");

		Pregunta pregunta18 = new Pregunta();
		pregunta18.setIdCategory(4);
		pregunta18.setIdRound(4);
		pregunta18.setName("¿Cuál de los siguientes es un lenguaje de programación compilado?");

		Pregunta pregunta19 = new Pregunta();
		pregunta19.setIdCategory(4);
		pregunta19.setIdRound(4);
		pregunta19.setName("¿Qué lenguaje de etiquetas se usa en un navegador?");

		Pregunta pregunta20 = new Pregunta();
		pregunta20.setIdCategory(4);
		pregunta20.setIdRound(4);
		pregunta20.setName("¿Qué lenguajes usamos más al lado del navegador?");


		Pregunta pregunta21 = new Pregunta();
		pregunta21.setIdCategory(5);
		pregunta21.setIdRound(5);
		pregunta21.setName("El servidor es un proveedor de información y el cliente es un consumidor de información, nos referimos a... ");

		Pregunta pregunta22 = new Pregunta();
		pregunta22.setIdCategory(5);
		pregunta22.setIdRound(5);
		pregunta22.setName("¿Qué es un algoritmo?");

		Pregunta pregunta23 = new Pregunta();
		pregunta23.setIdCategory(5);
		pregunta23.setIdRound(5);
		pregunta23.setName("Un lenguaje que se usa para hacer consultas en una base de datos relacional.");

		Pregunta pregunta24 = new Pregunta();
		pregunta24.setIdCategory(5);
		pregunta24.setIdRound(5);
		pregunta24.setName("Dentro de las bases de la programación tenemos los siguientes paradigmas: ");

		Pregunta pregunta25 = new Pregunta();
		pregunta25.setIdCategory(5);
		pregunta25.setIdRound(5);
		pregunta25.setName("Para evaluar una expresión booleana utilizo un...");

		List<Pregunta> preguntaList = Arrays.asList(pregunta1, pregunta2, pregunta3, pregunta4, pregunta5, pregunta6, pregunta7,
				pregunta8, pregunta9, pregunta10, pregunta11, pregunta12, pregunta13, pregunta14, pregunta15, pregunta16, pregunta17,
				pregunta18, pregunta19, pregunta20, pregunta20, pregunta21, pregunta22, pregunta23, pregunta24, pregunta25);

		for (Pregunta preguntas: preguntaList) {
			preguntaService.save(preguntas);
		}

	}

}


