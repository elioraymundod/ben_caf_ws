/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.umg.beneficiocafe.payload.request;

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

public class CrearAnexoRequest {
    @NotNull
    private UUID solicitud;
    
    @NotBlank
    @NotNull
    @Size(min = 5, max = 300)
    private String observaciones;
    
    @NotBlank
    @NotNull
    @Size(min = 1, max = 1)
    private String sobranteFaltante;
    
    @NotNull
    private UUID usuarioCreacion;
}
