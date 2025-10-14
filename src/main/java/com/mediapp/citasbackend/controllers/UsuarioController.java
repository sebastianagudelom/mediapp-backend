package com.mediapp.citasbackend.controllers;

import com.mediapp.citasbackend.entities.Usuario;
import com.mediapp.citasbackend.services.interfaces.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;

    /**
     * Crear un nuevo usuario
     */
    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario nuevoUsuario = usuarioService.guardarUsuario(usuario);
            return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Obtener todos los usuarios
     */
    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerTodosLosUsuarios() {
        List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    /**
     * Obtener usuario por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Integer id) {
        Optional<Usuario> usuario = usuarioService.obtenerUsuarioPorId(id);
        return usuario.map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Actualizar un usuario
     */
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(
            @PathVariable Integer id,
            @RequestBody Usuario usuario) {
        try {
            Usuario usuarioActualizado = usuarioService.actualizarUsuario(id, usuario);
            return ResponseEntity.ok(usuarioActualizado);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Eliminar un usuario
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer id) {
        try {
            usuarioService.eliminarUsuario(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Obtener usuario por email
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> obtenerUsuarioPorEmail(@PathVariable String email) {
        Optional<Usuario> usuario = usuarioService.obtenerUsuarioPorEmail(email);
        return usuario.map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Verificar si existe un email
     */
    @GetMapping("/email/existe/{email}")
    public ResponseEntity<Boolean> existeEmail(@PathVariable String email) {
        boolean existe = usuarioService.existeEmail(email);
        return ResponseEntity.ok(existe);
    }

    /**
     * Obtener usuario por teléfono
     */
    @GetMapping("/telefono/{telefono}")
    public ResponseEntity<Usuario> obtenerUsuarioPorTelefono(@PathVariable String telefono) {
        Optional<Usuario> usuario = usuarioService.obtenerUsuarioPorTelefono(telefono);
        return usuario.map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Verificar si existe un teléfono
     */
    @GetMapping("/telefono/existe/{telefono}")
    public ResponseEntity<Boolean> existeTelefono(@PathVariable String telefono) {
        boolean existe = usuarioService.existeTelefono(telefono);
        return ResponseEntity.ok(existe);
    }

    /**
     * Obtener usuarios por tipo
     */
    @GetMapping("/tipo/{tipoUsuario}")
    public ResponseEntity<List<Usuario>> obtenerUsuariosPorTipo(
            @PathVariable Usuario.TipoUsuario tipoUsuario) {
        List<Usuario> usuarios = usuarioService.obtenerUsuariosPorTipo(tipoUsuario);
        return ResponseEntity.ok(usuarios);
    }

    /**
     * Obtener usuarios por estado
     */
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Usuario>> obtenerUsuariosPorEstado(
            @PathVariable Usuario.Estado estado) {
        List<Usuario> usuarios = usuarioService.obtenerUsuariosPorEstado(estado);
        return ResponseEntity.ok(usuarios);
    }

    /**
     * Obtener usuarios por tipo y estado
     */
    @GetMapping("/tipo/{tipoUsuario}/estado/{estado}")
    public ResponseEntity<List<Usuario>> obtenerUsuariosPorTipoYEstado(
            @PathVariable Usuario.TipoUsuario tipoUsuario,
            @PathVariable Usuario.Estado estado) {
        List<Usuario> usuarios = usuarioService.obtenerUsuariosPorTipoYEstado(tipoUsuario, estado);
        return ResponseEntity.ok(usuarios);
    }

    /**
     * Obtener usuarios activos por tipo
     */
    @GetMapping("/activos/tipo/{tipoUsuario}")
    public ResponseEntity<List<Usuario>> obtenerUsuariosActivosPorTipo(
            @PathVariable Usuario.TipoUsuario tipoUsuario) {
        List<Usuario> usuarios = usuarioService.obtenerUsuariosActivosPorTipo(tipoUsuario);
        return ResponseEntity.ok(usuarios);
    }

    /**
     * Obtener médicos activos
     */
    @GetMapping("/medicos/activos")
    public ResponseEntity<List<Usuario>> obtenerMedicosActivos() {
        List<Usuario> medicos = usuarioService.obtenerMedicosActivos();
        return ResponseEntity.ok(medicos);
    }

    /**
     * Obtener pacientes activos
     */
    @GetMapping("/pacientes/activos")
    public ResponseEntity<List<Usuario>> obtenerPacientesActivos() {
        List<Usuario> pacientes = usuarioService.obtenerPacientesActivos();
        return ResponseEntity.ok(pacientes);
    }

    /**
     * Buscar usuarios por nombre o apellido
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<Usuario>> buscarUsuariosPorNombreOApellido(
            @RequestParam String busqueda) {
        List<Usuario> usuarios = usuarioService.buscarUsuariosPorNombreOApellido(busqueda);
        return ResponseEntity.ok(usuarios);
    }

    /**
     * Obtener usuarios por ciudad
     */
    @GetMapping("/ciudad/{ciudad}")
    public ResponseEntity<List<Usuario>> obtenerUsuariosPorCiudad(@PathVariable String ciudad) {
        List<Usuario> usuarios = usuarioService.obtenerUsuariosPorCiudad(ciudad);
        return ResponseEntity.ok(usuarios);
    }

    /**
     * Obtener usuarios por país
     */
    @GetMapping("/pais/{pais}")
    public ResponseEntity<List<Usuario>> obtenerUsuariosPorPais(@PathVariable String pais) {
        List<Usuario> usuarios = usuarioService.obtenerUsuariosPorPais(pais);
        return ResponseEntity.ok(usuarios);
    }

    /**
     * Contar usuarios por tipo
     */
    @GetMapping("/contar/tipo/{tipoUsuario}")
    public ResponseEntity<Long> contarUsuariosPorTipo(
            @PathVariable Usuario.TipoUsuario tipoUsuario) {
        Long count = usuarioService.contarUsuariosPorTipo(tipoUsuario);
        return ResponseEntity.ok(count);
    }

    /**
     * Contar usuarios activos
     */
    @GetMapping("/contar/activos")
    public ResponseEntity<Long> contarUsuariosActivos() {
        Long count = usuarioService.contarUsuariosActivos();
        return ResponseEntity.ok(count);
    }

    /**
     * Activar usuario
     */
    @PatchMapping("/{id}/activar")
    public ResponseEntity<Usuario> activarUsuario(@PathVariable Integer id) {
        try {
            Usuario usuario = usuarioService.activarUsuario(id);
            return ResponseEntity.ok(usuario);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Desactivar usuario
     */
    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Usuario> desactivarUsuario(@PathVariable Integer id) {
        try {
            Usuario usuario = usuarioService.desactivarUsuario(id);
            return ResponseEntity.ok(usuario);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Bloquear usuario
     */
    @PatchMapping("/{id}/bloquear")
    public ResponseEntity<Usuario> bloquearUsuario(@PathVariable Integer id) {
        try {
            Usuario usuario = usuarioService.bloquearUsuario(id);
            return ResponseEntity.ok(usuario);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
