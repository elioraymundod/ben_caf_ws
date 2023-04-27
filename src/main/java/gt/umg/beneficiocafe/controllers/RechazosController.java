/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.umg.beneficiocafe.controllers;

import gt.umg.beneficiocafe.payload.request.CrearRechazoRequest;
import gt.umg.beneficiocafe.services.RechazosService;
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
@RequestMapping("/api/ben-caf/rechazos")
public class RechazosController {
    private RechazosService rechazosService;

    public RechazosController(RechazosService rechazosService) {
        this.rechazosService = rechazosService;
    }
    
    @PostMapping("/create")
    @PreAuthorize("hasRole('UMG_BC_BENEFICIO')")
    public ResponseEntity<?> crearRechazo(@Valid @RequestBody CrearRechazoRequest rechazo) {
        return rechazosService.crearRechazo(rechazo);
    }
}
