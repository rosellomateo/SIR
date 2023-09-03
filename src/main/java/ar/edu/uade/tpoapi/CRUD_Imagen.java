package ar.edu.uade.tpoapi;

import ar.edu.uade.tpoapi.modelo.Imagen;
import ar.edu.uade.tpoapi.repository.ImagenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@SpringBootApplication
public class CRUD_Imagen implements CommandLineRunner {
    @Autowired
    ImagenRepository imagenRepository;

    public static void main(String[] args) {
        SpringApplication.run(CRUD_Imagen.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        //CRUD Imagen
        //1. Encontrar una imagen determinada
        Optional<Imagen> img = imagenRepository.findByNumero(1);
        System.out.println(img.toString());

        //2. Traer todas las imagenes
        List<Imagen> imgs = imagenRepository.findAll();
        for (Imagen i :
                imgs) {
            System.out.println(i.toString());
        }

        //3- Guardar una img en la BD
        System.out.println();
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese el url de la imagen");
        String url = sc.nextLine();

        System.out.println("Ingrese el tipo de imagen");
        String tipo = sc.nextLine();

        Imagen imagen = imagenRepository.save(new Imagen(url,tipo));
        System.out.println(imagen.toString());

        //4. Borrar una img determinada
        System.out.println();
        System.out.println("Ingrese el nro de " +
                "la imagen que desea borrar ");
        int imgABorrar = sc.nextInt();
        if (imagenRepository.existsById(imgABorrar)){
            imagenRepository.deleteById(imgABorrar);
        }else {
            System.out.println("La imagen que  desea borrar" +
                    "no se encuentra registrada");
        }

        //5. Hace falta update?
        throw new Exception("Hasta aca llegamos master");
    }
}
