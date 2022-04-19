package org.udg.pds.springtodo.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.udg.pds.springtodo.controller.exceptions.ServiceException;
import org.udg.pds.springtodo.entity.*;
import org.udg.pds.springtodo.service.ArtistaService;
import org.udg.pds.springtodo.service.CancoService;
import org.udg.pds.springtodo.service.UsuariService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@RequestMapping(path="/cancons")
@RestController
public class CancoController extends BaseController {
    @Autowired
    CancoService cancoService;
    @Autowired
    ArtistaService artistaService;
    @Autowired
    UsuariService usuariService;

    @GetMapping("")
    @JsonView(Views.Public.class)


    public Collection<Canco> getAllCancons (HttpSession httpSession){
        comprovarLogejat(httpSession);
        return cancoService.obtenirCancons();
    }

    @PostMapping
    public Canco crearCanco(HttpSession session,@RequestBody afegirCanco canco){
        comprovarLogejat(session);
        Long id = obtenirSessioUsuari(session);
        Usuari u = usuariService.getUser(id);
        if (u.getJoComArtista()!=null){
            Artista a = artistaService.obtenirPerUsuariId(id);
            Canco c = new Canco(canco.nomCanco,canco.genere,canco.any,canco.imatge,a);
            return cancoService.guardarCanco(c);
        }else {
            throw new ServiceException("No ets un artista");
        }
    }

    @GetMapping("/{id}")
    @JsonView(Views.Public.class)
    public Canco getCancoById(HttpSession httpSession, @PathVariable("id") Long id){
        comprovarLogejat(httpSession);
        return cancoService.obtenirCancoById(id);
    }

    @GetMapping("/pagination/{genre}/{offset}/{pageSize}")
    public List<Canco> getCanconsByGenere(HttpSession httpSession, @PathVariable("offset") int offset, @PathVariable("pageSize") int pageSize, @PathVariable("genre") String genre){
        comprovarLogejat(httpSession);
        return cancoService.getSongsByGenre(offset,pageSize,genre).getContent();
    }

    @PutMapping( "/{id}")
    public void modifyCanco(HttpSession httpSession, @PathVariable("id") Long id, @Valid @RequestBody UpdateCanco cancoUpdate){
        Long idUsuari = obtenirSessioUsuari(httpSession);

        Artista artista = artistaService.obtenirPerUsuariId(idUsuari);

        Canco canco = cancoService.obtenirCancoById(id);

        if(cancoService.buscarCancoPerArtista(artista).contains(canco)){
            if(cancoUpdate.nomCanco != null)
                canco.setNomCanco(cancoUpdate.nomCanco);
            if(cancoUpdate.imatge != null)
                canco.setImatge(cancoUpdate.imatge);
            if(cancoUpdate.any != null)
                canco.setAny(cancoUpdate.any);
            if(cancoUpdate.genere != null)
                canco.setGenere(cancoUpdate.genere);

            cancoService.guardarCanco(canco);
        }else
            throw new ServiceException("Aquest cançó no forma part del artista");
    }

    @DeleteMapping("/{id}")
    public void deleteCanco(HttpSession httpSession, @PathVariable("id") Long id){
        Long idUsuari = obtenirSessioUsuari(httpSession);

        Artista artista = artistaService.obtenirPerUsuariId(idUsuari);

        Canco canco = cancoService.obtenirCancoById(id);

        if(cancoService.buscarCancoPerArtista(artista).contains(canco)){
            cancoService.esborrarCanco(canco);
        }else
            throw new ServiceException("Aquesta cançó no forma part del artista");
    }

    @GetMapping("/titol/{titol}")
    @JsonView(Views.Public.class)
    public Collection<Canco> getAllCanconsByTitle(HttpSession httpSession, @PathVariable("titol") String titol){
        comprovarLogejat(httpSession);
        return cancoService.buscarCancoPerTitol(titol);
    }

    @GetMapping("/artista/{id}")
    @JsonView(Views.Public.class)
    public Collection<Canco> getAllCanconsByArtista(HttpSession httpSession, @PathVariable("id") Long idArtista){
        comprovarLogejat(httpSession);
        Artista artista = artistaService.obtenirPerId(idArtista);
        return cancoService.buscarCancoPerArtista(artista);
    }



    @GetMapping(path = "/search/{cadena}")
    @JsonView(Views.Public.class)
    public List<Canco> getSearchedSongs(HttpSession session, @PathVariable("cadena") String cadena){
        comprovarLogejat(session);
        return cancoService.obtenirCanconsContenenCadena(cadena);
    }

    @GetMapping("/artista/me")
    @JsonView(Views.Public.class)
    public Collection<Canco> getmyCancons(HttpSession httpSession){
        comprovarLogejat(httpSession);
        Long loggedUserId=obtenirSessioUsuari(httpSession);

        Artista artista = artistaService.obtenirPerUsuariId(loggedUserId);
        return cancoService.buscarCancoPerArtista(artista);
    }


    static class UpdateCanco {
        public String nomCanco;
        public String genere;
        public Integer any;
        public String imatge;
    }

    static class afegirCanco{
        public String nomCanco;
        public String genere;
        public Integer any;
        public String imatge;
    }
}
