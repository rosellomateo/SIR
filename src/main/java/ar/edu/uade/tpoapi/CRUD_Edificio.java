package ar.edu.uade.tpoapi;

import java.util.*;

import ar.edu.uade.tpoapi.modelo.Persona;
import ar.edu.uade.tpoapi.modelo.Unidad;
import ar.edu.uade.tpoapi.repository.UnidadRepository;
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
	@Autowired
	UnidadRepository unidadRepository;

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
		System.out.println("Ingrese el codigo del edificio del cual desea actualizar info.\n" +
				"En este caso el nombre del edificio se remplazara por nombre actualizado \n" +
				"y la direccion por direccion actualizada");
		int codActualizacion = sc.nextInt();
		if(edificioRepository.existsById(codActualizacion)){
			edificioRepository.saveAndFlush(new Edificio(codActualizacion,"nombre actualizado","direccion actualizada"));

		}
		System.out.println("Presione enter para continuar. " +
				"La proxima operacion es recuperar todas las " +
				"unidades que haya en el edificio");
		sc.nextLine();

		//6. recuperar todas las unidades en un edificio == Controlador.getUnidadesPorEdificio
		System.out.println("Recuperando todas las unidades de un edificio");
		System.out.println("");
		System.out.println("Lo que se muestra a continuacion por cada unidad es \n" +
				"el piso de la unidad, el nro de la unidad y si esta habitado o no");
		Edificio edificioVerUnidades = edificioRepository.getEdificioByCodigo(1);
		List<Unidad> unidadesXEdificio = edificioVerUnidades.getUnidades();
		for (Unidad u :
				unidadesXEdificio) {
			System.out.println(u.toString());
		}

		System.out.println("Presione enter para continuar. " +
				"La proxima operacion es recuperar todas las " +
				"unidades habilitadas de un edificio");
		sc.nextLine();

		//7. recuperar todas las unidades habilitadas de un edificio == Controlador.habilitadosPorEdificio
		System.out.println("Recuperando todas las unidades habilitadas por edificio");
		Edificio edificioVerHabitados = edificioRepository.getEdificioByCodigo(1);
		unidadesXEdificio = edificioVerHabitados.getUnidades();
		for (Unidad u :
				unidadesXEdificio) {
			if (u.estaHabitado()) {
				System.out.println(u.toString());
			}
			}
		System.out.println("Presione enter para continuar. " +
				"La proxima operacion es recuperar todos los dueños en un edificio");
		sc.nextLine();

		//8. recuperar todos los dueños en un edificio == Controlador.dueñosPorEdificio
		System.out.println("Recuperando todos los dueños en un edificio");
		Edificio edificioVerDuenios = edificioRepository.getEdificioByCodigo(1);
		Set<Persona> duenios = edificioVerDuenios.duenios();
		for (Persona p :
				duenios) {
			System.out.println(p.toString());
		}
		System.out.println("Presione enter para continuar. " +
				"La proxima operacion es recuperar los inquilinos de un edificio");

		//9. recuperar todos los inquilinos de un edificio == Controlador.habitantesPorEdificio
		System.out.println("");
		System.out.println("Recuperando todos los inquilinos de un edificio");
		Edificio edificioVerInquilinos = edificioRepository.getEdificioByCodigo(1);
		Set<Persona> inquilinos = edificioVerDuenios.habitantes();
		for (Persona p :
				inquilinos) {
			System.out.println(p.toString());
		}
		sc.nextLine();
		System.out.println("Esta fue la ultima operacion." +
				" Presione enter para lanzar una excepcion " +
				"que finalizara el programa ");
		sc.nextLine();
		throw new Exception("___________________ FIN CRUD EDIFICIO ___________________");

	}
}
