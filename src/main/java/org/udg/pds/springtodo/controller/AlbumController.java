package org.udg.pds.springtodo.controller;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.udg.pds.springtodo.controller.exceptions.ServiceException;
import org.udg.pds.springtodo.entity.Album;
import org.udg.pds.springtodo.entity.Artista;
import org.udg.pds.springtodo.service.AlbumService;
import org.udg.pds.springtodo.service.ArtistaService;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Controller
@RequestMapping(path="/albums")
public class AlbumController extends BaseController {
    @Autowired
    ArtistaService artistaService;

    @Autowired
    AlbumService albumService;

    @GetMapping
    public Collection<Album> getAllAlbums(HttpSession httpSession){
        comprovarNoLogejat(httpSession);
        return albumService.obtenirAlbums();
    }

    @PostMapping
    public void addNewAlbum(HttpSession httpSession, @RequestBody NewAlbum newAlbum){
        comprovarNoLogejat(httpSession);

        Album album = new Album(newAlbum.titol, newAlbum.imatge, newAlbum.descripcio);
        albumService.guardarAlbum(album);
    }

    @GetMapping("/{id}")
    public Album getAlbumById(HttpSession httpSession, @PathVariable("id") Long id){
        comprovarNoLogejat(httpSession);
        return albumService.obtenirAlbumPerId(id);
    }

    @PutMapping( "/{id}")
    public void modifyImageAlbum(HttpSession httpSession, @PathVariable("id") Long id, @RequestBody UpdateAlbum albumUpdate){
        comprovarNoLogejat(httpSession);

        Album album = albumService.obtenirAlbumPerId(id);
        if(albumUpdate.titol != null)
            album.setTitol(albumUpdate.titol);
        if(albumUpdate.imatge != null)
            album.setImatge(albumUpdate.imatge);
        if(albumUpdate.descripcio != null)
            album.setDescripcio(albumUpdate.descripcio);

        albumService.guardarAlbum(album);
    }

    @GetMapping("/titol/{titol}")
    public Collection<Album> getAllAlbumsByTitle(HttpSession httpSession, @PathVariable("titol") String titol){
        comprovarNoLogejat(httpSession);
        return albumService.buscarAlbumPerTitol(titol);
    }

    @GetMapping("/artista/{id}")
    public Collection<Album> getAllAlbumsByArtista(HttpSession httpSession, @PathVariable("id") Long idArtista){
        comprovarNoLogejat(httpSession);

        Artista artista = artistaService.obtenirPerId(idArtista);

        if(artista != null){
            return albumService.buscarAlbumPerArtista(artista);
        }else
            throw new ServiceException("No existeix l'artista amb aquest identificador.");
    }

    static class UpdateAlbum {
        public String titol;
        public String imatge;
        public String descripcio;
    }

    static class NewAlbum{
        @NotNull
        public String titol;
        public String imatge;
        public String descripcio;
    }


}
