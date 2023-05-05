/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.umg.beneficiocafe.services;

import gt.umg.beneficiocafe.exceptions.BadRequestException;
import gt.umg.beneficiocafe.models.BCPilotos;
import gt.umg.beneficiocafe.models.BCRoles;
import gt.umg.beneficiocafe.models.BCUsuarios;
import gt.umg.beneficiocafe.models.Roles;
import gt.umg.beneficiocafe.payload.request.CrearUsuarioRequest;
import gt.umg.beneficiocafe.payload.request.GetUsuarioRequest;
import gt.umg.beneficiocafe.payload.request.LoginRequest;
import gt.umg.beneficiocafe.payload.request.PilotoRequest;
import gt.umg.beneficiocafe.payload.response.JwtResponse;
import gt.umg.beneficiocafe.payload.response.SuccessResponse;
import gt.umg.beneficiocafe.repository.PilotosRepository;
import gt.umg.beneficiocafe.repository.RolesRepository;
import gt.umg.beneficiocafe.repository.UsuariosRepository;
import gt.umg.beneficiocafe.security.jwt.JwtUtils;
import gt.umg.beneficiocafe.security.services.UserDetailsImpl;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Elio Raymundo
 */
@Service
public class UsuariosService {

    private final UsuariosRepository usuariosRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEndocer;
    private final PilotosRepository pilotosRepository;
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;
    private static final Logger logger = LoggerFactory.getLogger(UsuariosService.class);

    public UsuariosService(UsuariosRepository usuariosRepository,PilotosRepository pilotosRepository, RolesRepository rolesRepository, PasswordEncoder passwordEndocer, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.usuariosRepository = usuariosRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEndocer = passwordEndocer;
        this.pilotosRepository = pilotosRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }
    
    /*
        Metodo para obtener los datos de un usuario
    */
    public Optional<BCUsuarios> getUsuario(GetUsuarioRequest login) {
        String respuesta;
        logger.info("El usuario es ", login);
        Optional<BCUsuarios> user = usuariosRepository.findByUsername(login.getLogin());
        return user;
    }

    /*
        Metodo para registrar un usuario
    */
    public ResponseEntity<?> registrarUsuario(CrearUsuarioRequest usuario) throws BadRequestException {
        String respuesta;
        logger.info("El usuario es ", usuario);
        if (usuariosRepository.existsByCorreoUsuario(usuario.getCorreoUsuario())) {
            respuesta = "No fue posible crear el nuevo usuario, ya existe un usuario registrado con el correo proporcionado";
            throw new BadRequestException(respuesta);
        }

        try {
            // Inicializar nuevo usuario con el correo y contrasenia proporcionada
            BCUsuarios nuevoUsuario = new BCUsuarios(usuario.getCorreoUsuario(), passwordEndocer.encode(usuario.getPasswordUsuario()), usuario.getUsername());
            logger.info("El nuevo usuario es " + nuevoUsuario);
            Set<String> rolUsuarioNuevo = usuario.getRolUsuario();
            logger.info("El rol del nuevo usuario es " + rolUsuarioNuevo);
            Set<BCRoles> listaRoles = new HashSet<>();

            // Validar que hayan indicado el rol a asignarle al nuevo usuario
            if (rolUsuarioNuevo.isEmpty()) {
                respuesta = "No puede crear un usuario sin rol, por favor agregar el rol correspondiente";
                logger.error(respuesta);
                throw new BadRequestException(respuesta);
            } else {
                // Validar si los roles indicados existen
                rolUsuarioNuevo.forEach(rol -> {
                    switch (rol) {
                        case "ROLE_UMG_BC_ADMINISTRADOR": {
                            BCRoles rolAdministrador = rolesRepository.findByNombreRol(Roles.ROLE_UMG_BC_ADMINISTRADOR)
                                    .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
                            listaRoles.add(rolAdministrador);
                            break;
                        }
                        case "ROLE_UMG_BC_AGRICULTOR": {
                            BCRoles rolAgricultor = rolesRepository.findByNombreRol(Roles.ROLE_UMG_BC_AGRICULTOR)
                                    .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
                            listaRoles.add(rolAgricultor);
                            break;
                        }
                        case "ROLE_UMG_BC_BENEFICIO": {
                            BCRoles rolAdministrador = rolesRepository.findByNombreRol(Roles.ROLE_UMG_BC_BENEFICIO)
                                    .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
                            listaRoles.add(rolAdministrador);
                            break;
                        }
                        case "ROLE_UMG_BC_PESO_CABAL": {
                            BCRoles rolAdministrador = rolesRepository.findByNombreRol(Roles.ROLE_UMG_BC_PESO_CABAL)
                                    .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
                            listaRoles.add(rolAdministrador);
                            break;
                        }
                        default:
                            break;
                    }
                });
            }
            // Si la lista de roles esta vacia indicar al usuario y no permitir la creacion del nuevo usuario
            if (listaRoles.isEmpty()) {
                logger.error("El rol ingresado no es valido");
                throw new BadRequestException("El rol ingresado no es valido");
            } else {
                // Si la lista de roles no esta vacia, proceder a crear el usuario
                logger.info("El usuario a registrar es " + nuevoUsuario);
                nuevoUsuario.setRoles(listaRoles);
                usuariosRepository.save(nuevoUsuario);
                return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "El usuario se registro correctamente", nuevoUsuario));
            }
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
    
    public ResponseEntity<?> login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        
        //Generar token de autenticacion
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(new JwtResponse(
                userDetails.getUsername(),
                jwt,
                roles));
    }
    
    /*
        Metodo para obtener informacion de un transportista
    */
    public Optional<BCPilotos> getPiloto(PilotoRequest transportistaRequest) {
        String respuesta;
        logger.info("El piloto a consultar es ", transportistaRequest);
        Optional<BCPilotos> transportista = pilotosRepository.findByLicenciaPiloto(transportistaRequest.getLicencia());
        return transportista;
    }

}
