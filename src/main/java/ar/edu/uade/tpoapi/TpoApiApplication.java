package ar.edu.uade.tpoapi;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ar.edu.uade.tpoapi.dao.EdificioRepository;
import ar.edu.uade.tpoapi.dao.ImagenRepository;
import ar.edu.uade.tpoapi.dao.PersonaRepository;
import ar.edu.uade.tpoapi.dao.ReclamoRepository;
import ar.edu.uade.tpoapi.dao.UnidadRepository;
import ar.edu.uade.tpoapi.modelo.Edificio;
import ar.edu.uade.tpoapi.modelo.Imagen;
import ar.edu.uade.tpoapi.modelo.Persona;
import ar.edu.uade.tpoapi.modelo.Reclamo;
import ar.edu.uade.tpoapi.modelo.Unidad;

@SpringBootApplication
public class TpoApiApplication implements CommandLineRunner{

	@Autowired
	ImagenRepository imagenRepository;

	@Autowired
	EdificioRepository edificioRepository;

	@Autowired
	UnidadRepository unidadRepository;

	@Autowired
	PersonaRepository personaRepository;

	@Autowired
	ReclamoRepository reclamoRepository;	

    public static void main(String[] args) {

		SpringApplication.run(TpoApiApplication.class, args);
    }

    @Override
	public void run(String... args) throws Exception {
		
		System.out.println("Hola mundo");
		List<Imagen> imagenes = imagenRepository.findAll();

		for(Imagen i : imagenes)
			System.out.println(i.toString());

		System.out.println("Edificios:");

		List<Edificio> edificios = edificioRepository.findAll();

		for(Edificio e : edificios)
			System.out.println(e.toString());

		System.out.println("Unidades:");

		List<Unidad> unidades = unidadRepository.findAll();

		for(Unidad u : unidades)
			System.out.println(u.toString());

		System.out.println("Personas:");

		List<Persona> personas = personaRepository.findAll();

		for(Persona p : personas)
			System.out.println(p.toString());

		System.out.println("Reclamos:");

		List<Reclamo> reclamos = reclamoRepository.findAll();

		for(Reclamo r : reclamos)
			System.out.println(r.toString());
	}
}
