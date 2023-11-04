package ar.edu.uade.tpoapi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.uade.tpoapi.controlador.request.Persona.UpdatePersonaDTO;
import ar.edu.uade.tpoapi.modelo.Persona;
import ar.edu.uade.tpoapi.modelo.Enumerations.Rol;
import ar.edu.uade.tpoapi.repository.PersonaRepository;
import ar.edu.uade.tpoapi.views.MetaData;
import ar.edu.uade.tpoapi.views.SendRequest;
import jakarta.validation.Valid;

@Service
public class PersonaService {
    @Autowired
    PersonaRepository personaRepository;

    @Autowired
    SendMessageService sendMessageService;

    public boolean eliminarPersona(String documento) {
        try {
            personaRepository.deleteById(documento);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean existePersona(String documento) {

        return personaRepository.existsById(documento);
    }

    public boolean existeMail(String mail) {
        return personaRepository.existsByMail(mail);
    }

    public Persona buscarPersona(String documento) {
        return personaRepository.findById(documento).orElse(null);
        
    }

    public Boolean guardarPersona(Persona personaRegistro) {
        try {
            personaRepository.save(personaRegistro);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Persona buscarPersonaPorMail(String mail) {
        Optional<Persona> persona = personaRepository.findByMail(mail);
        return persona.orElse(null);
    }

    public Persona modificarPersona(@Valid UpdatePersonaDTO updatePersonaDTO) {
        Persona persona = buscarPersona(updatePersonaDTO.getDocumento());
        persona.setNombre(updatePersonaDTO.getNombre());
        persona.setRol(updatePersonaDTO.getRoles());
        return personaRepository.save(persona);
    }

    public void enviarMailConfirmacion(String documentoValidar, String mailValidar) {
        Persona persona = buscarPersona(documentoValidar);
        if(persona == null)
        {
            return;
        }
        String token = generarToken();
        persona.setTokenVerificacion(token);
        persona.setMail(mailValidar);
        personaRepository.save(persona);

        List<MetaData> metaData = new ArrayList<>();
        metaData.add(new MetaData("name", persona.getNombre()));
        metaData.add(new MetaData("confirmationCode", token));

        sendMessageService.sendMessage(SendRequest.builder().to(mailValidar).subject("Confirmacion de mail")
                .template(2).metaData(metaData).build());
    }

    private String generarToken() {
        return String.valueOf((int) (Math.random() * 999999 + 100000));
    }

    public void confirmarMail(String mail) {
        //confirmar mail y enviar mail de bienvenida
        Persona persona = buscarPersonaPorMail(mail);
        persona.setCuentaVerificado(true);
        persona.setTokenVerificacion(null);
        personaRepository.save(persona);
        
        List<MetaData> metaData = new ArrayList<>();
        metaData.add(new MetaData("name", persona.getNombre()));

        sendMessageService.sendMessage(SendRequest.builder().to(mail).subject("Bienvenido a la aplicacion")
                .template(3).metaData(metaData).build());
    }

    public String enviarMailOlvidePassword(String mail) {
        Persona persona = buscarPersonaPorMail(mail);

        if(persona == null)
        {
            return "No se encontro una persona con ese mail";
        }

        if(!persona.isCuentaVerificado())
        {
            return "La cuenta no esta verificada";
        }

        if(persona.getTokenVerificacion() != null)
        {
            return "Ya se envio un mail para recuperar el password";
        }

        String token = generarToken();
        persona.setTokenVerificacion(token);
        personaRepository.save(persona);

        List<MetaData> metaData = new ArrayList<>();
        metaData.add(new MetaData("name", persona.getNombre()));
        metaData.add(new MetaData("resetCode", token));

        sendMessageService.sendMessage(SendRequest.builder().to(mail).subject("Recuperacion de password")
                .template(10).metaData(metaData).build());
        return "Se envio un mail para recuperar el password";
    }
}
