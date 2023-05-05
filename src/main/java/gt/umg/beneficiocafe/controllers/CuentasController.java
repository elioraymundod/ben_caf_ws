/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.umg.beneficiocafe.controllers;

import gt.umg.beneficiocafe.payload.request.CambiarEstadoCuentaRequest;
import gt.umg.beneficiocafe.payload.request.CrearCuentaRequest;
import gt.umg.beneficiocafe.services.CuentasService;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Elio Raymundo
 */

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/ben-caf/cuentas")
public class CuentasController {
    private CuentasService cuentasService;

    public CuentasController(CuentasService cuentasService) {
        this.cuentasService = cuentasService;
    }
    
    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_UMG_BC_BENEFICIO')")
    public ResponseEntity<?> crearCuenta(@Valid @RequestBody CrearCuentaRequest nuevaCuenta) {
        return cuentasService.crearCuenta(nuevaCuenta);
    }
    
    @PutMapping("/update")
    @PreAuthorize("hasRole('ROLE_UMG_BC_BENEFICIO') || hasRole('ROLE_UMG_BC_AGRICULTOR') || hasRole('ROLE_UMG_BC_PESO_CABAL')")
    public ResponseEntity<?> actualizarEstadoCuenta(@Valid @RequestBody CambiarEstadoCuentaRequest cuenta) {
        return cuentasService.cambiarEstadoCuenta(cuenta);
    }
    
}
