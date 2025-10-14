package com.mediapp.citasbackend.dtos;

import com.mediapp.citasbackend.entities.Usuario;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;
    
    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 100, message = "El apellido no puede exceder 100 caracteres")
    private String apellido;
    
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe ser válido")
    @Size(max = 100, message = "El email no puede exceder 100 caracteres")
    private String email;
    
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, max = 255, message = "La contraseña debe tener entre 6 y 255 caracteres")
    private String contraseña;
    
    @Past(message = "La fecha de nacimiento debe ser anterior a hoy")
    private LocalDate fechaNacimiento;
    
    private Usuario.Genero genero;
    
    @Size(max = 20, message = "El teléfono no puede exceder 20 caracteres")
    private String telefono;
    
    @Size(max = 150, message = "La dirección no puede exceder 150 caracteres")
    private String direccion;
    
    @Size(max = 100, message = "La ciudad no puede exceder 100 caracteres")
    private String ciudad;
    
    @Size(max = 100, message = "El país no puede exceder 100 caracteres")
    private String pais;
    
    @NotNull(message = "El tipo de usuario es obligatorio")
    private Usuario.TipoUsuario tipoUsuario;
}
