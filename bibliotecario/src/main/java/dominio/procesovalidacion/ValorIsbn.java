package dominio.procesovalidacion;

import java.util.Calendar;
import java.util.Date;

import dominio.Libro;
import dominio.Prestamo;

public class ValorIsbn implements ValidarDatos{
	
	@Override
	public boolean esPosiblePrestamo(String isbn, String nombreUsuario) {
		int tamanoIsbn = isbn.length();
		int sumatoriaNumeros = 0;
		for (int posicionIsbn = 0; posicionIsbn < tamanoIsbn; posicionIsbn ++){
			int valorSeleccionado = isbn.charAt(posicionIsbn) - 48; 
					
			if (valorSeleccionado>=0 && valorSeleccionado<=9){
				sumatoriaNumeros += valorSeleccionado;	
			}
		}	
		return sumatoriaNumeros >= 30;
	}
	
	public Prestamo calcularFechaEntrega(Libro libro, String nombreUsuario ){
		
		Calendar fecha = Calendar.getInstance();	
		quitarHora(fecha);
		Date  fechaSolicitud = fecha.getTime();
			int diasHabiles = 15;
			int diasContados = 1;
			
			while(diasContados < diasHabiles){
				if (fecha.get(Calendar.DAY_OF_WEEK)  != Calendar.SUNDAY){
				diasContados++;
				}
				fecha.add(Calendar.DAY_OF_YEAR, 1);
			}
			
			if (fecha.get(Calendar.DAY_OF_WEEK)  == Calendar.SUNDAY){	
				fecha.add(Calendar.DAY_OF_YEAR, 1);
			}
			
			Date fechaEntregaMaxima = fecha.getTime();
			Prestamo prestamo =new Prestamo(fechaSolicitud, libro, fechaEntregaMaxima, nombreUsuario);
			return prestamo;
	}
	
	private void quitarHora(Calendar calendario) {
		calendario.set(Calendar.HOUR, 0);
		calendario.set(Calendar.MILLISECOND, 0);
		calendario.set(Calendar.SECOND, 0);
		calendario.set(Calendar.MINUTE, 0);
	}
}
