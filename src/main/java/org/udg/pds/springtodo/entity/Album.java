package org.udg.pds.springtodo.entity;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name="album")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAlbum;

    @NotNull
    private String titol;

    @NotNull
    private String nomArtista;

    private String imatge;

    private String descripcio;

    @ManyToOne(fetch = FetchType.EAGER)
    private Artista artista;

    // TODO: Falta el OneToMany de Cançó però encara no està creat

    public Album(){};

    public Album(String titol, String imatge, String descripcio, Artista artista){
        this.titol = titol;
        this.imatge = imatge;
        this.descripcio = descripcio;
        this.artista = artista;
        this.nomArtista = artista.getJoComUsuari().getNomUsuari();
    };

    /**
     * GETTERS
     * */

    @JsonView(Views.Public.class)
    public Long getIdAlbum() {
        return idAlbum;
    }

    @JsonView(Views.Public.class)
    public String getTitol(){
        return titol;
    }

    @JsonView(Views.Public.class)
    public String getImatge(){
        return imatge;
    }

    @JsonView(Views.Public.class)
    public String getDescripcio(){
        return descripcio;
    }

    @JsonView(Views.Public.class)
    public String getNomArtista() {return nomArtista; }

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

    public void setNomArtista(String nomArtista){this.nomArtista = nomArtista;}
}
