package dominio.procesovalidacion;

import java.util.Calendar;

import dominio.excepcion.PrestamoException;

public class EsDomingo implements ValidarDatos{

	@Override
	public boolean esPosiblePrestamo(String isbn, String nombreUsuario) {
		
		Calendar fechaSolicitud = Calendar.getInstance();
		
		if (fechaSolicitud.get(Calendar.DAY_OF_WEEK)  == Calendar.SUNDAY){
			throw new PrestamoException("No se puede prestar un libro los dias domingos");
		}
		return false;
	}
	
}
