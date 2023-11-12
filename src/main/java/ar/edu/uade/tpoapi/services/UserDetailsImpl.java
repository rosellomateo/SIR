package ar.edu.uade.tpoapi.services;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ar.edu.uade.tpoapi.modelo.Persona;

@Service
public class UserDetailsImpl implements UserDetailsService{

    @Autowired
    private PersonaService personaService;

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException
    {
        Persona persona = personaService.buscarPersonaPorMail(mail);

        SimpleGrantedAuthority  authority = new SimpleGrantedAuthority("ROLE_" + persona.getRol().name());
        Collection<SimpleGrantedAuthority> authorities = Arrays.asList(authority);

        
        return new User(persona.getMail(), persona.getPassword(),true,true,true,true,authorities);
    }
    
}
