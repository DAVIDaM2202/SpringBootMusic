package org.udg.pds.springtodo.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity(name="album")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "idAlbum", scope = Album.class)
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAlbum;

    @NotNull
    private String titol;

    private String imatge;

    private String descripcio;

    @NotNull
    private String nomArtista;

    @ManyToOne
    @JoinColumn(name = "artista_id")
    private Artista artista;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "album", orphanRemoval = true)
    private List<Canco> cancons;

    public Album(){};

    public Album(String titol, String imatge, String descripcio, Artista artista){
        this.titol = titol;
        this.imatge = imatge;
        this.descripcio = descripcio;
        this.artista = artista;
        this.nomArtista = artista.getJoComUsuari().getNomUsuari();
        this.cancons = new ArrayList<>();
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
    public Artista getArtista() {
        return artista;
    }

    @JsonView(Views.Public.class)
    public String getNomArtista() {return nomArtista; }

    @JsonView(Views.Public.class)
    public List<Long> getCancons() {
        List<Long> idCancons = new ArrayList<>();
        for (Canco c : cancons){
            idCancons.add(c.getIdCanco());
        }
        return idCancons;
    }

    @JsonIgnore
    public Canco getCancoAlbum(int posicio) {return cancons.get(posicio);}

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

    public void setArtista(Artista artista) {
        this.artista = artista;
    }

    public void setNomArtista(String nomArtista){this.nomArtista = nomArtista;}

    public void setCancons(List<Canco> cancons){this.cancons = cancons;}

    public void setCancoAlbum(Canco canco){this.cancons.add(canco);}
}
