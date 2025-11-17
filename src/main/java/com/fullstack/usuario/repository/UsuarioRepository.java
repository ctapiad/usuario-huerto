package com.fullstack.usuario.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.fullstack.usuario.model.entity.UsuarioEntity;

@Repository
public interface UsuarioRepository extends MongoRepository<UsuarioEntity, String> {

    Optional<UsuarioEntity> findByEmail(String email);
    boolean existsByEmail(String email);
    List<UsuarioEntity> findByIdTipoUsuario(Integer idTipoUsuario);
    List<UsuarioEntity> findByIdComuna(Integer idComuna);
    
    @Query("{ 'nombre': { $regex: ?0, $options: 'i' } }")
    List<UsuarioEntity> findByNombreContaining(String nombre);

}
