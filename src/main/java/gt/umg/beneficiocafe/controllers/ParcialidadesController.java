/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.umg.beneficiocafe.controllers;

import gt.umg.beneficiocafe.models.BCParcialidades;
import gt.umg.beneficiocafe.payload.request.AtenderParcialidadRequest;
import gt.umg.beneficiocafe.payload.request.AtendidoRequest;
import gt.umg.beneficiocafe.payload.request.CambiarEstadoSolicitudRequest;
import gt.umg.beneficiocafe.payload.request.CrearParcialidadesRequest;
import gt.umg.beneficiocafe.payload.request.SolicitudRequest;
import gt.umg.beneficiocafe.services.ParcialidadesService;
import java.util.List;
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
    
    
    @PostMapping("/cantidad")
    @PreAuthorize("hasRole('ROLE_UMG_BC_AGRICULTOR') || hasRole('ROLE_UMG_BC_PESO_CABAL') ")
    public ResponseEntity<?> obtenerCantidadParcialidades(@Valid @RequestBody SolicitudRequest solicitud) {
        return parcialidadesService.getCantidadParcialidades(solicitud);
    }
    
    @PostMapping("/revision")
    @PreAuthorize("hasRole('ROLE_UMG_BC_PESO_CABAL')")
    public List<BCParcialidades> obtenerParcialidadesPendientes(@Valid @RequestBody AtendidoRequest atendido) {
        return parcialidadesService.getParcialidadesPendientes(atendido);
    }
    
    @PutMapping("/update")
    @PreAuthorize("hasRole('ROLE_UMG_BC_PESO_CABAL')")
    public ResponseEntity<?> actualizarEstadoSolicitudAgricultor(@Valid @RequestBody AtenderParcialidadRequest parcialidad) {
        return parcialidadesService.atenderParcialidad(parcialidad);
    }
    
}
