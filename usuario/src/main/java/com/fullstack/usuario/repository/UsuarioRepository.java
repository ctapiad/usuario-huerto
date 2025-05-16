package com.fullstack.usuario.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fullstack.usuario.model.entity.UsuarioEntity;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {

    UsuarioEntity findByRut(String rut);
    void deleteByRut(String rut);
    boolean existsByRut(String rut);


}
