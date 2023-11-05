package ar.edu.uade.tpoapi.controlador;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.uade.tpoapi.controlador.request.Edificio.EdificioDTO;
import ar.edu.uade.tpoapi.exceptions.EdificioException;
import ar.edu.uade.tpoapi.modelo.Edificio;
import ar.edu.uade.tpoapi.services.EdificioService;
import ar.edu.uade.tpoapi.views.EdificioView;
import ar.edu.uade.tpoapi.views.PersonaView;
import ar.edu.uade.tpoapi.views.UnidadView;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/edificio")
public class ControladorEdificio {
    @Autowired
    EdificioService edificioService;

    @GetMapping(value = "/getAll")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados') or hasRole('SuperAdmin')")
    public ResponseEntity<?> getEdificios(){
        List<EdificioView>edificioViews = edificioService.buscarTodosEdificios();
        if(edificioViews == null)
            return ResponseEntity.internalServerError().build();
        if(edificioViews.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(edificioViews);
    }

    @GetMapping(value = "/getUnidades")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados') or hasRole('SuperAdmin')")
    public ResponseEntity<?> getUnidadesPorEdificio(@RequestParam int codigo) throws EdificioException{
         Set<UnidadView> unidades = buscarEdificio(codigo).unidades();
         if (unidades == null)
             return ResponseEntity.internalServerError().build();
        if(unidades.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(unidades);
    }

    @GetMapping(value = "/habilitados")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados') or hasRole('SuperAdmin')")
    public ResponseEntity<?> habilitadosPorEdificio(@RequestParam int codigo) throws EdificioException{
        Set<PersonaView> habilitados = buscarEdificio(codigo).habilitados();
        if(habilitados == null)
            return ResponseEntity.internalServerError().build();
        if(habilitados.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(habilitados);
    }

    @GetMapping(value = "/duenios")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados') or hasRole('SuperAdmin')")
    public ResponseEntity<?> dueniosPorEdificio(@RequestParam int codigo) throws EdificioException{
        Set<PersonaView> duenios = buscarEdificio(codigo).duenios();
        if(duenios == null)
            return ResponseEntity.internalServerError().build();
        if(duenios.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(duenios);
    }

    @GetMapping(value = "/habitantes")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados') or hasRole('SuperAdmin')")
    public ResponseEntity<?> habitantesPorEdificio(@RequestParam int codigo) throws EdificioException{
        Set<PersonaView> habitantes = buscarEdificio(codigo).habitantes();
        if(habitantes == null)
            return ResponseEntity.internalServerError().build();
        if(habitantes.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(habitantes);
    }

    @PostMapping(value = "/agregarEdificio")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados') or hasRole('SuperAdmin')")
    public ResponseEntity<?> agregarEdificio(@Valid @RequestBody EdificioDTO createEdificioDTO ) throws EdificioException {
        if(edificioService.existeNombre(createEdificioDTO.getNombre()))
            return ResponseEntity.badRequest().body("Ya existe un edificio con ese nombre");
        else if (edificioService.existeDireccion(createEdificioDTO.getDireccion()))
            return ResponseEntity.badRequest().body("Ya existe un edificio con esa direccion");
        else{
            Edificio edificio = Edificio.builder()
                    .nombre(createEdificioDTO.getNombre())
                    .direccion(createEdificioDTO.getDireccion())
                    .build();
            ;
            edificio = edificioService.guardarEdificio(edificio);
            if (edificio == null)
                return ResponseEntity.internalServerError().build();
            if (edificio.getCodigo() == 0)
                return ResponseEntity.badRequest().body("No se pudo agregar el edificio");
            return ResponseEntity.ok().body("Edificio agregado correctamente");
        }
    }

    @GetMapping(value = "/buscar")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados') or hasRole('SuperAdmin')")
    public ResponseEntity<?> buscarEdificioPorCodigo(@RequestParam int codigo) throws EdificioException {
        EdificioView edificioView = edificioService.buscarEdificioPorCodigo(codigo).toView();
        if(edificioView == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(edificioView);
    }

    @PatchMapping(value = "/modificar")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados') or hasRole('SuperAdmin')")
    public ResponseEntity<?> modificarEdificio(@Valid @RequestBody EdificioView edificioView) throws EdificioException {
        if(edificioService.existeEdificio(edificioView.getCodigo()))
        {
            try {
                edificioService.modificarEdificio(edificioView);
                return ResponseEntity.ok().body("Edificio modificado correctamente");
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("No se pudo modificar el edificio");
            }
        }
        else
            return ResponseEntity.badRequest().body("No existe un edificio con ese codigo");
    }

    @DeleteMapping(value = "/eliminar")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados') or hasRole('SuperAdmin')")
    @Transactional
    public ResponseEntity<?> eliminarEdificio(@RequestParam int codigo) throws EdificioException {
        if(edificioService.existeEdificio(codigo))
        {
            try {
                edificioService.eliminarEdificio(codigo);
                return ResponseEntity.ok().body("Edificio eliminado correctamente");
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("No se pudo eliminar el edificio" + e.getMessage());
            }
        }
        else
            return ResponseEntity.badRequest().body("No existe un edificio con ese codigo");
    }

    protected Edificio buscarEdificio(int codigo) throws EdificioException {
        return edificioService.buscarEdificioPorCodigo(codigo);
    }
}
