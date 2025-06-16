package com.fullstack.usuario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fullstack.usuario.model.Usuario;
import com.fullstack.usuario.model.entity.UsuarioEntity;
import com.fullstack.usuario.repository.UsuarioRepository;
import com.fullstack.usuario.service.UsuarioService;

public class UsuarioTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;
    private UsuarioEntity usuarioEntity;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        usuario = new Usuario( "12.345.678-9", "Juan", "Pérez", "correo@gmail.com", 
                               "Calle Falsa 123", "contrasena123", "ADMIN", 123456789, 
                               new java.util.Date());
        usuarioEntity = new UsuarioEntity();
        usuarioEntity.setRut("12.345.678-9");
        usuarioEntity.setNombre("Juan");
        usuarioEntity.setApellido_paterno("Pérez");
        usuarioEntity.setCorreo("correo@gmail.com");
        usuarioEntity.setDireccion("Calle Falsa 123");
        usuarioEntity.setContrasena("contrasena123");
        usuarioEntity.setRol("ADMIN");
        usuarioEntity.setTelefono(123456789);
        usuarioEntity.setFecha_nacimiento(new java.util.Date());
    }

    @Test
    public void testCrearUsuario_nuevo(){
        when(usuarioRepository.existsByRut(usuario.getRut())).thenReturn(false);
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuarioEntity);

        String result = usuarioService.crearUsuario(usuario);
        assertEquals("Usuario creado correctamente", result);
    }

    @Test
    public void testCrearUsuario_existe() {
    when(usuarioRepository.existsByRut(usuario.getRut())).thenReturn(true);

    String result = usuarioService.crearUsuario(usuario);
    assertEquals(null, result);
}

    @Test
    public void testTraerUsuarioporRut() {
        when(usuarioRepository.findByRut(usuario.getRut())).thenReturn(usuarioEntity);

        Usuario result = usuarioService.obtenerUsuario(usuario.getRut());
        assertEquals(usuario.getRut(), result.getRut());
        assertEquals(usuario.getNombre(), result.getNombre());
    }

    @Test
    public void testTraerUsuarioNoExiste() {
        when(usuarioRepository.findByRut(usuario.getRut())).thenReturn(null);

        Usuario result = usuarioService.obtenerUsuario(usuario.getRut());
        assertEquals(null, result);
    }

    @Test
    public void testActualizarUsuario_existe() {
        when(usuarioRepository.existsByRut(usuario.getRut())).thenReturn(true);
        when(usuarioRepository.findByRut(usuario.getRut())).thenReturn(usuarioEntity);
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuarioEntity);

        String result = usuarioService.modificarUsuario(usuario);
        assertEquals("", result);
    }

    @Test
    public void testBorrarUsuario() {
        when(usuarioRepository.existsByRut(usuario.getRut())).thenReturn(true);
        doNothing().when(usuarioRepository).deleteByRut("12.345.678-9");

        String result = usuarioService.borrarUsuario(usuario.getRut());
        assertEquals("rut " + usuario.getRut() + " eliminado correctamente", result);
    }

}
