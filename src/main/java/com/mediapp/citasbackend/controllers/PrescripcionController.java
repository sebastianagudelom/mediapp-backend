package com.mediapp.citasbackend.controllers;

import com.mediapp.citasbackend.entities.Prescripcion;
import com.mediapp.citasbackend.services.interfaces.PrescripcionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/prescripciones")
@RequiredArgsConstructor
@Tag(name = "Prescripciones", description = "Gestión de prescripciones médicas - recetas y medicamentos")
@SecurityRequirement(name = "bearerAuth")
public class PrescripcionController {

    private final PrescripcionService prescripcionService;

    @PostMapping
    @Operation(summary = "Crear nueva prescripción", 
               description = "Registra una nueva prescripción médica asociada a un historial")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Prescripción creada exitosamente",
            content = @Content(schema = @Schema(implementation = Prescripcion.class))),
        @ApiResponse(responseCode = "400", description = "Datos de prescripción inválidos")
    })
    public ResponseEntity<Prescripcion> crearPrescripcion(
        @Parameter(description = "Datos de la prescripción a crear", required = true)
        @RequestBody Prescripcion prescripcion) {
        try {
            Prescripcion nuevaPrescripcion = prescripcionService.guardarPrescripcion(prescripcion);
            return new ResponseEntity<>(nuevaPrescripcion, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    @Operation(summary = "Obtener todas las prescripciones", 
               description = "Retorna listado completo de prescripciones médicas")
    @ApiResponse(responseCode = "200", description = "Lista de prescripciones obtenida exitosamente")
    public ResponseEntity<List<Prescripcion>> obtenerTodasLasPrescripciones() {
        List<Prescripcion> prescripciones = prescripcionService.obtenerTodasLasPrescripciones();
        return ResponseEntity.ok(prescripciones);
    }

    @GetMapping("/ordenadas")
    @Operation(summary = "Obtener prescripciones ordenadas", 
               description = "Retorna prescripciones ordenadas por fecha de emisión")
    @ApiResponse(responseCode = "200", description = "Lista ordenada de prescripciones")
    public ResponseEntity<List<Prescripcion>> obtenerTodasLasPrescripcionesOrdenadas() {
        List<Prescripcion> prescripciones = prescripcionService.obtenerTodasLasPrescripcionesOrdenadas();
        return ResponseEntity.ok(prescripciones);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener prescripción por ID", 
               description = "Retorna los detalles de una prescripción específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Prescripción encontrada"),
        @ApiResponse(responseCode = "404", description = "Prescripción no encontrada")
    })
    public ResponseEntity<Prescripcion> obtenerPrescripcionPorId(
        @Parameter(description = "ID de la prescripción", required = true, example = "1")
        @PathVariable Integer id) {
        Optional<Prescripcion> prescripcion = prescripcionService.obtenerPrescripcionPorId(id);
        return prescripcion.map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Actualizar una prescripción
     */
    @PutMapping("/{id}")
    public ResponseEntity<Prescripcion> actualizarPrescripcion(
            @PathVariable Integer id,
            @RequestBody Prescripcion prescripcion) {
        try {
            Prescripcion prescripcionActualizada = prescripcionService.actualizarPrescripcion(id, prescripcion);
            return ResponseEntity.ok(prescripcionActualizada);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Eliminar una prescripción
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPrescripcion(@PathVariable Integer id) {
        try {
            prescripcionService.eliminarPrescripcion(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Obtener prescripciones por historial médico
     */
    @GetMapping("/historial/{idHistorial}")
    public ResponseEntity<List<Prescripcion>> obtenerPrescripcionesPorHistorial(@PathVariable Integer idHistorial) {
        List<Prescripcion> prescripciones = prescripcionService.obtenerPrescripcionesPorHistorialId(idHistorial);
        return ResponseEntity.ok(prescripciones);
    }

    /**
     * Obtener prescripciones por paciente
     */
    @GetMapping("/paciente/{idPaciente}")
    public ResponseEntity<List<Prescripcion>> obtenerPrescripcionesPorPaciente(@PathVariable Integer idPaciente) {
        List<Prescripcion> prescripciones = prescripcionService.obtenerPrescripcionesPorPaciente(idPaciente);
        return ResponseEntity.ok(prescripciones);
    }

    /**
     * Obtener últimas prescripciones por paciente
     */
    @GetMapping("/paciente/{idPaciente}/ultimas")
    public ResponseEntity<List<Prescripcion>> obtenerUltimasPrescripcionesPorPaciente(
            @PathVariable Integer idPaciente) {
        List<Prescripcion> prescripciones = prescripcionService.obtenerUltimasPrescripcionesPorPaciente(idPaciente);
        return ResponseEntity.ok(prescripciones);
    }

    /**
     * Obtener prescripciones recientes por paciente
     */
    @GetMapping("/paciente/{idPaciente}/recientes")
    public ResponseEntity<List<Prescripcion>> obtenerPrescripcionesRecientesPorPaciente(
            @PathVariable Integer idPaciente,
            @RequestParam(defaultValue = "30") Integer dias) {
        List<Prescripcion> prescripciones = 
                prescripcionService.obtenerPrescripcionesRecientesPorPaciente(idPaciente, dias);
        return ResponseEntity.ok(prescripciones);
    }

    /**
     * Obtener prescripciones activas por paciente
     */
    @GetMapping("/paciente/{idPaciente}/activas")
    public ResponseEntity<List<Prescripcion>> obtenerPrescripcionesActivasPorPaciente(
            @PathVariable Integer idPaciente) {
        List<Prescripcion> prescripciones = prescripcionService.obtenerPrescripcionesActivasPorPaciente(idPaciente);
        return ResponseEntity.ok(prescripciones);
    }

    /**
     * Obtener prescripciones por médico
     */
    @GetMapping("/medico/{idMedico}")
    public ResponseEntity<List<Prescripcion>> obtenerPrescripcionesPorMedico(@PathVariable Integer idMedico) {
        List<Prescripcion> prescripciones = prescripcionService.obtenerPrescripcionesPorMedico(idMedico);
        return ResponseEntity.ok(prescripciones);
    }

    /**
     * Obtener prescripciones por paciente y médico
     */
    @GetMapping("/paciente/{idPaciente}/medico/{idMedico}")
    public ResponseEntity<List<Prescripcion>> obtenerPrescripcionesPorPacienteYMedico(
            @PathVariable Integer idPaciente,
            @PathVariable Integer idMedico) {
        List<Prescripcion> prescripciones = 
                prescripcionService.obtenerPrescripcionesPorPacienteYMedico(idPaciente, idMedico);
        return ResponseEntity.ok(prescripciones);
    }

    /**
     * Obtener prescripciones por cita
     */
    @GetMapping("/cita/{idCita}")
    public ResponseEntity<List<Prescripcion>> obtenerPrescripcionesPorCita(@PathVariable Integer idCita) {
        List<Prescripcion> prescripciones = prescripcionService.obtenerPrescripcionesPorCita(idCita);
        return ResponseEntity.ok(prescripciones);
    }

    /**
     * Obtener prescripciones por nombre exacto de medicamento
     */
    @GetMapping("/medicamento/{nombreMedicamento}")
    public ResponseEntity<List<Prescripcion>> obtenerPrescripcionesPorNombreMedicamento(
            @PathVariable String nombreMedicamento) {
        List<Prescripcion> prescripciones = 
                prescripcionService.obtenerPrescripcionesPorNombreMedicamento(nombreMedicamento);
        return ResponseEntity.ok(prescripciones);
    }

    /**
     * Buscar prescripciones por medicamento (búsqueda parcial)
     */
    @GetMapping("/buscar/medicamento")
    public ResponseEntity<List<Prescripcion>> buscarPrescripcionesPorMedicamento(
            @RequestParam String medicamento) {
        List<Prescripcion> prescripciones = prescripcionService.buscarPrescripcionesPorMedicamento(medicamento);
        return ResponseEntity.ok(prescripciones);
    }

    /**
     * Buscar prescripciones del paciente por medicamento
     */
    @GetMapping("/paciente/{idPaciente}/buscar/medicamento")
    public ResponseEntity<List<Prescripcion>> buscarPrescripcionesPacientePorMedicamento(
            @PathVariable Integer idPaciente,
            @RequestParam String medicamento) {
        List<Prescripcion> prescripciones = 
                prescripcionService.buscarPrescripcionesPacientePorMedicamento(idPaciente, medicamento);
        return ResponseEntity.ok(prescripciones);
    }

    /**
     * Obtener prescripciones por fecha
     */
    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<Prescripcion>> obtenerPrescripcionesPorFecha(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        List<Prescripcion> prescripciones = prescripcionService.obtenerPrescripcionesPorFecha(fecha);
        return ResponseEntity.ok(prescripciones);
    }

    /**
     * Obtener prescripciones del día
     */
    @GetMapping("/del-dia")
    public ResponseEntity<List<Prescripcion>> obtenerPrescripcionesDelDia() {
        List<Prescripcion> prescripciones = prescripcionService.obtenerPrescripcionesDelDia();
        return ResponseEntity.ok(prescripciones);
    }

    /**
     * Obtener prescripciones en rango de fechas
     */
    @GetMapping("/rango-fechas")
    public ResponseEntity<List<Prescripcion>> obtenerPrescripcionesEnRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        List<Prescripcion> prescripciones = 
                prescripcionService.obtenerPrescripcionesEnRangoFechas(fechaInicio, fechaFin);
        return ResponseEntity.ok(prescripciones);
    }

    /**
     * Obtener prescripciones por paciente en rango de fechas
     */
    @GetMapping("/paciente/{idPaciente}/rango-fechas")
    public ResponseEntity<List<Prescripcion>> obtenerPrescripcionesPorPacienteEnRangoFechas(
            @PathVariable Integer idPaciente,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        List<Prescripcion> prescripciones = 
                prescripcionService.obtenerPrescripcionesPorPacienteEnRangoFechas(idPaciente, fechaInicio, fechaFin);
        return ResponseEntity.ok(prescripciones);
    }

    /**
     * Obtener prescripciones por médico en rango de fechas
     */
    @GetMapping("/medico/{idMedico}/rango-fechas")
    public ResponseEntity<List<Prescripcion>> obtenerPrescripcionesPorMedicoEnRangoFechas(
            @PathVariable Integer idMedico,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        List<Prescripcion> prescripciones = 
                prescripcionService.obtenerPrescripcionesPorMedicoEnRangoFechas(idMedico, fechaInicio, fechaFin);
        return ResponseEntity.ok(prescripciones);
    }

    /**
     * Obtener prescripciones por duración específica
     */
    @GetMapping("/duracion/{duracionDias}")
    public ResponseEntity<List<Prescripcion>> obtenerPrescripcionesPorDuracion(@PathVariable Integer duracionDias) {
        List<Prescripcion> prescripciones = prescripcionService.obtenerPrescripcionesPorDuracion(duracionDias);
        return ResponseEntity.ok(prescripciones);
    }

    /**
     * Obtener prescripciones con duración mínima
     */
    @GetMapping("/duracion-minima/{diasMinimos}")
    public ResponseEntity<List<Prescripcion>> obtenerPrescripcionesConDuracionMinima(
            @PathVariable Integer diasMinimos) {
        List<Prescripcion> prescripciones = prescripcionService.obtenerPrescripcionesConDuracionMinima(diasMinimos);
        return ResponseEntity.ok(prescripciones);
    }

    /**
     * Obtener prescripciones sin duración especificada
     */
    @GetMapping("/sin-duracion")
    public ResponseEntity<List<Prescripcion>> obtenerPrescripcionesSinDuracion() {
        List<Prescripcion> prescripciones = prescripcionService.obtenerPrescripcionesSinDuracion();
        return ResponseEntity.ok(prescripciones);
    }

    /**
     * Obtener prescripciones de tratamientos largos
     */
    @GetMapping("/tratamientos-largos")
    public ResponseEntity<List<Prescripcion>> obtenerPrescripcionesTratamientosLargos() {
        List<Prescripcion> prescripciones = prescripcionService.obtenerPrescripcionesTratamientosLargos();
        return ResponseEntity.ok(prescripciones);
    }

    /**
     * Obtener prescripciones por dosis
     */
    @GetMapping("/dosis/{dosis}")
    public ResponseEntity<List<Prescripcion>> obtenerPrescripcionesPorDosis(@PathVariable String dosis) {
        List<Prescripcion> prescripciones = prescripcionService.obtenerPrescripcionesPorDosis(dosis);
        return ResponseEntity.ok(prescripciones);
    }

    /**
     * Obtener prescripciones por frecuencia
     */
    @GetMapping("/frecuencia/{frecuencia}")
    public ResponseEntity<List<Prescripcion>> obtenerPrescripcionesPorFrecuencia(
            @PathVariable String frecuencia) {
        List<Prescripcion> prescripciones = prescripcionService.obtenerPrescripcionesPorFrecuencia(frecuencia);
        return ResponseEntity.ok(prescripciones);
    }

    /**
     * Obtener prescripciones con instrucciones
     */
    @GetMapping("/con-instrucciones")
    public ResponseEntity<List<Prescripcion>> obtenerPrescripcionesConInstrucciones() {
        List<Prescripcion> prescripciones = prescripcionService.obtenerPrescripcionesConInstrucciones();
        return ResponseEntity.ok(prescripciones);
    }

    /**
     * Obtener prescripciones con instrucciones por paciente
     */
    @GetMapping("/paciente/{idPaciente}/con-instrucciones")
    public ResponseEntity<List<Prescripcion>> obtenerPrescripcionesConInstruccionesPorPaciente(
            @PathVariable Integer idPaciente) {
        List<Prescripcion> prescripciones = 
                prescripcionService.obtenerPrescripcionesConInstruccionesPorPaciente(idPaciente);
        return ResponseEntity.ok(prescripciones);
    }

    /**
     * Contar prescripciones por paciente
     */
    @GetMapping("/paciente/{idPaciente}/contar")
    public ResponseEntity<Long> contarPrescripcionesPorPaciente(@PathVariable Integer idPaciente) {
        Long count = prescripcionService.contarPrescripcionesPorPaciente(idPaciente);
        return ResponseEntity.ok(count);
    }

    /**
     * Contar prescripciones por médico
     */
    @GetMapping("/medico/{idMedico}/contar")
    public ResponseEntity<Long> contarPrescripcionesPorMedico(@PathVariable Integer idMedico) {
        Long count = prescripcionService.contarPrescripcionesPorMedico(idMedico);
        return ResponseEntity.ok(count);
    }

    /**
     * Contar prescripciones por medicamento
     */
    @GetMapping("/contar/medicamento")
    public ResponseEntity<Long> contarPrescripcionesPorMedicamento(@RequestParam String medicamento) {
        Long count = prescripcionService.contarPrescripcionesPorMedicamento(medicamento);
        return ResponseEntity.ok(count);
    }

    /**
     * Contar total de prescripciones
     */
    @GetMapping("/contar")
    public ResponseEntity<Long> contarPrescripciones() {
        Long count = prescripcionService.contarPrescripciones();
        return ResponseEntity.ok(count);
    }

    /**
     * Obtener medicamentos más prescritos
     */
    @GetMapping("/estadisticas/medicamentos-mas-prescritos")
    public ResponseEntity<Map<String, Long>> obtenerMedicamentosMasPrescritos() {
        Map<String, Long> estadisticas = prescripcionService.obtenerMedicamentosMasPrescritos();
        return ResponseEntity.ok(estadisticas);
    }

    /**
     * Obtener medicamentos más prescritos por médico
     */
    @GetMapping("/medico/{idMedico}/estadisticas/medicamentos-mas-prescritos")
    public ResponseEntity<Map<String, Long>> obtenerMedicamentosMasPrescritosPorMedico(
            @PathVariable Integer idMedico) {
        Map<String, Long> estadisticas = prescripcionService.obtenerMedicamentosMasPrescritosPorMedico(idMedico);
        return ResponseEntity.ok(estadisticas);
    }

    /**
     * Verificar si paciente tiene prescripción de medicamento
     */
    @GetMapping("/paciente/{idPaciente}/tiene-medicamento")
    public ResponseEntity<Boolean> pacienteTienePrescripcionDeMedicamento(
            @PathVariable Integer idPaciente,
            @RequestParam String medicamento) {
        boolean tiene = prescripcionService.pacienteTienePrescripcionDeMedicamento(idPaciente, medicamento);
        return ResponseEntity.ok(tiene);
    }

    /**
     * Verificar si prescripción está activa
     */
    @GetMapping("/{id}/esta-activa")
    public ResponseEntity<Boolean> prescripcionEstaActiva(@PathVariable Integer id) {
        boolean estaActiva = prescripcionService.prescripcionEstaActiva(id);
        return ResponseEntity.ok(estaActiva);
    }

    /**
     * Calcular fecha de fin de tratamiento
     */
    @GetMapping("/{id}/fecha-fin-tratamiento")
    public ResponseEntity<LocalDate> calcularFechaFinTratamiento(@PathVariable Integer id) {
        try {
            Optional<Prescripcion> prescripcion = prescripcionService.obtenerPrescripcionPorId(id);
            if (prescripcion.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            LocalDate fechaFin = prescripcionService.calcularFechaFinTratamiento(prescripcion.get());
            return ResponseEntity.ok(fechaFin);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
