package org.udg.pds.springtodo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.udg.pds.springtodo.entity.Usuari;
import org.udg.pds.springtodo.repository.UsuariRepository;

@Service
public class UsuariService {
    @Autowired
    UsuariRepository usuariRepository;

    public void guardarUsuari(Usuari u){
        usuariRepository.save(u);
    }

}
