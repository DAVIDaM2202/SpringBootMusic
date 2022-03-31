package org.udg.pds.springtodo.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.udg.pds.springtodo.controller.exceptions.ServiceException;
import org.udg.pds.springtodo.entity.Usuari;
import org.udg.pds.springtodo.repository.UsuariRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UsuariService {
    @Autowired
    UsuariRepository usuariRepository;

    public void guardarUsuari(Usuari u){
        u.setPassword(BCrypt.withDefaults().hashToString(12, u.getPassword().toCharArray()));
        u.setImage("https://www.webespacio.com/wp-content/uploads/2012/01/foto-perfil.jpg");
        usuariRepository.save(u);
    }

    public Usuari comprovarContrasenya(String nomUsuari, String password){
        List<Usuari> uc = usuariRepository.buscarPerNomUsuari(nomUsuari);

        if (uc.size() == 0) throw new ServiceException("L'usuari no existeix");

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

    //Actualitzem els camps que ens interesin entre username,email, descripcio
    public String updateProfileUser(Usuari user) {

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




}
