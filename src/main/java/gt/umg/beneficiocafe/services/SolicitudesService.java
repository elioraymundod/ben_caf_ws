/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.umg.beneficiocafe.services;

import gt.umg.beneficiocafe.exceptions.BadRequestException;
import gt.umg.beneficiocafe.models.BCSolicitudes;
import gt.umg.beneficiocafe.payload.request.CambiarEstadoSolicitudRequest;
import gt.umg.beneficiocafe.payload.request.CrearSolicitudRequest;
import gt.umg.beneficiocafe.payload.request.ValidarSolicitudRequest;
import gt.umg.beneficiocafe.payload.response.SuccessResponse;
import gt.umg.beneficiocafe.projections.SolicitudValidaProjection;
import gt.umg.beneficiocafe.repository.SolicitudesRepository;
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
                            solicitud.getPiloto(), solicitud.getUsuarioCreacion(), ManejoFechas.setTimeZoneDateGT(new Date()));
            solicitudesRepository.save(nuevaSolicitud);
            return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "La solicitud se creo exitosamente", nuevaSolicitud));
        } catch(BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
    
    /*
        Metodo para validar si una solicitud es valida o no es valida para poder proceder
    */
    public ResponseEntity<?> validarSolicitud(ValidarSolicitudRequest solicitud) throws BadRequestException{
       Boolean respuesta = false;
        logger.info("La solicitud a validar es " + solicitud.getSolicitud());
        try{
            SolicitudValidaProjection solicitudValida = null;
            solicitudValida = solicitudesRepository.validarSolicitud(solicitud.getSolicitud());
            Boolean test = solicitudValida.getPilotoEstatus();
            logger.info("se obtuvo " + test);
            if (solicitudValida.getPilotoEstatus() && solicitudValida.getTransporteEstatus()) {
                return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "La solicitud es valida", solicitudValida));
            }
            return ResponseEntity.ok(new SuccessResponse(HttpStatus.PRECONDITION_FAILED, "La solicitud no es valida", solicitudValida));
        } catch(BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
    
    /*
        Metodo para cambiar el estado de una solicitud
    */
    public ResponseEntity<?> cambiarEstadoSolicitud(CambiarEstadoSolicitudRequest solicitud) throws BadRequestException{
         String respuesta;
        logger.info("La solicitud a cambiarle el estado es " + solicitud.getSolicitud());
        try{
            BCSolicitudes solicitudBuscada = null;
            solicitudBuscada = solicitudesRepository.getSolicitudById(solicitud.getSolicitud());
            if (solicitudBuscada == null) {
                return ResponseEntity.ok(new SuccessResponse(HttpStatus.NOT_FOUND, "No se encontro la solicitud indicada", null));
            } else {
                solicitudBuscada.setEstadoSolicitud(solicitud.getNuevoEstado());
                solicitudBuscada.setUsuarioModificacion(solicitud.getUsuarioModificacion());
                solicitudBuscada.setFechaModificacion(ManejoFechas.setTimeZoneDateGT(new Date()));
                solicitudesRepository.save(solicitudBuscada);
                return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Se actualizo la solicitud correctamente", solicitudBuscada));
            }
        } catch(BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
    
}
