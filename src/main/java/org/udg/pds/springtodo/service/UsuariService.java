package org.udg.pds.springtodo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.udg.pds.springtodo.controller.exceptions.ServiceException;
import org.udg.pds.springtodo.entity.Usuari;
import org.udg.pds.springtodo.repository.UsuariRepository;

import java.util.List;

@Service
public class UsuariService {
    @Autowired
    UsuariRepository usuariRepository;

    public void guardarUsuari(Usuari u){
        usuariRepository.save(u);
    }

    public Usuari comprovarContrasenya(String nomUsuari, String password){
        List<Usuari> uc = usuariRepository.buscarPerNomUsuari(nomUsuari);

        if (uc.size() == 0) throw new ServiceException("L'usuari no existeix");

        Usuari u = uc.get(0);

        if (u.getPassword().equals(password))
            return u;
        else
            throw new ServiceException("La contrasenya no es correcte");
    }

}
