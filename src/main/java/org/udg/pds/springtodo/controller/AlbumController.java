package org.udg.pds.springtodo.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.udg.pds.springtodo.controller.exceptions.ServiceException;
import org.udg.pds.springtodo.entity.Album;
import org.udg.pds.springtodo.entity.Artista;
import org.udg.pds.springtodo.entity.Views;
import org.udg.pds.springtodo.service.AlbumService;
import org.udg.pds.springtodo.service.ArtistaService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@RestController
@RequestMapping(path="/albums")
public class AlbumController extends BaseController {
    @Autowired
    ArtistaService artistaService;

    @Autowired
    AlbumService albumService;

    @GetMapping("")
    @JsonView(Views.Public.class)
    public Collection<Album> getAllAlbums(HttpSession httpSession){
        comprovarLogejat(httpSession);
        return albumService.obtenirAlbums();
    }

    @PostMapping("")
    public void addNewAlbum(HttpSession httpSession, @RequestBody NewAlbum newAlbum){
        Long id = obtenirSessioUsuari(httpSession);

        Artista artista = artistaService.obtenirPerUsuariId(id);

        Album album = new Album(newAlbum.titol, newAlbum.imatge, newAlbum.descripcio, artista);
        albumService.guardarAlbum(album);
    }

    @GetMapping("/{id}")
    @JsonView(Views.Public.class)
    public Album getAlbumById(HttpSession httpSession, @PathVariable("id") Long id){
        comprovarLogejat(httpSession);
        return albumService.obtenirAlbumPerId(id);
    }

    @PutMapping( "/{id}")
    public void modifyImageAlbum(HttpSession httpSession, @PathVariable("id") Long id, @Valid @RequestBody UpdateAlbum albumUpdate){
        Long idUsuari = obtenirSessioUsuari(httpSession);

        Artista artista = artistaService.obtenirPerUsuariId(idUsuari);

        Album album = albumService.obtenirAlbumPerId(id);

        if(albumService.buscarAlbumPerArtista(artista).contains(album)){
            if(albumUpdate.titol != null)
                album.setTitol(albumUpdate.titol);
            if(albumUpdate.imatge != null)
                album.setImatge(albumUpdate.imatge);
            if(albumUpdate.descripcio != null)
                album.setDescripcio(albumUpdate.descripcio);

            albumService.guardarAlbum(album);
        }else
            throw new ServiceException("Aquest album no forma part del artista");
    }

    @GetMapping("/titol/{titol}")
    @JsonView(Views.Public.class)
    public Collection<Album> getAllAlbumsByTitle(HttpSession httpSession, @PathVariable("titol") String titol){
        comprovarLogejat(httpSession);
        return albumService.buscarAlbumPerTitol(titol);
    }

    @GetMapping("/artista/{id}")
    @JsonView(Views.Public.class)
    public Collection<Album> getAllAlbumsByArtista(HttpSession httpSession, @PathVariable("id") Long idArtista){
        comprovarLogejat(httpSession);
        Artista artista = artistaService.obtenirPerId(idArtista);
        return albumService.buscarAlbumPerArtista(artista);
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
