package com.example.biblio_Api.Exceptions;

public class LivresNotFoundException extends RuntimeException {

	
	  public LivresNotFoundException(Long id) {  
		  super("le livre est introuvable " + id);
		  }
}
