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
        //CRUD Persona
        //1.Encontrar una persona determinada
        Optional<Persona> persona = personaRepository.findByDocumento("DNI29988738");
        System.out.println(persona.toString());

        //2. Encontrar todas las personas
        List<Persona> p = personaRepository.findAll();
        for (Persona per:
                p) {
            System.out.println(per.toString());
        }

        //3. Guardar personas en la BD

        Scanner sc = new Scanner(System.in);
        System.out.println();
        System.out.println("Ingrese el doc");
        String doc = sc.nextLine();

        System.out.println("ingrese el nombre");
        String nombre = sc.nextLine();

        personaRepository.save(new Persona(doc,nombre,null,null));

        //4. Borrar una persona por id, en este caso documento
        System.out.println();
        System.out.println("Ingrese el documento de " +
                "la persona que quiera borrar");
        String docABorrar = sc.nextLine();

        if(personaRepository.existsByDocumento(docABorrar)){
            personaRepository.deleteByDocumento(docABorrar);
        }

        //5. Actualizar alguna persona
        System.out.println("Ingrese el documento de la persona que desea modificar");

        String docABuscar = sc.nextLine();

        System.out.println("Ingrese el nuevo nombre");
        String nuevoNom = sc.nextLine();

        System.out.println("Ingrese el nuevo mail");
        String nuevoMail = sc.nextLine();

        System.out.println("Ingrese la nueva contraseña");
        String nuevaPass = sc.nextLine();

        if(personaRepository.existsByDocumento(docABuscar)){
            personaRepository.save(new Persona(docABuscar,nuevoNom,nuevoMail,nuevaPass));
        }else {
            System.out.println("La persona con el " +
                    "documento que ingreso no se encuentra" +
                    "registrada");
        }

        throw new Exception("Fin clase");
    }   
}
        