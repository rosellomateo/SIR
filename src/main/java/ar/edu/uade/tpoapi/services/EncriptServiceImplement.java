package ar.edu.uade.tpoapi.services;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class EncriptServiceImplement{
    
    public boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

    public String encrypt(String password) {
        return BCrypt.hashpw(password,BCrypt.gensalt());
    }
}