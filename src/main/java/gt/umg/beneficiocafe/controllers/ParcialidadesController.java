/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.umg.beneficiocafe.controllers;

import gt.umg.beneficiocafe.payload.request.CrearParcialidadesRequest;
import gt.umg.beneficiocafe.services.ParcialidadesService;
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
@RequestMapping("/api/ben-caf/parcialidades")
public class ParcialidadesController {
    private ParcialidadesService parcialidadesService;

    public ParcialidadesController(ParcialidadesService parcialidadesService) {
        this.parcialidadesService = parcialidadesService;
    }
    
    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_UMG_BC_AGRICULTOR')")
    public ResponseEntity<?> enviarParcialidad(@Valid @RequestBody CrearParcialidadesRequest parcialidad) {
        return parcialidadesService.enviarParcialidad(parcialidad);
    }
    
}
