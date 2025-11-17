package com.fullstack.usuario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;

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
        usuario = new Usuario();
        usuario.setId("507f1f77bcf86cd799439011");
        usuario.setNombre("Juan Pérez");
        usuario.setEmail("juan.perez@huerto.cl");
        usuario.setPassword("password123");
        usuario.setFechaRegistro(new Date());
        usuario.setDireccion("Calle Falsa 123");
        usuario.setTelefono(123456789);
        usuario.setIdComuna(1);
        usuario.setIdTipoUsuario(2);

        usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId("507f1f77bcf86cd799439011");
        usuarioEntity.setNombre("Juan Pérez");
        usuarioEntity.setEmail("juan.perez@huerto.cl");
        usuarioEntity.setPassword("password123");
        usuarioEntity.setFechaRegistro(new Date());
        usuarioEntity.setDireccion("Calle Falsa 123");
        usuarioEntity.setTelefono(123456789);
        usuarioEntity.setIdComuna(1);
        usuarioEntity.setIdTipoUsuario(2);
    }

    @Test
    public void testCrearUsuario_nuevo(){
        when(usuarioRepository.existsByEmail(anyString())).thenReturn(false);
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuarioEntity);

        String result = usuarioService.crearUsuario(usuario);
        assertEquals("Usuario creado correctamente", result);
    }

    @Test
    public void testCrearUsuario_emailExiste() {
        when(usuarioRepository.existsByEmail(anyString())).thenReturn(true);

        String result = usuarioService.crearUsuario(usuario);
        assertEquals("Ya existe un usuario con ese email", result);
    }

    @Test
    public void testObtenerUsuarioPorId() {
        when(usuarioRepository.findById(anyString())).thenReturn(Optional.of(usuarioEntity));

        Usuario result = usuarioService.obtenerUsuario("507f1f77bcf86cd799439011");
        assertNotNull(result);
        assertEquals(usuario.getId(), result.getId());
        assertEquals(usuario.getNombre(), result.getNombre());
        assertEquals(usuario.getEmail(), result.getEmail());
    }

    @Test
    public void testObtenerUsuarioNoExiste() {
        when(usuarioRepository.findById(anyString())).thenReturn(Optional.empty());

        Usuario result = usuarioService.obtenerUsuario("507f1f77bcf86cd799439011");
        assertEquals(null, result);
    }

    @Test
    public void testObtenerUsuarioPorEmail() {
        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.of(usuarioEntity));

        Usuario result = usuarioService.obtenerUsuarioPorEmail("juan.perez@huerto.cl");
        assertNotNull(result);
        assertEquals(usuario.getEmail(), result.getEmail());
    }

    @Test
    public void testModificarUsuario_existe() {
        when(usuarioRepository.findById(anyString())).thenReturn(Optional.of(usuarioEntity));
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuarioEntity);

        String result = usuarioService.modificarUsuario(usuario);
        assertTrue(result.contains("modificado correctamente"));
    }

    @Test
    public void testBorrarUsuario() {
        when(usuarioRepository.existsById(anyString())).thenReturn(true);

        String result = usuarioService.borrarUsuario("507f1f77bcf86cd799439011");
        assertTrue(result.contains("eliminado correctamente"));
    }

}
