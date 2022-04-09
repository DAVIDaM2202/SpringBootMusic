package org.udg.pds.springtodo.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity(name = "canco")
public class Canco implements Serializable {

    private static final long serialVersionUID = 1L;

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
    @ManyToOne
    @JoinColumn(name="artista")
    private Artista artista;

    /*
    @ManyToOne
    @JoinColumn(name="album")
    private Album album;

    @ManyToMany (mappedBy = "cancons")
    @JoinTable(name = "cancons_playlist", joinColumns = @JoinColumn(name = "idCanco"), inverseJoinColumns = @JoinColumn(name = "idPlaylist"))
    Set<Playlist> playslists=new HashSet<>();
    */

    //Constructors
    public Canco(){}

    public Canco(String nomCanco, String genere, Integer any, String imatge, Artista artista) {
        this.nomCanco = nomCanco;
        this.genere = genere;
        this.any = any;
        this.imatge = imatge;
        this.artista = artista;
    }

    public Canco(Long idCanco, String nomCanco, String genere, Integer any, String artista, String imatge, Integer idAlbum, Integer reproduccions, Double valoracio, Integer idUsuari) {
        this.idCanco = idCanco;
        this.nomCanco = nomCanco;
        this.genere = genere;
        this.any = any;
        this.imatge = imatge;
        this.reproduccions = reproduccions;
        this.valoracio = valoracio;
    }

    //Getters i setters
    public Long getIdCanco() {
        return idCanco;
    }

    public void setIdCanco(Long id) {
        this.idCanco = id;
    }

    public String nomCanco() {
        return nomCanco;
    }

    public void setNomCanco(String nomCanco) {this.nomCanco = nomCanco;}

    public String genere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public Integer getAny() {
        return any;
    }

    public void setAny(Integer any) {
        this.any = any;
    }

    public String getImatge() {
        return imatge;
    }

    public void setImage(String imatge) {
        this.imatge = imatge;
    }

    public Integer getReproduccions() {
        return reproduccions;
    }

    public void setReproduccions(Integer reproduccions) {this.reproduccions = reproduccions; }

    public Double getValoracio() {
        return valoracio;
    }

    public void setValoracio(Double valoracio) {
        this.valoracio = valoracio;
    }
}
