package com.fullstack.usuario.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrearUsuarioDto {
    private String nombre;
    private String email;
    private String password;
    private String direccion;
    private Integer telefono;
    private Integer idComuna;
    private Long idTipoUsuario;
}