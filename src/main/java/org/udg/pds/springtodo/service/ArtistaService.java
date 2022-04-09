package org.udg.pds.springtodo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.udg.pds.springtodo.controller.exceptions.ServiceException;
import org.udg.pds.springtodo.entity.Artista;
import org.udg.pds.springtodo.entity.Usuari;
import org.udg.pds.springtodo.repository.ArtistaRepository;

import java.util.Optional;

@Service
public class ArtistaService {
    @Autowired
    ArtistaRepository artistaRepository;

    @Autowired
    UsuariService usuariService;

    public void guardarArtista(Artista artista){
        artistaRepository.save(artista);
    }

    public Artista obtenirPerUsuariId(Long id) {
        Usuari usuari = usuariService.getUser(id);

        Optional<Artista> artista = artistaRepository.findById(usuari.getJoComArtista().getIdArtista());

        if(artista.isPresent())
            return artista.get();
        else
            throw new ServiceException("No existeix l'artista amb aquest identificador.");
    }

    public Artista obtenirPerId(Long id){
        Optional<Artista> artista = artistaRepository.findById(id);

        if (artista.isPresent())
            return artista.get();
        else
            throw new ServiceException("No existeix l'artista amb aquest identificador.");
    }
}
