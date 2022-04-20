package org.udg.pds.springtodo.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.udg.pds.springtodo.entity.Artista;
import org.udg.pds.springtodo.entity.Usuari;

import java.util.List;

@Repository
public interface UsuariRepository extends JpaRepository<Usuari,Long> {

    @Query("SELECT u FROM usuaris u WHERE u.nomUsuari=:nomUsuari")
    List<Usuari> buscarPerNomUsuari(@Param("nomUsuari") String nomUsuari);

    @Query("SELECT u FROM usuaris u WHERE u.email=:email")
    List<Usuari> buscarPerCorreu(@Param("email") String email);



    List<Usuari>getAllByNomUsuariContains(String cadena, Pageable loadSix);
}
