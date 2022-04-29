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
		guardarOpciones();


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
		int mostrarPregunta = 1 + random.nextInt  (6-1);
		System.out.println("pregunta : "+mostrarPregunta );
		pregunta = preguntas.get(mostrarPregunta-1);
		System.out.println(pregunta.getName());

		Opciones optionToValidate = new Opciones();
		List<Opciones> opciones = opcionesService.getOpcionesByPregunta(pregunta.getId());

		for(Opciones option: opciones){

			System.out.println(option.getName());
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
					if(respuestaPregunta == 1 || respuestaPregunta == 2 || respuestaPregunta == 3
							|| respuestaPregunta == 4){
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
	private void guardarOpciones() {

		Opciones opcion1 = new Opciones();
		opcion1.setIdQuestion(1);
		opcion1.setName("Es un concepto similar al de 'array");
		opcion1.setOptionCorrect(false);

		Opciones opcion2 = new Opciones();
		opcion2.setIdQuestion(1);
		opcion2.setName("Es un tipo particular de variable");
		opcion2.setOptionCorrect(false);

		Opciones opcion3 = new Opciones();
		opcion3.setIdQuestion(1);
		opcion3.setName("Es un modelo o plantilla a partir de la cual creamos objetos");
		opcion3.setOptionCorrect(true);

		Opciones opcion4 = new Opciones();
		opcion4.setIdQuestion(1);
		opcion4.setName("Es una categoria de datos ordenada secuencialmente");
		opcion4.setOptionCorrect(false);

		Opciones opcion5 = new Opciones();
		opcion5.setIdQuestion(2);
		opcion5.setName("Sus cardinalidad y su tipo");
		opcion5.setOptionCorrect(false);

		Opciones opcion6 = new Opciones();
		opcion6.setIdQuestion(2);
		opcion6.setName("Sus atributos y sus métodos");
		opcion6.setOptionCorrect(true);

		Opciones opcion7 = new Opciones();
		opcion7.setIdQuestion(2);
		opcion7.setName("La forma en que establece comunicación e intercambia mensajes");
		opcion7.setOptionCorrect(false);

		Opciones opcion8 = new Opciones();
		opcion8.setIdQuestion(2);
		opcion8.setName("Su interfaz y los eventos asociados");
		opcion8.setOptionCorrect(false);

		Opciones opcion9 = new Opciones();
		opcion9.setIdQuestion(3);
		opcion9.setName("public class Componente extends Producto");
		opcion9.setOptionCorrect(false);

		Opciones opcion10 = new Opciones();
		opcion10.setIdQuestion(3);
		opcion10.setName("public class Componente inherit Producto");
		opcion10.setOptionCorrect(true);

		Opciones opcion11 = new Opciones();
		opcion11.setIdQuestion(3);
		opcion11.setName("public class Componente implements Producto");
		opcion11.setOptionCorrect(false);

		Opciones opcion12 = new Opciones();
		opcion12.setIdQuestion(3);
		opcion12.setName("public class Componente belong to Producto");
		opcion12.setOptionCorrect(false);

		Opciones opcion13 = new Opciones();
		opcion13.setIdQuestion(4);
		opcion13.setName("Duplicar una clase");
		opcion13.setOptionCorrect(false);

		Opciones opcion14 = new Opciones();
		opcion14.setIdQuestion(4);
		opcion14.setName("Eliminar una clase");
		opcion14.setOptionCorrect(false);

		Opciones opcion15 = new Opciones();
		opcion15.setIdQuestion(4);
		opcion15.setName("Crear un objeto a partir de la clase");
		opcion15.setOptionCorrect(true);

		Opciones opcion16 = new Opciones();
		opcion16.setIdQuestion(4);
		opcion16.setName("Conectar dos clases entre sí");
		opcion16.setOptionCorrect(false);

		Opciones opcion17 = new Opciones();
		opcion17.setIdQuestion(5);
		opcion17.setName("Una función utilizada para intercambiar valores");
		opcion17.setOptionCorrect(false);

		Opciones opcion18 = new Opciones();
		opcion18.setIdQuestion(5);
		opcion18.setName("Es el sobrenombre de la versión 1.3 del JDK");
		opcion18.setOptionCorrect(false);

		Opciones opcion19 = new Opciones();
		opcion19.setIdQuestion(5);
		opcion19.setName("Un framework específico para Android");
		opcion19.setOptionCorrect(false);

		Opciones opcion20 = new Opciones();
		opcion20.setIdQuestion(5);
		opcion20.setName("Una librería para construir interfaces gráficas");
		opcion20.setOptionCorrect(true);

		Opciones opcion21 = new Opciones();
		opcion21.setIdQuestion(6);
		opcion21.setName("Una libreria de Java");
		opcion21.setOptionCorrect(false);

		Opciones opcion22 = new Opciones();
		opcion22.setIdQuestion(6);
		opcion22.setName("Una versión de Java especial para servidores");
		opcion22.setOptionCorrect(false);

		Opciones opcion23 = new Opciones();
		opcion23.setIdQuestion(6);
		opcion23.setName("Un IDE para desarrollar aplicaciones");
		opcion23.setOptionCorrect(true);

		Opciones opcion24 = new Opciones();
		opcion24.setIdQuestion(6);
		opcion24.setName("Ninguna de las anteriores");
		opcion24.setOptionCorrect(false);

		Opciones opcion25 = new Opciones();
		opcion25.setIdQuestion(7);
		opcion25.setName("El formato de intercambio de datos");
		opcion25.setOptionCorrect(false);

		Opciones opcion26 = new Opciones();
		opcion26.setIdQuestion(7);
		opcion26.setName("El formato que obtenemos tras compilar un fuente .java");
		opcion26.setOptionCorrect(false);

		Opciones opcion27 = new Opciones();
		opcion27.setIdQuestion(7);
		opcion27.setName("Un tipo de variable");
		opcion27.setOptionCorrect(false);

		Opciones opcion28 = new Opciones();
		opcion28.setIdQuestion(7);
		opcion28.setName("Un depurador de código");
		opcion28.setOptionCorrect(true);

		Opciones opcion29 = new Opciones();
		opcion29.setIdQuestion(8);
		opcion29.setName("public class Componente interface Producto");
		opcion29.setOptionCorrect(false);

		Opciones opcion30 = new Opciones();
		opcion30.setIdQuestion(8);
		opcion30.setName("Componente cp = new Componente (interfaz)");
		opcion30.setOptionCorrect(false);

		Opciones opcion31 = new Opciones();
		opcion31.setIdQuestion(8);
		opcion31.setName("public class Componente implements Printable");
		opcion31.setOptionCorrect(true);

		Opciones opcion32 = new Opciones();
		opcion32.setIdQuestion(8);
		opcion32.setName("public class Componente belong to");
		opcion32.setOptionCorrect(false);

		Opciones opcion33 = new Opciones();
		opcion33.setIdQuestion(9);
		opcion33.setName("Editarlo para modificar su comportamiento");
		opcion33.setOptionCorrect(false);

		Opciones opcion34 = new Opciones();
		opcion34.setIdQuestion(9);
		opcion34.setName("Cambiarle el nombre dejándolo con la misma funcionalidad");
		opcion34.setOptionCorrect(false);

		Opciones opcion35 = new Opciones();
		opcion35.setIdQuestion(9);
		opcion35.setName("Crear un método con el mismo nombre pero diferentes argumentos");
		opcion35.setOptionCorrect(true);

		Opciones opcion36 = new Opciones();
		opcion36.setIdQuestion(9);
		opcion36.setName("Añadirle funcionalidades a un método");
		opcion36.setOptionCorrect(false);

		Opciones opcion37 = new Opciones();
		opcion37.setIdQuestion(10);
		opcion37.setName("Un error que lanza un método cuando algo va mal");
		opcion37.setOptionCorrect(true);

		Opciones opcion38 = new Opciones();
		opcion38.setIdQuestion(10);
		opcion38.setName("Un objeto que no puede ser instanciado");
		opcion38.setOptionCorrect(false);

		Opciones opcion39 = new Opciones();
		opcion39.setIdQuestion(10);
		opcion39.setName("Un bucle que no finaliza");
		opcion39.setOptionCorrect(false);

		Opciones opcion40 = new Opciones();
		opcion40.setIdQuestion(10);
		opcion40.setName("Un tipo de evento muy utilizado al crear interfaces");
		opcion40.setOptionCorrect(false);

		Opciones opcion41 = new Opciones();
		opcion41.setIdQuestion(11);
		opcion41.setName("Una manera de comunicarse con el hardware");
		opcion41.setOptionCorrect(false);

		Opciones opcion42 = new Opciones();
		opcion42.setIdQuestion(11);
		opcion42.setName("Una forma de diseñar código para la máquina");
		opcion42.setOptionCorrect(false);

		Opciones opcion43 = new Opciones();
		opcion43.setIdQuestion(11);
		opcion43.setName("Lo relacionado con la codificación de páginas web y sitios interactivos");
		opcion43.setOptionCorrect(false);

		Opciones opcion44 = new Opciones();
		opcion44.setIdQuestion(11);
		opcion44.setName("Una manera definida para escribir instrucciones claras para programar aplicaciones de alto nivel");
		opcion44.setOptionCorrect(true);

		Opciones opcion45= new Opciones();
		opcion45.setIdQuestion(12);
		opcion45.setName("Es un método dentro de un objeto de clase");
		opcion45.setOptionCorrect(false);

		Opciones opcion46 = new Opciones();
		opcion46.setIdQuestion(12);
		opcion46.setName("Es un método que tiene un efecto secundario que incrementa un valor global");
		opcion46.setOptionCorrect(true);

		Opciones opcion47 = new Opciones();
		opcion47.setIdQuestion(12);
		opcion47.setName("Determina el ganador del juego, ya sea el jugador 1 o el 2");
		opcion47.setOptionCorrect(false);

		Opciones opcion48 = new Opciones();
		opcion48.setIdQuestion(12);
		opcion48.setName("Es una función para determinar un resultado");
		opcion48.setOptionCorrect(false);

		Opciones opcion49= new Opciones();
		opcion49.setIdQuestion(13);
		opcion49.setName("Es un método de acceso a los datos");
		opcion49.setOptionCorrect(false);

		Opciones opcion50 = new Opciones();
		opcion50.setIdQuestion(13);
		opcion50.setName("Es un mecanismo para consultar información");
		opcion50.setOptionCorrect(false);

		Opciones opcion51 = new Opciones();
		opcion51.setIdQuestion(13);
		opcion51.setName("Es un elemento del programa que puede cambiar");
		opcion51.setOptionCorrect(false);

		Opciones opcion52 = new Opciones();
		opcion52.setIdQuestion(13);
		opcion52.setName("Es una declaración dentro del programa para definir un valor dinámico");
		opcion52.setOptionCorrect(true);

		Opciones opcion53= new Opciones();
		opcion53.setIdQuestion(14);
		opcion53.setName("Un tipo determinado para crear objetos de ese mismo tipo");
		opcion53.setOptionCorrect(true);

		Opciones opcion54 = new Opciones();
		opcion54.setIdQuestion(14);
		opcion54.setName("Es una categoría del mismo tipo");
		opcion54.setOptionCorrect(false);

		Opciones opcion55 = new Opciones();
		opcion55.setIdQuestion(14);
		opcion55.setName("Son varios objetos heredados");
		opcion55.setOptionCorrect(false);

		Opciones opcion56 = new Opciones();
		opcion56.setIdQuestion(14);
		opcion56.setName("Ninguna de las anteriores");
		opcion56.setOptionCorrect(false);

		Opciones opcion57= new Opciones();
		opcion57.setIdQuestion(15);
		opcion57.setName("Un lenguaje de programación que transpira código nativo");
		opcion57.setOptionCorrect(false);

		Opciones opcion58 = new Opciones();
		opcion58.setIdQuestion(15);
		opcion58.setName("Un lenguaje que requiere pasar por un proceso de transformación a código de máquina");
		opcion58.setOptionCorrect(true);

		Opciones opcion59 = new Opciones();
		opcion59.setIdQuestion(15);
		opcion59.setName("Un lenguaje que requiere de un controlador específico para que pueda correr");
		opcion59.setOptionCorrect(false);

		Opciones opcion60 = new Opciones();
		opcion60.setIdQuestion(15);
		opcion60.setName("Ninguna de las anteriores");
		opcion60.setOptionCorrect(false);

		Opciones opcion61= new Opciones();
		opcion61.setIdQuestion(16);
		opcion61.setName("Control para toma de decisiones de cómputo (if, else, switch, etcétera)");
		opcion61.setOptionCorrect(true);

		Opciones opcion62 = new Opciones();
		opcion62.setIdQuestion(16);
		opcion62.setName("Interacciones para la gestión del flujo de trabajo");
		opcion62.setOptionCorrect(false);

		Opciones opcion63 = new Opciones();
		opcion63.setIdQuestion(16);
		opcion63.setName("Condicionales y preguntar para tomar decisiones");
		opcion63.setOptionCorrect(false);

		Opciones opcion64 = new Opciones();
		opcion64.setIdQuestion(16);
		opcion64.setName("Todo lo relacionado con flujos de intercambios lógicos");
		opcion64.setOptionCorrect(false);

		Opciones opcion65= new Opciones();
		opcion65.setIdQuestion(17);
		opcion65.setName("Programación funcional");
		opcion65.setOptionCorrect(false);

		Opciones opcion66 = new Opciones();
		opcion66.setIdQuestion(17);
		opcion66.setName("Evitar efectos secundarios");
		opcion66.setOptionCorrect(false);

		Opciones opcion67 = new Opciones();
		opcion67.setIdQuestion(17);
		opcion67.setName("Encapsulamiento");
		opcion67.setOptionCorrect(true);

		Opciones opcion68 = new Opciones();
		opcion68.setIdQuestion(17);
		opcion68.setName("Abstracciones por métodos");
		opcion68.setOptionCorrect(false);

		Opciones opcion69= new Opciones();
		opcion69.setIdQuestion(18);
		opcion69.setName("Java");
		opcion69.setOptionCorrect(true);

		Opciones opcion70 = new Opciones();
		opcion70.setIdQuestion(18);
		opcion70.setName("JavaScript");
		opcion70.setOptionCorrect(false);

		Opciones opcion71 = new Opciones();
		opcion71.setIdQuestion(18);
		opcion71.setName("Python");
		opcion71.setOptionCorrect(false);

		Opciones opcion72 = new Opciones();
		opcion72.setIdQuestion(18);
		opcion72.setName("HTML");
		opcion72.setOptionCorrect(false);

		Opciones opcion73= new Opciones();
		opcion73.setIdQuestion(19);
		opcion73.setName("Java");
		opcion73.setOptionCorrect(false);

		Opciones opcion74 = new Opciones();
		opcion74.setIdQuestion(19);
		opcion74.setName("JavaScript");
		opcion74.setOptionCorrect(false);

		Opciones opcion75 = new Opciones();
		opcion75.setIdQuestion(19);
		opcion75.setName("Python");
		opcion75.setOptionCorrect(false);

		Opciones opcion76 = new Opciones();
		opcion76.setIdQuestion(19);
		opcion76.setName("HTML");
		opcion76.setOptionCorrect(true);

		Opciones opcion77= new Opciones();
		opcion77.setIdQuestion(20);
		opcion77.setName("Java");
		opcion77.setOptionCorrect(true);

		Opciones opcion78 = new Opciones();
		opcion78.setIdQuestion(20);
		opcion78.setName("CSS");
		opcion78.setOptionCorrect(false);

		Opciones opcion79 = new Opciones();
		opcion79.setIdQuestion(20);
		opcion79.setName("Python");
		opcion79.setOptionCorrect(false);

		Opciones opcion80 = new Opciones();
		opcion80.setIdQuestion(20);
		opcion80.setName("HTML");
		opcion80.setOptionCorrect(false);

		Opciones opcion81= new Opciones();
		opcion81.setIdQuestion(21);
		opcion81.setName("Cliente-Servidor");
		opcion81.setOptionCorrect(true);

		Opciones opcion82 = new Opciones();
		opcion81.setIdQuestion(21);
		opcion81.setName("Servidor-Servidor");
		opcion81.setOptionCorrect(false);

		Opciones opcion83 = new Opciones();
		opcion83.setIdQuestion(21);
		opcion83.setName("Servidor-Cliente");
		opcion83.setOptionCorrect(false);

		Opciones opcion84 = new Opciones();
		opcion84.setIdQuestion(21);
		opcion84.setName("Servidor");
		opcion84.setOptionCorrect(false);

		Opciones opcion85= new Opciones();
		opcion85.setIdQuestion(22);
		opcion85.setName("Una estructura programada orientada a una sintaxis");
		opcion85.setOptionCorrect(false);

		Opciones opcion86 = new Opciones();
		opcion86.setIdQuestion(22);
		opcion86.setName("Una función matemática");
		opcion86.setOptionCorrect(false);

		Opciones opcion87 = new Opciones();
		opcion87.setIdQuestion(22);
		opcion87.setName("Instrucciones lógicas con un propósito específico");
		opcion87.setOptionCorrect(true);

		Opciones opcion88 = new Opciones();
		opcion88.setIdQuestion(22);
		opcion88.setName("Código fuente");
		opcion88.setOptionCorrect(false);

		Opciones opcion89= new Opciones();
		opcion89.setIdQuestion(23);
		opcion89.setName("DSL");
		opcion89.setOptionCorrect(false);

		Opciones opcion90 = new Opciones();
		opcion90.setIdQuestion(23);
		opcion90.setName("SQL");
		opcion90.setOptionCorrect(true);

		Opciones opcion91 = new Opciones();
		opcion91.setIdQuestion(23);
		opcion91.setName("PL");
		opcion91.setOptionCorrect(false);

		Opciones opcion92 = new Opciones();
		opcion92.setIdQuestion(23);
		opcion92.setName("QUERY");
		opcion92.setOptionCorrect(false);

		Opciones opcion93= new Opciones();
		opcion93.setIdQuestion(24);
		opcion93.setName("Programación orientada a objetos");
		opcion93.setOptionCorrect(true);

		Opciones opcion94 = new Opciones();
		opcion94.setIdQuestion(24);
		opcion94.setName("Programación orientada a clases");
		opcion94.setOptionCorrect(false);

		Opciones opcion95 = new Opciones();
		opcion95.setIdQuestion(24);
		opcion95.setName("Programación orientada a atributos");
		opcion95.setOptionCorrect(false);

		Opciones opcion96 = new Opciones();
		opcion96.setIdQuestion(24);
		opcion96.setName("Programación funcional");
		opcion96.setOptionCorrect(false);

		Opciones opcion97= new Opciones();
		opcion97.setIdQuestion(25);
		opcion97.setName("FOR");
		opcion97.setOptionCorrect(false);

		Opciones opcion98 = new Opciones();
		opcion98.setIdQuestion(25);
		opcion98.setName("ELSE");
		opcion98.setOptionCorrect(false);

		Opciones opcion99 = new Opciones();
		opcion98.setIdQuestion(25);
		opcion98.setName("WHILE");
		opcion98.setOptionCorrect(false);

		Opciones opcion100 = new Opciones();
		opcion99.setIdQuestion(25);
		opcion99.setName("IF");
		opcion99.setOptionCorrect(true);


		List<Opciones> opcionesList = Arrays.asList(opcion1,opcion2,opcion3,opcion4,opcion5,opcion6,opcion7,opcion8,
				opcion9,opcion10,opcion11,opcion12,opcion13,opcion14,opcion15,opcion16,opcion17,opcion18,opcion19,opcion20,
				opcion21,opcion22,opcion23,opcion24,opcion25,opcion26,opcion27,opcion28,opcion29,opcion30,opcion31,opcion32,
				opcion33,opcion34,opcion35,opcion36,opcion37,opcion38,opcion39,opcion40,opcion41,opcion42,opcion43,opcion44,
				opcion45,opcion46,opcion47,opcion48,opcion49,opcion50,opcion51,opcion52,opcion53,opcion54,opcion55,opcion56,
				opcion57,opcion58,opcion59,opcion60,opcion61,opcion62,opcion63,opcion64,opcion65,opcion66,opcion67,opcion68,
				opcion69,opcion70,opcion71,opcion72,opcion73,opcion74,opcion75,opcion76,opcion77,opcion78,opcion79,opcion80,
				opcion81,opcion82,opcion83,opcion84,opcion85,opcion86,opcion87,opcion88,opcion89,opcion90,opcion91,opcion92,
				opcion93,opcion94,opcion95,opcion96,opcion97,opcion98,opcion99, opcion100);

		for (Opciones opciones: opcionesList) {
			opcionesService.save(opciones);
		}

	}

}


