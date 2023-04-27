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
public class CrearParcialidadesRequest {
    @NotNull
    private UUID solicitud;
    
    @NotNull
    private Double pesoEnviado;
    
    @NotBlank
    @NotNull
    @Size(min = 6, max = 15)
    private String placa;
    
    @NotBlank
    @NotNull
    @Size(min = 10, max = 15)
    private String piloto;
    
    @NotNull
    private UUID usuarioCreacion;
    
}
