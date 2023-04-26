/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.umg.beneficiocafe.controllers;

import gt.umg.beneficiocafe.payload.request.CrearUsuarioRequest;
import gt.umg.beneficiocafe.payload.request.LoginRequest;
import gt.umg.beneficiocafe.services.UsuariosService;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/ben-caf/users")
public class UsuariosController {

    private UsuariosService usuariosService;

    public UsuariosController(UsuariosService usuarioService) {
        this.usuariosService = usuarioService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody CrearUsuarioRequest nuevoUsuario) {
        return usuariosService.registrarUsuario(nuevoUsuario);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        return usuariosService.login(loginRequest);
    }
}
