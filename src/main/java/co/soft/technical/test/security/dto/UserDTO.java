package co.soft.technical.test.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class UserDTO {
    @NotBlank(message = "El nombre es requerido")
    private String name;
    @NotBlank(message = "El apellido es requerido")
    private String lastName;
    @NotBlank(message = "El nombre de usuario es requerido")
    private String username;
    @Email(message = "El email no cumple con los requerimientos")
    @NotNull(message = "El email es requerido")
    private String email;
    @NotBlank(message = "La contraseña es requerida")
    @NotNull(message = "La contraseña es requerida")
    private String password;
    @NotNull(message = "La lista de roles es requerida")
    private Set<String> roles = new HashSet<>();
}
