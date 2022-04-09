package org.udg.pds.springtodo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.udg.pds.springtodo.controller.exceptions.ServiceException;
import org.udg.pds.springtodo.entity.Canco;
import org.udg.pds.springtodo.repository.CancoRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CancoService {

    @Autowired
    CancoRepository cancoRepository;


    public Collection<Canco> getCancons() {
        return StreamSupport.stream(cancoRepository.findAll().spliterator(),false).collect(Collectors.toList());
    }

    public Canco getCanco(Long cancoId) {
        Optional <Canco> canco = cancoRepository.findById(cancoId);

        if (canco.isPresent()) {
            return canco.get();
        }
        else{
            throw new ServiceException("La canço no existeix");
        }
    }

    /*public Collection<Canco> getCanconsGenere(String genere) {
        return cancoRepository.buscarPerGenere(genere);
    }*/

    /*public Collection<Canco> getCanconsAlbum(Integer idAlbum) {
        return cancoRepository.buscarPerAlbum(idAlbum);
    }*/

    public Collection<Canco> getCanconsArtista(String artista) {
        return cancoRepository.buscarPerArtista(artista);
    }

    public CancoRepository crud() {
        return cancoRepository;
    }

    public Canco guardarCanco(Canco c){return cancoRepository.save(c);}
}
