package org.udg.pds.springtodo.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.udg.pds.springtodo.entity.Artista;
import org.udg.pds.springtodo.entity.Canco;
import java.util.List;

@Repository
public interface CancoRepository extends JpaRepository<Canco,Long> {

    List<Canco> findAllByArtista(Artista artista);

    List<Canco> findAllByNomCanco(String nomCanco);

    List<Canco> getAllByNomCancoContains(String cadena, Pageable loadSix);

    @Query("SELECT c FROM canco c")
    List<Canco> getAll();


}





