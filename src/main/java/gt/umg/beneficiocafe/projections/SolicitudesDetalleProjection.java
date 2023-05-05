/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gt.umg.beneficiocafe.projections;

import java.util.Date;
import java.util.UUID;

/**
 *
 * @author Elio Raymundo
 */
public interface SolicitudesDetalleProjection {
    public String getEstado();
    public String getNombrePiloto();
    public String getPlaca();
    public String getPiloto();
    public String getDescripcion();
    public String getIdSolicitud();
    public Boolean getParcialidadesCompletas();
    public String getNoCuenta();
    public String getIdCuenta();
    /*
    public UUID getEstadoSolicitud();
    public Integer getCantidadParcialidades();
    public UUID getUsuarioCreacion();
    public Date getFechaCreacion();
    public UUID getUsuarioModificacion();
    public Date getFechaModificacion();
    */
}
