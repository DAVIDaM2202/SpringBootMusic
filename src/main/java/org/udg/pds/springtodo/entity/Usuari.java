package org.udg.pds.springtodo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "usuaris")
public class Usuari implements Serializable {

    private static final long serialVersionUID = 1L;

    //Atributs
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nomUsuari;

    @NotNull
    private String email;

    @NotNull
    private String password;

    private String description; //pot ser null

    @NotNull
    private Boolean notificarCancons;


    private String image;

    //Relacions

    @JsonIgnore
    @OneToOne(mappedBy = "joComUsuari")
    private Artista joComArtista; //pot ser Null


    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "usuari_segueix_artista", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "artist_id"))
    Set<Artista> following=new HashSet<>();


    //Constructors
    public Usuari(){}

    public Usuari(String nomUsuari, String email, String password, String description, Boolean notificarCancons, String image) {
        this.nomUsuari = nomUsuari;
        this.email = email;
        this.password = password;
        this.description = description;
        this.notificarCancons = notificarCancons;
        this.image=image;
    }
    public Usuari(String nomUsuari, String email, String password,String image) {
        this.nomUsuari = nomUsuari;
        this.email = email;
        this.password = password;
        this.description = "";
        this.notificarCancons = false;
        this.image=image;
    }

    //Getters i setters

    @JsonView(Views.Public.class)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonView(Views.Public.class)
    public String getNomUsuari() {
        return nomUsuari;
    }

    public void setNomUsuari(String nomUsuari) {
        this.nomUsuari = nomUsuari;
    }

    @JsonView(Views.Public.class)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonView(Views.Public.class)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonView(Views.Public.class)
    public Boolean getNotificarCancons() {
        return notificarCancons;
    }

    public void setNotificarCancons(Boolean notificarCancons) {
        this.notificarCancons = notificarCancons;
    }

    @JsonView(Views.Public.class)
    public Artista getJoComArtista() {
        return joComArtista;
    }

    public void setJoComArtista(Artista joComArtista) {
        this.joComArtista = joComArtista;
    }

    @JsonView(Views.Public.class)
    public Set<Artista> getFollowing() {
        return following;
    }

    public void setFollowing(Set<Artista> following) {
        this.following = following;
    }

    @JsonView(Views.Public.class)
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
