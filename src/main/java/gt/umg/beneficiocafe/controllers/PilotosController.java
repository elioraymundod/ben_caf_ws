/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.umg.beneficiocafe.controllers;

import gt.umg.beneficiocafe.payload.request.CrearPilotoRequest;
import gt.umg.beneficiocafe.services.PilotosService;
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
@RequestMapping("/api/ben-caf/pilotos")
public class PilotosController {
    private PilotosService pilotosService;

    public PilotosController(PilotosService pilotosService) {
        this.pilotosService = pilotosService;
    }
    
    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_UMG_BC_ADMINISTRADOR')")
    public ResponseEntity<?> registrarPiloto(@Valid @RequestBody CrearPilotoRequest nuevoPiloto) {
        return pilotosService.registrarPiloto(nuevoPiloto);
    }
}
