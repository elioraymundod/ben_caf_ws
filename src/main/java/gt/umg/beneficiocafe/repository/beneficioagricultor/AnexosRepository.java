/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gt.umg.beneficiocafe.repository.beneficioagricultor;

import gt.umg.beneficiocafe.models.beneficioagricultor.BCAnexos;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Elio Raymundo
 */
@Repository
public interface AnexosRepository extends JpaRepository<BCAnexos, UUID>{
    
}
