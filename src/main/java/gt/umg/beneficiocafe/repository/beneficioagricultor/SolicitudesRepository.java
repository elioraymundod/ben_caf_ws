/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gt.umg.beneficiocafe.repository.beneficioagricultor;

import gt.umg.beneficiocafe.dto.SolicitudesDto;
import gt.umg.beneficiocafe.models.beneficioagricultor.BCSolicitudes;
import gt.umg.beneficiocafe.projections.BestClientsProjection;
import gt.umg.beneficiocafe.projections.ClientsWitthSobrantesFaltantes;
import gt.umg.beneficiocafe.projections.SolicitudValidaProjection;
import gt.umg.beneficiocafe.projections.SolicitudesDetalleProjection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.hibernate.dialect.PostgreSQL10Dialect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Elio Raymundo
 */
@Repository
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
            + "	be.descripcion as estado, \n"
            + "	bp.nombre as nombrePiloto,\n"
            + "	bs.placa as placa,\n"
            + "	bs.piloto,\n"
            + "	bs.total_pesaje as totalPesaje,\n"
            + "    cast(id_solicitud as text) as idSolicitud,\n"
            + "	cast((select bc.id_cuenta from umg_beneficio_cafe.bc_cuentas bc where bc.solicitud = bs.id_solicitud limit 1) as text) as idCuenta,\n"
            + "    (select bc.no_cuenta from umg_beneficio_cafe.bc_cuentas bc where bc.solicitud = bs.id_solicitud limit 1 ) as noCuenta,\n"
            + "    case \n"
            + "    	when bs.cantidad_parcialidades <= (select \n"
            + "			count(*) \"cantidadParcialidades\"\n"
            + "			from umg_beneficio_cafe.bc_parcialidades bp \n"
            + "			where bp.solicitud = bs.id_solicitud) then false\n"
            + "    	when bs.cantidad_parcialidades > (select \n"
            + "			count(*) \"cantidadParcialidades\"\n"
            + "			from umg_beneficio_cafe.bc_parcialidades bp \n"
            + "			where bp.solicitud = bs.id_solicitud) then true\n"
            + "    end as parcialidadesCompletas\n"
            + "from umg_beneficio_cafe.bc_solicitudes bs\n"
            + "inner join umg_beneficio_cafe.bc_estados be on be.id_estado  = bs.estado_solicitud\n"
            + "inner join umg_beneficio_cafe.bc_pilotos bp on bp.licencia_piloto = bs.piloto \n"
            + "where bs.usuario_creacion  = :usuario order by bs.fecha_creacion desc ",
            nativeQuery = true
    )
    public List<SolicitudesDetalleProjection> solicitudesByUser(@Param("usuario") UUID usuario);

    @Query(value = "select \n"
            + "	be.descripcion as estado, \n"
            + "	bp.nombre as nombrePiloto,\n"
            + "	bs.placa as placa,\n"
            + "	bs.piloto,\n"
            + "	bs.total_pesaje as totalPesaje,\n"
            + "    cast(id_solicitud as text) as idSolicitud,\n"
            + "	cast((select bc.id_cuenta from umg_beneficio_cafe.bc_cuentas bc where bc.solicitud = bs.id_solicitud limit 1) as text) as idCuenta,\n"
            + "    (select bc.no_cuenta from umg_beneficio_cafe.bc_cuentas bc where bc.solicitud = bs.id_solicitud limit 1 ) as noCuenta,\n"
            + "    case \n"
            + "    	when bs.cantidad_parcialidades <= (select \n"
            + "			count(*) \"cantidadParcialidades\"\n"
            + "			from umg_beneficio_cafe.bc_parcialidades bp \n"
            + "			where bp.solicitud = bs.id_solicitud) then false\n"
            + "    	when bs.cantidad_parcialidades > (select \n"
            + "			count(*) \"cantidadParcialidades\"\n"
            + "			from umg_beneficio_cafe.bc_parcialidades bp \n"
            + "			where bp.solicitud = bs.id_solicitud) then true\n"
            + "    end as parcialidadesCompletas\n"
            + "from umg_beneficio_cafe.bc_solicitudes bs\n"
            + "inner join umg_beneficio_cafe.bc_estados be on be.id_estado  = bs.estado_solicitud\n"
            + "inner join umg_beneficio_cafe.bc_pilotos bp on bp.licencia_piloto = bs.piloto \n"
            + "where bs.estado_solicitud  = :estado order by bs.fecha_creacion desc ",
            nativeQuery = true
    )
    public List<SolicitudesDetalleProjection> solicitudesByEstado(@Param("estado") UUID estado);

    @Query(value = "select \n"
            + " bs.* \n"
            + " from umg_beneficio_cafe.bc_solicitudes bs \n"
            + " where bs.id_solicitud =:solicitud",
            nativeQuery = true
    )
    public BCSolicitudes getSolicitudById(@Param("solicitud") UUID solicitud);

    Optional<BCSolicitudes> findByIdSolicitud(String solicitud);

    @Query(value = "select * \n"
            + "from umg_beneficio_cafe.bc_solicitudes bs where piloto = :piloto and estado_solicitud <> 'c5e2a590-eb00-11ed-a05b-0242ac120003';",
            nativeQuery = true
    )
    public List<BCSolicitudes> consultarPilotoAsignado(@Param("piloto") String piloto);

    @Query(value = "select sum(bs.total_pesaje) as totalPesaje, bu.username ,\n"
            + "count(bs.total_pesaje) as totalSolicitudes\n"
            + "from umg_beneficio_cafe.bc_solicitudes bs \n"
            + "\n"
            + "inner join umg_beneficio_cafe.bc_usuarios bu on bu.id_usuario = bs.usuario_creacion \n"
            + "\n"
            + "group by bu.username order by totalPesaje desc\n"
            + "\n"
            + "limit 1",
            nativeQuery = true
    )
    public List<BestClientsProjection> getBestClients();

    @Query(value = "select \n"
            + "ba.observaciones , ba.sobrante_faltante sobranteFaltante, ba.peso, bc.no_cuenta cuenta,\n"
            + "(select bu2.username\n"
            + "from umg_beneficio_cafe.bc_usuarios bu2 where bu2.id_usuario = bu.id_usuario) as agricultor\n"
            + "from umg_beneficio_cafe.bc_anexos ba \n"
            + "inner join umg_beneficio_cafe.bc_solicitudes bs on bs.id_solicitud = ba.solicitud \n"
            + "inner join umg_beneficio_cafe.bc_usuarios bu on bu.id_usuario = \n"
            + "(select bs2.usuario_creacion \n"
            + "from umg_beneficio_cafe.bc_solicitudes bs2 where bs2.id_solicitud = bs.id_solicitud)\n"
            + "inner join umg_beneficio_cafe.bc_cuentas bc on bc.solicitud = ba.solicitud ",
            nativeQuery = true
    )
    public List<ClientsWitthSobrantesFaltantes> getClientsWithSobrantesFaltantes();

}
