package com.mediapp.citasbackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {
    private String token;
    private String refreshToken;
    @Builder.Default
    private String tipo = "Bearer";
    private Integer idUsuario;
    private String email;
    private String nombre;
    private String apellido;
    private String tipoUsuario;
}
