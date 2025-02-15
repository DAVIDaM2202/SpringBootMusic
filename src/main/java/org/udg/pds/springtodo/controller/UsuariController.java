package org.udg.pds.springtodo.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.udg.pds.springtodo.Global;
import org.udg.pds.springtodo.controller.exceptions.ControllerException;
import org.udg.pds.springtodo.controller.exceptions.ServiceException;
import org.udg.pds.springtodo.entity.Artista;
import org.udg.pds.springtodo.entity.Usuari;
import org.udg.pds.springtodo.entity.Views;
import org.udg.pds.springtodo.service.ArtistaService;
import org.udg.pds.springtodo.service.UsuariService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@RequestMapping(path="/usuaris")
@RestController
public class UsuariController extends BaseController {
    @Autowired
    UsuariService usuariService;
    @Autowired
    ArtistaService artistaService;
    @Autowired
    ImageController imageController;
    @Autowired
    Global global;
    @GetMapping
    public Collection<Usuari> getAllUsers(HttpSession session){
        return usuariService.obtenirTots();
    }
    //Ens retorna els camps del usuari
    @GetMapping("/profile")
    @JsonView(Views.Public.class)
    public Usuari getUserById(HttpSession session){
        Long loggedUserId=obtenirSessioUsuari(session);
        return usuariService.getUser(loggedUserId);
    }
    @GetMapping("/profile/{id}")
    @JsonView(Views.Public.class)
    public Usuari getProfileById(HttpSession session,@PathVariable("id") Long userId){
        obtenirSessioUsuari(session);
        return usuariService.getUser(userId);
    }

    @GetMapping("/me")
    @JsonView(Views.Public.class)
    public Usuari GetMe(HttpSession session){
        return usuariService.getUser(obtenirSessioUsuari(session));
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

        if(ru.image!=null){
            imageController.deleteImage(user.getImage().substring(user.getImage().lastIndexOf("/")+1));
            user.setImage(ru.image);
        }
        usuariService.updateUser(user);
        return user;
    }

    @GetMapping(path="/check")
    public String checkLoggedIn(HttpSession session) {
        obtenirSessioUsuari(session);
        return BaseController.OK_MESSAGE;
    }

    @GetMapping(path = "/search/{cadena}")
    @JsonView(Views.Public.class)
    public List<Usuari> getSearchedUsers(HttpSession session,@PathVariable("cadena") String cadena){
        comprovarLogejat(session);
        return usuariService.obtenirUsuarisPerNom(cadena);
    }

    @GetMapping("/me/following")
    public List<Artista> getArtistsIFollow(HttpSession httpSession){
        Long idUsuari = obtenirSessioUsuari(httpSession);
        Usuari me = usuariService.getUser(idUsuari);
        return new ArrayList<>(me.getFollowing());
    }

    @PostMapping(path="/login")
    @JsonView(Views.Private.class)
    public Usuari logUser(HttpSession session, @Valid @RequestBody LoginUser usuari){
        comprovarNoLogejat(session);

        Usuari u = usuariService.comprovarContrasenya(usuari.identity, usuari.password);
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
                Usuari u = new Usuari(ru.username,ru.email,ru.password,"http://localhost:8080/images/anonim.JPG");
                usuariService.guardarUsuari(u);
                if(ru.artist){
                    Artista a = new Artista(u);
                    artistaService.guardarArtista(a);
                    u.setJoComArtista(a);
                }
                usuariService.updateUser(u);
                session.setAttribute("simpleapp_auth_id", u.getId());
                return u;
            }
        }
        else{
            throw new ServiceException("El nom usuari o email ja existeixen");
        }

    }

    @PutMapping("/update/password")
    public Usuari changePassword(HttpSession httpSession, @RequestBody ChangePasswordUser changePasswordUser){
        Long loggedUserId = obtenirSessioUsuari(httpSession);

        Usuari usuari = usuariService.getUser(loggedUserId);

        if(usuariService.comprovarContrasenya(usuari.getNomUsuari(),changePasswordUser.currentPassword) != null){

            if(!changePasswordUser.currentPassword.equals(changePasswordUser.newPassword)){
                usuari.setPassword(changePasswordUser.newPassword);
                usuariService.guardarUsuari(usuari);
            }

        }

        return usuari;
    }

    @PutMapping("/forgot/password")
    public Usuari forgotPassword(@RequestBody ForgotPasswordUser u){
        Usuari user = usuariService.obtenirPerCorreuONom(u.email);

        if(user != null){
            user.setPassword(u.password);
            usuariService.guardarUsuari(user);
        }

        return user;
    }
    @PutMapping("/setNotifications")
    public Usuari changeNotifications(HttpSession httpSession,@RequestBody UserNotifications u){
        Long loggedUserId = obtenirSessioUsuari(httpSession);

        Usuari usuari = usuariService.getUser(loggedUserId);

        usuari.setNotificarCancons(u.notifications);
        usuariService.updateUser(usuari);
        return usuari;
    }

    @DeleteMapping("/{id}")
    public String deleteUser(HttpSession session,@PathVariable("id") Long userId){
        return null;
    }

    @PutMapping(path = "/follow")
    private Artista followUser(HttpSession session, @Valid  @RequestBody Follow follow) {
        Long loggedUserId = obtenirSessioUsuari(session);
        Usuari me = usuariService.getUser(loggedUserId);
        Artista artista = artistaService.obtenirPerId(follow.idArtista);
        Set<Artista> following = me.getFollowing();
        following.add(artista);
        me.setFollowing(following);
        usuariService.updateUser(me);
        return artista;
    }

    @PutMapping(path = "/unfollow")
    private Artista unfollowUser(HttpSession session, @Valid  @RequestBody Follow follow) {
        Long loggedUserId = obtenirSessioUsuari(session);
        Usuari me = usuariService.getUser(loggedUserId);
        Artista artista = artistaService.obtenirPerId(follow.idArtista);
        Set<Artista> following = me.getFollowing();
        following.remove(artista);
        me.setFollowing(following);
        usuariService.updateUser(me);
        return artista;
    }


    static class LoginUser {
        @NotNull
        public String identity;
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

    static class ForgotPasswordUser {
        @NotNull
        public String email;
        @NotNull
        public String password;
    }

    static class ChangePasswordUser {
        @NotNull
        public String currentPassword;
        @NotNull
        public String newPassword;
    }

    static class UserNotifications{
        @NotNull
        public Boolean notifications;
    }
    static class Follow{
        @NotNull
        public Long idArtista;
    }

}
