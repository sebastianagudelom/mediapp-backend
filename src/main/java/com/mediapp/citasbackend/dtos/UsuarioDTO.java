package com.mediapp.citasbackend.dtos;

import com.mediapp.citasbackend.entities.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    private Integer idUsuario;
    private String nombre;
    private String apellido;
    private String email;
    private LocalDate fechaNacimiento;
    private Usuario.Genero genero;
    private String telefono;
    private String direccion;
    private String ciudad;
    private String pais;
    private Usuario.TipoUsuario tipoUsuario;
    private String fotoPerfil;
    private LocalDateTime fechaRegistro;
    private Usuario.Estado estado;
}
