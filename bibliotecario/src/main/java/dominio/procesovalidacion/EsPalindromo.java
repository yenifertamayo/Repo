package dominio.procesovalidacion;

import dominio.excepcion.PrestamoException;

public class EsPalindromo implements ValidarDatos{

	@Override
	public boolean esPosiblePrestamo(String isbn, String nombreUsuario) {
		int tamanoIsbn = isbn.length();
		
				
		for (int posicionIsbn = 0; posicionIsbn < tamanoIsbn/2; posicionIsbn ++){
			if (isbn.charAt(posicionIsbn) != isbn.charAt((tamanoIsbn-1)-posicionIsbn)){
				return false;
				
			}			
		}	
		throw new PrestamoException("los libros palíndromos solo se pueden utilizar en la biblioteca");
	}

}
