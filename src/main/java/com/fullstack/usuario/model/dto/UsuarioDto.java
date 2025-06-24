package com.fullstack.usuario.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto {
    private String rut;
    private String nombre;
    private String apellido_paterno;
    private String correo;
    private String rol;
    private int telefono;
    
   

}
