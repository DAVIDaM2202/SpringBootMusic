package org.udg.pds.springtodo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.udg.pds.springtodo.entity.Usuari;

@Repository
public interface UsuariRepository extends JpaRepository<Usuari,Long> {

}
