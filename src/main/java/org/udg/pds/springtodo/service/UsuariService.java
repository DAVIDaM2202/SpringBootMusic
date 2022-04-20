package org.udg.pds.springtodo.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.udg.pds.springtodo.controller.exceptions.ServiceException;
import org.udg.pds.springtodo.entity.Artista;
import org.udg.pds.springtodo.entity.Usuari;
import org.udg.pds.springtodo.repository.ArtistaRepository;
import org.udg.pds.springtodo.repository.UsuariRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UsuariService {
    @Autowired
    UsuariRepository usuariRepository;
    ArtistaRepository artistaRepository;
    public void guardarUsuari(Usuari u){
        u.setPassword(BCrypt.withDefaults().hashToString(12, u.getPassword().toCharArray()));
        usuariRepository.save(u);
    }

    public Usuari comprovarContrasenya(String identity, String password){
        List<Usuari> uc = usuariRepository.buscarPerNomUsuari(identity);

        if (uc.size() == 0)
            uc = usuariRepository.buscarPerCorreu(identity);

        if(uc.size() == 0)
            throw new ServiceException("L'usuari no existeix");

        Usuari u = uc.get(0);

        final BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), u.getPassword());

        if (!result.verified) {
            throw new ServiceException("La contrasenya no es correcte");
        }

        else return u;

    }
    //Ens retorna el usuari a partir del seu id, si no existeix ens retorna que no existeix
    public Usuari getUser(Long id) {
        Optional<Usuari> uo = usuariRepository.findById(id);
        if (uo.isPresent())
            return uo.get();
        else
            throw new ServiceException(String.format("User with id = % dos not exists", id));
    }

    public Artista getArtist(Long id) {
        Optional<Artista> artista = artistaRepository.findById(id);

        if (artista.isPresent())
            return artista.get();
        else
            throw new ServiceException("No existeix l'artista amb aquest identificador.");
    }

    //Actualitzem els camps que ens interesin entre username,email, descripcio
    public String updateUser(Usuari user) {

        usuariRepository.save(user);

        return "ok";
    }

    // Obtenir usuari per nom o correu
    public Usuari obtenirPerCorreuONom(String correuONom){
        if(!noExisteixUsuari(correuONom, correuONom)){

            List<Usuari> ul_username = usuariRepository.buscarPerNomUsuari(correuONom);
            List<Usuari> ul_email = usuariRepository.buscarPerCorreu(correuONom);

            if(!ul_username.isEmpty())
                return ul_username.get(0);

            if(!ul_email.isEmpty())
                return ul_email.get(0);
        }

        return new Usuari();
    }

    //Buscar Usuari per correu o username
    public Boolean noExisteixUsuari(String email, String username){
        return usuariRepository.buscarPerNomUsuari(username).isEmpty() && usuariRepository.buscarPerCorreu(email).isEmpty();
    }

    public Boolean noExisteixNom(String name){
        return usuariRepository.buscarPerNomUsuari(name).isEmpty();
    }
    public Boolean noExisteixEmail(String email){
        return usuariRepository.buscarPerCorreu(email).isEmpty();
    }

    public List<Usuari> obtenirUsuarisPerNom(String cadena){
        Pageable loadSix = PageRequest.of(0, 6);
        return usuariRepository.getAllByNomUsuariContains(cadena,loadSix);
    }
    public List<Usuari> obtenirTots(){
        return usuariRepository.findAll();
    }






}
