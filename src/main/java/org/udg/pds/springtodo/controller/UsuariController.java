package org.udg.pds.springtodo.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
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
    private Usuari updateProfileUser(HttpSession session, @Valid  @RequestBody UpdateUser ru) {

        Long loggedUserId = obtenirSessioUsuari(session);
        Usuari user = usuariService.getUser(loggedUserId);
        //Si he modificat el email o el username
        if (!user.getEmail().equals(ru.email) || !user.getNomUsuari().equals(ru.username)) {
            //Si he modificat el nom usuari mirem si existeix
            if(!user.getNomUsuari().equals(ru.username)) {
                if (usuariService.noExisteixNom(ru.username)) {
                    user.setNomUsuari(ru.username);
                } else {
                    throw new ServiceException("El nom usuari o email ja existeixen");
                }
            }
             if(!user.getEmail().equals(ru.email)){
                if (usuariService.noExisteixEmail(ru.email)){
                    user.setEmail(ru.email);
                }else{
                    throw new ServiceException("El nom usuari o email ja existeixen");
                }
            }
        }
        user.setDescription(ru.description);
        user.setImage(ru.image);
        usuariService.updateProfileUser(user);
        return user;
    }

    //Actualitzem la contrasenya
    @PutMapping(path = "/changePassword")
    private Usuari changePassword(HttpSession session, @Valid  @RequestBody ChangePassword ru) {
        Long loggedUserId = obtenirSessioUsuari(session);
        Usuari user = usuariService.getUser(loggedUserId);
        String encryptedPassword = BCrypt.withDefaults().hashToString(12, ru.newPassword.toCharArray());
        user.setPassword(encryptedPassword);
        usuariService.updateProfileUser(user);
        return user;
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
    public Usuari registerUser(HttpSession session,@Valid  @RequestBody RegisterUser ru){
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
                session.setAttribute("simpleapp_auth_id", u.getId());
                return u;
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
    static class ChangePassword{
        @NotNull
        public String newPassword;
    }
}
