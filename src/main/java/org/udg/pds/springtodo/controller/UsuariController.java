package org.udg.pds.springtodo.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.udg.pds.springtodo.entity.Usuari;
import org.udg.pds.springtodo.entity.Views;
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

    @GetMapping
    public Collection<Usuari> getAllUsers(HttpSession session){
        return null;
    }

    @GetMapping("/{id}")
    public Usuari getUserById(HttpSession session, @PathVariable("id") Long userId){
        return null;
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
        return null;
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

    static class RegisterUser {
        @NotNull
        public String username;
        @NotNull
        public String email;
        @NotNull
        public String password;
    }
}
