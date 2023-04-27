/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gt.umg.beneficiocafe.repository;

import gt.umg.beneficiocafe.models.BCSolicitudes;
import gt.umg.beneficiocafe.projections.SolicitudValidaProjection;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Elio Raymundo
 */
public interface SolicitudesRepository extends JpaRepository<BCSolicitudes, UUID> {

    @Query(value = "select \n"
            + " (select bp.permitido_en_beneficio from umg_beneficio_cafe.bc_pilotos bp where bp.licencia_piloto = bs.piloto) \"pilotoEstatus\",\n"
            + " (select bt.permitido_en_beneficio from umg_beneficio_cafe.bc_transportes bt where bt.placa_transporte = bs.placa) \"transporteEstatus\"\n"
            + " from umg_beneficio_cafe.bc_solicitudes bs \n"
            + " where bs.id_solicitud =:solicitud",
            nativeQuery = true
    )
    public SolicitudValidaProjection validarSolicitud(@Param("solicitud") UUID solicitud);
    
    @Query(value = "select \n"
            + " bs.* \n"
            + " from umg_beneficio_cafe.bc_solicitudes bs \n"
            + " where bs.id_solicitud =:solicitud",
            nativeQuery = true
    )
    public BCSolicitudes getSolicitudById(@Param("solicitud") UUID solicitud);
    
    Optional<BCSolicitudes> findByIdSolicitud(String solicitud);
}
