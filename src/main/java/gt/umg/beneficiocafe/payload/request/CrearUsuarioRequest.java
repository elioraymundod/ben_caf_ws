/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.umg.beneficiocafe.payload.request;

import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Elio Raymundo
 */

@Getter
@Setter
public class CrearUsuarioRequest {
    @NotBlank
    @NotNull
    @Size(min = 3, max = 20)
    private String username;
    
    @NotBlank
    @NotNull
    @Size(max = 50)
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}")
    private String correoUsuario;
    
    @NotBlank
    @NotNull
    @Size(min = 8, max = 225)
    private String passwordUsuario;
    
    private Set<String> rolUsuario;
    
}
