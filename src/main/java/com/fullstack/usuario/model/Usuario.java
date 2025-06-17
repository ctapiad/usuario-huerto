package com.fullstack.usuario.model;



import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {


    private String rut;
    private String nombre;
    private String apellido_paterno;
    private String correo;
    private String direccion;
    private String contrasena;
    private String rol;
    private int telefono;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fecha_nacimiento;


}
