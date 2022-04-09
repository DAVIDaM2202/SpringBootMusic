package org.udg.pds.springtodo.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.udg.pds.springtodo.controller.exceptions.ServiceException;
import org.udg.pds.springtodo.entity.Artista;
import org.udg.pds.springtodo.entity.Canco;
import org.udg.pds.springtodo.entity.Usuari;
import org.udg.pds.springtodo.entity.Views;
import org.udg.pds.springtodo.service.ArtistaService;
import org.udg.pds.springtodo.service.CancoService;
import org.udg.pds.springtodo.service.UsuariService;

import javax.servlet.http.HttpSession;
import java.util.Collection;

@RequestMapping(path="/cancons")
@RestController
public class CancoController extends BaseController {
    @Autowired
    CancoService cancoService;
    @Autowired
    ArtistaService artistaService;
    @Autowired
    UsuariService usuariService;

    @GetMapping
    public Collection<Canco> getAllCancons(HttpSession session){  //Ens retorna totes les cançons
        obtenirSessioUsuari(session);
        return cancoService.getCancons();
    }

    @GetMapping(path="/{idCanco}")
    @JsonView(Views.Public.class)
    public Canco getCancoById(HttpSession session,@PathVariable("idCanco")Long cancoId){
        obtenirSessioUsuari(session);
        return cancoService.getCanco(cancoId);
    }

    /*@GetMapping(path="/{genere}")
    @JsonView(Views.Public.class)
    public Collection<Canco> getCancoByGenre(HttpSession session,@PathVariable("genere")String genere){
        obtenirSessioUsuari(session);
        return cancoService.getCanconsGenere(genere);
    }*/

    /*@GetMapping(path="/{idAlbum}")
    @JsonView(Views.Public.class)
    public Collection<Canco> getCancoByAlbum(HttpSession session,@PathVariable("idAlbum")Integer idAlbum){
        obtenirSessioUsuari(session);
        return cancoService.getCanconsAlbum(idAlbum);
    }*/

    @GetMapping(path="/{artista}")
    @JsonView(Views.Public.class)
    public Collection<Canco> getCancoByArtist(HttpSession session,@PathVariable("artista")String artista){
        obtenirSessioUsuari(session);
        return cancoService.getCanconsArtista(artista);
    }

    @PostMapping
    public Canco crearCanco(HttpSession session,@RequestBody afegirCanco canco){

        Long id = obtenirSessioUsuari(session);
        Usuari u = usuariService.getUser(id);
        if (u.getJoComArtista()!=null){
            Artista a = artistaService.obtenirPerId(id);
            Canco c = new Canco(canco.nomCanco,canco.genere,canco.any,canco.imatge,a);
            return cancoService.guardarCanco(c);
        }else {
            throw new ServiceException("No ets un artista");
        }

    }

    @DeleteMapping(path="/{id}")
    @JsonView(Views.Public.class)
    public String delCancoById(HttpSession session,@PathVariable("id")Long cancoId){
        obtenirSessioUsuari(session);
        cancoService.crud().deleteById(cancoId);
        return BaseController.OK_MESSAGE;
    }

    static class afegirCanco{
        public String nomCanco;
        public String genere;
        public Integer any;
        public String imatge;
    }
}
