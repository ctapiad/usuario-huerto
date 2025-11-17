package com.fullstack.usuario.model.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.Data;

@Document(collection = "usuario")
@Data
public class UsuarioEntity {

    @Id
    private String id;

    @Field("nombre")
    private String nombre;

    @Field("email")
    private String email;

    @Field("password")
    private String password;

    @Field("fecha_registro")
    private Date fechaRegistro;

    @Field("direccion")
    private String direccion;

    @Field("telefono")
    private Integer telefono;

    @Field("id_comuna")
    private Integer idComuna;

    @Field("id_tipo_usuario")
    private Integer idTipoUsuario;

    // Constructor por defecto
    public UsuarioEntity() {
        this.fechaRegistro = new Date(); // Asigna fecha actual por defecto
    }
}
