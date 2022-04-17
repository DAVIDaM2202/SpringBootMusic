package org.udg.pds.springtodo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.udg.pds.springtodo.controller.exceptions.ServiceException;
import org.udg.pds.springtodo.entity.Album;
import org.udg.pds.springtodo.entity.Artista;
import org.udg.pds.springtodo.entity.Usuari;
import org.udg.pds.springtodo.repository.AlbumRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class AlbumService {
    @Autowired
    AlbumRepository albumRepository;

    public void guardarAlbum(Album album){
        albumRepository.save(album);
    }

    public void esborrarAlbum(Album album){
        albumRepository.delete(album);
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

    public List<Album> obtenirAlbumsContenenCadena(String cadena){
        Pageable loadSix = PageRequest.of(0, 6);
        return albumRepository.getAllByTitolContains(cadena,loadSix);
    }
}
