package org.udg.pds.springtodo.entity;

import javax.validation.constraints.NotNull;

//Es la clase que utilitzarem per editar el perfil del usuari
public class UpdateUser {

    @NotNull
    public String username;
    @NotNull
    public String email;
    @NotNull
    public String description;
    public String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
