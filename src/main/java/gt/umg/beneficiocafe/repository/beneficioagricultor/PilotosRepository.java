/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.umg.beneficiocafe.repository.beneficioagricultor;

import gt.umg.beneficiocafe.models.beneficioagricultor.BCPilotos;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Elio Raymundo
 */
@Repository
public interface PilotosRepository extends JpaRepository<BCPilotos, String> {
    Optional<BCPilotos> findByLicenciaPiloto(String licencia);
}
