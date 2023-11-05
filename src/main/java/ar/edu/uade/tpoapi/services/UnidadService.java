package ar.edu.uade.tpoapi.services;

import ar.edu.uade.tpoapi.controlador.request.Unidad.TransferirUnidadDTO;
import ar.edu.uade.tpoapi.controlador.request.Unidad.UnidadDTO;
import ar.edu.uade.tpoapi.modelo.Unidad;
import ar.edu.uade.tpoapi.repository.PersonaRepository;
import ar.edu.uade.tpoapi.repository.UnidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ar.edu.uade.tpoapi.modelo.Persona;

@Service
public class UnidadService {
    @Autowired
    UnidadRepository unidadRepository;
    @Autowired
    PersonaRepository personaRepository;

    public boolean existeUnidad(int identificador){
        return unidadRepository.existsById(identificador);
    }

    public Unidad buscarUnidad(int identificador){
        if (existeUnidad(identificador)) {
            return unidadRepository.findById(identificador).get();
        }else{
            return null;
        }
    }

    public Unidad buscarUnidad(int codigo, String piso, String numero) {
        Unidad unidad = unidadRepository.findByEdificioCodigoAndPisoAndNumero(codigo, piso, numero);
        return unidad;
    }

    public Unidad crearUnidad(Unidad unidad) {

        return unidadRepository.save(unidad);
    }

    public void eliminarUnidad(Unidad unidad) {
        unidadRepository.delete(unidad);

    }
    public String transferirUnidad(TransferirUnidadDTO transferirUnidadDTO){

        Persona persona = personaRepository.findByDocumento(transferirUnidadDTO.getDocumento()).orElse(null);

        if(persona != null){
          Unidad unidad = unidadRepository.findById(transferirUnidadDTO.getIdentificador()).orElse(null);
          if(unidad != null){
              unidad.transferir(persona);
              return "Unidad transferida";
          }
          else {
              return "No se ha encontrado una unidad con ese codigo";
          }
        }
        return "No se ha encontrado la persona";
    }




}
