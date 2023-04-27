/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.umg.beneficiocafe.payload.request;

import java.util.Date;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Elio Raymundo
 */

@Getter
@Setter

public class CambiarEstadoSolicitudRequest {
    
    @NotNull
    private UUID solicitud;
    
    @NotNull
    private UUID nuevoEstado;
    
    @NotNull
    private UUID usuarioModificacion;
    
}
