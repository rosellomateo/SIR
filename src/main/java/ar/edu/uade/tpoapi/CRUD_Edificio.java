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
import ar.edu.uade.tpoapi.modelo.Edificio;
@SpringBootApplication
public class CRUD_Edificio implements CommandLineRunner{

	@Autowired
	EdificioRepository edificioRepository;

    public static void main(String[] args) {

		SpringApplication.run(CRUD_Edificio.class, args);
    }

    @Override
	public void run(String... args) throws Exception {
		//CRUD Edificio
		//1.Encontrar un edificio determinado == Controlador.BuscarEdificio
		Scanner sc = new Scanner(System.in);
		System.out.println("Test unitario para la persistencia " +
				"de la clase Edificio");
		System.out.println("");
		System.out.println("Buscando un edificio en particular, " +
				"en este caso el edificio con codigo = 1 ");
		Optional<Edificio> edificio = edificioRepository.findByCodigo(1);
		System.out.println(edificio.toString());
		System.out.println("");
		System.out.println("Presione enter para continuar." +
				" La siguiente operacion es recuperar todos los edificios");
		sc.nextLine();

		//2. Encontrar todos los edificios == Controlador.getEdificios
		System.out.println("Recuperando todos los edificios");
		List<Edificio> e = edificioRepository.findAll();
		for (Edificio ed:
			 e) {
			System.out.println(ed.toString());
		}
		System.out.println("Presione enter para continuar." +
				" La siguiente operacion es guardar un nuevo edificio");
		sc.nextLine();

		//3. Guardar edificio en la BD
		System.out.println("Guardando un edificio nuevo, " +
				"en este caso el id va a ser un autonumerico" +
				" el nombre va ser Edificio Demo y" +
				" la direccion sera Lima123");
		Edificio ed  = edificioRepository.saveAndFlush(new Edificio("edificio demo","Lima 123"));
		System.out.println(ed.toString());
		System.out.println("Presione enter para continuar. " +
				"La proxima operacion sera borrar un edificio");
		sc.nextLine();

		//4. Borrar por un edificio por ID
		System.out.println("Borrando un edificio a eleccion por ID");
		System.out.println("");
		System.out.println("Ingrese el codigo del edificio a borrar");
		int codBorrar = sc.nextInt();
		if(edificioRepository.existsById(codBorrar)){
			edificioRepository.deleteById(codBorrar);

		}else {
			System.out.println("No hay edificio con ese codigo");
		}
		System.out.println("Presione enter para continuar. " +
				"La proxima operacion es actualizar un edificio");
		sc.nextLine();

		//5.actualizar algun edificio por id
		System.out.println("Actualizando algun edificio por un ID especifico");
		System.out.println("");
		System.out.println("Ingrese el cod del edificio del cual desea actualizar info." +
				" En este caso el nombre del edificio se remplazara por nombre actualizado " +
				"y la direccion por direccion actualizada");
		int codActualizacion = sc.nextInt();
		if(edificioRepository.existsById(codActualizacion)){
			edificioRepository.saveAndFlush(new Edificio(codActualizacion,"nombre actualizado","direccion actualizada"));

		}
		sc.nextLine();
		System.out.println("Esta fue la ultima operacion." +
				" Presione enter para lanzar una excepcion " +
				"que finalizara el programa ");
		sc.nextLine();
		throw new Exception("___________________ FIN CRUD EDIFICIO ___________________");

	}
}
