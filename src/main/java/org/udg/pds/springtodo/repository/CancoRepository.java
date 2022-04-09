package org.udg.pds.springtodo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.udg.pds.springtodo.entity.Canco;
import java.util.List;

@Repository
public interface CancoRepository extends JpaRepository<Canco,Long> {
    /*@Query("SELECT c FROM canco c WHERE c.genere=:genere")
    List<Canco> buscarPerGenere(@Param("genera") String genere);*/

    /*@Query("SELECT c FROM canco c WHERE c.idAlbum=:idAlbum")
    List<Canco> buscarPerAlbum(@Param("idAlbum") Integer idAlbum);*/

    @Query("SELECT c FROM canco c WHERE c.artista=:artista")
    List<Canco> buscarPerArtista(@Param("artista") String artista);
}





