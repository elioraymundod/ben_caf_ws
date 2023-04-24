package gt.umg.beneficiocafe.services;


import gt.umg.beneficiocafe.exceptions.AuthBadRequestException;
import gt.umg.beneficiocafe.models.ERole;
import gt.umg.beneficiocafe.models.Role;
import gt.umg.beneficiocafe.models.User;
import gt.umg.beneficiocafe.payload.request.LoginRequest;
import gt.umg.beneficiocafe.payload.request.SignupRequest;
import gt.umg.beneficiocafe.payload.response.JwtResponse;
import gt.umg.beneficiocafe.payload.response.MessageResponse;
import gt.umg.beneficiocafe.payload.response.SuccessResponse;
import gt.umg.beneficiocafe.repository.RoleRepository;
import gt.umg.beneficiocafe.repository.UserRepository;
import gt.umg.beneficiocafe.security.jwt.JwtUtils;
import gt.umg.beneficiocafe.security.services.UserDetailsImpl;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {
    private UserRepository userRepository;
    private PasswordEncoder encoder;
    private RoleRepository roleRepository;
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    public AuthService(UserRepository userRepository, RoleRepository roleRepository,
                       PasswordEncoder encoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    public ResponseEntity<?> authenticateUserSvc(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

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

    public ResponseEntity<?> registerUserSvc(SignupRequest signUpRequest) throws AuthBadRequestException {
        String message;
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            message = String.format("El usuario %s ya existe", signUpRequest.getUsername());
            throw new AuthBadRequestException(message);
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            message = String.format("El email %s ya existe", signUpRequest.getEmail());
            throw new AuthBadRequestException(message);
        }/**/

        try {
            User user = new User(signUpRequest.getUsername(),
                    signUpRequest.getEmail(),
                    encoder.encode(signUpRequest.getPassword()));

            Set<String> strRoles = signUpRequest.getRole();
            Set<Role> roles = new HashSet<>();
            logger.info("strRoles " + strRoles);
            if (strRoles.isEmpty()) {
                logger.error("role esta vacio ");
                throw new AuthBadRequestException("El role esta vacio");

            } else {
                logger.info("validando role ");
                strRoles.forEach(role -> {
                    if (role.isEmpty()) {
                        logger.info("role empty");
                        throw new AuthBadRequestException("Rol no valido");
                    }
                    switch (role) {
                        case "admin":
                            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                    .orElseThrow(() -> new RuntimeException("Role is not found."));
                            roles.add(adminRole);

                            break;
                        case "beneficio":
                            Role beneficioRole = roleRepository.findByName(ERole.ROLE_BENEFICIO)
                                    .orElseThrow(() -> new RuntimeException("Role is not found."));
                            roles.add(beneficioRole);

                            break;
                        case "agricultor":
                            Role agricultorRole = roleRepository.findByName(ERole.ROLE_AGRICULTOR)
                                    .orElseThrow(() -> new RuntimeException("Role is not found."));
                            roles.add(agricultorRole);

                            break;
                        case "peso":
                            Role pesoCabalRole = roleRepository.findByName(ERole.ROLE_PESO_CABAL)
                                    .orElseThrow(() -> new RuntimeException("Role is not found."));
                            roles.add(pesoCabalRole);

                            break;
                        case "user":
                            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                    .orElseThrow(() -> new RuntimeException("Role is not found."));
                            roles.add(userRole);

                            break;
                        default:
                            break;
                    }
                });
            }
            user.setRoles(roles);
            logger.info("user" + user);
            if (user.getRoles().isEmpty()) {
                logger.error("rol no valido");
                throw new AuthBadRequestException("Rol no valido");
            }
            userRepository.save(user);
            return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK,"User registered successfully!"));
        } catch (AuthBadRequestException e) {
            throw new AuthBadRequestException(e.getMessage());
        }
    }
}
