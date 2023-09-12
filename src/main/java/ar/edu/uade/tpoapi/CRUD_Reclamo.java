package ar.edu.uade.tpoapi;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import ar.edu.uade.tpoapi.modelo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ar.edu.uade.tpoapi.repository.EdificioRepository;
import ar.edu.uade.tpoapi.repository.PersonaRepository;
import ar.edu.uade.tpoapi.repository.ReclamoRepository;
import ar.edu.uade.tpoapi.repository.UnidadRepository;
import ar.edu.uade.tpoapi.views.Estado;

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
        System.out.println("Traemos el reclamo con numero 2");
        Optional<Reclamo> reclamo = reclamoRepository.getReclamoByNumero(2);
        System.out.println(reclamo.toString());

        // 3.  Guardar reclamo en la BD
        Persona usuario = personaRepository.getByDocumento("DNI29988738");
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
        System.out.println("");
        System.out.println("Recuperando todos los reclamos de una persona con documento DNI29988738");
        List<Reclamo> reclamosXPersona = reclamoRepository.findByUsuarioDocumento("DNI29988738");
        for (Reclamo r :
                reclamosXPersona) {
            System.out.println(r.toString());
        }
        System.out.println("Presione enter para continuar. La proxima operacion " +
                "es recuperar todos los reclamos de una unidad");
        sc.nextLine(); 

        //6. Obteniendo todos los reclamos de una unidad
        System.out.println("");
        System.out.println("Recuperando todos los reclamos de una unidad identificador 5");
        List<Reclamo> reclamosXUnidad = reclamoRepository.findByUnidadId(5);
        for (Reclamo r :
                reclamosXUnidad) {
            System.out.println(r.toString());
        }
        System.out.println("Presione enter para continuar. La proxima operacion es " +
                "agregar una imagen al reclamo 2");
        sc.nextLine();

        //7 agregar una imagen al reclamo 2
        System.out.println("Agregando dos imagenes al reclamo ");
        Reclamo reclamo2 = reclamoRepository.getReclamoByNumero(2).get();
        reclamo2.agregarImagen("imagen1.jpg", "jpg");
        reclamo2.agregarImagen("imagen2.jpg", "png");
        reclamoRepository.saveAndFlush(reclamo2);
        System.out.println("Presione enter para continuar. La proxima operacion es " +
                "modificar el estado del reclamo numero 2 a terminado");
        sc.nextLine();

        //8 cambiar el estado del reclamo 2 a finalizado
        reclamo2.cambiarEstado(Estado.terminado);
        reclamoRepository.saveAndFlush(reclamo2);
        System.out.println("Presione enter para continuar. La proxima operacion es " +
                "borrar el reclamo recien agregado");
        sc.nextLine();

        //9 borrar el ultimo reclamo agregado
        reclamoRepository.delete(reclamoGuardado);
        reclamoRepository.flush();
        System.out.println("Reclamo borrado");
        System.out.println("Esa fue la ultima opreacion. Presione enter para " +
                "lanzar una excepcion y detener la ejecucion");
        sc.nextLine();

        throw new Exception("_____________ Finalizado con exito _____________");
    }
    
}
