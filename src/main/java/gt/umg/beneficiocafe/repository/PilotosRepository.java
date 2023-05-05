/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.umg.beneficiocafe.repository;

import gt.umg.beneficiocafe.models.BCPilotos;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Elio Raymundo
 */
public interface PilotosRepository extends JpaRepository<BCPilotos, String> {
    Optional<BCPilotos> findByLicenciaPiloto(String licencia);
}
