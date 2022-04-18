package org.udg.pds.springtodo.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.udg.pds.springtodo.entity.Artista;
import org.udg.pds.springtodo.entity.Usuari;
import org.udg.pds.springtodo.entity.Views;
import org.udg.pds.springtodo.service.ArtistaService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping(path="/artistes")
public class ArtistaController {
    @Autowired
    ArtistaService artistaService;


    @GetMapping
    public Collection<Artista> getAllArtists(HttpSession httpSession){
        return null;
    }

    @GetMapping("/{id}")
    public Artista getArtistById(HttpSession httpSession, @PathVariable("id") Long id){
        return null;
    }
}
