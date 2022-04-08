package org.udg.pds.springtodo.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name="albums")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAlbum;

    @NotNull
    private String titol;

    private String imatge;

    private String descripcio;

    @ManyToOne(fetch = FetchType.EAGER)
    private Artista artista;

    // TODO: Falta el OneToMany de Cançó però encara no està creat

    public Album(){};

    public Album(String titol, String imatge, String descripcio){
        this.titol = titol;
        this.imatge = imatge;
        this.descripcio = descripcio;
    };

    /**
     * GETTERS
     * */

    public Long getIdAlbum() {
        return idAlbum;
    }

    public String getTitol(){
        return titol;
    }

    public String getImatge(){
        return imatge;
    }

    public String getDescripcio(){
        return descripcio;
    }

    /**
     * SETTERS
     * */

    public void setTitol(String nouTitol){
        this.titol = nouTitol;
    }

    public void setImatge(String novaImatge){
        this.imatge = novaImatge;
    }

    public void setDescripcio(String novaDescripcio){
        this.descripcio = novaDescripcio;
    }
}
