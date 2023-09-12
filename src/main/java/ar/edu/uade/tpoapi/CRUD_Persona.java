package ar.edu.uade.tpoapi;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import ar.edu.uade.tpoapi.modelo.Persona;
import ar.edu.uade.tpoapi.repository.PersonaRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CRUD_Persona implements CommandLineRunner {
    @Autowired
    PersonaRepository personaRepository;
    public static void main(String[] args) {

        SpringApplication.run(CRUD_Persona.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("___________________ INICIO CRUD PERSONA ___________________");
        //1.Encontrar una persona determinada == Controlador.BuscarPersona
        System.out.println("Buscando el registro con documento DNI29988738 y mostrando los datos de la persona");
        Optional<Persona> persona = personaRepository.findByDocumento("DNI29988738");
        System.out.println(persona.toString());
        System.out.println("Presione enter para seguir");
        sc.nextLine();

        //2. Encontrar todas las personas
        System.out.println("Recuperando todas las personas");
        List<Persona> personas = personaRepository.findAll();
        for (Persona p: personas)
        {
            System.out.println(p.toString());
        }
        System.out.println("Presione enter para seguir");
        sc.nextLine();

        //3. Guardar personas en la BD == Controlador.AgregarPersona
        System.out.println("Guardado de persona");
        System.out.println("el campo documento seria: DNI43900195, el nombre va a ser Sebastian, el mail y la contraseña seran null");
        personaRepository.saveAndFlush(new Persona("DNI43900195","Sebastian Bernasconi",null,null));
        System.out.println("Guardado!");
        System.out.println("Presione enter para seguir");
        sc.nextLine();

        //4. Actualizar alguna persona mas registro de usuario
        System.out.println("Actualizando los datos de una persona " +
                " y registro del usuario con mail y password ");
        System.out.println("El nombre se mantendra igual" +
                " y lo que se va a modificar es el mail y la contraseña");
        if(personaRepository.existsByDocumento("DNI43900195")){
            Optional<Persona> personaARegistrar = personaRepository.findByDocumento("DNI43900195");
            if(personaARegistrar.isPresent())
                if(personaARegistrar.get().getMail() == null && personaARegistrar.get().getPassword() == null)
                {
                    Persona personaAGuardar = personaARegistrar.get();
                    personaRepository.saveAndFlush(new Persona("DNI43900195","Sebastian",
                    "seba@uade.edu.ar","Contraseña"));
                }
            System.out.println("Actualizado!");
        }else {
            System.out.println("La persona con el " +
                    "documento que ingreso no se encuentra" +
                    "registrada");
        }
        System.out.println("Presione enter para seguir");
        sc.nextLine();


        //5. Modificado de contraseña
        persona = personaRepository.findByDocumento("DNI43900195");
        if (persona.isPresent()) {
            Persona personaContraseñaACambiar = persona.get();
            if(personaContraseñaACambiar.getPassword() == "Contraseña"){
                personaRepository.saveAndFlush(new Persona(personaContraseñaACambiar.getDocumento(),
                        personaContraseñaACambiar.getNombre(), personaContraseñaACambiar.getMail(), "aproved?"));
            }
        }
        System.out.println("Presione enter para seguir");
        sc.nextLine();
        //6. Borrar una persona por id, en este caso documento == Controlador.EliminarPersona
        System.out.println("Borrado de una persona especifica");

        if(personaRepository.existsByDocumento("DNI43900195")){
            personaRepository.deleteByDocumento("DNI43900195");
            System.out.println("Borrado!");
        }

        throw new Exception("___________________ FIN CRUD PERSONA ___________________");
    }
} 