package dominio;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dominio.excepcion.PrestamoException;
import dominio.procesovalidacion.EsDomingo;
import dominio.procesovalidacion.EsPalindromo;
import dominio.procesovalidacion.ValidarDatos;
import dominio.procesovalidacion.ValorIsbn;
import dominio.repositorio.RepositorioLibro;
import dominio.repositorio.RepositorioPrestamo;

public class Bibliotecario {

	public static final String EL_LIBRO_NO_SE_ENCUENTRA_DISPONIBLE = "El libro no se encuentra disponible";

	private RepositorioLibro repositorioLibro;
	private RepositorioPrestamo repositorioPrestamo;
	
	private List<ValidarDatos> reglasPrestamo = new ArrayList<>();
	
	public Bibliotecario(RepositorioLibro repositorioLibro2, RepositorioPrestamo repositorioPrestamo2) {
		this.repositorioLibro = repositorioLibro2;
		this.repositorioPrestamo = repositorioPrestamo2;
		agregarReglas();
	}

	public void agregarReglas() {
		
		reglasPrestamo.add(new EsDomingo());
		reglasPrestamo.add(new EsPalindromo());
	}

	public boolean esPosiblePrestamo(String isbn, String nombreUsuario) {
		
		for (ValidarDatos reglaEsCorrecto : reglasPrestamo) {
			if(reglaEsCorrecto.esPosiblePrestamo(isbn, nombreUsuario)){
				return false;
			}
		}
		return true;
	}
		
	public Prestamo prestar(String isbn, String nombreUsuario) {

		Libro libroBuscar = repositorioLibro.obtenerPorIsbn(isbn);
		Calendar fecha = Calendar.getInstance();	
		Date fechaSolicitud = fecha.getTime();
		
		if(libroBuscar != null && !esPrestado(isbn)){
			
			if(esPosiblePrestamo(isbn, nombreUsuario)){
				
				ValorIsbn valorIsbn=new ValorIsbn();
				Prestamo prestamo= new Prestamo(fechaSolicitud, libroBuscar, null, nombreUsuario);
				
				if(valorIsbn.esPosiblePrestamo(isbn, nombreUsuario)){
					
					prestamo = valorIsbn.calcularFechaEntrega(libroBuscar, nombreUsuario);
				}
				
				repositorioPrestamo.agregar(prestamo);
				return prestamo;
			}
		}
		else{
			throw new PrestamoException(EL_LIBRO_NO_SE_ENCUENTRA_DISPONIBLE);
		}
		
		return null;
	}

	public boolean esPrestado(String isbn) {
		
		Libro libroesPrestado = repositorioPrestamo.obtenerLibroPrestadoPorIsbn(isbn);
		
		return libroesPrestado != null;
		
		
	}
	
	public Libro guardarLibro(Libro libroAGuardar){
		
		try {
			repositorioLibro.obtenerPorIsbn(libroAGuardar.getIsbn());
		} catch (Exception e) {
			repositorioLibro.agregar(libroAGuardar);
			return libroAGuardar;
		}
		
		throw new PrestamoException("El libro no se puede guardar porque ya existe en la base de datos");
	}

}
