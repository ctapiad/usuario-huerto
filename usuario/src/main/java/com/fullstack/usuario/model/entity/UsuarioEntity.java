package com.fullstack.usuario.model.entity;

import java.util.Date;

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
    private String apellido_paterno;
    private String correo;
    private String direccion;
    private String contrasena;
    private String rol;
    private int telefono;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha_nacimiento;


}
