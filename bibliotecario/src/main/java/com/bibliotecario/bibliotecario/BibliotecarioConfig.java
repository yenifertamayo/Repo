package com.bibliotecario.bibliotecario;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dominio.Bibliotecario;
import dominio.repositorio.RepositorioLibro;
import dominio.repositorio.RepositorioPrestamo;

@Configuration
public class BibliotecarioConfig {

	@Bean
	public Bibliotecario createBibliotecario(RepositorioLibro repositorioLibro, RepositorioPrestamo repositorioPrestamo){
		return new Bibliotecario(repositorioLibro, repositorioPrestamo);
	}
	
}
