/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.umg.beneficiocafe.services;

import gt.umg.beneficiocafe.exceptions.BadRequestException;
import gt.umg.beneficiocafe.models.BCSolicitudes;
import gt.umg.beneficiocafe.payload.request.CrearSolicitudRequest;
import gt.umg.beneficiocafe.payload.response.SuccessResponse;
import gt.umg.beneficiocafe.repository.SolicitudesRepository;
import gt.umg.beneficiocafe.security.jwt.JwtUtils;
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
public class SolicitudesService {
    
    private final SolicitudesRepository solicitudesRepository;
    private JwtUtils jwtUtils;
    private static final Logger logger = LoggerFactory.getLogger(UsuariosService.class);

    public SolicitudesService(SolicitudesRepository solicitudesRepository, JwtUtils jwtUtils) {
        this.solicitudesRepository = solicitudesRepository;
        this.jwtUtils = jwtUtils;
    }
    
    /*
        Metodo para crear una solicitud
    */
    public ResponseEntity<?> registrarSolicitud(CrearSolicitudRequest solicitud) throws BadRequestException{
        String respuesta;
        logger.info("La solicitud a crear es " + solicitud);
        try{
            BCSolicitudes nuevaSolicitud = new BCSolicitudes(solicitud.getEstadoSolicitiud(), solicitud.getPlaca(), solicitud.getCantidadParcialidades(),
                            solicitud.getPiloto(), solicitud.getUsuarioCreacion(), solicitud.getFechaCreacion());
            solicitudesRepository.save(nuevaSolicitud);
            return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "La solicitud se creo exitosamente", nuevaSolicitud));
        } catch(BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
    
}
