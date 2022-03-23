package org.udg.pds.springtodo.entity;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity(name="artistes")
public class Artista {
    //Atributs
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idArtista;

    @NotNull
    private Boolean notificacionsComentaris;

    //Relacions

    @OneToOne
    @JoinColumn(name="usuari_artista")
    private Usuari joComUsuari;

    @ManyToMany(mappedBy = "following")
    Set<Usuari> followers=new HashSet<>();


    //Constructors

    public Artista(){}

    public Artista(Usuari usuari){
        this.joComUsuari=usuari;
        this.notificacionsComentaris=false;
    }

    //Getters i setters


    public Long getIdArtista() {
        return idArtista;
    }

    public void setIdArtista(Long idArtista) {
        this.idArtista = idArtista;
    }

    public Boolean getNotificacionsComentaris() {
        return notificacionsComentaris;
    }

    public void setNotificacionsComentaris(Boolean notificacionsComentaris) {
        this.notificacionsComentaris = notificacionsComentaris;
    }

    public Usuari getJoComUsuari() {
        return joComUsuari;
    }

    public void setJoComUsuari(Usuari joComUsuari) {
        this.joComUsuari = joComUsuari;
    }

    public Set<Usuari> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<Usuari> followers) {
        this.followers = followers;
    }
}
