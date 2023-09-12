package ar.edu.uade.tpoapi.services;

import ar.edu.uade.tpoapi.modelo.Edificio;
import ar.edu.uade.tpoapi.repository.EdificioRepository;

import java.util.List;

public class EdificioService  {

    private EdificioRepository edificioRepository;
    private static EdificioService instancia;
    public static EdificioService getInstancia(){
        if(instancia == null){
            instancia = new EdificioService();
        }
        return instancia;
    }
    private EdificioService(){
    }

    public List<Edificio> getAll(){

        return edificioRepository.findAll();
    }


}
