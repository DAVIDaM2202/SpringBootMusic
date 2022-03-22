package org.udg.pds.springtodo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.udg.pds.springtodo.controller.UsuariController;
import org.udg.pds.springtodo.controller.exceptions.ServiceException;
import org.udg.pds.springtodo.entity.UpdateUser;
import org.udg.pds.springtodo.entity.Usuari;
import org.udg.pds.springtodo.repository.UsuariRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UsuariService {
    @Autowired
    UsuariRepository usuariRepository;

    public void guardarUsuari(Usuari u){
        usuariRepository.save(u);
    }

    public Usuari comprovarContrasenya(String nomUsuari, String password){
        List<Usuari> uc = usuariRepository.buscarPerNomUsuari(nomUsuari);

        if (uc.size() == 0) throw new ServiceException("L'usuari no existeix");

        Usuari u = uc.get(0);

        if (u.getPassword().equals(password))
            return u;
        else
            throw new ServiceException("La contrasenya no es correcte");
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
    public Usuari updateProfileUser(UpdateUser ru, Long loggedUserId) {
        Optional<Usuari> user = usuariRepository.findById(loggedUserId);
        user.get().setNomUsuari(ru.getUsername());
        user.get().setEmail(ru.getEmail());
        user.get().setDescription(ru.getDescription());
        user.get().setImage(ru.getImage());
        final Usuari updatedUser = usuariRepository.save(user.get());

        return updatedUser;
    }

}
