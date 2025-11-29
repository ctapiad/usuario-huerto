package com.fullstack.usuario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fullstack.usuario.model.Usuario;
import com.fullstack.usuario.model.dto.LoginRequestDto;
import com.fullstack.usuario.model.dto.LoginResponseDto;
import com.fullstack.usuario.model.dto.UsuarioDto;
import com.fullstack.usuario.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name = "Usuario Controller", description = "API para gestión de usuarios del sistema de productos del campo")
@CrossOrigin(origins = "*")
@org.springframework.boot.autoconfigure.condition.ConditionalOnProperty(
    name = "app.database.enabled", 
    havingValue = "true", 
    matchIfMissing = true
)
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Obtener todos los usuarios")
    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> obtenerTodosLosUsuarios() {
        List<Usuario> usuarios = usuarioService.getAllUsuarios();
        if (usuarios == null || usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @Operation(summary = "Obtener un usuario por ID")
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable String id) {
        Usuario usuario = usuarioService.obtenerUsuario(id);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Obtener un usuario por email")
    @GetMapping("/usuarios/email/{email}")
    public ResponseEntity<Usuario> obtenerUsuarioPorEmail(@PathVariable String email) {
        Usuario usuario = usuarioService.obtenerUsuarioPorEmail(email);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Crear un nuevo usuario")
    @PostMapping("/usuarios")
    public ResponseEntity<String> crearUsuario(@RequestBody Usuario usuario) {
        String resultado = usuarioService.crearUsuario(usuario);
        if (resultado.contains("correctamente")) {
            return ResponseEntity.status(201).body(resultado);
        }
        return ResponseEntity.badRequest().body(resultado);
    }

    @Operation(summary = "Eliminar un usuario por ID")
    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable String id) {
        String resultado = usuarioService.borrarUsuario(id);
        if (resultado.contains("correctamente")) {
            return ResponseEntity.ok(resultado);
        }
        return ResponseEntity.badRequest().body(resultado);
    }

    @Operation(summary = "Modificar un usuario existente")
    @PutMapping("/usuarios")
    public ResponseEntity<String> modificarUsuario(@RequestBody Usuario usuario) {
        String resultado = usuarioService.modificarUsuario(usuario);
        if (resultado.contains("correctamente")) {
            return ResponseEntity.ok(resultado);
        }
        return ResponseEntity.badRequest().body(resultado);
    }

    @Operation(summary = "Obtener DTO de usuario por ID")
    @GetMapping("/usuarios/{id}/dto")
    public ResponseEntity<UsuarioDto> obtenerUsuarioDto(@PathVariable String id) {
        UsuarioDto usuarioDto = usuarioService.obtenerUsuarioDto(id);
        if (usuarioDto != null) {
            return ResponseEntity.ok(usuarioDto);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Buscar usuarios por nombre")
    @GetMapping("/usuarios/buscar/{nombre}")
    public ResponseEntity<List<Usuario>> buscarUsuariosPorNombre(@PathVariable String nombre) {
        List<Usuario> usuarios = usuarioService.buscarUsuariosPorNombre(nombre);
        if (usuarios == null || usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @Operation(summary = "Obtener usuarios por tipo")
    @GetMapping("/usuarios/tipo/{idTipoUsuario}")
    public ResponseEntity<List<Usuario>> obtenerUsuariosPorTipo(@PathVariable Integer idTipoUsuario) {
        List<Usuario> usuarios = usuarioService.obtenerUsuariosPorTipo(idTipoUsuario);
        if (usuarios == null || usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @Operation(summary = "Login de usuario")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequest) {
        LoginResponseDto response = usuarioService.login(loginRequest);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(401).body(response);
    }
}


