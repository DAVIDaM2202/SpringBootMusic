package org.udg.pds.springtodo.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.udg.pds.springtodo.entity.Artista;
import org.udg.pds.springtodo.entity.Usuari;
import org.udg.pds.springtodo.entity.Views;
import org.udg.pds.springtodo.service.ArtistaService;
import org.udg.pds.springtodo.service.UsuariService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(path="/artistes")
public class ArtistaController extends BaseController {
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
