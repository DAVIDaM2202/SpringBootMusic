package org.udg.pds.springtodo.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
public class Usuari {

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

    //Relacions

    @OneToOne(mappedBy = "joComUsuari")
    private Artista joComArtista; //pot ser Null

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "usuari_segueix_artista", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "artist_id"))
    Set<Artista> following;


    //Constructors
    public Usuari(){}

    public Usuari(String nomUsuari, String email, String password, String description, Boolean notificarCancons) {
        this.nomUsuari = nomUsuari;
        this.email = email;
        this.password = password;
        this.description = description;
        this.notificarCancons = notificarCancons;
    }

    //Getters i setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomUsuari() {
        return nomUsuari;
    }

    public void setNomUsuari(String nomUsuari) {
        this.nomUsuari = nomUsuari;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getNotificarCancons() {
        return notificarCancons;
    }

    public void setNotificarCancons(Boolean notificarCancons) {
        this.notificarCancons = notificarCancons;
    }

    public Artista getJoComArtista() {
        return joComArtista;
    }

    public void setJoComArtista(Artista joComArtista) {
        this.joComArtista = joComArtista;
    }

    public Set<Artista> getFollowing() {
        return following;
    }

    public void setFollowing(Set<Artista> following) {
        this.following = following;
    }
}
