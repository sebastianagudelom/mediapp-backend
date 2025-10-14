package com.mediapp.citasbackend.controllers;

import com.mediapp.citasbackend.entities.Medico;
import com.mediapp.citasbackend.services.interfaces.MedicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/medicos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MedicoController {

    private final MedicoService medicoService;

    /**
     * Crear un nuevo médico
     */
    @PostMapping
    public ResponseEntity<Medico> crearMedico(@RequestBody Medico medico) {
        try {
            Medico nuevoMedico = medicoService.guardarMedico(medico);
            return new ResponseEntity<>(nuevoMedico, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Obtener todos los médicos
     */
    @GetMapping
    public ResponseEntity<List<Medico>> obtenerTodosLosMedicos() {
        List<Medico> medicos = medicoService.obtenerTodosLosMedicos();
        return ResponseEntity.ok(medicos);
    }

    /**
     * Obtener médico por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Medico> obtenerMedicoPorId(@PathVariable Integer id) {
        Optional<Medico> medico = medicoService.obtenerMedicoPorId(id);
        return medico.map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Actualizar un médico
     */
    @PutMapping("/{id}")
    public ResponseEntity<Medico> actualizarMedico(
            @PathVariable Integer id,
            @RequestBody Medico medico) {
        try {
            Medico medicoActualizado = medicoService.actualizarMedico(id, medico);
            return ResponseEntity.ok(medicoActualizado);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Eliminar un médico
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMedico(@PathVariable Integer id) {
        try {
            medicoService.eliminarMedico(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Obtener médico por ID de usuario
     */
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<Medico> obtenerMedicoPorUsuario(@PathVariable Integer idUsuario) {
        Optional<Medico> medico = medicoService.obtenerMedicoPorUsuarioId(idUsuario);
        return medico.map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Verificar si existe médico por ID de usuario
     */
    @GetMapping("/usuario/{idUsuario}/existe")
    public ResponseEntity<Boolean> existeMedicoPorUsuario(@PathVariable Integer idUsuario) {
        boolean existe = medicoService.existeMedicoPorUsuarioId(idUsuario);
        return ResponseEntity.ok(existe);
    }

    /**
     * Obtener médico por número de licencia
     */
    @GetMapping("/licencia/{numeroLicencia}")
    public ResponseEntity<Medico> obtenerMedicoPorNumeroLicencia(@PathVariable String numeroLicencia) {
        Optional<Medico> medico = medicoService.obtenerMedicoPorNumeroLicencia(numeroLicencia);
        return medico.map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Verificar si existe número de licencia
     */
    @GetMapping("/licencia/{numeroLicencia}/existe")
    public ResponseEntity<Boolean> existeNumeroLicencia(@PathVariable String numeroLicencia) {
        boolean existe = medicoService.existeNumeroLicencia(numeroLicencia);
        return ResponseEntity.ok(existe);
    }

    /**
     * Obtener médicos por especialidad
     */
    @GetMapping("/especialidad/{idEspecialidad}")
    public ResponseEntity<List<Medico>> obtenerMedicosPorEspecialidad(@PathVariable Integer idEspecialidad) {
        List<Medico> medicos = medicoService.obtenerMedicosPorEspecialidadId(idEspecialidad);
        return ResponseEntity.ok(medicos);
    }

    /**
     * Obtener médicos verificados por especialidad
     */
    @GetMapping("/especialidad/{idEspecialidad}/verificados")
    public ResponseEntity<List<Medico>> obtenerMedicosVerificadosPorEspecialidad(
            @PathVariable Integer idEspecialidad) {
        List<Medico> medicos = medicoService.obtenerMedicosVerificadosPorEspecialidad(idEspecialidad);
        return ResponseEntity.ok(medicos);
    }

    /**
     * Obtener médicos por estado de verificación
     */
    @GetMapping("/estado-verificacion/{estadoVerificacion}")
    public ResponseEntity<List<Medico>> obtenerMedicosPorEstadoVerificacion(
            @PathVariable Medico.EstadoVerificacion estadoVerificacion) {
        List<Medico> medicos = medicoService.obtenerMedicosPorEstadoVerificacion(estadoVerificacion);
        return ResponseEntity.ok(medicos);
    }

    /**
     * Obtener médicos verificados
     */
    @GetMapping("/verificados")
    public ResponseEntity<List<Medico>> obtenerMedicosVerificados() {
        List<Medico> medicos = medicoService.obtenerMedicosVerificados();
        return ResponseEntity.ok(medicos);
    }

    /**
     * Obtener médicos pendientes de verificación
     */
    @GetMapping("/pendientes-verificacion")
    public ResponseEntity<List<Medico>> obtenerMedicosPendientesVerificacion() {
        List<Medico> medicos = medicoService.obtenerMedicosPendientesVerificacion();
        return ResponseEntity.ok(medicos);
    }

    /**
     * Obtener médicos activos y verificados
     */
    @GetMapping("/activos-verificados")
    public ResponseEntity<List<Medico>> obtenerMedicosActivosVerificados() {
        List<Medico> medicos = medicoService.obtenerMedicosActivosVerificados();
        return ResponseEntity.ok(medicos);
    }

    /**
     * Obtener médicos por hospital
     */
    @GetMapping("/hospital/{hospitalAfiliado}")
    public ResponseEntity<List<Medico>> obtenerMedicosPorHospital(@PathVariable String hospitalAfiliado) {
        List<Medico> medicos = medicoService.obtenerMedicosPorHospital(hospitalAfiliado);
        return ResponseEntity.ok(medicos);
    }

    /**
     * Obtener médicos con experiencia mínima
     */
    @GetMapping("/experiencia-minima/{aniosMinimos}")
    public ResponseEntity<List<Medico>> obtenerMedicosConExperienciaMinima(@PathVariable Integer aniosMinimos) {
        List<Medico> medicos = medicoService.obtenerMedicosConExperienciaMinima(aniosMinimos);
        return ResponseEntity.ok(medicos);
    }

    /**
     * Obtener médicos por rango de experiencia
     */
    @GetMapping("/experiencia-rango")
    public ResponseEntity<List<Medico>> obtenerMedicosPorRangoExperiencia(
            @RequestParam Integer minAnos,
            @RequestParam Integer maxAnos) {
        List<Medico> medicos = medicoService.obtenerMedicosPorRangoExperiencia(minAnos, maxAnos);
        return ResponseEntity.ok(medicos);
    }

    /**
     * Obtener médicos con calificación mínima
     */
    @GetMapping("/calificacion-minima/{calificacionMinima}")
    public ResponseEntity<List<Medico>> obtenerMedicosConCalificacionMinima(
            @PathVariable BigDecimal calificacionMinima) {
        List<Medico> medicos = medicoService.obtenerMedicosConCalificacionMinima(calificacionMinima);
        return ResponseEntity.ok(medicos);
    }

    /**
     * Obtener médicos mejor calificados
     */
    @GetMapping("/mejor-calificados")
    public ResponseEntity<List<Medico>> obtenerMedicosMejorCalificados() {
        List<Medico> medicos = medicoService.obtenerMedicosMejorCalificados();
        return ResponseEntity.ok(medicos);
    }

    /**
     * Obtener médicos mejor calificados por especialidad
     */
    @GetMapping("/especialidad/{idEspecialidad}/mejor-calificados")
    public ResponseEntity<List<Medico>> obtenerMedicosMejorCalificadosPorEspecialidad(
            @PathVariable Integer idEspecialidad) {
        List<Medico> medicos = medicoService.obtenerMedicosMejorCalificadosPorEspecialidad(idEspecialidad);
        return ResponseEntity.ok(medicos);
    }

    /**
     * Obtener médicos sin calificación
     */
    @GetMapping("/sin-calificacion")
    public ResponseEntity<List<Medico>> obtenerMedicosSinCalificacion() {
        List<Medico> medicos = medicoService.obtenerMedicosSinCalificacion();
        return ResponseEntity.ok(medicos);
    }

    /**
     * Buscar médicos por nombre
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<Medico>> buscarMedicosPorNombre(@RequestParam String nombre) {
        List<Medico> medicos = medicoService.buscarMedicosPorNombre(nombre);
        return ResponseEntity.ok(medicos);
    }

    /**
     * Buscar médicos activos por nombre
     */
    @GetMapping("/buscar/activos")
    public ResponseEntity<List<Medico>> buscarMedicosActivosPorNombre(@RequestParam String nombre) {
        List<Medico> medicos = medicoService.buscarMedicosActivosPorNombre(nombre);
        return ResponseEntity.ok(medicos);
    }

    /**
     * Obtener médicos por ciudad
     */
    @GetMapping("/ciudad/{ciudad}")
    public ResponseEntity<List<Medico>> obtenerMedicosPorCiudad(@PathVariable String ciudad) {
        List<Medico> medicos = medicoService.obtenerMedicosPorCiudad(ciudad);
        return ResponseEntity.ok(medicos);
    }

    /**
     * Obtener médicos por ciudad y especialidad
     */
    @GetMapping("/ciudad/{ciudad}/especialidad/{idEspecialidad}")
    public ResponseEntity<List<Medico>> obtenerMedicosPorCiudadYEspecialidad(
            @PathVariable String ciudad,
            @PathVariable Integer idEspecialidad) {
        List<Medico> medicos = medicoService.obtenerMedicosPorCiudadYEspecialidad(ciudad, idEspecialidad);
        return ResponseEntity.ok(medicos);
    }

    /**
     * Contar médicos por especialidad
     */
    @GetMapping("/especialidad/{idEspecialidad}/contar")
    public ResponseEntity<Long> contarMedicosPorEspecialidad(@PathVariable Integer idEspecialidad) {
        Long count = medicoService.contarMedicosPorEspecialidad(idEspecialidad);
        return ResponseEntity.ok(count);
    }

    /**
     * Contar médicos verificados
     */
    @GetMapping("/contar/verificados")
    public ResponseEntity<Long> contarMedicosVerificados() {
        Long count = medicoService.contarMedicosVerificados();
        return ResponseEntity.ok(count);
    }

    /**
     * Contar médicos pendientes
     */
    @GetMapping("/contar/pendientes")
    public ResponseEntity<Long> contarMedicosPendientes() {
        Long count = medicoService.contarMedicosPendientes();
        return ResponseEntity.ok(count);
    }

    /**
     * Obtener médicos recientes
     */
    @GetMapping("/recientes")
    public ResponseEntity<List<Medico>> obtenerMedicosRecientes() {
        List<Medico> medicos = medicoService.obtenerMedicosRecientes();
        return ResponseEntity.ok(medicos);
    }

    /**
     * Obtener médico por email
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<Medico> obtenerMedicoPorEmail(@PathVariable String email) {
        Optional<Medico> medico = medicoService.obtenerMedicoPorEmail(email);
        return medico.map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Obtener médicos con biografía
     */
    @GetMapping("/con-bio")
    public ResponseEntity<List<Medico>> obtenerMedicosConBio() {
        List<Medico> medicos = medicoService.obtenerMedicosConBio();
        return ResponseEntity.ok(medicos);
    }

    /**
     * Obtener médicos disponibles (con calendario)
     */
    @GetMapping("/disponibles")
    public ResponseEntity<List<Medico>> obtenerMedicosDisponibles() {
        List<Medico> medicos = medicoService.obtenerMedicosDisponibles();
        return ResponseEntity.ok(medicos);
    }

    /**
     * Obtener médicos disponibles por especialidad
     */
    @GetMapping("/disponibles/especialidad/{idEspecialidad}")
    public ResponseEntity<List<Medico>> obtenerMedicosDisponiblesPorEspecialidad(
            @PathVariable Integer idEspecialidad) {
        List<Medico> medicos = medicoService.obtenerMedicosDisponiblesPorEspecialidad(idEspecialidad);
        return ResponseEntity.ok(medicos);
    }

    /**
     * Obtener todos los médicos ordenados por calificación
     */
    @GetMapping("/ordenados/calificacion")
    public ResponseEntity<List<Medico>> obtenerTodosLosMedicosOrdenadosPorCalificacion() {
        List<Medico> medicos = medicoService.obtenerTodosLosMedicosOrdenadosPorCalificacion();
        return ResponseEntity.ok(medicos);
    }

    /**
     * Verificar médico
     */
    @PatchMapping("/{id}/verificar")
    public ResponseEntity<Medico> verificarMedico(@PathVariable Integer id) {
        try {
            Medico medico = medicoService.verificarMedico(id);
            return ResponseEntity.ok(medico);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Marcar médico como pendiente
     */
    @PatchMapping("/{id}/pendiente")
    public ResponseEntity<Medico> marcarComoPendiente(@PathVariable Integer id) {
        try {
            Medico medico = medicoService.marcarComoPendiente(id);
            return ResponseEntity.ok(medico);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Actualizar calificación promedio del médico
     */
    @PatchMapping("/{id}/calificacion")
    public ResponseEntity<Medico> actualizarCalificacionPromedio(
            @PathVariable Integer id,
            @RequestBody Map<String, BigDecimal> request) {
        try {
            BigDecimal calificacion = request.get("calificacion");
            if (calificacion == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            Medico medico = medicoService.actualizarCalificacionPromedio(id, calificacion);
            return ResponseEntity.ok(medico);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Verificar si la licencia es única (excluyendo un ID específico)
     */
    @GetMapping("/licencia-unica")
    public ResponseEntity<Boolean> licenciaEsUnica(
            @RequestParam String numeroLicencia,
            @RequestParam(required = false) Integer idExcluir) {
        boolean esUnica = medicoService.licenciaEsUnica(numeroLicencia, idExcluir);
        return ResponseEntity.ok(esUnica);
    }
}
