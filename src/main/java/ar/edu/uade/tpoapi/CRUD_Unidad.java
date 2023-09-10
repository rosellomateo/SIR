package ar.edu.uade.tpoapi;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ar.edu.uade.tpoapi.modelo.Edificio;
import ar.edu.uade.tpoapi.modelo.Unidad;
import ar.edu.uade.tpoapi.repository.EdificioRepository;
import ar.edu.uade.tpoapi.repository.UnidadRepository;

@SpringBootApplication
public class CRUD_Unidad implements CommandLineRunner {
    @Autowired
    UnidadRepository unidadRepository;
    @Autowired
    EdificioRepository edificioRepository;
    public static void main(String[] args) {
        SpringApplication.run(CRUD_Unidad.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
        
        Scanner sc = new Scanner(System.in) ;
        //1. Encontrar una unidad especifica
        System.out.println("Encontrar una unidad deseada para el id 7");
        Optional<Unidad> unidad = unidadRepository.findById(7);
        System.out.println(unidad.toString());
        System.out.println("Ingrese enter para continuar");
        sc.nextLine();

        //2. Encontar todas las unidades
        System.out.println("Encontrar todas las unidades");
        List<Unidad> u = unidadRepository.findAll();
        for (Unidad un:u) {
			System.out.println(un.toString());
        }

        //3. Guardar una unidad en la base de datos
        System.out.println("guardado unidad para el piso 7 numero 72 y edificio 1");
        Optional<Edificio> edificio = edificioRepository.findById(1);
        if(edificio.isPresent())
        {
            Unidad unidadGuardar = unidadRepository.save(new Unidad("7","72",edificio.get()));
            System.out.println(unidadGuardar.toString());
            System.out.println("Ingrese enter para continuar");
            sc.nextLine();
            System.out.println("modificando la unidad guardada");
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

        //Transferir una unidad
        System.out.println("Transferir una unidad");
        

        throw new Exception("---------------------------------finalizado con exito---------------------------------");
    }
    
    
    
}
