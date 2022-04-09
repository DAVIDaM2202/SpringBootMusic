package org.udg.pds.springtodo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.udg.pds.springtodo.entity.Artista;
import org.udg.pds.springtodo.repository.ArtistaRepository;

@Service
public class ArtistaService {
    @Autowired
    ArtistaRepository artistaRepository;

    public void guardarArtista(Artista artista){
        artistaRepository.save(artista);
    }
    public Artista obtenirPerId(Long id){return artistaRepository.getById(id);}
}
