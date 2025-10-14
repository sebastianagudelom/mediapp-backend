package com.mediapp.citasbackend.controllers;

import com.mediapp.citasbackend.entities.Cita;
import com.mediapp.citasbackend.services.interfaces.CitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/citas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CitaController {

    private final CitaService citaService;

    /**
     * Crear una nueva cita
     */
    @PostMapping
    public ResponseEntity<Cita> crearCita(@RequestBody Cita cita) {
        try {
            Cita nuevaCita = citaService.guardarCita(cita);
            return new ResponseEntity<>(nuevaCita, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Obtener todas las citas
     */
    @GetMapping
    public ResponseEntity<List<Cita>> obtenerTodasLasCitas() {
        List<Cita> citas = citaService.obtenerTodasLasCitas();
        return ResponseEntity.ok(citas);
    }

    /**
     * Obtener cita por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Cita> obtenerCitaPorId(@PathVariable Integer id) {
        Optional<Cita> cita = citaService.obtenerCitaPorId(id);
        return cita.map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Actualizar una cita
     */
    @PutMapping("/{id}")
    public ResponseEntity<Cita> actualizarCita(
            @PathVariable Integer id,
            @RequestBody Cita cita) {
        try {
            Cita citaActualizada = citaService.actualizarCita(id, cita);
            return ResponseEntity.ok(citaActualizada);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Eliminar una cita
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCita(@PathVariable Integer id) {
        try {
            citaService.eliminarCita(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Obtener citas por paciente
     */
    @GetMapping("/paciente/{idPaciente}")
    public ResponseEntity<List<Cita>> obtenerCitasPorPaciente(@PathVariable Integer idPaciente) {
        List<Cita> citas = citaService.obtenerCitasPorPacienteId(idPaciente);
        return ResponseEntity.ok(citas);
    }

    /**
     * Obtener citas por paciente y estado
     */
    @GetMapping("/paciente/{idPaciente}/estado/{estado}")
    public ResponseEntity<List<Cita>> obtenerCitasPorPacienteYEstado(
            @PathVariable Integer idPaciente,
            @PathVariable Cita.Estado estado) {
        List<Cita> citas = citaService.obtenerCitasPorPacienteYEstado(idPaciente, estado);
        return ResponseEntity.ok(citas);
    }

    /**
     * Obtener citas programadas por paciente
     */
    @GetMapping("/paciente/{idPaciente}/programadas")
    public ResponseEntity<List<Cita>> obtenerCitasProgramadasPorPaciente(@PathVariable Integer idPaciente) {
        List<Cita> citas = citaService.obtenerCitasProgramadasPorPaciente(idPaciente);
        return ResponseEntity.ok(citas);
    }

    /**
     * Obtener citas por médico
     */
    @GetMapping("/medico/{idMedico}")
    public ResponseEntity<List<Cita>> obtenerCitasPorMedico(@PathVariable Integer idMedico) {
        List<Cita> citas = citaService.obtenerCitasPorMedicoId(idMedico);
        return ResponseEntity.ok(citas);
    }

    /**
     * Obtener citas por médico y estado
     */
    @GetMapping("/medico/{idMedico}/estado/{estado}")
    public ResponseEntity<List<Cita>> obtenerCitasPorMedicoYEstado(
            @PathVariable Integer idMedico,
            @PathVariable Cita.Estado estado) {
        List<Cita> citas = citaService.obtenerCitasPorMedicoYEstado(idMedico, estado);
        return ResponseEntity.ok(citas);
    }

    /**
     * Obtener citas programadas por médico
     */
    @GetMapping("/medico/{idMedico}/programadas")
    public ResponseEntity<List<Cita>> obtenerCitasProgramadasPorMedico(@PathVariable Integer idMedico) {
        List<Cita> citas = citaService.obtenerCitasProgramadasPorMedico(idMedico);
        return ResponseEntity.ok(citas);
    }

    /**
     * Obtener citas por estado
     */
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Cita>> obtenerCitasPorEstado(@PathVariable Cita.Estado estado) {
        List<Cita> citas = citaService.obtenerCitasPorEstado(estado);
        return ResponseEntity.ok(citas);
    }

    /**
     * Obtener citas por fecha
     */
    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<Cita>> obtenerCitasPorFecha(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        List<Cita> citas = citaService.obtenerCitasPorFecha(fecha);
        return ResponseEntity.ok(citas);
    }

    /**
     * Obtener citas por médico y fecha
     */
    @GetMapping("/medico/{idMedico}/fecha/{fecha}")
    public ResponseEntity<List<Cita>> obtenerCitasPorMedicoYFecha(
            @PathVariable Integer idMedico,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        List<Cita> citas = citaService.obtenerCitasPorMedicoYFecha(idMedico, fecha);
        return ResponseEntity.ok(citas);
    }

    /**
     * Obtener citas por paciente y fecha
     */
    @GetMapping("/paciente/{idPaciente}/fecha/{fecha}")
    public ResponseEntity<List<Cita>> obtenerCitasPorPacienteYFecha(
            @PathVariable Integer idPaciente,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        List<Cita> citas = citaService.obtenerCitasPorPacienteYFecha(idPaciente, fecha);
        return ResponseEntity.ok(citas);
    }

    /**
     * Obtener citas en rango de fechas
     */
    @GetMapping("/rango-fechas")
    public ResponseEntity<List<Cita>> obtenerCitasEnRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        List<Cita> citas = citaService.obtenerCitasEnRangoFechas(fechaInicio, fechaFin);
        return ResponseEntity.ok(citas);
    }

    /**
     * Obtener citas por médico en rango de fechas
     */
    @GetMapping("/medico/{idMedico}/rango-fechas")
    public ResponseEntity<List<Cita>> obtenerCitasPorMedicoEnRangoFechas(
            @PathVariable Integer idMedico,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        List<Cita> citas = citaService.obtenerCitasPorMedicoEnRangoFechas(idMedico, fechaInicio, fechaFin);
        return ResponseEntity.ok(citas);
    }

    /**
     * Obtener citas por paciente en rango de fechas
     */
    @GetMapping("/paciente/{idPaciente}/rango-fechas")
    public ResponseEntity<List<Cita>> obtenerCitasPorPacienteEnRangoFechas(
            @PathVariable Integer idPaciente,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        List<Cita> citas = citaService.obtenerCitasPorPacienteEnRangoFechas(idPaciente, fechaInicio, fechaFin);
        return ResponseEntity.ok(citas);
    }

    /**
     * Verificar disponibilidad de médico en fecha y hora
     */
    @GetMapping("/verificar-disponibilidad")
    public ResponseEntity<Cita> verificarDisponibilidadMedicoFechaHora(
            @RequestParam Integer idMedico,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime hora) {
        Optional<Cita> cita = citaService.verificarDisponibilidadMedicoFechaHora(idMedico, fecha, hora);
        return cita.map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Verificar si médico está disponible en fecha y hora
     */
    @GetMapping("/medico-disponible")
    public ResponseEntity<Boolean> medicoDisponibleEnFechaHora(
            @RequestParam Integer idMedico,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime hora) {
        boolean disponible = citaService.medicoDisponibleEnFechaHora(idMedico, fecha, hora);
        return ResponseEntity.ok(disponible);
    }

    /**
     * Obtener citas por tipo
     */
    @GetMapping("/tipo/{tipoCita}")
    public ResponseEntity<List<Cita>> obtenerCitasPorTipo(@PathVariable Cita.TipoCita tipoCita) {
        List<Cita> citas = citaService.obtenerCitasPorTipo(tipoCita);
        return ResponseEntity.ok(citas);
    }

    /**
     * Obtener citas de telemedicina programadas por médico
     */
    @GetMapping("/medico/{idMedico}/telemedicina-programadas")
    public ResponseEntity<List<Cita>> obtenerCitasTelemedicinaProgramadasPorMedico(
            @PathVariable Integer idMedico) {
        List<Cita> citas = citaService.obtenerCitasTelemedicinaProgramadasPorMedico(idMedico);
        return ResponseEntity.ok(citas);
    }

    /**
     * Obtener próximas citas por paciente
     */
    @GetMapping("/paciente/{idPaciente}/proximas")
    public ResponseEntity<List<Cita>> obtenerProximasCitasPorPaciente(@PathVariable Integer idPaciente) {
        List<Cita> citas = citaService.obtenerProximasCitasPorPaciente(idPaciente);
        return ResponseEntity.ok(citas);
    }

    /**
     * Obtener próximas citas por médico
     */
    @GetMapping("/medico/{idMedico}/proximas")
    public ResponseEntity<List<Cita>> obtenerProximasCitasPorMedico(@PathVariable Integer idMedico) {
        List<Cita> citas = citaService.obtenerProximasCitasPorMedico(idMedico);
        return ResponseEntity.ok(citas);
    }

    /**
     * Obtener historial de citas por paciente
     */
    @GetMapping("/paciente/{idPaciente}/historial")
    public ResponseEntity<List<Cita>> obtenerHistorialCitasPorPaciente(@PathVariable Integer idPaciente) {
        List<Cita> citas = citaService.obtenerHistorialCitasPorPaciente(idPaciente);
        return ResponseEntity.ok(citas);
    }

    /**
     * Obtener historial de citas por médico
     */
    @GetMapping("/medico/{idMedico}/historial")
    public ResponseEntity<List<Cita>> obtenerHistorialCitasPorMedico(@PathVariable Integer idMedico) {
        List<Cita> citas = citaService.obtenerHistorialCitasPorMedico(idMedico);
        return ResponseEntity.ok(citas);
    }

    /**
     * Contar citas por estado
     */
    @GetMapping("/contar/estado/{estado}")
    public ResponseEntity<Long> contarCitasPorEstado(@PathVariable Cita.Estado estado) {
        Long count = citaService.contarCitasPorEstado(estado);
        return ResponseEntity.ok(count);
    }

    /**
     * Contar citas por médico y estado
     */
    @GetMapping("/contar/medico/{idMedico}/estado/{estado}")
    public ResponseEntity<Long> contarCitasPorMedicoYEstado(
            @PathVariable Integer idMedico,
            @PathVariable Cita.Estado estado) {
        Long count = citaService.contarCitasPorMedicoYEstado(idMedico, estado);
        return ResponseEntity.ok(count);
    }

    /**
     * Contar citas por paciente y estado
     */
    @GetMapping("/contar/paciente/{idPaciente}/estado/{estado}")
    public ResponseEntity<Long> contarCitasPorPacienteYEstado(
            @PathVariable Integer idPaciente,
            @PathVariable Cita.Estado estado) {
        Long count = citaService.contarCitasPorPacienteYEstado(idPaciente, estado);
        return ResponseEntity.ok(count);
    }

    /**
     * Obtener citas del día por médico
     */
    @GetMapping("/medico/{idMedico}/dia/{fecha}")
    public ResponseEntity<List<Cita>> obtenerCitasDelDiaPorMedico(
            @PathVariable Integer idMedico,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        List<Cita> citas = citaService.obtenerCitasDelDiaPorMedico(idMedico, fecha);
        return ResponseEntity.ok(citas);
    }

    /**
     * Obtener citas canceladas en rango
     */
    @GetMapping("/canceladas-rango")
    public ResponseEntity<List<Cita>> obtenerCitasCanceladasEnRango(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        List<Cita> citas = citaService.obtenerCitasCanceladasEnRango(fechaInicio, fechaFin);
        return ResponseEntity.ok(citas);
    }

    /**
     * Verificar si existe cita entre paciente, médico y fecha
     */
    @GetMapping("/existe")
    public ResponseEntity<Boolean> existeCitaEntrePacienteMedicoYFecha(
            @RequestParam Integer idPaciente,
            @RequestParam Integer idMedico,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaCita) {
        boolean existe = citaService.existeCitaEntrePacienteMedicoYFecha(idPaciente, idMedico, fechaCita);
        return ResponseEntity.ok(existe);
    }

    /**
     * Obtener última cita entre paciente y médico
     */
    @GetMapping("/ultima")
    public ResponseEntity<Cita> obtenerUltimaCitaEntrePacienteYMedico(
            @RequestParam Integer idPaciente,
            @RequestParam Integer idMedico) {
        Optional<Cita> cita = citaService.obtenerUltimaCitaEntrePacienteYMedico(idPaciente, idMedico);
        return cita.map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Completar una cita
     */
    @PatchMapping("/{id}/completar")
    public ResponseEntity<Cita> completarCita(@PathVariable Integer id) {
        try {
            Cita cita = citaService.completarCita(id);
            return ResponseEntity.ok(cita);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Cancelar una cita
     */
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Cita> cancelarCita(@PathVariable Integer id) {
        try {
            Cita cita = citaService.cancelarCita(id);
            return ResponseEntity.ok(cita);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Marcar como no asistió
     */
    @PatchMapping("/{id}/no-asistio")
    public ResponseEntity<Cita> marcarComoNoAsistio(@PathVariable Integer id) {
        try {
            Cita cita = citaService.marcarComoNoAsistio(id);
            return ResponseEntity.ok(cita);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
