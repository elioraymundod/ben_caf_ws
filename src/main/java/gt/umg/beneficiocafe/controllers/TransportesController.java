/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.umg.beneficiocafe.controllers;

import gt.umg.beneficiocafe.payload.request.CrearTransporteRequest;
import gt.umg.beneficiocafe.services.TransportesService;
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
@RequestMapping("/api/ben-caf/transportes")
public class TransportesController {
    private TransportesService transportesService;

    public TransportesController(TransportesService transportesService) {
        this.transportesService = transportesService;
    }
    
    @PostMapping("/create")
    @PreAuthorize("hasRole('UMG_BC_ADMINISTRADOR')")
    public ResponseEntity<?> registrarPiloto(@Valid @RequestBody CrearTransporteRequest nuevoTransporte) {
        return transportesService.registrarTransporte(nuevoTransporte);
    }
}
