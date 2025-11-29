package com.fullstack.usuario.model.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
    private boolean success;
    private String message;
    private String id;
    private String nombre;
    private String email;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fechaRegistro;
    private String direccion;
    private Integer telefono;
    private Integer idComuna;
    private Integer idTipoUsuario;
}
