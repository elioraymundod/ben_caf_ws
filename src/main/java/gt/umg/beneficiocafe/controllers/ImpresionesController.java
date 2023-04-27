/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.umg.beneficiocafe.controllers;

import gt.umg.beneficiocafe.payload.request.CrearRegistroImpresionRequest;
import gt.umg.beneficiocafe.services.ImpresionesService;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Elio Raymundo
 */

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/ben-caf/impresiones")
public class ImpresionesController {
    private ImpresionesService impresionesService ;

    public ImpresionesController(ImpresionesService impresionesService) {
        this.impresionesService = impresionesService;
    }
    
    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_UMG_BC_PESO_CABAL')")
    public ResponseEntity<?> registrarImpresion(@Valid @RequestBody CrearRegistroImpresionRequest p) {
        return impresionesService.registrarBitacoraImpresion(p);
    }
}
