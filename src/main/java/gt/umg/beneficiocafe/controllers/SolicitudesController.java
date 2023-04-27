/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.umg.beneficiocafe.controllers;

import gt.umg.beneficiocafe.payload.request.CambiarEstadoSolicitudRequest;
import gt.umg.beneficiocafe.payload.request.CrearSolicitudRequest;
import gt.umg.beneficiocafe.payload.request.ValidarSolicitudRequest;
import gt.umg.beneficiocafe.services.SolicitudesService;
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
@RequestMapping("/api/ben-caf/solicitud")
public class SolicitudesController {
    private SolicitudesService solicitudesService;

    public SolicitudesController(SolicitudesService solicitudesService) {
        this.solicitudesService = solicitudesService;
    }
    
    @PostMapping("/create")
    @PreAuthorize("hasRole('UMG_BC_AGRICULTOR')")
    public ResponseEntity<?> registrarSolicitud(@Valid @RequestBody CrearSolicitudRequest nuevaSolicitud) {
        return solicitudesService.registrarSolicitud(nuevaSolicitud);
    }
    
    @PostMapping("/validate")
    @PreAuthorize("hasRole('UMG_BC_BENEFICIO')")
    public ResponseEntity<?> validarSolicitud(@Valid @RequestBody ValidarSolicitudRequest solicitud) {
        return solicitudesService.validarSolicitud(solicitud);
    }
    
    @PutMapping("/update")
    @PreAuthorize("hasRole('UMG_BC_BENEFICIO')")
    public ResponseEntity<?> actualizarEstadoSolicitud(@Valid @RequestBody CambiarEstadoSolicitudRequest solicitud) {
        return solicitudesService.cambiarEstadoSolicitud(solicitud);
    }
    
}
