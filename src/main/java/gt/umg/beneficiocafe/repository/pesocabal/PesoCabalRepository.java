/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gt.umg.beneficiocafe.repository.pesocabal;

import gt.umg.beneficiocafe.models.pesocabal.BCPesajesBascula;
import gt.umg.beneficiocafe.projections.TotalPesajeProjection;
import java.util.Date;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Elio Raymundo
 */
@Repository
public interface PesoCabalRepository extends JpaRepository<BCPesajesBascula, UUID> {

    @Query(value = "select \n"
            + " bs.* \n"
            + " from umg_peso_cabal.bc_pesajes_bascula bs \n"
            + " where bs.id_pesaje =:pesaje",
            nativeQuery = true
    )
    public BCPesajesBascula getPesajeById(@Param("pesaje") UUID pesaje);

    @Query(value = "select \n"
            + " bs.* \n"
            + " from umg_peso_cabal.bc_pesajes_bascula bs \n"
            + " where bs.parcialidad =:parcialidad",
            nativeQuery = true
    )
    public BCPesajesBascula getPesajeByParcialidad(@Param("parcialidad") UUID parcialidad);

    @Query(value = "select coalesce(sum(bpb.peso) , 0) \n"
            + "from umg_peso_cabal.bc_pesajes_bascula bpb \n"
            + "where DATE(bpb.fecha_creacion) = :pFecha;",
            nativeQuery = true
    )
    public Integer getTotalPesajeByFecha(@Param("pFecha") String pFecha);

    @Query(value = "select coalesce(sum(bpb.peso) , 0)\n"
            + "from umg_peso_cabal.bc_pesajes_bascula bpb \n"
            + "where DATE(bpb.fecha_creacion) between :pFechaInicio and :pFechaFin ;",
            nativeQuery = true
    )
    public Integer getTotalPesajesByRangoFechas(@Param("pFechaInicio") Date pFechaInicio, @Param("pFechaFin") Date pFechaFin);
//TotalPesajeProjection
    
}
