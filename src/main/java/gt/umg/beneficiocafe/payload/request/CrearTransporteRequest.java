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
public class CrearTransporteRequest {
    @NotBlank
    @NotNull
    @Size(min = 6, max = 15)
    private String placaTransporte;
    
    @NotBlank
    @NotNull
    @Size(min = 3, max = 30)
    private String marca;
    
    @NotBlank
    @NotNull
    @Size(min = 4, max = 30)
    private String color;
    
    @NotNull
    private UUID usuarioCreacion;
        
}
