package ar.edu.uade.tpoapi;

import ar.edu.uade.tpoapi.repository.UnidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ar.edu.uade.tpoapi.repository.EdificioRepository;

@SpringBootApplication
public class CRUD_Edificio implements CommandLineRunner{

	@Autowired
	EdificioRepository edificioRepository;
	@Autowired
	UnidadRepository unidadRepository;

    public static void main(String[] args) {

		SpringApplication.run(CRUD_Edificio.class, args);
    }

    @Override
	public void run(String... args) throws Exception {
		

	}
}
