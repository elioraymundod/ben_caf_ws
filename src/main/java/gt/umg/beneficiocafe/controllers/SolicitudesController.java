/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.umg.beneficiocafe.controllers;

import gt.umg.beneficiocafe.dto.SolicitudesDto;
import gt.umg.beneficiocafe.models.BCSolicitudes;
import gt.umg.beneficiocafe.payload.request.CambiarEstadoSolicitudRequest;
import gt.umg.beneficiocafe.payload.request.CrearSolicitudRequest;
import gt.umg.beneficiocafe.payload.request.EstadoRequest;
import gt.umg.beneficiocafe.payload.request.UsuarioRequest;
import gt.umg.beneficiocafe.payload.request.ValidarSolicitudRequest;
import gt.umg.beneficiocafe.projections.SolicitudesDetalleProjection;
import gt.umg.beneficiocafe.services.SolicitudesService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
    @PreAuthorize("hasRole('ROLE_UMG_BC_AGRICULTOR')")
    public ResponseEntity<?> registrarSolicitud(@Valid @RequestBody CrearSolicitudRequest nuevaSolicitud) {
        return solicitudesService.registrarSolicitud(nuevaSolicitud);
    }
    
    @PostMapping("/agricultor")
    @PreAuthorize("hasRole('ROLE_UMG_BC_AGRICULTOR')")
    public ResponseEntity<List<SolicitudesDetalleProjection>> obtenerSolicitudesByUsuario(@Valid @RequestBody UsuarioRequest usuario) {
        List<SolicitudesDetalleProjection> respuesta = solicitudesService.getSolicitudesByUsuario(usuario);
        return ResponseEntity.ok(respuesta);
    }
    
    @PostMapping("/estado")
    @PreAuthorize("hasRole('ROLE_UMG_BC_BENEFICIO') || hasRole('UMG_BC_PESO_CABAL')")
    public ResponseEntity<List<SolicitudesDetalleProjection>> obtenerSolicitudesByEstado(@Valid @RequestBody EstadoRequest estado) {
        List<SolicitudesDetalleProjection> respuesta = solicitudesService.getSolicitudesByEstado(estado);
        return ResponseEntity.ok(respuesta);
    }
    
    @PostMapping("/validate")
    @PreAuthorize("hasRole('ROLE_UMG_BC_BENEFICIO')")
    public ResponseEntity<?> validarSolicitud(@Valid @RequestBody ValidarSolicitudRequest solicitud) {
        return solicitudesService.validarSolicitud(solicitud);
    }
    
    @PutMapping("/update")
    @PreAuthorize("hasRole('ROLE_UMG_BC_BENEFICIO') || hasRole('ROLE_UMG_BC_PESO_CABAL')")
    public ResponseEntity<?> actualizarEstadoSolicitud(@Valid @RequestBody CambiarEstadoSolicitudRequest solicitud) {
        return solicitudesService.cambiarEstadoSolicitud(solicitud);
    }
    
    @PutMapping("/update/agricultor")
    @PreAuthorize("hasRole('ROLE_UMG_BC_AGRICULTOR')")
    public ResponseEntity<?> actualizarEstadoSolicitudAgricultor(@Valid @RequestBody CambiarEstadoSolicitudRequest solicitud) {
        return solicitudesService.cambiarEstadoSolicitud(solicitud);
    }
    
    @PostMapping("/faltantes/sobrantes")
    @PreAuthorize("hasRole('ROLE_UMG_BC_BENEFICIO')")
    public ResponseEntity<?> validarFaltantesSobrantes(@Valid @RequestBody ValidarSolicitudRequest solicitud) {
        return solicitudesService.validarFaltantesSobrantes(solicitud);
    }
    
}
