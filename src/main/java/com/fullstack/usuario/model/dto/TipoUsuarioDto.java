package com.fullstack.usuario.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoUsuarioDto {
    private Long idTipoUsuario;
    private String nombre;
    private String descripcion;
}