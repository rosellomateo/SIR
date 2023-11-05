package ar.edu.uade.tpoapi.controlador;

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
import ar.edu.uade.tpoapi.controlador.request.Reclamo.ComentarReclamoDTO;
import ar.edu.uade.tpoapi.controlador.request.Reclamo.ImagenReclamoDTO;
import ar.edu.uade.tpoapi.controlador.request.Reclamo.ReclamoDTO;
import ar.edu.uade.tpoapi.exceptions.EdificioException;
import ar.edu.uade.tpoapi.exceptions.PersonaException;
import ar.edu.uade.tpoapi.exceptions.ReclamoException;
import ar.edu.uade.tpoapi.exceptions.UnidadException;
import ar.edu.uade.tpoapi.modelo.Reclamo;
import ar.edu.uade.tpoapi.services.ReclamoService;
import ar.edu.uade.tpoapi.views.ReclamoView;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/Reclamo")
public class ControladorReclamo 
{

    @Autowired
    ReclamoService reclamoService;
    
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
    public ResponseEntity<?> reclamosPorUnidad(@RequestParam int identificador) {
        List<ReclamoView> resultado = reclamoService.reclamosPorUnidad(identificador);
        if (resultado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/numero")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados') or hasRole('SuperAdmin') or hasRole('Residente')or hasRole('Encargado')")
    public ResponseEntity<?> reclamosPorNumero(@RequestParam int numero) throws ReclamoException {
        Reclamo resultado = reclamoService.buscarReclamo(numero);
        if (resultado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(resultado.toView());
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
    public ResponseEntity<?> agregarReclamo(@Valid @RequestBody ReclamoDTO reclamoDTO) throws EdificioException, UnidadException, PersonaException {
        return reclamoService.agregarReclamo(reclamoDTO);
    }

    @PutMapping("/agregar-imagen")
    @PreAuthorize("hasRole('Residente')or hasRole('Encargado')")
    public ResponseEntity<?> agregarImagenAReclamo(@Valid @RequestBody ImagenReclamoDTO imagenDTO) throws ReclamoException {
        if(reclamoService.agregarImagenAReclamo(imagenDTO))
        {
            return ResponseEntity.ok("Imagen agregada correctamente");
        }
        else
        {
            return ResponseEntity.badRequest().body("Error al agregar la imagen");
        }
    }

    @PostMapping("/cambiar-estado")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados') or hasRole('SuperAdmin') or hasRole('Residente')or hasRole('Encargado')")
    public ResponseEntity<?> cambiarEstado(@Valid @RequestBody CambiarEstadoDTO cambiarEstadoDTO) throws ReclamoException {
        if (reclamoService.ActualizarEstado(cambiarEstadoDTO)) {
            return ResponseEntity.ok("Estado cambiado correctamente");
        } else {
            return ResponseEntity.badRequest().body("Error al actualizar el estado");
        }
    }

    @PutMapping("/comentarReclamo")
    public ResponseEntity<?> comentarReclamo(@Valid @RequestBody ComentarReclamoDTO comentarReclamoDTO) throws ReclamoException {
        return reclamoService.comentarReclamo(comentarReclamoDTO);
    }
}


