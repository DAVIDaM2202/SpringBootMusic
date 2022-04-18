package org.udg.pds.springtodo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.udg.pds.springtodo.controller.exceptions.ServiceException;

import org.udg.pds.springtodo.entity.Album;
import org.udg.pds.springtodo.entity.Artista;
import org.udg.pds.springtodo.entity.Canco;
import org.udg.pds.springtodo.repository.CancoRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Service
public class CancoService {

    @Autowired
    CancoRepository cancoRepository;

    public Collection<Canco> obtenirCancons(){
        return cancoRepository.getAll();
    }

    public Canco obtenirCancoById(Long id){
        Optional<Canco> cancons = cancoRepository.findById(id);

        if(cancons.isPresent())
            return cancons.get();
        else
            throw new ServiceException(String.format("Cançó with id = % dos not exists", id));
    }

    public Collection<Canco> buscarCancoPerArtista(Artista artista){
        return cancoRepository.findAllByArtista(artista);
    }

    public void esborrarCanco(Canco canco){
        cancoRepository.delete(canco);
    }

    public Collection<Canco> buscarCancoPerTitol(String nameCanco){
        return cancoRepository.findAllByNomCanco(nameCanco);
    }

    public Page<Canco> getSongsByGenre(int offset, int pageSize, String genre){
        return cancoRepository.findAllByGenereOrderByReproduccionsDesc(PageRequest.of(offset,pageSize),genre);
    }

    public CancoRepository crud() {
        return cancoRepository;
    }

    public Canco guardarCanco(Canco c){return cancoRepository.save(c);}

    public List<Canco> obtenirCanconsContenenCadena(String cadena){
        Pageable loadSix = PageRequest.of(0, 6);
        return cancoRepository.getAllByNomCancoContains(cadena,loadSix);
    }

}
