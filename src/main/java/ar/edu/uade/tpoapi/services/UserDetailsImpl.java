package ar.edu.uade.tpoapi.services;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
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

        Collection <? extends GrantedAuthority> authorities = persona.getRoles()
            .stream()
            .map(rol -> new SimpleGrantedAuthority("ROLE_".concat(rol.getRol().name())))
            .collect(Collectors.toSet());

        return new User(persona.getMail(), persona.getPassword(),true,true,true,true,authorities);
    }
    
}
