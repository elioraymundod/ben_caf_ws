/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.umg.beneficiocafe.services;

import gt.umg.beneficiocafe.exceptions.BadRequestException;
import gt.umg.beneficiocafe.models.BCTransportes;
import gt.umg.beneficiocafe.payload.request.CrearTransporteRequest;
import gt.umg.beneficiocafe.payload.response.SuccessResponse;
import gt.umg.beneficiocafe.repository.TransportesRepository;
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
public class TransportesService {
    
    private final TransportesRepository transportesRepository;
    private JwtUtils jwtUtils;
    private static final Logger logger = LoggerFactory.getLogger(TransportesRepository.class);

    public TransportesService(TransportesRepository transportesRepository, JwtUtils jwtUtils) {
        this.transportesRepository = transportesRepository;
        this.jwtUtils = jwtUtils;
    }
    
    /*
        Metodo para crear un transporte
    */
    public ResponseEntity<?> registrarTransporte(CrearTransporteRequest transporte) throws BadRequestException{
        String respuesta;
        logger.info("El transporte a crear es " + transporte);
        try{
            BCTransportes nuevoTransporte = new BCTransportes(transporte.getPlacaTransporte(), transporte.getMarca(), transporte.getColor(), transporte.getUsuarioCreacion(), ManejoFechas.setTimeZoneDateGT(new Date()), false);
            transportesRepository.save(nuevoTransporte);
            return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "El transporte se creo exitosamente", nuevoTransporte));
        } catch(BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
    
}
