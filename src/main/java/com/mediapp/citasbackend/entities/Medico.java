package com.mediapp.citasbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "medicos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medico")
    private Integer idMedico;

    @OneToOne
    @JoinColumn(name = "id_usuario", nullable = false, unique = true)
    private Usuario usuario;

    @Column(name = "numero_licencia", length = 50)
    private String numeroLicencia;

    @ManyToOne
    @JoinColumn(name = "id_especialidad")
    private Especialidad especialidad;

    @Column(name = "hospital_afiliado", length = 100)
    private String hospitalAfiliado;

    @Column(name = "experiencia_anos")
    private Integer experienciaAnos;

    @Column(name = "resumen_bio", columnDefinition = "TEXT")
    private String resumenBio;

    @Column(name = "calificacion_promedio", precision = 2, scale = 1)
    private BigDecimal calificacionPromedio;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_verificacion", nullable = false, length = 20)
    private EstadoVerificacion estadoVerificacion;

    @Column(name = "fecha_verificacion")
    private LocalDate fechaVerificacion;

    @PrePersist
    protected void onCreate() {
        if (estadoVerificacion == null) {
            estadoVerificacion = EstadoVerificacion.PENDIENTE;
        }
    }

    public enum EstadoVerificacion {
        VERIFICADO,
        PENDIENTE
    }
}
