package org.udg.pds.springtodo.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.udg.pds.springtodo.controller.exceptions.ServiceException;
import org.udg.pds.springtodo.entity.Artista;
import org.udg.pds.springtodo.entity.Usuari;
import org.udg.pds.springtodo.entity.Views;
import org.udg.pds.springtodo.service.ArtistaService;
import org.udg.pds.springtodo.service.UsuariService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Optional;

@RequestMapping(path="/usuaris")
@RestController
public class UsuariController extends BaseController {
    @Autowired
    UsuariService usuariService;
    @Autowired
    ArtistaService artistaService;

    @GetMapping
    public Collection<Usuari> getAllUsers(HttpSession session){
        return null;
    }
    //Ens retorna els camps del usuari
    @GetMapping("/profile")
    @JsonView(Views.Public.class)
    public Usuari getUserById(HttpSession session){
        Long loggedUserId=obtenirSessioUsuari(session);
        return usuariService.getUser(loggedUserId);
    }
    //Actualitzem els camps que ens interesin entre username,email, descripcio
    @PutMapping(path = "/update")
    private String updateProfileUser(HttpSession session, @Valid  @RequestBody UpdateUser ru)
    {
        Long loggedUserId=obtenirSessioUsuari(session);
        Usuari user = usuariService.getUser(loggedUserId);
        user.setNomUsuari(ru.username);
        user.setEmail(ru.email);
        user.setDescription(ru.description);
        user.setImage(ru.image);
        usuariService.updateProfileUser(user);

        return BaseController.OK_MESSAGE;
    }



    @GetMapping(path="/check")
    public String checkLoggedIn(HttpSession session) {

        obtenirSessioUsuari(session);

        return BaseController.OK_MESSAGE;
    }

    @PostMapping(path="/login")
    @JsonView(Views.Private.class)
    public Usuari logUser(HttpSession session, @Valid @RequestBody LoginUser usuari){
        comprovarNoLogejat(session);

        Usuari u = usuariService.comprovarContrasenya(usuari.nomUsuari, usuari.password);
        session.setAttribute("simpleapp_auth_id", u.getId());
        return u;
    }

    @PostMapping(path="/logout")
    @JsonView(Views.Private.class)
    public String logout(HttpSession session) {
        obtenirSessioUsuari(session);

        session.removeAttribute("simpleapp_auth_id");
        return BaseController.OK_MESSAGE;
    }

    @PostMapping(path="/register")
    public String registerUser(HttpSession session, @Valid  @RequestBody UsuariController.RegisterUser ru){
        //confirmar que el correu no existeix i que el username tampoc
        if(usuariService.noExisteixUsuari(ru.email,ru.username)){
            //confirmar que la contrassenya no coincideix amb el nom d'usuari
            if(ru.password.equals(ru.username)){
                throw new ServiceException("La contrassenya no pot coincidir amb el nom d'usuari");
            }
            //Guardar usuari i artista si fos el cas
            else {
                Usuari u = new Usuari(ru.username,ru.email,ru.password);
                usuariService.guardarUsuari(u);
                if(ru.artist){
                    Artista a = new Artista(u);
                    artistaService.guardarArtista(a);
                    u.setJoComArtista(a);
                }
                usuariService.guardarUsuari(u);
                return "Usuari registrat correctament";
            }
        }
        else{
            throw new ServiceException("El nom usuari o email ja existeixen");
        }

    }

    @PutMapping("/{id}")
    public String updateUser(HttpSession session,@PathVariable("id") Long userId,@RequestBody Usuari u){
        return null;
    }

    @DeleteMapping("/{id}")
    public String deleteUser(HttpSession session,@PathVariable("id") Long userId){
        return null;
    }


    static class LoginUser {
        @NotNull
        public String nomUsuari;
        @NotNull
        public String password;
    }
   //Es la clase que utilitzarem per editar el perfil del usuari
    static class UpdateUser {
        @NotNull
        public String username;
        @NotNull
        public String email;
        @NotNull
        public String description;

        public String image;
    }

    static class RegisterUser {
        @NotNull
        public String username;
        @NotNull
        public String email;
        @NotNull
        public String password;
        @NotNull
        public Boolean artist;
    }
}
