package ar.edu.uade.tpoapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ar.edu.uade.tpoapi.modelo.Persona;
import ar.edu.uade.tpoapi.services.PersonaService;

@SpringBootApplication
public class Sir implements CommandLineRunner{

  @Autowired
  private PersonaService servicePersona;
    public static void main(String[] args) {

		SpringApplication.run(Sir.class, args);
    }

    @Override
	public void run(String... args) throws Exception {
		
      Persona p = servicePersona.buscarPersonaPorMail("carlosfernandezmendez091@gmail.com");
      System.out.println(p.toString());
  
	}
}
