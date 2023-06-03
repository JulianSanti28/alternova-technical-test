package co.soft.technical.test.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class LoginDTO {
    @NotBlank(message = "El nombre de usuario es requerido.")
    private String username;
    @NotBlank(message = "La contraseña es requerida.")
    private String password;
}
