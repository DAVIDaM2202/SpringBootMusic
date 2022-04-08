package org.udg.pds.springtodo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.udg.pds.springtodo.controller.exceptions.ServiceException;
import org.udg.pds.springtodo.entity.Album;
import org.udg.pds.springtodo.entity.Artista;
import org.udg.pds.springtodo.repository.AlbumRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class AlbumService {
    @Autowired
    AlbumRepository albumRepository;

    public void guardarAlbum(Album album){
        albumRepository.save(album);
    }

    public Collection<Album> obtenirAlbums(){
        return albumRepository.getAll();
    }

    public Album obtenirAlbumPerId(Long id){

        Optional<Album> album = albumRepository.findById(id);

        if(album.isPresent())
            return album.get();
        else
            throw new ServiceException(String.format("Album with id = % dos not exists", id));
    }

    public Collection<Album> buscarAlbumPerTitol(String titol){
        return albumRepository.findAllByTitol(titol);
    }

    public Collection<Album> buscarAlbumPerArtista(Artista artista){
        return albumRepository.findAllByArtista(artista);
    }
}
