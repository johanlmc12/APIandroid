package com.example.ucevaapp20232.db;

public class Usuario {
    private int id_usuario;
    private String email;
    private String password;
    public Usuario(int id_usuario, String email, String password) {
        this.id_usuario = id_usuario;
        this.email = email;
        this.password = password;
    }
    public int getId() {
        return id_usuario;
    }

    public void setId(int id) {
        this.id_usuario = id;
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

}
