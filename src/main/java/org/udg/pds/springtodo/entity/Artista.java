package org.udg.pds.springtodo.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity(name="artistes")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "idArtista", scope = Artista.class)
public class Artista {
    //Atributs
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idArtista;

    @NotNull
    private Boolean notificacionsComentaris;

    //Relacions
    @JsonIgnore
    @OneToOne
    private Usuari joComUsuari;

    @ManyToMany(mappedBy = "following")
    Set<Usuari> followers=new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "artista", orphanRemoval = true)
    Set<Album> albums = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "artista", orphanRemoval = true)
    Set<Canco> cancons = new HashSet<>();



    //Constructors

    public Artista(){}

    public Artista(Usuari usuari){
        this.joComUsuari=usuari;
        this.notificacionsComentaris=false;
    }

    //Getters i setters

    @JsonView(Views.Public.class)

    public Long getIdArtista() {
        return idArtista;
    }

    public void setIdArtista(Long idArtista) {
        this.idArtista = idArtista;
    }

    @JsonView(Views.Public.class)

    public Boolean getNotificacionsComentaris() {
        return notificacionsComentaris;
    }

    public void setNotificacionsComentaris(Boolean notificacionsComentaris) {
        this.notificacionsComentaris = notificacionsComentaris;
    }
    @JsonView(Views.Public.class)
    public Usuari getJoComUsuari() {
        return joComUsuari;
    }

    public void setJoComUsuari(Usuari joComUsuari) {
        this.joComUsuari = joComUsuari;
    }
    @JsonView(Views.Public.class)
    public Set<Long> getFollowers() {
        Set<Long> idUsers = new HashSet<>();
        for (Usuari u : followers){
            idUsers.add(u.getId());
        }
        return idUsers;
    }

    public void setFollowers(Set<Usuari> followers) {
        this.followers = followers;
    }

}
