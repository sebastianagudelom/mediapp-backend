package com.mediapp.citasbackend.controllers;

import com.mediapp.citasbackend.entities.Paciente;
import com.mediapp.citasbackend.entities.Usuario;
import com.mediapp.citasbackend.services.interfaces.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
@Tag(name = "Pacientes", description = "Gestión de pacientes - registro, consulta y actualización de información personal y médica")
@SecurityRequirement(name = "bearerAuth")
public class PacienteController {

    private final PacienteService pacienteService;

    @PostMapping
    @Operation(summary = "Crear nuevo paciente", description = "Registra un nuevo paciente en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Paciente creado exitosamente",
            content = @Content(schema = @Schema(implementation = Paciente.class))),
        @ApiResponse(responseCode = "400", description = "Datos de paciente inválidos")
    })
    public ResponseEntity<Paciente> crearPaciente(
        @Parameter(description = "Datos del paciente a crear", required = true)
        @RequestBody Paciente paciente) {
        try {
            Paciente nuevoPaciente = pacienteService.guardarPaciente(paciente);
            return new ResponseEntity<>(nuevoPaciente, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    @Operation(summary = "Obtener todos los pacientes", description = "Retorna listado completo de pacientes del sistema")
    @ApiResponse(responseCode = "200", description = "Lista de pacientes obtenida exitosamente")
    public ResponseEntity<List<Paciente>> obtenerTodosLosPacientes() {
        List<Paciente> pacientes = pacienteService.obtenerTodosLosPacientes();
        return ResponseEntity.ok(pacientes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener paciente por ID", description = "Retorna los detalles de un paciente específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Paciente encontrado"),
        @ApiResponse(responseCode = "404", description = "Paciente no encontrado")
    })
    public ResponseEntity<Paciente> obtenerPacientePorId(
        @Parameter(description = "ID del paciente", required = true, example = "1")
        @PathVariable Integer id) {
        Optional<Paciente> paciente = pacienteService.obtenerPacientePorId(id);
        return paciente.map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar paciente", description = "Actualiza la información de un paciente existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Paciente actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Paciente no encontrado")
    })
    public ResponseEntity<Paciente> actualizarPaciente(
            @Parameter(description = "ID del paciente", required = true, example = "1")
            @PathVariable Integer id,
            @Parameter(description = "Datos actualizados del paciente", required = true)
            @RequestBody Paciente paciente) {
        try {
            Paciente pacienteActualizado = pacienteService.actualizarPaciente(id, paciente);
            return ResponseEntity.ok(pacienteActualizado);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar paciente", description = "Elimina un paciente del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Paciente eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Paciente no encontrado")
    })
    public ResponseEntity<Void> eliminarPaciente(
        @Parameter(description = "ID del paciente", required = true, example = "1")
        @PathVariable Integer id) {
        try {
            pacienteService.eliminarPaciente(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/usuario/{idUsuario}")
    @Operation(summary = "Obtener paciente por ID de usuario", 
               description = "Retorna el perfil de paciente asociado a un usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Paciente encontrado"),
        @ApiResponse(responseCode = "404", description = "Paciente no encontrado para este usuario")
    })
    public ResponseEntity<Paciente> obtenerPacientePorUsuario(
        @Parameter(description = "ID del usuario", required = true, example = "1")
        @PathVariable Integer idUsuario) {
        Optional<Paciente> paciente = pacienteService.obtenerPacientePorUsuarioId(idUsuario);
        return paciente.map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/usuario/{idUsuario}/existe")
    @Operation(summary = "Verificar si existe paciente por usuario", 
               description = "Verifica si existe un perfil de paciente asociado al usuario")
    @ApiResponse(responseCode = "200", description = "Resultado de la verificación")
    public ResponseEntity<Boolean> existePacientePorUsuario(
        @Parameter(description = "ID del usuario", required = true, example = "1")
        @PathVariable Integer idUsuario) {
        boolean existe = pacienteService.existePacientePorUsuarioId(idUsuario);
        return ResponseEntity.ok(existe);
    }

    /**
     * Obtener paciente por número de identificación
     */
    @GetMapping("/identificacion/{numeroIdentificacion}")
    public ResponseEntity<Paciente> obtenerPacientePorNumeroIdentificacion(
            @PathVariable String numeroIdentificacion) {
        Optional<Paciente> paciente = pacienteService.obtenerPacientePorNumeroIdentificacion(numeroIdentificacion);
        return paciente.map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Verificar si existe número de identificación
     */
    @GetMapping("/identificacion/{numeroIdentificacion}/existe")
    public ResponseEntity<Boolean> existeNumeroIdentificacion(@PathVariable String numeroIdentificacion) {
        boolean existe = pacienteService.existeNumeroIdentificacion(numeroIdentificacion);
        return ResponseEntity.ok(existe);
    }

    /**
     * Obtener pacientes por tipo de sangre
     */
    @GetMapping("/tipo-sangre/{tipoSangre}")
    public ResponseEntity<List<Paciente>> obtenerPacientesPorTipoSangre(@PathVariable String tipoSangre) {
        List<Paciente> pacientes = pacienteService.obtenerPacientesPorTipoSangre(tipoSangre);
        return ResponseEntity.ok(pacientes);
    }

    /**
     * Obtener pacientes sin tipo de sangre
     */
    @GetMapping("/sin-tipo-sangre")
    public ResponseEntity<List<Paciente>> obtenerPacientesSinTipoSangre() {
        List<Paciente> pacientes = pacienteService.obtenerPacientesSinTipoSangre();
        return ResponseEntity.ok(pacientes);
    }

    /**
     * Obtener pacientes activos
     */
    @GetMapping("/activos")
    public ResponseEntity<List<Paciente>> obtenerPacientesActivos() {
        List<Paciente> pacientes = pacienteService.obtenerPacientesActivos();
        return ResponseEntity.ok(pacientes);
    }

    /**
     * Buscar pacientes por nombre
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<Paciente>> buscarPacientesPorNombre(@RequestParam String nombre) {
        List<Paciente> pacientes = pacienteService.buscarPacientesPorNombre(nombre);
        return ResponseEntity.ok(pacientes);
    }

    /**
     * Buscar pacientes activos por nombre
     */
    @GetMapping("/buscar/activos")
    public ResponseEntity<List<Paciente>> buscarPacientesActivosPorNombre(@RequestParam String nombre) {
        List<Paciente> pacientes = pacienteService.buscarPacientesActivosPorNombre(nombre);
        return ResponseEntity.ok(pacientes);
    }

    /**
     * Obtener paciente por email
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<Paciente> obtenerPacientePorEmail(@PathVariable String email) {
        Optional<Paciente> paciente = pacienteService.obtenerPacientePorEmail(email);
        return paciente.map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Obtener pacientes por ciudad
     */
    @GetMapping("/ciudad/{ciudad}")
    public ResponseEntity<List<Paciente>> obtenerPacientesPorCiudad(@PathVariable String ciudad) {
        List<Paciente> pacientes = pacienteService.obtenerPacientesPorCiudad(ciudad);
        return ResponseEntity.ok(pacientes);
    }

    /**
     * Obtener pacientes por país
     */
    @GetMapping("/pais/{pais}")
    public ResponseEntity<List<Paciente>> obtenerPacientesPorPais(@PathVariable String pais) {
        List<Paciente> pacientes = pacienteService.obtenerPacientesPorPais(pais);
        return ResponseEntity.ok(pacientes);
    }

    /**
     * Obtener pacientes con alergias
     */
    @GetMapping("/con-alergias")
    public ResponseEntity<List<Paciente>> obtenerPacientesConAlergias() {
        List<Paciente> pacientes = pacienteService.obtenerPacientesConAlergias();
        return ResponseEntity.ok(pacientes);
    }

    /**
     * Obtener pacientes con enfermedades crónicas
     */
    @GetMapping("/con-enfermedades-cronicas")
    public ResponseEntity<List<Paciente>> obtenerPacientesConEnfermedadesCronicas() {
        List<Paciente> pacientes = pacienteService.obtenerPacientesConEnfermedadesCronicas();
        return ResponseEntity.ok(pacientes);
    }

    /**
     * Obtener pacientes con medicamentos actuales
     */
    @GetMapping("/con-medicamentos-actuales")
    public ResponseEntity<List<Paciente>> obtenerPacientesConMedicamentosActuales() {
        List<Paciente> pacientes = pacienteService.obtenerPacientesConMedicamentosActuales();
        return ResponseEntity.ok(pacientes);
    }

    /**
     * Buscar pacientes por alergia específica
     */
    @GetMapping("/buscar/alergia")
    public ResponseEntity<List<Paciente>> buscarPacientesPorAlergia(@RequestParam String alergia) {
        List<Paciente> pacientes = pacienteService.buscarPacientesPorAlergia(alergia);
        return ResponseEntity.ok(pacientes);
    }

    /**
     * Buscar pacientes por enfermedad específica
     */
    @GetMapping("/buscar/enfermedad")
    public ResponseEntity<List<Paciente>> buscarPacientesPorEnfermedad(@RequestParam String enfermedad) {
        List<Paciente> pacientes = pacienteService.buscarPacientesPorEnfermedad(enfermedad);
        return ResponseEntity.ok(pacientes);
    }

    /**
     * Buscar pacientes por medicamento específico
     */
    @GetMapping("/buscar/medicamento")
    public ResponseEntity<List<Paciente>> buscarPacientesPorMedicamento(@RequestParam String medicamento) {
        List<Paciente> pacientes = pacienteService.buscarPacientesPorMedicamento(medicamento);
        return ResponseEntity.ok(pacientes);
    }

    /**
     * Obtener pacientes con contacto de emergencia
     */
    @GetMapping("/con-contacto-emergencia")
    public ResponseEntity<List<Paciente>> obtenerPacientesConContactoEmergencia() {
        List<Paciente> pacientes = pacienteService.obtenerPacientesConContactoEmergencia();
        return ResponseEntity.ok(pacientes);
    }

    /**
     * Obtener pacientes sin contacto de emergencia
     */
    @GetMapping("/sin-contacto-emergencia")
    public ResponseEntity<List<Paciente>> obtenerPacientesSinContactoEmergencia() {
        List<Paciente> pacientes = pacienteService.obtenerPacientesSinContactoEmergencia();
        return ResponseEntity.ok(pacientes);
    }

    /**
     * Obtener pacientes con perfil completo
     */
    @GetMapping("/perfil-completo")
    public ResponseEntity<List<Paciente>> obtenerPacientesConPerfilCompleto() {
        List<Paciente> pacientes = pacienteService.obtenerPacientesConPerfilCompleto();
        return ResponseEntity.ok(pacientes);
    }

    /**
     * Obtener pacientes con perfil incompleto
     */
    @GetMapping("/perfil-incompleto")
    public ResponseEntity<List<Paciente>> obtenerPacientesConPerfilIncompleto() {
        List<Paciente> pacientes = pacienteService.obtenerPacientesConPerfilIncompleto();
        return ResponseEntity.ok(pacientes);
    }

    /**
     * Contar pacientes activos
     */
    @GetMapping("/contar/activos")
    public ResponseEntity<Long> contarPacientesActivos() {
        Long count = pacienteService.contarPacientesActivos();
        return ResponseEntity.ok(count);
    }

    /**
     * Contar pacientes totales
     */
    @GetMapping("/contar")
    public ResponseEntity<Long> contarPacientes() {
        Long count = pacienteService.contarPacientes();
        return ResponseEntity.ok(count);
    }

    /**
     * Contar pacientes por tipo de sangre
     */
    @GetMapping("/contar/tipo-sangre/{tipoSangre}")
    public ResponseEntity<Long> contarPacientesPorTipoSangre(@PathVariable String tipoSangre) {
        Long count = pacienteService.contarPacientesPorTipoSangre(tipoSangre);
        return ResponseEntity.ok(count);
    }

    /**
     * Obtener pacientes por género
     */
    @GetMapping("/genero/{genero}")
    public ResponseEntity<List<Paciente>> obtenerPacientesPorGenero(@PathVariable Usuario.Genero genero) {
        List<Paciente> pacientes = pacienteService.obtenerPacientesPorGenero(genero);
        return ResponseEntity.ok(pacientes);
    }

    /**
     * Obtener pacientes recientes
     */
    @GetMapping("/recientes")
    public ResponseEntity<List<Paciente>> obtenerPacientesRecientes() {
        List<Paciente> pacientes = pacienteService.obtenerPacientesRecientes();
        return ResponseEntity.ok(pacientes);
    }

    /**
     * Obtener todos los pacientes ordenados por nombre
     */
    @GetMapping("/ordenados/nombre")
    public ResponseEntity<List<Paciente>> obtenerTodosLosPacientesOrdenadosPorNombre() {
        List<Paciente> pacientes = pacienteService.obtenerTodosLosPacientesOrdenadosPorNombre();
        return ResponseEntity.ok(pacientes);
    }

    /**
     * Obtener pacientes con historial médico
     */
    @GetMapping("/con-historial-medico")
    public ResponseEntity<List<Paciente>> obtenerPacientesConHistorialMedico() {
        List<Paciente> pacientes = pacienteService.obtenerPacientesConHistorialMedico();
        return ResponseEntity.ok(pacientes);
    }

    /**
     * Obtener pacientes sin historial médico
     */
    @GetMapping("/sin-historial-medico")
    public ResponseEntity<List<Paciente>> obtenerPacientesSinHistorialMedico() {
        List<Paciente> pacientes = pacienteService.obtenerPacientesSinHistorialMedico();
        return ResponseEntity.ok(pacientes);
    }

    /**
     * Obtener pacientes con citas programadas
     */
    @GetMapping("/con-citas-programadas")
    public ResponseEntity<List<Paciente>> obtenerPacientesConCitasProgramadas() {
        List<Paciente> pacientes = pacienteService.obtenerPacientesConCitasProgramadas();
        return ResponseEntity.ok(pacientes);
    }

    /**
     * Verificar si la identificación es única (excluyendo un ID específico)
     */
    @GetMapping("/identificacion-unica")
    public ResponseEntity<Boolean> identificacionEsUnica(
            @RequestParam String numeroIdentificacion,
            @RequestParam(required = false) Integer idExcluir) {
        boolean esUnica = pacienteService.identificacionEsUnica(numeroIdentificacion, idExcluir);
        return ResponseEntity.ok(esUnica);
    }
}
