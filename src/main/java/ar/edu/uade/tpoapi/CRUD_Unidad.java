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
        //CRUD Unidad
        //1.Encontar una unidad deseada
        Optional<Unidad> unidad = unidadRepository.findById(7);
        System.out.println(unidad.toString());

        //2. Encontar todas las unidades
        List<Unidad> u = unidadRepository.findAll();
        for (Unidad un:u) {
			System.out.println(un.toString());
        }
        //3. Guardar una unidad en la base de datos
        Scanner sc = new Scanner(System.in) ;
        System.out.println("ingrese Piso");
        String piso = sc.nextLine();
        System.out.println("ingrese numero");
        String numero = sc.nextLine();
        List<Edificio> edificios = edificioRepository.findAll(); 
        for (Edificio ed: edificios ){
            System.out.println(ed.toString());
        }
        System.out.println("ingrese numero del codigo del edificio");
        Edificio edificio = edificioRepository.getEdificioByCodigo(sc.nextInt());
        Unidad unidadGuardar = unidadRepository.save(new Unidad(piso,numero,edificio));
        System.out.println(unidadGuardar.toString());
        
        //4. Borrar una unidad de la BD

        
        List<Unidad> uni = unidadRepository.findAll(); 
        for (Unidad un:uni ){
            System.out.println(un.toString());
        }
        System.out.println("ingrese numero del codigo de la unidad que desea eliminar");
        Integer id = sc.nextInt();
        unidadRepository.deleteById(id);
        System.out.println(unidadRepository.existsById(id));

        


        throw new Exception("---------------------------------finalizado con exito---------------------------------");
    }
    
    
    
}
