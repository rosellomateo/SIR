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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.uade.tpoapi.controlador.request.Reclamo.CambiarEstadoDTO;
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
    
    @GetMapping("/edificio")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados') or hasRole('SuperAdmin') or hasRole('Residente')or hasRole('Encargado')")
    public ResponseEntity<?> reclamosPorEdificio(@RequestParam int codigo) {
        List<ReclamoView> resultado = reclamoService.reclamosPorEdificio(codigo);
        if (resultado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/unidad")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados') or hasRole('SuperAdmin') or hasRole('Residente')or hasRole('Encargado')")
    public ResponseEntity<?> reclamosPorUnidad(@Valid @RequestBody UnidadDTO unidadDTO) {
        List<ReclamoView> resultado = reclamoService.reclamosPorUnidad(unidadDTO);
        if (resultado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/numero")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados') or hasRole('SuperAdmin') or hasRole('Residente')or hasRole('Encargado')")
    public ResponseEntity<?> reclamosPorNumero(@RequestParam int numero) throws ReclamoException {
        ReclamoView resultado = buscarReclamo(numero).toView();
        if (resultado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/persona")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados') or hasRole('SuperAdmin') or hasRole('Residente')or hasRole('Encargado')")
    public ResponseEntity<?> reclamosPorPersona(@RequestParam String documento) {
        List<ReclamoView> resultado = reclamoService.reclamosPorPersona(documento);
        if (resultado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(resultado);
    }


    @PutMapping("/agregar")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados') or hasRole('SuperAdmin') or hasRole('Residente')or hasRole('Encargado')")
    public ResponseEntity<?> agregarReclamo(ReclamoDTO reclamoDTO) throws EdificioException, UnidadException, PersonaException {
        Edificio edificio = controladorEdificio.buscarEdificio(reclamoDTO.getCodigo());
        Unidad unidad = controlerUnidad.buscarUnidad(reclamoDTO.getCodigo(), reclamoDTO.getPiso(),reclamoDTO.getNumero());
        Persona persona = controlerPersona.buscarPersona(reclamoDTO.getDocumento());
        Reclamo reclamo = new Reclamo(persona, edificio, reclamoDTO.getUbicacion(), reclamoDTO.getDescripcion(), unidad);
        reclamo = reclamoService.agregarReclamo(reclamo);
        return ResponseEntity.ok("Reclamo agregado correctamente " + reclamo.getNumero());
    }

    @PutMapping("/agregar-imagen")
    @PreAuthorize("hasRole('Residente')or hasRole('Encargado')")
    public ResponseEntity<?> agregarImagenAReclamo(ImagenReclamoDTO imagenDTO) throws ReclamoException {
        Reclamo reclamo = buscarReclamo(imagenDTO.getNumero());
        reclamo.agregarImagen(imagenDTO.getDireccion(),imagenDTO.getTipo());
        reclamoService.agregarImagenAReclamo(reclamo);
        return ResponseEntity.ok("Imagen agregada correctamente");
    }

    @PostMapping("/cambiar-estado")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados') or hasRole('SuperAdmin') or hasRole('Residente')or hasRole('Encargado')")
    public ResponseEntity<?> cambiarEstado(@RequestBody CambiarEstadoDTO dto) throws ReclamoException {
        Reclamo reclamo = buscarReclamo(dto.getNumero());
        reclamo.cambiarEstado(dto.getEstado());
        boolean isUpdated = reclamoService.ActualizarEstado(reclamo);
        if (isUpdated) {
            return ResponseEntity.ok("Estado cambiado correctamente");
        } else {
            return ResponseEntity.badRequest().body("Error al actualizar el estado");
        }
    }
    
    private Reclamo buscarReclamo(int numero) throws ReclamoException {
        Reclamo reclamo = reclamoService.buscarReclamo(numero);
        if (reclamo == null) {
            throw new ReclamoException("No existe reclamo con ese numero");
        }
        return reclamo;
    }
}
