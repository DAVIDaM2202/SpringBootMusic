package org.udg.pds.springtodo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.udg.pds.springtodo.entity.Artista;

@Repository
public interface ArtistaRepository extends JpaRepository<Artista,Long> {
}
