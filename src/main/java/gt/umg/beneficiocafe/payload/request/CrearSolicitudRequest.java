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
public class CrearSolicitudRequest {
    @NotNull
    private UUID estadoSolicitiud;
    
    @NotBlank
    @NotNull
    @Size(min = 3, max = 15)
    private String placa;
    
    @NotNull
    private Integer cantidadParcialidades;
    
    @NotBlank
    @NotNull
    @Size(min = 3, max = 15)
    private String piloto;
    
    @NotBlank
    @NotNull
    @Size(min = 5, max = 100)
    private String descripcion;
    
    @NotNull
    private UUID usuarioCreacion;
    
}
