package com.mediapp.citasbackend.controllers;

import com.mediapp.citasbackend.entities.Evaluacion;
import com.mediapp.citasbackend.services.interfaces.EvaluacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/evaluaciones")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EvaluacionController {

    private final EvaluacionService evaluacionService;

    /**
     * Crear una nueva evaluación
     */
    @PostMapping
    public ResponseEntity<Evaluacion> crearEvaluacion(@RequestBody Evaluacion evaluacion) {
        try {
            Evaluacion nuevaEvaluacion = evaluacionService.guardarEvaluacion(evaluacion);
            return new ResponseEntity<>(nuevaEvaluacion, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Obtener todas las evaluaciones
     */
    @GetMapping
    public ResponseEntity<List<Evaluacion>> obtenerTodasLasEvaluaciones() {
        List<Evaluacion> evaluaciones = evaluacionService.obtenerTodasLasEvaluaciones();
        return ResponseEntity.ok(evaluaciones);
    }

    /**
     * Obtener evaluación por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Evaluacion> obtenerEvaluacionPorId(@PathVariable Integer id) {
        Optional<Evaluacion> evaluacion = evaluacionService.obtenerEvaluacionPorId(id);
        return evaluacion.map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Actualizar una evaluación
     */
    @PutMapping("/{id}")
    public ResponseEntity<Evaluacion> actualizarEvaluacion(
            @PathVariable Integer id,
            @RequestBody Evaluacion evaluacion) {
        try {
            Evaluacion evaluacionActualizada = evaluacionService.actualizarEvaluacion(id, evaluacion);
            return ResponseEntity.ok(evaluacionActualizada);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Eliminar una evaluación
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEvaluacion(@PathVariable Integer id) {
        try {
            evaluacionService.eliminarEvaluacion(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Obtener evaluaciones por paciente
     */
    @GetMapping("/paciente/{idPaciente}")
    public ResponseEntity<List<Evaluacion>> obtenerEvaluacionesPorPaciente(@PathVariable Integer idPaciente) {
        List<Evaluacion> evaluaciones = evaluacionService.obtenerEvaluacionesPorPacienteId(idPaciente);
        return ResponseEntity.ok(evaluaciones);
    }

    /**
     * Obtener evaluaciones por paciente ordenadas
     */
    @GetMapping("/paciente/{idPaciente}/ordenadas")
    public ResponseEntity<List<Evaluacion>> obtenerEvaluacionesPorPacienteOrdenadas(
            @PathVariable Integer idPaciente) {
        List<Evaluacion> evaluaciones = evaluacionService.obtenerEvaluacionesPorPacienteOrdenadas(idPaciente);
        return ResponseEntity.ok(evaluaciones);
    }

    /**
     * Obtener evaluaciones por médico
     */
    @GetMapping("/medico/{idMedico}")
    public ResponseEntity<List<Evaluacion>> obtenerEvaluacionesPorMedico(@PathVariable Integer idMedico) {
        List<Evaluacion> evaluaciones = evaluacionService.obtenerEvaluacionesPorMedicoId(idMedico);
        return ResponseEntity.ok(evaluaciones);
    }

    /**
     * Obtener evaluaciones por médico ordenadas
     */
    @GetMapping("/medico/{idMedico}/ordenadas")
    public ResponseEntity<List<Evaluacion>> obtenerEvaluacionesPorMedicoOrdenadas(
            @PathVariable Integer idMedico) {
        List<Evaluacion> evaluaciones = evaluacionService.obtenerEvaluacionesPorMedicoOrdenadas(idMedico);
        return ResponseEntity.ok(evaluaciones);
    }

    /**
     * Obtener evaluaciones por calificación
     */
    @GetMapping("/calificacion/{calificacion}")
    public ResponseEntity<List<Evaluacion>> obtenerEvaluacionesPorCalificacion(
            @PathVariable Integer calificacion) {
        List<Evaluacion> evaluaciones = evaluacionService.obtenerEvaluacionesPorCalificacion(calificacion);
        return ResponseEntity.ok(evaluaciones);
    }

    /**
     * Obtener evaluaciones por médico y calificación
     */
    @GetMapping("/medico/{idMedico}/calificacion/{calificacion}")
    public ResponseEntity<List<Evaluacion>> obtenerEvaluacionesPorMedicoYCalificacion(
            @PathVariable Integer idMedico,
            @PathVariable Integer calificacion) {
        List<Evaluacion> evaluaciones = 
                evaluacionService.obtenerEvaluacionesPorMedicoYCalificacion(idMedico, calificacion);
        return ResponseEntity.ok(evaluaciones);
    }

    /**
     * Obtener evaluaciones con calificación mínima
     */
    @GetMapping("/calificacion-minima/{minCalificacion}")
    public ResponseEntity<List<Evaluacion>> obtenerEvaluacionesConCalificacionMinima(
            @PathVariable Integer minCalificacion) {
        List<Evaluacion> evaluaciones = 
                evaluacionService.obtenerEvaluacionesConCalificacionMinima(minCalificacion);
        return ResponseEntity.ok(evaluaciones);
    }

    /**
     * Obtener evaluaciones por médico con calificación mínima
     */
    @GetMapping("/medico/{idMedico}/calificacion-minima/{minCalificacion}")
    public ResponseEntity<List<Evaluacion>> obtenerEvaluacionesPorMedicoConCalificacionMinima(
            @PathVariable Integer idMedico,
            @PathVariable Integer minCalificacion) {
        List<Evaluacion> evaluaciones = 
                evaluacionService.obtenerEvaluacionesPorMedicoConCalificacionMinima(idMedico, minCalificacion);
        return ResponseEntity.ok(evaluaciones);
    }

    /**
     * Calcular promedio de calificaciones por médico
     */
    @GetMapping("/medico/{idMedico}/promedio")
    public ResponseEntity<Double> calcularPromedioCalificacionesPorMedico(@PathVariable Integer idMedico) {
        Double promedio = evaluacionService.calcularPromedioCalificacionesPorMedico(idMedico);
        return ResponseEntity.ok(promedio);
    }

    /**
     * Contar evaluaciones por médico
     */
    @GetMapping("/medico/{idMedico}/contar")
    public ResponseEntity<Long> contarEvaluacionesPorMedico(@PathVariable Integer idMedico) {
        Long count = evaluacionService.contarEvaluacionesPorMedico(idMedico);
        return ResponseEntity.ok(count);
    }

    /**
     * Contar evaluaciones por paciente
     */
    @GetMapping("/paciente/{idPaciente}/contar")
    public ResponseEntity<Long> contarEvaluacionesPorPaciente(@PathVariable Integer idPaciente) {
        Long count = evaluacionService.contarEvaluacionesPorPaciente(idPaciente);
        return ResponseEntity.ok(count);
    }

    /**
     * Obtener evaluaciones en rango de fechas
     */
    @GetMapping("/rango-fechas")
    public ResponseEntity<List<Evaluacion>> obtenerEvaluacionesEnRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        List<Evaluacion> evaluaciones = evaluacionService.obtenerEvaluacionesEnRangoFechas(fechaInicio, fechaFin);
        return ResponseEntity.ok(evaluaciones);
    }

    /**
     * Obtener evaluaciones por médico en rango de fechas
     */
    @GetMapping("/medico/{idMedico}/rango-fechas")
    public ResponseEntity<List<Evaluacion>> obtenerEvaluacionesPorMedicoEnRangoFechas(
            @PathVariable Integer idMedico,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        List<Evaluacion> evaluaciones = 
                evaluacionService.obtenerEvaluacionesPorMedicoEnRangoFechas(idMedico, fechaInicio, fechaFin);
        return ResponseEntity.ok(evaluaciones);
    }

    /**
     * Obtener evaluaciones con respuesta del médico
     */
    @GetMapping("/con-respuesta")
    public ResponseEntity<List<Evaluacion>> obtenerEvaluacionesConRespuesta() {
        List<Evaluacion> evaluaciones = evaluacionService.obtenerEvaluacionesConRespuesta();
        return ResponseEntity.ok(evaluaciones);
    }

    /**
     * Obtener evaluaciones sin respuesta del médico
     */
    @GetMapping("/sin-respuesta")
    public ResponseEntity<List<Evaluacion>> obtenerEvaluacionesSinRespuesta() {
        List<Evaluacion> evaluaciones = evaluacionService.obtenerEvaluacionesSinRespuesta();
        return ResponseEntity.ok(evaluaciones);
    }

    /**
     * Obtener evaluaciones sin respuesta por médico
     */
    @GetMapping("/medico/{idMedico}/sin-respuesta")
    public ResponseEntity<List<Evaluacion>> obtenerEvaluacionesSinRespuestaPorMedico(
            @PathVariable Integer idMedico) {
        List<Evaluacion> evaluaciones = evaluacionService.obtenerEvaluacionesSinRespuestaPorMedico(idMedico);
        return ResponseEntity.ok(evaluaciones);
    }

    /**
     * Obtener evaluaciones con comentario
     */
    @GetMapping("/con-comentario")
    public ResponseEntity<List<Evaluacion>> obtenerEvaluacionesConComentario() {
        List<Evaluacion> evaluaciones = evaluacionService.obtenerEvaluacionesConComentario();
        return ResponseEntity.ok(evaluaciones);
    }

    /**
     * Obtener últimas evaluaciones por médico
     */
    @GetMapping("/medico/{idMedico}/ultimas")
    public ResponseEntity<List<Evaluacion>> obtenerUltimasEvaluacionesPorMedico(
            @PathVariable Integer idMedico) {
        List<Evaluacion> evaluaciones = evaluacionService.obtenerUltimasEvaluacionesPorMedico(idMedico);
        return ResponseEntity.ok(evaluaciones);
    }

    /**
     * Obtener evaluaciones recientes (últimos X días)
     */
    @GetMapping("/recientes")
    public ResponseEntity<List<Evaluacion>> obtenerEvaluacionesRecientes(@RequestParam int dias) {
        List<Evaluacion> evaluaciones = evaluacionService.obtenerEvaluacionesRecientes(dias);
        return ResponseEntity.ok(evaluaciones);
    }

    /**
     * Obtener distribución de calificaciones por médico
     */
    @GetMapping("/medico/{idMedico}/distribucion")
    public ResponseEntity<Map<Integer, Long>> obtenerDistribucionCalificacionesPorMedico(
            @PathVariable Integer idMedico) {
        Map<Integer, Long> distribucion = evaluacionService.obtenerDistribucionCalificacionesPorMedico(idMedico);
        return ResponseEntity.ok(distribucion);
    }

    /**
     * Obtener mejores evaluaciones en general
     */
    @GetMapping("/mejores")
    public ResponseEntity<List<Evaluacion>> obtenerMejoresEvaluaciones() {
        List<Evaluacion> evaluaciones = evaluacionService.obtenerMejoresEvaluaciones();
        return ResponseEntity.ok(evaluaciones);
    }

    /**
     * Obtener mejores evaluaciones por médico
     */
    @GetMapping("/medico/{idMedico}/mejores")
    public ResponseEntity<List<Evaluacion>> obtenerMejoresEvaluacionesPorMedico(
            @PathVariable Integer idMedico) {
        List<Evaluacion> evaluaciones = evaluacionService.obtenerMejoresEvaluacionesPorMedico(idMedico);
        return ResponseEntity.ok(evaluaciones);
    }

    /**
     * Verificar si existe evaluación entre paciente y médico
     */
    @GetMapping("/existe")
    public ResponseEntity<Boolean> existeEvaluacionEntrePacienteYMedico(
            @RequestParam Integer idPaciente,
            @RequestParam Integer idMedico) {
        boolean existe = evaluacionService.existeEvaluacionEntrePacienteYMedico(idPaciente, idMedico);
        return ResponseEntity.ok(existe);
    }

    /**
     * Obtener evaluaciones entre paciente y médico
     */
    @GetMapping("/entre-paciente-medico")
    public ResponseEntity<List<Evaluacion>> obtenerEvaluacionesEntrePacienteYMedico(
            @RequestParam Integer idPaciente,
            @RequestParam Integer idMedico) {
        List<Evaluacion> evaluaciones = 
                evaluacionService.obtenerEvaluacionesEntrePacienteYMedico(idPaciente, idMedico);
        return ResponseEntity.ok(evaluaciones);
    }

    /**
     * Agregar respuesta del médico a una evaluación
     */
    @PatchMapping("/{id}/respuesta")
    public ResponseEntity<Evaluacion> agregarRespuestaMedico(
            @PathVariable Integer id,
            @RequestBody Map<String, String> request) {
        try {
            String respuesta = request.get("respuesta");
            if (respuesta == null || respuesta.trim().isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            Evaluacion evaluacion = evaluacionService.agregarRespuestaMedico(id, respuesta);
            return ResponseEntity.ok(evaluacion);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
