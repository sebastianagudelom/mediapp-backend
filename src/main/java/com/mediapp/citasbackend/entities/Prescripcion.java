package com.mediapp.citasbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "prescripciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prescripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prescripcion")
    private Integer idPrescripcion;

    @ManyToOne
    @JoinColumn(name = "id_historial", nullable = false)
    private HistorialMedico historialMedico;

    @Column(name = "nombre_medicamento", nullable = false, length = 100)
    private String nombreMedicamento;

    @Column(name = "dosis", length = 50)
    private String dosis;

    @Column(name = "frecuencia", length = 50)
    private String frecuencia;

    @Column(name = "duracion_dias")
    private Integer duracionDias;

    @Column(name = "instrucciones", columnDefinition = "TEXT")
    private String instrucciones;

    @Column(name = "fecha_prescripcion", nullable = false)
    private LocalDate fechaPrescripcion;

    @PrePersist
    protected void onCreate() {
        if (fechaPrescripcion == null) {
            fechaPrescripcion = LocalDate.now();
        }
    }
}
