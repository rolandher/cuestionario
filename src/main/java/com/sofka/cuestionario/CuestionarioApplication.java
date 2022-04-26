package com.sofka.cuestionario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class CuestionarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(CuestionarioApplication.class, args);
		Scanner scanner = new Scanner(System.in);
		String user;
		System.out.println("*****************************");
		System.out.println("*****cuestionario******");
		System.out.println("*******************************");
		System.out.println("Por favor ingrese su nombre: ");
		user = scanner.nextLine();
		System.out.println("Bienvenido"+ user);
	}

}



