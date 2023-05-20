/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.umg.beneficiocafe.controllers;

import gt.umg.beneficiocafe.models.beneficioagricultor.BCPilotos;
import gt.umg.beneficiocafe.models.beneficioagricultor.BCUsuarios;
import gt.umg.beneficiocafe.payload.request.CrearUsuarioRequest;
import gt.umg.beneficiocafe.payload.request.GetUsuarioRequest;
import gt.umg.beneficiocafe.payload.request.LoginRequest;
import gt.umg.beneficiocafe.payload.request.PilotoRequest;
import gt.umg.beneficiocafe.payload.request.SolicitudRequest;
import gt.umg.beneficiocafe.services.ParcialidadesService;
import gt.umg.beneficiocafe.services.PilotosService;
import gt.umg.beneficiocafe.services.UsuariosService;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/ben-caf/users")
public class UsuariosController {

    private UsuariosService usuariosService;
    private ParcialidadesService parcialidadesService;

    public UsuariosController(UsuariosService usuarioService, PilotosService pilotosService, ParcialidadesService parcialidadesService) {
        this.usuariosService = usuarioService;
        this.parcialidadesService = parcialidadesService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody CrearUsuarioRequest nuevoUsuario) {
        return usuariosService.registrarUsuario(nuevoUsuario);
    }
    
    @PostMapping("/user")
    public Optional<BCUsuarios> getUsuario(@Valid @RequestBody GetUsuarioRequest usuario) {
        return usuariosService.getUsuario(usuario.getLogin());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        return usuariosService.login(loginRequest);
    }
    
    @PostMapping("/piloto")
    public Optional<BCPilotos> getPiloto(@Valid @RequestBody PilotoRequest transportistaRequest) {
        return usuariosService.getPiloto(transportistaRequest);
    }
    
    @PostMapping("/qrvalido")
    public Boolean getQRValido(@Valid @RequestBody SolicitudRequest solicitud) {
        return usuariosService.getQRValido(solicitud);
    }
    
    @PutMapping("/escanearqr")
    public ResponseEntity<?> escanearQR(@Valid @RequestBody SolicitudRequest solicitud) {
        return parcialidadesService.escanearQR(solicitud);
    }
}
