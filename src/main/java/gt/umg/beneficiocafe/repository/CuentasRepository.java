/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gt.umg.beneficiocafe.repository;

import gt.umg.beneficiocafe.models.BCCuentas;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Elio Raymundo
 */
public interface CuentasRepository extends JpaRepository<BCCuentas, String> {

    @Query(value = "select \n"
            + " bs.* \n"
            + " from umg_beneficio_cafe.bc_cuentas bs \n"
            + " where bs.id_cuenta =:idCuenta",
            nativeQuery = true
    )
    public BCCuentas getCuentaById(@Param("idCuenta") UUID cuenta);

    @Query(value = "select \n"
            + " bs.* \n"
            + " from umg_beneficio_cafe.bc_cuentas bs \n"
            + " where bs.solicitud =:idSolicitud",
            nativeQuery = true
    )
    public BCCuentas getCuentaBySolicitud(@Param("idSolicitud") UUID solicitud);
}
