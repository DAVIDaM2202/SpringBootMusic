package org.udg.pds.springtodo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.udg.pds.springtodo.entity.Album;
import org.udg.pds.springtodo.entity.Artista;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album,Long> {
    @Query("SELECT a FROM album a where a.artista=:artista")
    List<Album> buscarAlbumPerArtistes(@Param("artista") Artista artista);

    @Query("SELECT a FROM album a where a.titol=:titol")
    List<Album> buscarAlbumPerTitol(@Param("titol") String titol);
}
