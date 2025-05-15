package com.fullstack.usuario.model.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
public class UsuarioEntity {

    @Id
    private String rut;

    private String nombre;
    private String apellidoPaterno;
    private String correo;
    private String direccion;
    private String contraseña;
    private String rol;
    private int telefono;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false,updatable = false)
    private Date fechaNacimiento;


}
