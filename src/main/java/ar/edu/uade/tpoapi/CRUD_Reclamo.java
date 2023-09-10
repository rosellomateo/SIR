package ar.edu.uade.tpoapi;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ar.edu.uade.tpoapi.modelo.Edificio;
import ar.edu.uade.tpoapi.modelo.Persona;
import ar.edu.uade.tpoapi.modelo.Reclamo;
import ar.edu.uade.tpoapi.modelo.Unidad;
import ar.edu.uade.tpoapi.repository.EdificioRepository;
import ar.edu.uade.tpoapi.repository.PersonaRepository;
import ar.edu.uade.tpoapi.repository.ReclamoRepository;
import ar.edu.uade.tpoapi.repository.UnidadRepository;

@SpringBootApplication
public class CRUD_Reclamo implements CommandLineRunner{
    @Autowired
    ReclamoRepository reclamoRepository;
    @Autowired
    EdificioRepository edificioRepository;
    @Autowired
    PersonaRepository personaRepository;
    @Autowired
    UnidadRepository unidadRepository;

    public static void main(String[] args) {
        SpringApplication.run(CRUD_Reclamo.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        // 1.  Traer todos los reclamos
        List<Reclamo> reclamos = reclamoRepository.findAll();

        for (Reclamo reclamo : reclamos) {
            System.out.println(reclamo.toString());
        }

        // 2.  Traer reclamo por numero

        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese el numero del reclamo");
        Optional<Reclamo> reclamo = reclamoRepository.getReclamoByNumero(sc.nextInt());
        System.out.println(reclamo.toString());

        // 3.  Guardar reclamo en la BD
        Persona usuario = personaRepository.getByDocumento("DNI29988738");
        List<Edificio> edificios = edificioRepository.findAll();
        System.out.println("Listando edificios");
        for (Edificio edificio : edificios) {
            System.out.println(edificio.toString());
        }

        Edificio edificio = edificioRepository.getEdificioByCodigo(1);

        String ubicacion = "Esquina inferior derecha de la cocina";
        String descripcion = "Pared manchada por perdida de agua";

        Unidad unidad = unidadRepository.getById(5);

        Reclamo nuevoReclamo = new Reclamo(usuario, edificio, ubicacion, descripcion, unidad);
        Reclamo reclamoGuardado = reclamoRepository.saveAndFlush(nuevoReclamo);
        System.out.println("Reclamo guardado");
        System.out.println(reclamoGuardado.toString());
        System.out.println("Presione enter para continuar.");
        sc.nextLine();

        //4. Obteniendo todos los reclamos de un edificio
        System.out.println("");
        System.out.println("Recuperando todos los reclamos de un edificio");
        List<Reclamo> reclamosXEdificio = reclamoRepository.findByEdificioCodigo(1);
            for (Reclamo r :
                    reclamosXEdificio) {
                System.out.println(r.toString());
        }
        System.out.println("Presione enter para continuar. La proxima operacion " +
                "es recuperar todos los reclamos de una persona");
        sc.nextLine();

        //5. Obteniendo los reclamos de una persona
        /* System.out.println("");
        System.out.println("Recuperando todos los reclamos de una persona");
        List<Reclamo> reclamosXPersona = reclamoRepository.findAllByDocumento("DNI43900195");
        for (Reclamo r :
                reclamosXPersona) {
            System.out.println(r.toString());
        }
        System.out.println("Presione enter para continuar. La proxima operacion " +
                "es recuperar todos los reclamos de una unidad");
        sc.nextLine(); */

        //6. Obteniendo todos los reclamos de una unidad
        /* System.out.println("");
        System.out.println("Recuperando todos los reclamos de una unidad");
        List<Reclamo> reclamosXUnidad = reclamoRepository.findAllByIdentificador(1);
        for (Reclamo r :
                reclamosXUnidad) {
            System.out.println(r.toString());
        }
        System.out.println("Esa fue la ultima opreacion. Presione enter para " +
                "lanzar una excepcion y detener la ejecucion");
        sc.nextLine(); */

        throw new Exception("_____________ Finalizado con exito _____________");
    }
    
}
