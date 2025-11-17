package com.fullstack.usuario.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComunaDto {
    private Integer idComuna;
    private String nombre;
    private String region;
}