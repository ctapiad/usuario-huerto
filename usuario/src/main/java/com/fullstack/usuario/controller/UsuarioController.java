package com.fullstack.usuario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fullstack.usuario.model.Usuario;
import com.fullstack.usuario.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService1;

    @Operation (summary = "Mostrar todos los usuarios")
    @GetMapping("/usuarios")
    public ResponseEntity <List<Usuario>> mostrarUsuarios(){
        if (usuarioService1.getAllUsuarios() != null) {
            return ResponseEntity.ok(usuarioService1.getAllUsuarios());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/usuarios/{rut}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable String rut){
        if (usuarioService1.obtenerUsuario(rut) != null) {
            return ResponseEntity.ok(usuarioService1.obtenerUsuario(rut));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/usuarios")
    public ResponseEntity<String> crearUsuario(@RequestBody Usuario usuario){
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/usuarios/{rut}")
    public ResponseEntity<String> borrarUsuario(@PathVariable String rut){
        if (usuarioService1.borrarUsuario(rut) != null){
            return ResponseEntity.ok(usuarioService1.borrarUsuario(rut));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/usuarios")
    public ResponseEntity<String> modificarUsuario(@RequestBody Usuario usuario){
        if(usuarioService1.modificarUsuario(usuario) != null){
            return ResponseEntity.ok(usuarioService1.modificarUsuario(usuario));
        }
        return ResponseEntity.notFound().build();
    }

}


