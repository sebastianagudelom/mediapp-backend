package com.mediapp.citasbackend.controllers;

import com.mediapp.citasbackend.entities.Especialidad;
import com.mediapp.citasbackend.services.interfaces.EspecialidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/especialidades")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EspecialidadController {

    private final EspecialidadService especialidadService;

    /**
     * Crear una nueva especialidad
     */
    @PostMapping
    public ResponseEntity<Especialidad> crearEspecialidad(@RequestBody Especialidad especialidad) {
        try {
            Especialidad nuevaEspecialidad = especialidadService.guardarEspecialidad(especialidad);
            return new ResponseEntity<>(nuevaEspecialidad, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Obtener todas las especialidades
     */
    @GetMapping
    public ResponseEntity<List<Especialidad>> obtenerTodasLasEspecialidades() {
        List<Especialidad> especialidades = especialidadService.obtenerTodasLasEspecialidades();
        return ResponseEntity.ok(especialidades);
    }

    /**
     * Obtener especialidad por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Especialidad> obtenerEspecialidadPorId(@PathVariable Integer id) {
        Optional<Especialidad> especialidad = especialidadService.obtenerEspecialidadPorId(id);
        return especialidad.map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Actualizar una especialidad
     */
    @PutMapping("/{id}")
    public ResponseEntity<Especialidad> actualizarEspecialidad(
            @PathVariable Integer id,
            @RequestBody Especialidad especialidad) {
        try {
            Especialidad especialidadActualizada = especialidadService.actualizarEspecialidad(id, especialidad);
            return ResponseEntity.ok(especialidadActualizada);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Eliminar una especialidad
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEspecialidad(@PathVariable Integer id) {
        try {
            especialidadService.eliminarEspecialidad(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Obtener especialidad por nombre exacto
     */
    @GetMapping("/nombre/{nombreEspecialidad}")
    public ResponseEntity<Especialidad> obtenerEspecialidadPorNombre(
            @PathVariable String nombreEspecialidad) {
        Optional<Especialidad> especialidad = especialidadService.obtenerEspecialidadPorNombre(nombreEspecialidad);
        return especialidad.map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Verificar si existe una especialidad por nombre
     */
    @GetMapping("/existe/nombre/{nombreEspecialidad}")
    public ResponseEntity<Boolean> existeEspecialidadPorNombre(@PathVariable String nombreEspecialidad) {
        boolean existe = especialidadService.existeEspecialidadPorNombre(nombreEspecialidad);
        return ResponseEntity.ok(existe);
    }

    /**
     * Obtener especialidades por estado
     */
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Especialidad>> obtenerEspecialidadesPorEstado(
            @PathVariable Especialidad.Estado estado) {
        List<Especialidad> especialidades = especialidadService.obtenerEspecialidadesPorEstado(estado);
        return ResponseEntity.ok(especialidades);
    }

    /**
     * Obtener especialidades activas
     */
    @GetMapping("/activas")
    public ResponseEntity<List<Especialidad>> obtenerEspecialidadesActivas() {
        List<Especialidad> especialidades = especialidadService.obtenerEspecialidadesActivas();
        return ResponseEntity.ok(especialidades);
    }

    /**
     * Buscar especialidades por nombre (búsqueda parcial)
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<Especialidad>> buscarEspecialidadesPorNombre(
            @RequestParam String nombre) {
        List<Especialidad> especialidades = especialidadService.buscarEspecialidadesPorNombre(nombre);
        return ResponseEntity.ok(especialidades);
    }

    /**
     * Buscar especialidades activas por nombre (búsqueda parcial)
     */
    @GetMapping("/buscar/activas")
    public ResponseEntity<List<Especialidad>> buscarEspecialidadesActivasPorNombre(
            @RequestParam String nombre) {
        List<Especialidad> especialidades = especialidadService.buscarEspecialidadesActivasPorNombre(nombre);
        return ResponseEntity.ok(especialidades);
    }

    /**
     * Buscar especialidades por descripción
     */
    @GetMapping("/buscar/descripcion")
    public ResponseEntity<List<Especialidad>> buscarEspecialidadesPorDescripcion(
            @RequestParam String texto) {
        List<Especialidad> especialidades = especialidadService.buscarEspecialidadesPorDescripcion(texto);
        return ResponseEntity.ok(especialidades);
    }

    /**
     * Contar especialidades por estado
     */
    @GetMapping("/contar/estado/{estado}")
    public ResponseEntity<Long> contarEspecialidadesPorEstado(@PathVariable Especialidad.Estado estado) {
        Long count = especialidadService.contarEspecialidadesPorEstado(estado);
        return ResponseEntity.ok(count);
    }

    /**
     * Contar especialidades activas
     */
    @GetMapping("/contar/activas")
    public ResponseEntity<Long> contarEspecialidadesActivas() {
        Long count = especialidadService.contarEspecialidadesActivas();
        return ResponseEntity.ok(count);
    }

    /**
     * Obtener especialidades ordenadas alfabéticamente
     */
    @GetMapping("/ordenadas")
    public ResponseEntity<List<Especialidad>> obtenerEspecialidadesOrdenadas() {
        List<Especialidad> especialidades = especialidadService.obtenerEspecialidadesOrdenadas();
        return ResponseEntity.ok(especialidades);
    }

    /**
     * Obtener especialidades que tienen médicos asociados
     */
    @GetMapping("/con-medicos")
    public ResponseEntity<List<Especialidad>> obtenerEspecialidadesConMedicos() {
        List<Especialidad> especialidades = especialidadService.obtenerEspecialidadesConMedicos();
        return ResponseEntity.ok(especialidades);
    }

    /**
     * Obtener especialidades recientes
     */
    @GetMapping("/recientes")
    public ResponseEntity<List<Especialidad>> obtenerEspecialidadesRecientes() {
        List<Especialidad> especialidades = especialidadService.obtenerEspecialidadesRecientes();
        return ResponseEntity.ok(especialidades);
    }

    /**
     * Activar especialidad
     */
    @PatchMapping("/{id}/activar")
    public ResponseEntity<Especialidad> activarEspecialidad(@PathVariable Integer id) {
        try {
            Especialidad especialidad = especialidadService.activarEspecialidad(id);
            return ResponseEntity.ok(especialidad);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Desactivar especialidad
     */
    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Especialidad> desactivarEspecialidad(@PathVariable Integer id) {
        try {
            Especialidad especialidad = especialidadService.desactivarEspecialidad(id);
            return ResponseEntity.ok(especialidad);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Verificar si el nombre es único (excluyendo un ID específico)
     */
    @GetMapping("/nombre-unico")
    public ResponseEntity<Boolean> nombreEsUnico(
            @RequestParam String nombre,
            @RequestParam(required = false) Integer idExcluir) {
        boolean esUnico = especialidadService.nombreEsUnico(nombre, idExcluir);
        return ResponseEntity.ok(esUnico);
    }
}
