/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.umg.beneficiocafe.services;

import gt.umg.beneficiocafe.exceptions.BadRequestException;
import gt.umg.beneficiocafe.models.BCPilotos;
import gt.umg.beneficiocafe.payload.request.CrearPilotoRequest;
import gt.umg.beneficiocafe.payload.response.SuccessResponse;
import gt.umg.beneficiocafe.repository.PilotosRepository;
import gt.umg.beneficiocafe.security.jwt.JwtUtils;
import gt.umg.beneficiocafe.util.ManejoFechas;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author Elio Raymundo
 */

@Service
public class PilotosService {
    private final PilotosRepository pilotosRepository;
    private JwtUtils jwtUtils;
    private static final Logger logger = LoggerFactory.getLogger(PilotosService.class);

    public PilotosService(PilotosRepository pilotosRepository, JwtUtils jwtUtils) {
        this.pilotosRepository = pilotosRepository;
        this.jwtUtils = jwtUtils;
    }
    
    /*
        Metodo para crear un piloto
    */
    public ResponseEntity<?> registrarPiloto(CrearPilotoRequest piloto) throws BadRequestException{
        String respuesta;
        logger.info("El piloto a crear es " + piloto);
        try{
            BCPilotos nuevoPiloto = new BCPilotos(piloto.getLicenciaPiloto(), piloto.getNombre(), piloto.getCelular(), piloto.getCorreo(), piloto.getUsuarioCreacion(), ManejoFechas.setTimeZoneDateGT(new Date()), false);
            pilotosRepository.save(nuevoPiloto);
            return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "El piloto se creo exitosamente", nuevoPiloto));
        } catch(BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
    
    
}
