package ar.edu.uade.tpoapi;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ar.edu.uade.tpoapi.modelo.Edificio;
import ar.edu.uade.tpoapi.modelo.Persona;
import ar.edu.uade.tpoapi.modelo.Unidad;
import ar.edu.uade.tpoapi.repository.EdificioRepository;
import ar.edu.uade.tpoapi.repository.PersonaRepository;
import ar.edu.uade.tpoapi.repository.UnidadRepository;

@SpringBootApplication
public class CRUD_Unidad implements CommandLineRunner {
    @Autowired
    UnidadRepository unidadRepository;
    @Autowired
    EdificioRepository edificioRepository;
    @Autowired
    PersonaRepository personaRepository;
    public static void main(String[] args) {
        SpringApplication.run(CRUD_Unidad.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
        
        Scanner sc = new Scanner(System.in) ;
        //1. Encontrar una unidad especifica
        System.out.println("Encontrar una unidad deseada para el id 7");
        Optional<Unidad> unidad = unidadRepository.findById(7);
        System.out.println(unidad.get().toString());
        System.out.println("Ingrese enter para continuar");
        sc.nextLine();

        //2. Encontar todas las unidades
        System.out.println("Encontrar todas las unidades");
        List<Unidad> u = unidadRepository.findAll();
        for (Unidad un:u) {
			System.out.println(un.toString());
        }
        System.out.println("Ingrese enter para continuar");
        sc.nextLine();

        //3. Guardar una unidad en la base de datos
        System.out.println("guardado unidad para el piso 7 numero 72 y edificio 1");
        Optional<Edificio> edificio = edificioRepository.findById(1);
        if(edificio.isPresent())
        {
            Unidad unidadGuardar = unidadRepository.save(new Unidad("7","72",edificio.get()));
            System.out.println(unidadGuardar.toString());
            System.out.println("Ingrese enter para continuar");
            sc.nextLine();
            System.out.println("modificando la unidad guardada anteriormente para el piso 8 numero 82");
            Unidad unidadModificada = new Unidad(unidadGuardar.getId(),"8","82",edificio.get());
            unidadModificada = unidadRepository.save(unidadModificada);
            System.out.println(unidadModificada.toString());
            System.out.println("Ingrese enter para continuar");
            sc.nextLine();
            System.out.println("eliminando la ultima unidad guardada");
            unidadRepository.delete(unidadModificada);
            System.out.println("Ingrese enter para continuar");
            sc.nextLine();
        }

        System.out.println("Agregando un dueño con documento DNI30161468 a la unidad con id 1");
        Optional<Persona> personaDueño = personaRepository.findById("DNI30161468");
        if(personaDueño.isPresent())
        {
            Unidad unidadDueño = unidadRepository.findById(1).get();
            unidadDueño.agregarDuenio(personaDueño.get());
            unidadRepository.save(unidadDueño);
            System.out.println("Ingrese enter para continuar");
            sc.nextLine();
        }

        System.out.println("Transferir una unidad");
        System.out.println("se va a transferir la unidad con id 1 al documento DNI22333444");
        Optional<Persona> persona = personaRepository.findById("DNI22333444");
        if(persona.isPresent())
        {
            Unidad unidadTransferir = unidadRepository.findById(1).get();
            unidadTransferir.transferir(persona.get());
            unidadRepository.save(unidadTransferir);
            System.out.println("Ingrese enter para continuar");
            sc.nextLine();
        }

        System.out.println("liberando la unidad con id 1");
        Unidad unidadLiberar = unidadRepository.findById(1).get();
        unidadLiberar.liberar();
        unidadRepository.save(unidadLiberar);
        System.out.println("Ingrese enter para continuar");
        sc.nextLine();


        System.out.println("Aquilando con documento DNI30600888 a la unidad con id 1");
        Optional<Persona> personaAlquiler = personaRepository.findById("DNI30600888");
        if(personaAlquiler.isPresent())
        {
            Unidad unidadAlquiler = unidadRepository.findById(1).get();
            unidadAlquiler.alquilar(personaAlquiler.get());
            unidadRepository.save(unidadAlquiler);
            System.out.println("Ingrese enter para continuar");
            sc.nextLine();
        }
        //Alquilar unidad
        System.out.println("Agregando un inquilino con documento DNI30161468 a la unidad con id 1");
        Optional<Persona> personaInquilino = personaRepository.findById("DNI30161468");
        if(personaInquilino.isPresent())
        {
            Unidad unidadInquilino = unidadRepository.findById(1).get();
            unidadInquilino.agregarInquilino(personaInquilino.get());
            unidadRepository.save(unidadInquilino);
            System.out.println("Ingrese enter para continuar");
            sc.nextLine();
        }

        System.out.println("liberando la unidad con id 1");
        unidadLiberar.liberar();
        unidadRepository.save(unidadLiberar);
        System.out.println("Ingrese enter para continuar");
        sc.nextLine();

        System.out.println("habitando la unidad con id 1");
        Unidad unidadHabitar = unidadRepository.findById(1).get();
        unidadHabitar.habitar();
        unidadRepository.save(unidadHabitar);
        System.out.println("Ingrese enter para continuar");
        sc.nextLine();

        System.out.println("Imprimir todos los duenios de la unidad con id 2");
        Unidad unidadDuenios = unidadRepository.findById(2).get();
        List<Persona> duenios = unidadDuenios.getDuenios();
        for (Persona duenio:duenios) {
            System.out.println(duenio.toString());
        }
        System.out.println("Ingrese enter para continuar");
        sc.nextLine();

        System.out.println("Imprimir todos los inquilinos de la unidad con id 3");
        Unidad unidadInquilinos = unidadRepository.findById(3).get();
        List<Persona> inquilinos = unidadInquilinos.getInquilinos();
        for (Persona inquilino:inquilinos) {
            System.out.println(inquilino.toString());
        }
        System.out.println("Ingrese enter para continuar");
        sc.nextLine();

        System.out.println("Terminando la ejecucion del programa presione enter para finalizar");
        sc.nextLine();
        sc.close();

        throw new Exception("---------------------------------finalizado con exito---------------------------------");
    }
    
    
    
}
