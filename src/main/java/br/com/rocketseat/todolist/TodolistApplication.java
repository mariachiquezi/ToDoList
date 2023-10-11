package br.com.rocketseat.todolist;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//anotacao interface que define que essa é a classe inicial do projeto
@SpringBootApplication
public class TodolistApplication {

	public static void main(String[] args) {
		// aqui tem um tomcat que roda ocultamente
		SpringApplication.run(TodolistApplication.class, args);
	}

}
