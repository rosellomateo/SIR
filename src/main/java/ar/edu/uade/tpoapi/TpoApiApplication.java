package ar.edu.uade.tpoapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import ar.edu.uade.tpoapi.views.EdificioView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ar.edu.uade.tpoapi.repository.EdificioRepository;
import ar.edu.uade.tpoapi.repository.ImagenRepository;
import ar.edu.uade.tpoapi.repository.PersonaRepository;
import ar.edu.uade.tpoapi.repository.ReclamoRepository;
import ar.edu.uade.tpoapi.repository.UnidadRepository;
import ar.edu.uade.tpoapi.modelo.Edificio;
import ar.edu.uade.tpoapi.modelo.Imagen;
import ar.edu.uade.tpoapi.modelo.Persona;
import ar.edu.uade.tpoapi.modelo.Reclamo;
import ar.edu.uade.tpoapi.modelo.Unidad;
import ar.edu.uade.tpoapi.controlador.ControladorEdificio;
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
		//CRUD Edificio
		//1.Encontrar un edificio determinado
		Optional<Edificio> edificio = edificioRepository.findByCodigo(1);
		System.out.println(edificio.toString());

		//2. Encontrar todos los edificios
		List<Edificio> e = edificioRepository.findAll();
		for (Edificio ed:
			 e) {
			System.out.println(ed.toString());
		}

		//3. Guardar edificio en la BD
		Scanner sc = new Scanner(System.in);
		System.out.println("Ingrese el nombre");
		String nomb = sc.nextLine();
		System.out.println("Ingrese la direc");
		String direc = sc.nextLine();
		System.out.println("listo");
		Edificio ed  = edificioRepository.save(new Edificio(nomb,direc));
		System.out.println(ed.toString());

		//4. Borrar por un edificio por ID
		System.out.println("Ingrese el codigo del edificio a borrar");
		int codBorrar = sc.nextInt();
		if(edificioRepository.existsById(codBorrar)){
			edificioRepository.deleteById(codBorrar);

		}else {
			System.out.println("No hay edificio con ese codigo");
		}

		//5.actualizar algun edificio por id
		System.out.println("Ingrese el cod del edificio del cual desea actualizar info");
		int codActualizacion = sc.nextInt();
		String nombNuevo = sc.nextLine();
		String direcNueva = sc.nextLine();
		if(edificioRepository.existsById(codActualizacion)){
			edificioRepository.save(new Edificio(codActualizacion,nombNuevo,direcNueva));

		}


	}
}
