/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.umg.beneficiocafe.services;

import gt.umg.beneficiocafe.exceptions.BadRequestException;
import gt.umg.beneficiocafe.models.BCRechazos;
import gt.umg.beneficiocafe.payload.request.CrearRechazoRequest;
import gt.umg.beneficiocafe.payload.response.SuccessResponse;
import gt.umg.beneficiocafe.repository.RechazosRepository;
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
public class RechazosService {
    private final RechazosRepository rechazosRepository;
    private JwtUtils jwtUtils;
    private static final Logger logger = LoggerFactory.getLogger(RechazosService.class);

    public RechazosService(RechazosRepository rechazosRepository, JwtUtils jwtUtils) {
        this.rechazosRepository = rechazosRepository;
        this.jwtUtils = jwtUtils;
    }
    
    /*
        Metodo para crear un rechazo
    */
    public ResponseEntity<?> crearRechazo(CrearRechazoRequest rechazo) throws BadRequestException{
        String respuesta;
        logger.info("El rechazo a crear es " + rechazo);
        try{
            BCRechazos nuevoRechazo = new BCRechazos(rechazo.getSolicitud(), rechazo.getObservaciones(), rechazo.getUsuarioCreacion());
            nuevoRechazo.setFechaCreacion(ManejoFechas.setTimeZoneDateGT(new Date()));
            rechazosRepository.save(nuevoRechazo);
            return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "El rechazo se creo exitosamente", nuevoRechazo));
        } catch(BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
    
}
