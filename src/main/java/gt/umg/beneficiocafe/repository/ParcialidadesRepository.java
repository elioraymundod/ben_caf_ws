/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gt.umg.beneficiocafe.repository;

import gt.umg.beneficiocafe.models.BCCuentas;
import gt.umg.beneficiocafe.models.BCParcialidades;
import gt.umg.beneficiocafe.projections.CantidadParcialidadesProjection;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Elio Raymundo
 */
public interface ParcialidadesRepository extends JpaRepository<BCParcialidades, String> {

    @Query(value = "select \n"
            + "count(*) \"cantidadParcialidades\"\n"
            + "from umg_beneficio_cafe.bc_parcialidades bp \n"
            + "where bp.solicitud =:solicitud",
            nativeQuery = true
    )
    public CantidadParcialidadesProjection getCantidadParcialidades(@Param("solicitud") UUID solicitud);

    @Query(value = "select \n"
            + " bs.* \n"
            + " from umg_beneficio_cafe.bc_parcialidades bs \n"
            + " where bs.id_parcialidad =:parcialidad",
            nativeQuery = true
    )
    public BCParcialidades getParcialidadById(@Param("parcialidad") UUID parcialidad);

    @Query(value = "select *\n"
            + "from umg_beneficio_cafe.bc_parcialidades bp \n"
            + "where bp.atendido = :estado",
            nativeQuery = true
    )
    public List<BCParcialidades> getParcialidadesPendientes(@Param("estado") Boolean estado);

    @Query(value = "select *\n"
            + "from umg_beneficio_cafe.bc_parcialidades bp \n"
            + "where bp.solicitud  = :idSolicitud",
            nativeQuery = true
    )
    public List<BCParcialidades> getParcialidadesBySolicitud(@Param("idSolicitud") UUID solicitud);
}
