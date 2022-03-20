package org.udg.pds.springtodo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.udg.pds.springtodo.entity.Usuari;
import org.udg.pds.springtodo.service.UsuariService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Controller
@RequestMapping(path="/usuaris")
public class UsuariController {
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
    public String logUser(HttpSession session, @Valid @RequestBody UsuariController.LoginUser usuari){
        return null;
    }

    @PostMapping(path="/logout")
    public String logOutUser(HttpSession session){
        return null;
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
        public String username;
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
