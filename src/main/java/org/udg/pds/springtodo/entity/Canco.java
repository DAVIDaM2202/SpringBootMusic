package org.udg.pds.springtodo.entity;

import com.fasterxml.jackson.annotation.JsonView;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "canco")
public class Canco {

    //Atributs
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCanco;

    @NotNull
    private String nomCanco;

    @NotNull
    private String genere;

    @NotNull
    private Integer any;

    private String imatge;

    private Integer reproduccions;

    private Double valoracio;

    //relacions

    @ManyToOne(fetch = FetchType.EAGER)
    private Artista artista;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="album")
    private Album album;

    /*@ManyToMany (mappedBy = "cancons")
    @JoinTable(name = "cancons_playlist", joinColumns = @JoinColumn(name = "idCanco"), inverseJoinColumns = @JoinColumn(name = "idPlaylist"))
    Set<Playlist> playslists=new HashSet<>();*/

    //Constructors
    public Canco(){}

    public Canco(String nomCanco, String genere, Integer any, String imatge, Artista artista) {
        this.nomCanco = nomCanco;
        this.genere = genere;
        this.any = any;
        this.imatge = imatge;
        this.artista = artista;
        this.album = null;
    }

    //Getters i setters
    @JsonView(Views.Public.class)
    public Long getIdCanco() {
        return idCanco;
    }

    public void setIdCanco(Long idCanco) {
        this.idCanco = idCanco;
    }
    @JsonView(Views.Public.class)
    public String getNomCanco() {
        return nomCanco;
    }

    public void setNomCanco(String nomCanco) {
        this.nomCanco = nomCanco;
    }
    @JsonView(Views.Public.class)
    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }
    @JsonView(Views.Public.class)
    public Integer getAny() {
        return any;
    }

    public void setAny(Integer any) {
        this.any = any;
    }
    @JsonView(Views.Public.class)
    public String getImatge() {
        return imatge;
    }

    public void setImatge(String imatge) {
        this.imatge = imatge;
    }
    @JsonView(Views.Public.class)
    public Integer getReproduccions() {
        return reproduccions;
    }

    public void setReproduccions(Integer reproduccions) {
        this.reproduccions = reproduccions;
    }
    @JsonView(Views.Public.class)
    public Double getValoracio() {
        return valoracio;
    }

    public void setValoracio(Double valoracio) {
        this.valoracio = valoracio;
    }
    @JsonView(Views.Public.class)
    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }

    public Album getAlbum(){
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }
}
