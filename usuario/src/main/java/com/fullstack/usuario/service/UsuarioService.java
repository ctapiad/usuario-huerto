package com.fullstack.usuario.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fullstack.usuario.model.Usuario;
import com.fullstack.usuario.model.entity.UsuarioEntity;
import com.fullstack.usuario.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioService(){


    }

    public List<Usuario> getAllUsuarios() {
        try {
            List<UsuarioEntity> listaUsuario = (List<UsuarioEntity>) usuarioRepository.findAll();

            if (listaUsuario.isEmpty()) {
                throw new IllegalArgumentException("No hay usuarios registrados en la base de datos");
            } 

            List<Usuario> usuarios = new ArrayList<>();
            for (UsuarioEntity usuario : listaUsuario){
                Usuario nuevoUsuario = new Usuario();
                nuevoUsuario.setRut(usuario.getRut());
                nuevoUsuario.setNombre(usuario.getNombre());
                nuevoUsuario.setApellido_paterno(usuario.getApellidoPaterno());
                nuevoUsuario.setCorreo(usuario.getCorreo());
                nuevoUsuario.setDireccion(usuario.getDireccion());
                nuevoUsuario.setContraseña(usuario.getContraseña());
                nuevoUsuario.setRol(usuario.getRol());
                nuevoUsuario.setTelefono(usuario.getTelefono());
                nuevoUsuario.setFecha_nacimiento(usuario.getFechaNacimiento());
                usuarios.add(nuevoUsuario);
            }
            return usuarios;
        } catch (IllegalArgumentException e) {
            System.out.println("Error de validación: " + e.getMessage());
            return new ArrayList<>();
        } catch (Exception e) {
            System.out.println("Error al obtener los soportes: " + e.getMessage());
            return new ArrayList<>();
        }
    }


    public Usuario obtenerUsuario(String rut) {
        try {
            UsuarioEntity usuarioEntity = usuarioRepository.findByRut(rut);
            if (usuarioEntity == null) {
                throw new IllegalArgumentException("El usuario no existe");
            }
            Usuario Usuario = new Usuario();
            Usuario.setRut(usuarioEntity.getRut());
            Usuario.setNombre(usuarioEntity.getNombre());
            Usuario.setApellido_paterno(usuarioEntity.getApellidoPaterno());
            Usuario.setCorreo(usuarioEntity.getCorreo());
            Usuario.setDireccion(usuarioEntity.getDireccion());
            Usuario.setContraseña(usuarioEntity.getContraseña());
            Usuario.setRol(usuarioEntity.getRol());
            Usuario.setTelefono(usuarioEntity.getTelefono());
            Usuario.setFecha_nacimiento(usuarioEntity.getFechaNacimiento());
            return Usuario;
        } catch (IllegalArgumentException e) {
            System.out.println("Error de validación: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("Error al obtener el soporte: " + e.getMessage());
            return null;
        }
    }

    public String crearUsuario(Usuario usuario){
        try {
            Boolean estado = usuarioRepository.existsByRut(usuario.getRut());
            System.out.println();
            if (estado != true){
                UsuarioEntity usuarioNuevo = new UsuarioEntity();
                usuarioNuevo.setRut(usuario.getRut());
                usuarioNuevo.setNombre(usuario.getNombre());
                usuarioNuevo.setApellidoPaterno(usuario.getApellido_paterno());
                usuarioNuevo.setCorreo(usuario.getCorreo());
                usuarioNuevo.setDireccion(usuario.getDireccion());
                usuarioNuevo.setContraseña(usuario.getContraseña());
                usuarioNuevo.setRol(usuario.getRol());
                usuarioNuevo.setTelefono(usuario.getTelefono());
                usuarioNuevo.setFechaNacimiento(usuario.getFecha_nacimiento());
                usuarioRepository.save(usuarioNuevo);
                return "Usuario creado correctamente";
            }
            else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error al crear el usuario: " + e.getMessage());
            return null;
        }

    }


    public String borrarUsuario(String rut){

        try {
            if (usuarioRepository.existsByRut(rut)){
                usuarioRepository.deleteByRut(rut);
                System.out.println("Usuario con rut " + rut + " eliminado correctamente");
                return rut;
            }
            else {
                System.out.println("El usuario con rut " + rut + " no existe");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar el usuario: " + e.getMessage());
            return null;
        }
    }


    public String modificarUsuario (Usuario usuario){
        try {
            if (usuarioRepository.existsByRut(usuario.getRut())){
                UsuarioEntity usuarioExistente = usuarioRepository.findByRut(usuario.getRut());
                if (usuarioExistente != null) {
                usuarioExistente.setNombre(usuario.getNombre());
                usuarioExistente.setApellidoPaterno(usuario.getApellido_paterno());
                usuarioExistente.setCorreo(usuario.getCorreo());
                usuarioExistente.setDireccion(usuario.getDireccion());
                usuarioExistente.setContraseña(usuario.getContraseña());
                usuarioExistente.setRol(usuario.getRol());
                usuarioExistente.setTelefono(usuario.getTelefono());
                usuarioExistente.setFechaNacimiento(usuario.getFecha_nacimiento());
                usuarioRepository.save(usuarioExistente);
                System.out.println("Usuario con rut " + usuario.getRut() + " modificado correctamente");
                return "";
            } else {
                System.out.println("El usuario con rut " + usuario.getRut() + " no existe");
                return null;
            }
        } else {
            System.out.println("Error al modificar el usuario: ");
            return null;
        }
    } catch (Exception e) {
            System.out.println("Error al modificar el usuario: " + e.getMessage());
            return null;
        }
    }
}
