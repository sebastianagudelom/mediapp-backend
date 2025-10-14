package com.mediapp.citasbackend.controllers;

import com.mediapp.citasbackend.entities.CalendarioDisponibilidad;
import com.mediapp.citasbackend.entities.Medico;
import com.mediapp.citasbackend.services.interfaces.CalendarioDisponibilidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/calendario-disponibilidad")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CalendarioDisponibilidadController {

    private final CalendarioDisponibilidadService calendarioService;

    /**
     * Crear una nueva disponibilidad
     */
    @PostMapping
    public ResponseEntity<CalendarioDisponibilidad> crearDisponibilidad(
            @RequestBody CalendarioDisponibilidad disponibilidad) {
        try {
            CalendarioDisponibilidad nuevaDisponibilidad = calendarioService.guardarDisponibilidad(disponibilidad);
            return new ResponseEntity<>(nuevaDisponibilidad, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Obtener todas las disponibilidades
     */
    @GetMapping
    public ResponseEntity<List<CalendarioDisponibilidad>> obtenerTodasLasDisponibilidades() {
        List<CalendarioDisponibilidad> disponibilidades = calendarioService.obtenerTodasLasDisponibilidades();
        return ResponseEntity.ok(disponibilidades);
    }

    /**
     * Obtener disponibilidad por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<CalendarioDisponibilidad> obtenerDisponibilidadPorId(@PathVariable Integer id) {
        Optional<CalendarioDisponibilidad> disponibilidad = calendarioService.obtenerDisponibilidadPorId(id);
        return disponibilidad.map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Actualizar una disponibilidad
     */
    @PutMapping("/{id}")
    public ResponseEntity<CalendarioDisponibilidad> actualizarDisponibilidad(
            @PathVariable Integer id,
            @RequestBody CalendarioDisponibilidad disponibilidad) {
        try {
            CalendarioDisponibilidad disponibilidadActualizada = 
                    calendarioService.actualizarDisponibilidad(id, disponibilidad);
            return ResponseEntity.ok(disponibilidadActualizada);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Eliminar una disponibilidad
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDisponibilidad(@PathVariable Integer id) {
        try {
            calendarioService.eliminarDisponibilidad(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Obtener disponibilidades por médico
     */
    @GetMapping("/medico/{idMedico}")
    public ResponseEntity<List<CalendarioDisponibilidad>> obtenerDisponibilidadesPorMedico(
            @PathVariable Integer idMedico) {
        List<CalendarioDisponibilidad> disponibilidades = 
                calendarioService.obtenerDisponibilidadesPorMedicoId(idMedico);
        return ResponseEntity.ok(disponibilidades);
    }

    /**
     * Obtener disponibilidades activas por médico
     */
    @GetMapping("/medico/{idMedico}/activas")
    public ResponseEntity<List<CalendarioDisponibilidad>> obtenerDisponibilidadesActivasPorMedico(
            @PathVariable Integer idMedico) {
        List<CalendarioDisponibilidad> disponibilidades = 
                calendarioService.obtenerDisponibilidadesActivasPorMedico(idMedico);
        return ResponseEntity.ok(disponibilidades);
    }

    /**
     * Obtener disponibilidades por médico y día
     */
    @GetMapping("/medico/{idMedico}/dia/{dia}")
    public ResponseEntity<List<CalendarioDisponibilidad>> obtenerDisponibilidadesPorMedicoYDia(
            @PathVariable Integer idMedico,
            @PathVariable CalendarioDisponibilidad.DiaSemana dia) {
        List<CalendarioDisponibilidad> disponibilidades = 
                calendarioService.obtenerDisponibilidadesPorMedicoYDia(idMedico, dia);
        return ResponseEntity.ok(disponibilidades);
    }

    /**
     * Obtener disponibilidad específica por médico y día
     */
    @GetMapping("/medico/{idMedico}/dia-especifico/{dia}")
    public ResponseEntity<CalendarioDisponibilidad> obtenerDisponibilidadPorMedicoYDia(
            @PathVariable Integer idMedico,
            @PathVariable CalendarioDisponibilidad.DiaSemana dia) {
        Optional<CalendarioDisponibilidad> disponibilidad = 
                calendarioService.obtenerDisponibilidadPorMedicoYDia(idMedico, dia);
        return disponibilidad.map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Obtener disponibilidades por día de la semana
     */
    @GetMapping("/dia/{dia}")
    public ResponseEntity<List<CalendarioDisponibilidad>> obtenerDisponibilidadesPorDia(
            @PathVariable CalendarioDisponibilidad.DiaSemana dia) {
        List<CalendarioDisponibilidad> disponibilidades = 
                calendarioService.obtenerDisponibilidadesPorDia(dia);
        return ResponseEntity.ok(disponibilidades);
    }

    /**
     * Obtener disponibilidades por estado
     */
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<CalendarioDisponibilidad>> obtenerDisponibilidadesPorEstado(
            @PathVariable CalendarioDisponibilidad.Estado estado) {
        List<CalendarioDisponibilidad> disponibilidades = 
                calendarioService.obtenerDisponibilidadesPorEstado(estado);
        return ResponseEntity.ok(disponibilidades);
    }

    /**
     * Obtener disponibilidades por médico y estado
     */
    @GetMapping("/medico/{idMedico}/estado/{estado}")
    public ResponseEntity<List<CalendarioDisponibilidad>> obtenerDisponibilidadesPorMedicoYEstado(
            @PathVariable Integer idMedico,
            @PathVariable CalendarioDisponibilidad.Estado estado) {
        List<CalendarioDisponibilidad> disponibilidades = 
                calendarioService.obtenerDisponibilidadesPorMedicoYEstado(idMedico, estado);
        return ResponseEntity.ok(disponibilidades);
    }

    /**
     * Verificar conflictos de horario
     */
    @GetMapping("/verificar-conflictos")
    public ResponseEntity<List<CalendarioDisponibilidad>> verificarConflictosDeHorario(
            @RequestParam Integer idMedico,
            @RequestParam CalendarioDisponibilidad.DiaSemana dia,
            @RequestParam String horaInicio,
            @RequestParam String horaFin) {
        try {
            LocalTime inicio = LocalTime.parse(horaInicio);
            LocalTime fin = LocalTime.parse(horaFin);
            List<CalendarioDisponibilidad> conflictos = 
                    calendarioService.verificarConflictosDeHorario(idMedico, dia, inicio, fin);
            return ResponseEntity.ok(conflictos);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Verificar si tiene conflicto de horario
     */
    @GetMapping("/tiene-conflicto")
    public ResponseEntity<Boolean> tieneConflictoDeHorario(
            @RequestParam Integer idMedico,
            @RequestParam CalendarioDisponibilidad.DiaSemana dia,
            @RequestParam String horaInicio,
            @RequestParam String horaFin) {
        try {
            LocalTime inicio = LocalTime.parse(horaInicio);
            LocalTime fin = LocalTime.parse(horaFin);
            boolean tieneConflicto = 
                    calendarioService.tieneConflictoDeHorario(idMedico, dia, inicio, fin);
            return ResponseEntity.ok(tieneConflicto);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Contar disponibilidades activas por médico
     */
    @GetMapping("/medico/{idMedico}/contar-activas")
    public ResponseEntity<Long> contarDisponibilidadesActivasPorMedico(@PathVariable Integer idMedico) {
        Long count = calendarioService.contarDisponibilidadesActivasPorMedico(idMedico);
        return ResponseEntity.ok(count);
    }

    /**
     * Obtener médicos disponibles en un día
     */
    @GetMapping("/medicos-disponibles/dia/{dia}")
    public ResponseEntity<List<Medico>> obtenerMedicosDisponiblesEnDia(
            @PathVariable CalendarioDisponibilidad.DiaSemana dia) {
        List<Medico> medicos = calendarioService.obtenerMedicosDisponiblesEnDia(dia);
        return ResponseEntity.ok(medicos);
    }

    /**
     * Obtener médicos disponibles en un día y hora específica
     */
    @GetMapping("/medicos-disponibles/dia/{dia}/hora")
    public ResponseEntity<List<Medico>> obtenerMedicosDisponiblesEnDiaYHora(
            @PathVariable CalendarioDisponibilidad.DiaSemana dia,
            @RequestParam String hora) {
        try {
            LocalTime horaCita = LocalTime.parse(hora);
            List<Medico> medicos = 
                    calendarioService.obtenerMedicosDisponiblesEnDiaYHora(dia, horaCita);
            return ResponseEntity.ok(medicos);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Obtener disponibilidades ordenadas por médico
     */
    @GetMapping("/medico/{idMedico}/ordenadas")
    public ResponseEntity<List<CalendarioDisponibilidad>> obtenerDisponibilidadesOrdenadasPorMedico(
            @PathVariable Integer idMedico) {
        List<CalendarioDisponibilidad> disponibilidades = 
                calendarioService.obtenerDisponibilidadesOrdenadasPorMedico(idMedico);
        return ResponseEntity.ok(disponibilidades);
    }

    /**
     * Eliminar todas las disponibilidades de un médico
     */
    @DeleteMapping("/medico/{idMedico}")
    public ResponseEntity<Void> eliminarTodasLasDisponibilidadesPorMedico(@PathVariable Integer idMedico) {
        try {
            calendarioService.eliminarTodasLasDisponibilidadesPorMedico(idMedico);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Verificar si un médico tiene disponibilidad configurada
     */
    @GetMapping("/medico/{idMedico}/tiene-disponibilidad")
    public ResponseEntity<Boolean> medicoTieneDisponibilidadConfigurada(@PathVariable Integer idMedico) {
        boolean tieneDisponibilidad = calendarioService.medicoTieneDisponibilidadConfigurada(idMedico);
        return ResponseEntity.ok(tieneDisponibilidad);
    }

    /**
     * Obtener disponibilidades por intervalo de cita
     */
    @GetMapping("/intervalo/{intervalo}")
    public ResponseEntity<List<CalendarioDisponibilidad>> obtenerDisponibilidadesPorIntervalo(
            @PathVariable Integer intervalo) {
        List<CalendarioDisponibilidad> disponibilidades = 
                calendarioService.obtenerDisponibilidadesPorIntervalo(intervalo);
        return ResponseEntity.ok(disponibilidades);
    }

    /**
     * Activar disponibilidad
     */
    @PatchMapping("/{id}/activar")
    public ResponseEntity<CalendarioDisponibilidad> activarDisponibilidad(@PathVariable Integer id) {
        try {
            CalendarioDisponibilidad disponibilidad = calendarioService.activarDisponibilidad(id);
            return ResponseEntity.ok(disponibilidad);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Desactivar disponibilidad
     */
    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<CalendarioDisponibilidad> desactivarDisponibilidad(@PathVariable Integer id) {
        try {
            CalendarioDisponibilidad disponibilidad = calendarioService.desactivarDisponibilidad(id);
            return ResponseEntity.ok(disponibilidad);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
