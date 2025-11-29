package com.fullstack.usuario.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fullstack.usuario.model.Usuario;
import com.fullstack.usuario.model.dto.LoginRequestDto;
import com.fullstack.usuario.model.dto.LoginResponseDto;
import com.fullstack.usuario.model.dto.UsuarioDto;
import com.fullstack.usuario.model.entity.UsuarioEntity;
import com.fullstack.usuario.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UsuarioService() {
    }

    public List<Usuario> getAllUsuarios() {
        try {
            List<UsuarioEntity> listaUsuario = usuarioRepository.findAll();

            if (listaUsuario.isEmpty()) {
                System.out.println("No hay usuarios registrados en la base de datos");
                return new ArrayList<>();
            } 

            List<Usuario> usuarios = new ArrayList<>();
            for (UsuarioEntity usuarioEntity : listaUsuario) {
                usuarios.add(convertirEntityAUsuario(usuarioEntity));
            }
            return usuarios;
        } catch (Exception e) {
            System.out.println("Error al obtener los usuarios: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public Usuario obtenerUsuario(String id) {
        try {
            Optional<UsuarioEntity> usuarioEntity = usuarioRepository.findById(id);
            
            if (usuarioEntity.isPresent()) {
                return convertirEntityAUsuario(usuarioEntity.get());
            } else {
                throw new IllegalArgumentException("El usuario no existe");
            }
        } catch (Exception e) {
            System.out.println("Error al obtener el usuario: " + e.getMessage());
            return null;
        }
    }

    public Usuario obtenerUsuarioPorEmail(String email) {
        try {
            Optional<UsuarioEntity> usuarioEntity = usuarioRepository.findByEmail(email);
            if (usuarioEntity.isPresent()) {
                return convertirEntityAUsuario(usuarioEntity.get());
            } else {
                throw new IllegalArgumentException("El usuario no existe");
            }
        } catch (Exception e) {
            System.out.println("Error al obtener el usuario por email: " + e.getMessage());
            return null;
        }
    }

    public String crearUsuario(Usuario usuario) {
        try {
            // Verificar si ya existe un usuario con ese email
            if (usuarioRepository.existsByEmail(usuario.getEmail())) {
                return "Ya existe un usuario con ese email";
            }

            UsuarioEntity usuarioNuevo = new UsuarioEntity();
            // MongoDB genera automáticamente el ID, no lo establecemos
            usuarioNuevo.setNombre(usuario.getNombre());
            usuarioNuevo.setEmail(usuario.getEmail());
            // Hashear la contraseña con BCrypt antes de guardarla
            usuarioNuevo.setPassword(passwordEncoder.encode(usuario.getPassword()));
            usuarioNuevo.setFechaRegistro(usuario.getFechaRegistro() != null ? usuario.getFechaRegistro() : new Date());
            usuarioNuevo.setDireccion(usuario.getDireccion());
            usuarioNuevo.setTelefono(usuario.getTelefono());
            usuarioNuevo.setIdComuna(usuario.getIdComuna());
            usuarioNuevo.setIdTipoUsuario(usuario.getIdTipoUsuario());

            usuarioRepository.save(usuarioNuevo);
            return "Usuario creado correctamente";
        } catch (Exception e) {
            System.out.println("Error al crear el usuario: " + e.getMessage());
            return "Error al crear el usuario: " + e.getMessage();
        }
    }

    public String borrarUsuario(String id) {
        try {
            if (usuarioRepository.existsById(id)) {
                usuarioRepository.deleteById(id);
                return "Usuario con ID " + id + " eliminado correctamente";
            } else {
                return "El usuario con ID " + id + " no existe";
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar el usuario: " + e.getMessage());
            return "Error al eliminar el usuario: " + e.getMessage();
        }
    }

    public String modificarUsuario(Usuario usuario) {
        try {
            // Buscar el usuario por su ObjectId
            Optional<UsuarioEntity> usuarioOpt = usuarioRepository.findById(usuario.getId());
                
            if (usuarioOpt.isPresent()) {
                UsuarioEntity usuarioExistente = usuarioOpt.get();
                
                // Validar email duplicado si se está actualizando
                if (usuario.getEmail() != null && !usuario.getEmail().equals(usuarioExistente.getEmail())) {
                    Optional<UsuarioEntity> usuarioConEmail = usuarioRepository.findByEmail(usuario.getEmail());
                    if (usuarioConEmail.isPresent() && !usuarioConEmail.get().getId().equals(usuario.getId())) {
                        return "Ya existe otro usuario con ese email";
                    }
                }
                
                // Solo actualizar los campos que no son null
                if (usuario.getNombre() != null) {
                    usuarioExistente.setNombre(usuario.getNombre());
                }
                if (usuario.getEmail() != null) {
                    usuarioExistente.setEmail(usuario.getEmail());
                }
                if (usuario.getPassword() != null) {
                    // Hashear la contraseña si se está actualizando
                    usuarioExistente.setPassword(passwordEncoder.encode(usuario.getPassword()));
                }
                if (usuario.getDireccion() != null) {
                    usuarioExistente.setDireccion(usuario.getDireccion());
                }
                if (usuario.getTelefono() != null) {
                    usuarioExistente.setTelefono(usuario.getTelefono());
                }
                if (usuario.getIdComuna() != null) {
                    usuarioExistente.setIdComuna(usuario.getIdComuna());
                }
                if (usuario.getIdTipoUsuario() != null) {
                    usuarioExistente.setIdTipoUsuario(usuario.getIdTipoUsuario());
                }
                    
                usuarioRepository.save(usuarioExistente);
                return "Usuario con ID " + usuario.getId() + " modificado correctamente";
            } else {
                return "El usuario con ID " + usuario.getId() + " no existe";
            }
        } catch (Exception e) {
            System.out.println("Error al modificar el usuario: " + e.getMessage());
            return "Error al modificar el usuario: " + e.getMessage();
        }
    }

    public UsuarioDto obtenerUsuarioDto(String id) {
        try {
            Optional<UsuarioEntity> usuarioOpt = usuarioRepository.findById(id);
                
            if (usuarioOpt.isPresent()) {
                return convertirEntityAUsuarioDto(usuarioOpt.get());
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error al obtener el usuario DTO: " + e.getMessage());
            return null;
        }
    }

    public List<Usuario> buscarUsuariosPorNombre(String nombre) {
        try {
            List<UsuarioEntity> listaUsuarios = usuarioRepository.findByNombreContaining(nombre);
            List<Usuario> usuarios = new ArrayList<>();
            for (UsuarioEntity usuarioEntity : listaUsuarios) {
                usuarios.add(convertirEntityAUsuario(usuarioEntity));
            }
            return usuarios;
        } catch (Exception e) {
            System.out.println("Error al buscar usuarios por nombre: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<Usuario> obtenerUsuariosPorTipo(Integer idTipoUsuario) {
        try {
            List<UsuarioEntity> listaUsuarios = usuarioRepository.findByIdTipoUsuario(idTipoUsuario);
            List<Usuario> usuarios = new ArrayList<>();
            for (UsuarioEntity usuarioEntity : listaUsuarios) {
                usuarios.add(convertirEntityAUsuario(usuarioEntity));
            }
            return usuarios;
        } catch (Exception e) {
            System.out.println("Error al obtener usuarios por tipo: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Métodos de conversión
    private Usuario convertirEntityAUsuario(UsuarioEntity entity) {
        Usuario usuario = new Usuario();
        usuario.setId(entity.getId());
        usuario.setNombre(entity.getNombre());
        usuario.setEmail(entity.getEmail());
        usuario.setPassword(entity.getPassword());
        usuario.setFechaRegistro(entity.getFechaRegistro());
        usuario.setDireccion(entity.getDireccion());
        usuario.setTelefono(entity.getTelefono());
        usuario.setIdComuna(entity.getIdComuna());
        usuario.setIdTipoUsuario(entity.getIdTipoUsuario());
        return usuario;
    }

    private UsuarioDto convertirEntityAUsuarioDto(UsuarioEntity entity) {
        UsuarioDto dto = new UsuarioDto();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setEmail(entity.getEmail());
        dto.setFechaRegistro(entity.getFechaRegistro());
        dto.setDireccion(entity.getDireccion());
        dto.setTelefono(entity.getTelefono());
        dto.setIdComuna(entity.getIdComuna());
        dto.setIdTipoUsuario(entity.getIdTipoUsuario());
        return dto;
    }

    // Método de autenticación de login
    public LoginResponseDto login(LoginRequestDto loginRequest) {
        try {
            // Buscar usuario por email
            Optional<UsuarioEntity> usuarioOpt = usuarioRepository.findByEmail(loginRequest.getEmail());
            
            if (!usuarioOpt.isPresent()) {
                return new LoginResponseDto(false, "Usuario no encontrado", null, null, null, null, null, null, null, null);
            }
            
            UsuarioEntity usuario = usuarioOpt.get();
            
            // Verificar la contraseña usando BCrypt
            if (!passwordEncoder.matches(loginRequest.getPassword(), usuario.getPassword())) {
                return new LoginResponseDto(false, "Contraseña incorrecta", null, null, null, null, null, null, null, null);
            }
            
            // Login exitoso, retornar datos del usuario
            return new LoginResponseDto(
                true,
                "Login exitoso",
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getFechaRegistro(),
                usuario.getDireccion(),
                usuario.getTelefono(),
                usuario.getIdComuna(),
                usuario.getIdTipoUsuario()
            );
        } catch (Exception e) {
            System.out.println("Error en el proceso de login: " + e.getMessage());
            return new LoginResponseDto(false, "Error en el servidor: " + e.getMessage(), null, null, null, null, null, null, null, null);
        }
    }

    // Método temporal para migrar contraseñas en texto plano a BCrypt
    public String migrarContrasenas() {
        try {
            List<UsuarioEntity> todosLosUsuarios = usuarioRepository.findAll();
            int usuariosMigrados = 0;
            int usuariosYaMigrados = 0;
            
            for (UsuarioEntity usuario : todosLosUsuarios) {
                String password = usuario.getPassword();
                
                // Verificar si la contraseña ya está hasheada con BCrypt
                // Los hashes de BCrypt comienzan con $2a$, $2b$ o $2y$
                if (password != null && !password.startsWith("$2a$") && !password.startsWith("$2b$") && !password.startsWith("$2y$")) {
                    // La contraseña está en texto plano, hashearla
                    usuario.setPassword(passwordEncoder.encode(password));
                    usuarioRepository.save(usuario);
                    usuariosMigrados++;
                    System.out.println("Contraseña migrada para usuario: " + usuario.getEmail());
                } else {
                    usuariosYaMigrados++;
                }
            }
            
            String mensaje = String.format(
                "Migración completada. Usuarios migrados: %d, Usuarios ya con BCrypt: %d, Total: %d",
                usuariosMigrados, usuariosYaMigrados, todosLosUsuarios.size()
            );
            System.out.println(mensaje);
            return mensaje;
        } catch (Exception e) {
            String errorMsg = "Error durante la migración de contraseñas: " + e.getMessage();
            System.out.println(errorMsg);
            return errorMsg;
        }
    }
}
