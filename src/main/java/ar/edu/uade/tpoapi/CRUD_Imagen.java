// package ar.edu.uade.tpoapi;

// import ar.edu.uade.tpoapi.modelo.Imagen;
// import ar.edu.uade.tpoapi.repository.ImagenRepository;
// import jakarta.transaction.Transactional;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.SpringBootApplication;

// import java.util.List;
// import java.util.Optional;
// import java.util.Scanner;

// @SpringBootApplication
// public class CRUD_Imagen implements CommandLineRunner {
//     @Autowired
//     ImagenRepository imagenRepository;

//     /* public static void main(String[] args) {
//         SpringApplication.run(CRUD_Imagen.class, args);
//     } */

//     @Override
//     @Transactional
//     public void run(String... args) throws Exception {
//         //CRUD Imagen
//         //1. Encontrar una imagen determinada
//         Scanner sc = new Scanner(System.in);
//         System.out.println("Recuperando una imagen particular por id");
//         Optional<Imagen> img = imagenRepository.findByNumero(1);
//         System.out.println(img.toString());
//         System.out.println("Presione enter para continuar. La proxima operacion" +
//                 " es traer todas las imagenes");
//         sc.nextLine();

//         //2. Traer todas las imagenes
//         System.out.println("Recuperando todas las imagenes de la BD");
//         List<Imagen> imgs = imagenRepository.findAll();
//         for (Imagen i :
//                 imgs) {
//             System.out.println(i.toString());
//         }
//         System.out.println("Presione enter para continuar. La proxima operacion " +
//                 "es guardar una imagen");
//         sc.nextLine();

//         //3- Guardar una img en la BD == Controlador.
//         System.out.println();
//         System.out.println("Guardando una imagen, el tipo sera JPG y el link llevara a una imagen" +
//                 " de varios lenguajes de programacion");

//         String url = "https://www.zdnet.com/a/img/resize/a0dcec40a8cd8d2e1b3a9e12a" +
//                 "05c2819622d20be/2021/07/19/8a337c80-5ed6-43" +
//                 "a1-98fb-b981d420890f/programming-languages-shutterstock-1680857539.jpg?auto=webp&" +
//                 "fit=crop&height=1200&width=1200";

//         String tipo = "JPG";

//         Imagen imagen = imagenRepository.saveAndFlush(new Imagen(url,tipo));
//         System.out.println(imagen.toString());
//         System.out.println("Presione enter para continuar. La proxima operacion es" +
//                 " borrar una imagen en especifico por su id");
//         sc.nextLine();

//         //4. Borrar una img determinada
//         System.out.println();
//         System.out.println("Borrando una imagen de la BD por id, en este caso el id es 1");

//         if (imagenRepository.existsById(1)){
//             imagenRepository.deleteById(1);
//         }else {
//             System.out.println("La imagen que  desea borrar" +
//                     "no se encuentra registrada");
//         }
//         System.out.println("Esta fue la ultima operacion. Presione enter para" +
//                 " lanzar una excepcion que finalice el programa");
//         sc.nextLine();

//         throw new Exception("___________________ FIN CRUD IMAGEN ___________________");
//     }
// }
