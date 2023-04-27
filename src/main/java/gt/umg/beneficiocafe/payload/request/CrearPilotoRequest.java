/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.umg.beneficiocafe.payload.request;

import java.util.Date;
import java.util.UUID;
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

public class CrearPilotoRequest {
    @NotBlank
    @NotNull
    @Size(min = 3, max = 15)
    private String licenciaPiloto;
    
    @NotBlank
    @NotNull
    @Size(min = 3, max = 50)
    private String nombre;
    
    @NotBlank
    @NotNull
    @Size(min = 3, max = 10)
    private String celular;
    
    @NotBlank
    @NotNull
    @Size(min = 8, max = 50)
    private String correo;
    
    @NotNull
    private UUID usuarioCreacion;
    
}
