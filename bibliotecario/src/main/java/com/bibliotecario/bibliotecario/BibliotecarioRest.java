package com.bibliotecario.bibliotecario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dominio.Bibliotecario;
import dominio.Libro;
import dominio.PeticionPrestamo;
import dominio.Prestamo;
import dominio.repositorio.RepositorioLibro;
import dominio.repositorio.RepositorioPrestamo;

@RestController
@Transactional
public class BibliotecarioRest {

	@Autowired
	Bibliotecario bibliotecario;
	
	@Autowired
	RepositorioPrestamo repositorioPrestamo;
	
	@Autowired
	RepositorioLibro repositorioLibro;
	
	@RequestMapping(value = "/crearLibro", method = RequestMethod.POST)
	@ResponseBody
	public Libro servicioCrearLibro(@RequestBody Libro libro) {
		
		return bibliotecario.guardarLibro(libro);
	}
		
	@RequestMapping("/libro")
	public String libroPorIsbn(@RequestParam(value = "isbn") String isbn) {
		
		return repositorioLibro.obtenerPorIsbn(isbn).getTitulo();
	}
	
	@RequestMapping(value = "/listaLibros", method = RequestMethod.GET)
	@ResponseBody
	public List<Libro> servicioListarLibros() {
		
		return repositorioLibro.obtenerListaLibros();
	}
	
	@RequestMapping(value = "/prestar", method = RequestMethod.POST)
	@ResponseBody
	public Prestamo servicioPrestar(@RequestBody PeticionPrestamo perticionPrestamo) {
		
		return bibliotecario.prestar(perticionPrestamo.getIsbn(), perticionPrestamo.getNombreUsuario());
	}
	
	@RequestMapping(value = "/listaPrestamos", method = RequestMethod.GET)
	@ResponseBody
	public List<Prestamo> servicioListarPrestamos() {
		
		return repositorioPrestamo.obtenerListaPrestamos();
	}

}
