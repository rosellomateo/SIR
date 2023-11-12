package ar.edu.uade.tpoapi.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ar.edu.uade.tpoapi.controlador.request.Reclamo.CambiarEstadoDTO;
import ar.edu.uade.tpoapi.controlador.request.Reclamo.ComentarReclamoDTO;
import ar.edu.uade.tpoapi.controlador.request.Reclamo.ImagenReclamoDTO;
import ar.edu.uade.tpoapi.controlador.request.Reclamo.ReclamoDTO;
import ar.edu.uade.tpoapi.modelo.Comentario;
import ar.edu.uade.tpoapi.modelo.Edificio;
import ar.edu.uade.tpoapi.modelo.Imagen;
import ar.edu.uade.tpoapi.modelo.Persona;
import ar.edu.uade.tpoapi.modelo.Reclamo;
import ar.edu.uade.tpoapi.modelo.Unidad;
import ar.edu.uade.tpoapi.modelo.Enumerations.Estado;
import ar.edu.uade.tpoapi.repository.ComentarioRepository;
import ar.edu.uade.tpoapi.repository.EdificioRepository;
import ar.edu.uade.tpoapi.repository.ImagenRepository;
import ar.edu.uade.tpoapi.repository.PersonaRepository;
import ar.edu.uade.tpoapi.repository.ReclamoRepository;
import ar.edu.uade.tpoapi.repository.UnidadRepository;
import ar.edu.uade.tpoapi.views.MetaData;
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
    ComentarioRepository comentarioRepository;
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
            enviarMailCreacionReclamo(reclamo);
            return ResponseEntity.ok(reclamo.toView());
        }
        return ResponseEntity.badRequest().body("No se pudo crear el reclamo");
    }

    private void enviarMailCreacionReclamo(Reclamo reclamo) {
        List<MetaData> metaData = new ArrayList<>();
        metaData.add(new MetaData("nombrePersona", reclamo.getUsuario().getNombre()));
        metaData.add(new MetaData("numeroReclamo", String.valueOf(reclamo.getNumero())));
        metaData.add(new MetaData("descripcionReclamo", reclamo.getDescripcion()));
        metaData.add(new MetaData("ubicacionReclamo", reclamo.getUbicacion()));
        metaData.add(new MetaData("estadoReclamo", reclamo.getEstado().toString()));
        SendRequest sendRequest = SendRequest.builder()
        .to(reclamo.getUsuario().getMail())
        .subject("Creacion de reclamo")
        .template(12)
        .metaData(metaData)
        .build();
        sendMessageService.sendMessage(sendRequest);
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
            {
                enviarMailCambioEstado(reclamo);
                return true;
            }
            else
                return false;
        }
        return false;
    }

    private void enviarMailCambioEstado(Reclamo reclamo) {
        List<MetaData> metaData = new ArrayList<>();
        metaData.add(new MetaData("nombrePersona", reclamo.getUsuario().getNombre()));
        metaData.add(new MetaData("numeroReclamo", String.valueOf(reclamo.getNumero())));
        metaData.add(new MetaData("descripcionReclamo", reclamo.getDescripcion()));
        metaData.add(new MetaData("nuevoEstado", reclamo.getEstado().toString()));
        SendRequest sendRequest = SendRequest.builder()
        .to(reclamo.getUsuario().getMail())
        .subject("Cambio de estado de reclamo")
        .template(11)
        .metaData(metaData)
        .build();
        sendMessageService.sendMessage(sendRequest);
    }

    public Reclamo buscarReclamo(int numero) {
        return reclamoRepository.findById(numero).orElse(null);
    }

    public ResponseEntity<?> sendMail(SendRequest sendRequest) {
        return sendMessageService.sendMessage(sendRequest);
    }

    public ResponseEntity<?> comentarReclamo(ComentarReclamoDTO comentarReclamoDTO) {
        Reclamo reclamo = reclamoRepository.findById(comentarReclamoDTO.getNumero()).orElse(null);
        if (reclamo != null) {
            Persona persona = personaRepository.findByDocumento(comentarReclamoDTO.getDocumento()).orElse(null);
            if (persona != null) {
                
                List<ImagenReclamoDTO> imagenes = comentarReclamoDTO.getImagenes();
                List<Imagen> imagenesReclamo = new ArrayList<Imagen>();
                for (ImagenReclamoDTO imagenReclamoDTO : imagenes) {
                    Imagen imagen = Imagen.builder().direccion(imagenReclamoDTO.getDireccion())
                            .tipo(imagenReclamoDTO.getTipo()).build();
                    imagen = imagenRepository.saveAndFlush(imagen);
                    if (imagen == null) {
                        return ResponseEntity.badRequest().body("Error al comentar el reclamo");
                    }
                    imagenesReclamo.add(imagen);
                }
                Comentario comentarioPadre = comentarioRepository.findById(comentarReclamoDTO.getNumeroPadre()).orElse(null);
                Comentario comentario = Comentario.builder().texto(comentarReclamoDTO.getTexto()).comentarioPadre(comentarioPadre)
                .imagenes(imagenesReclamo).usuario(persona).fecha(new Date()).build();
                comentario = comentarioRepository.saveAndFlush(comentario);
                if (comentario == null) {
                    return ResponseEntity.badRequest().body("Error al comentar el reclamo");
                }
                if(comentarioPadre == null){
                    reclamo.agregarComentario(comentario);
                    reclamo = reclamoRepository.saveAndFlush(reclamo);
                    if (reclamo != null) {
                        enviarMailCargarComentario(reclamo);
                        return ResponseEntity.ok(reclamo.toView());
                    } else {
                        return ResponseEntity.badRequest().body("Error al comentar el reclamo");
                    }
                }
                else{
                    reclamo = reclamoRepository.findById(comentarReclamoDTO.getNumero()).orElse(null);
                    return ResponseEntity.ok(reclamo.toView());
                }
            } else {
                return ResponseEntity.badRequest().body("La persona no existe");
            }
        } else {
            return ResponseEntity.badRequest().body("El reclamo no existe");
        }
    }

    private void enviarMailCargarComentario(Reclamo reclamo) {
        List<MetaData> metaData = new ArrayList<>();
        metaData.add(new MetaData("nombrePersona", reclamo.getUsuario().getNombre()));
        metaData.add(new MetaData("numeroReclamo", String.valueOf(reclamo.getNumero())));
        metaData.add(new MetaData("descripcionReclamo", reclamo.getDescripcion()));
        metaData.add(new MetaData("ubicacionReclamo", reclamo.getUbicacion()));
        metaData.add(new MetaData("estadoReclamo", reclamo.getEstado().toString()));
        SendRequest sendRequest = SendRequest.builder()
        .to(reclamo.getUsuario().getMail())
        .subject("Nuevo comentario en reclamo")
        .template(13)
        .metaData(metaData)
        .build();
        sendMessageService.sendMessage(sendRequest);
    }

}
