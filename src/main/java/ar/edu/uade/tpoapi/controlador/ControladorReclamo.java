package ar.edu.uade.tpoapi.controlador;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.uade.tpoapi.controlador.request.Reclamo.ImagenReclamoDTO;
import ar.edu.uade.tpoapi.controlador.request.Reclamo.ReclamoDTO;
import ar.edu.uade.tpoapi.controlador.request.Reclamo.UnidadDTO;
import ar.edu.uade.tpoapi.exceptions.EdificioException;
import ar.edu.uade.tpoapi.exceptions.PersonaException;
import ar.edu.uade.tpoapi.exceptions.ReclamoException;
import ar.edu.uade.tpoapi.exceptions.UnidadException;
import ar.edu.uade.tpoapi.modelo.Edificio;
import ar.edu.uade.tpoapi.modelo.Persona;
import ar.edu.uade.tpoapi.modelo.Reclamo;
import ar.edu.uade.tpoapi.modelo.Unidad;
import ar.edu.uade.tpoapi.modelo.Enumerations.Estado;
import ar.edu.uade.tpoapi.services.ReclamoService;
import ar.edu.uade.tpoapi.views.ReclamoView;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/Reclamo")
public class ControladorReclamo 
{

    @Autowired
    ReclamoService reclamoService;

    private static ControladorReclamo instancia;
    private static final ControladorEdificio controladorEdificio = ControladorEdificio.getInstancia();
    private static final ControladorUnidad controlerUnidad = ControladorUnidad.getInstancia();
    private static final ControladorPersona controlerPersona = ControladorPersona.getInstancia();

    public static ControladorReclamo getInstancia() {
        if(instancia == null)
            instancia = new ControladorReclamo();
        return instancia;
    }
    
    @GetMapping("/Edificio")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados')or hasRole('SuperAdmin') or hasRole('Residente')or hasRole('Encargado') ")
    public ResponseEntity<?> reclamosPorEdificio(@RequestBody int codigo){
        List<ReclamoView> resultado = new ArrayList<ReclamoView>();
        if(reclamoService.reclamosPorEdificio(codigo).isEmpty()){
            return ResponseEntity.badRequest().body("No existen reclamos para ese edificio");
        }else{
            resultado = reclamoService.reclamosPorEdificio(codigo);
            return ResponseEntity.ok(resultado);
        }

    }

    @GetMapping("/Unidad")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados') or hasRole('SuperAdmin') or hasRole('Residente')or hasRole('Encargado')")
    public ResponseEntity<?> reclamosPorUnidad(@Valid @RequestBody UnidadDTO unidadDTO){
        List<ReclamoView> resultado = new ArrayList<ReclamoView>();
        if(reclamoService.reclamosPorUnidad(unidadDTO).isEmpty()){
            return ResponseEntity.badRequest().body("No existen reclamos para esa unidad");
        }else{
            resultado = reclamoService.reclamosPorUnidad(unidadDTO);
            return ResponseEntity.ok(resultado);
        }        
    }

    @GetMapping("/Numero")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados') or hasRole('SuperAdmin') or hasRole('Residente')or hasRole('Encargado')")
    public ResponseEntity<?> reclamosPorNumero(@RequestBody int numero)  throws ReclamoException {
        ReclamoView resultado = buscarReclamo(numero).toView();
        if (resultado == null) {
            return ResponseEntity.badRequest().body("No existe reclamoDTO con ese numero");
        } else {
            return ResponseEntity.ok(resultado);
        }
    }

    @GetMapping("/Persona")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados') or hasRole('SuperAdmin') or hasRole('Residente')or hasRole('Encargado')")
    public ResponseEntity<?> reclamosPorPersona(@RequestBody String documento) {
        List<ReclamoView> resultado = new ArrayList<ReclamoView>();
        if(reclamoService.reclamosPorPersona(documento).isEmpty()){
            return ResponseEntity.badRequest().body("No existen reclamos para esa persona");   
        }
        else{
            resultado = reclamoService.reclamosPorPersona(documento);
            return ResponseEntity.ok(resultado);
        }
    }


    @PutMapping("/Agregar")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados') or hasRole('SuperAdmin') or hasRole('Residente')or hasRole('Encargado')")
    public ResponseEntity<?> agregarReclamo(ReclamoDTO reclamoDTO) throws EdificioException, UnidadException, PersonaException {
        Edificio edificio = controladorEdificio.buscarEdificio(reclamoDTO.getCodigo());
        Unidad unidad = controlerUnidad.buscarUnidad(reclamoDTO.getCodigo(), reclamoDTO.getPiso(),reclamoDTO.getNumero());
        Persona persona = controlerPersona.buscarPersona(reclamoDTO.getDocumento());
        Reclamo reclamo = new Reclamo(persona, edificio, reclamoDTO.getUbicacion(), reclamoDTO.getDescripcion(), unidad);
        reclamo = reclamoService.agregarReclamo(reclamo);
        return ResponseEntity.ok("Reclamo agregado correctamente " + reclamo.getNumero());
    }

    @PutMapping("/AgregarImagen")
    @PreAuthorize("hasRole('Residente')or hasRole('Encargado')")
    public ResponseEntity<?> agregarImagenAReclamo(ImagenReclamoDTO imagenDTO) throws ReclamoException {
        Reclamo reclamo = buscarReclamo(imagenDTO.getNumero());
        reclamo.agregarImagen(imagenDTO.getDireccion(),imagenDTO.getTipo());
        reclamoService.agregarImagenAReclamo(reclamo);
        return ResponseEntity.ok("Imagen agregada correctamente");
    }

    @PostMapping("/CambiarEstado")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados') or hasRole('SuperAdmin') or hasRole('Residente')or hasRole('Encargado')")
    public ResponseEntity<?> cambiarEstado(@RequestBody int numero,@RequestBody Estado estado) throws ReclamoException {
        Reclamo reclamo = buscarReclamo(numero);
        reclamo.cambiarEstado(estado);
        reclamoService.ActualizarEstado(reclamo);
        return ResponseEntity.ok("Estado cambiado correctamente");
    }
    
    private Reclamo buscarReclamo(int numero) throws ReclamoException {
        Reclamo reclamo = reclamoService.buscarReclamo(numero);
        if (reclamo == null) {
            throw new ReclamoException("No existe reclamo con ese numero");
        }
        return reclamo;
    }
}
