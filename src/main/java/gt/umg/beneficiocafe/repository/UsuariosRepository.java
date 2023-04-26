/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gt.umg.beneficiocafe.repository;

import gt.umg.beneficiocafe.models.BCUsuarios;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Elio Raymundo
 */
public interface UsuariosRepository extends JpaRepository<BCUsuarios, UUID>{
    Boolean existsByCorreoUsuario(String correoUsuario);
    
    Optional<BCUsuarios> findByUsername(String username);
    
}
