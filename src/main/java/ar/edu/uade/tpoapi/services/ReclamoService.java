package ar.edu.uade.tpoapi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ar.edu.uade.tpoapi.controlador.request.Reclamo.CambiarEstadoDTO;
import ar.edu.uade.tpoapi.controlador.request.Reclamo.ComentarReclamoDTO;
import ar.edu.uade.tpoapi.controlador.request.Reclamo.ImagenReclamoDTO;
import ar.edu.uade.tpoapi.controlador.request.Reclamo.ReclamoDTO;
import ar.edu.uade.tpoapi.controlador.request.Reclamo.UnidadDTO;
import ar.edu.uade.tpoapi.modelo.Edificio;
import ar.edu.uade.tpoapi.modelo.Imagen;
import ar.edu.uade.tpoapi.modelo.Persona;
import ar.edu.uade.tpoapi.modelo.Reclamo;
import ar.edu.uade.tpoapi.modelo.Unidad;
import ar.edu.uade.tpoapi.modelo.Enumerations.Estado;
import ar.edu.uade.tpoapi.repository.EdificioRepository;
import ar.edu.uade.tpoapi.repository.ImagenRepository;
import ar.edu.uade.tpoapi.repository.PersonaRepository;
import ar.edu.uade.tpoapi.repository.ReclamoRepository;
import ar.edu.uade.tpoapi.repository.UnidadRepository;
import ar.edu.uade.tpoapi.views.ReclamoView;
import ar.edu.uade.tpoapi.views.SendRequest;

@Service
public class ReclamoService {
    @Autowired
    ReclamoRepository reclamoRepository;
    @Autowired
    UnidadRepository unidadRepository;
    @Autowired
    EdificioRepository edificioRepository;
    @Autowired
    PersonaRepository personaRepository;
    @Autowired
    ImagenRepository imagenRepository;
    @Autowired
    SendMessageService sendMessageService;

    public List<ReclamoView> reclamosPorEdificio(int codigo) {
        List<Reclamo> reclamos = reclamoRepository.findByEdificioCodigo(codigo);
        List<ReclamoView> resultado = new ArrayList<ReclamoView>();

        for (Reclamo reclamo : reclamos) {
            resultado.add(reclamo.toView());
        }
        return resultado;
    }

    public List<ReclamoView> reclamosPorUnidad(int identificador) {
        Unidad unidad = unidadRepository.getById(identificador);
        List<Reclamo> reclamos = reclamoRepository.findByUnidadId(unidad.getId());
        List<ReclamoView> resultado = new ArrayList<ReclamoView>();

        for (Reclamo reclamo : reclamos) {
            resultado.add(reclamo.toView());
        }
        return resultado;
    }

    public List<ReclamoView> reclamosPorPersona(String documento) {
        List<Reclamo> reclamos = reclamoRepository.findByUsuarioDocumento(documento);
        List<ReclamoView> resultado = new ArrayList<ReclamoView>();

        for (Reclamo reclamo : reclamos) {
            resultado.add(reclamo.toView());
        }
        return resultado;
    }

    public ResponseEntity<?> agregarReclamo(ReclamoDTO reclamoDTO) {
        Edificio edificio = edificioRepository.findByCodigo(reclamoDTO.getCodigo()).orElse(null);
        if(edificio == null){
            return ResponseEntity.badRequest().body("El edificio no existe");
        }
        Unidad unidad = unidadRepository.getById(reclamoDTO.getIdentificador());
        if(unidad == null){
            return ResponseEntity.badRequest().body("La unidad no existe");
        }
        Persona persona = personaRepository.findByDocumento(reclamoDTO.getDocumento()).orElse(null);
        if(persona == null){
            return ResponseEntity.badRequest().body("La persona no existe");
        }
        Reclamo reclamo = Reclamo.builder()
        .edificio(edificio)
        .ubicacion(reclamoDTO.getUbicacion())
        .descripcion(reclamoDTO.getDescripcion())
        .unidad(unidad)
        .usuario(persona)
        .estado(Estado.nuevo)
        .imagenes(new ArrayList<>())
        .build();
        reclamo = reclamoRepository.saveAndFlush(reclamo);
        if(reclamo != null){
            return ResponseEntity.ok(reclamo.toView());
        }
        return ResponseEntity.badRequest().body("No se pudo crear el reclamo");
    }

    public boolean agregarImagenAReclamo(ImagenReclamoDTO imagenDTO){
        Reclamo reclamo = reclamoRepository.findById(imagenDTO.getNumero()).orElse(null);
        Imagen imagen = Imagen.builder().direccion(imagenDTO.getDireccion()).tipo(imagenDTO.getTipo()).build();
        imagen = imagenRepository.saveAndFlush(imagen);
        if(imagen == null){
            return false;
        }
        if(reclamo != null){
            reclamo.agregarImagen(imagen);
            reclamo = reclamoRepository.saveAndFlush(reclamo);
            if(reclamo != null)
                return true;
            else
                return false;
        }
        return false;
    }

    public boolean ActualizarEstado(CambiarEstadoDTO cambiarEstadoDTO) {
        Reclamo reclamo = reclamoRepository.findById(cambiarEstadoDTO.getNumero()).orElse(null);
        if (reclamo != null) {
            reclamo.setEstado(cambiarEstadoDTO.getEstado());
            reclamo = reclamoRepository.saveAndFlush(reclamo);
            if (reclamo != null)
                return true;
            else
                return false;
        }
        return false;
    }

    public Reclamo buscarReclamo(int numero) {
        return reclamoRepository.findById(numero).orElse(null);
    }

    public ResponseEntity<?> sendMail(SendRequest sendRequest) {
        return sendMessageService.sendMessage(sendRequest);
    }

    public ResponseEntity<?> comentarReclamo(ComentarReclamoDTO comentarReclamoDTO) {
        return null;
    }

}
