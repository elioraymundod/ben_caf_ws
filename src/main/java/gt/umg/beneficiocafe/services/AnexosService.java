/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.umg.beneficiocafe.services;

import gt.umg.beneficiocafe.exceptions.BadRequestException;
import gt.umg.beneficiocafe.models.BCAnexos;
import gt.umg.beneficiocafe.models.BCSolicitudes;
import gt.umg.beneficiocafe.payload.request.CrearAnexoRequest;
import gt.umg.beneficiocafe.payload.response.SuccessResponse;
import gt.umg.beneficiocafe.repository.AnexosRepository;
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
public class AnexosService {
    private final AnexosRepository anexosRepository;
    private final SolicitudesRepository solicitudesRepository;
    private JwtUtils jwtUtils;
    private static final Logger logger = LoggerFactory.getLogger(AnexosService.class);

    public AnexosService(AnexosRepository anexosRepository, JwtUtils jwtUtils, SolicitudesRepository solicitudesRepository) {
        this.anexosRepository = anexosRepository;
        this.solicitudesRepository = solicitudesRepository;
        this.jwtUtils = jwtUtils;
    }
    
    /*
        Metodo para crear anexos
    */
    public ResponseEntity<?> crearAnexo(CrearAnexoRequest a) throws BadRequestException{
        String respuesta;
        logger.info("El anexo a registrar es " + a);
        try{
            BCSolicitudes p = solicitudesRepository.getSolicitudById(a.getSolicitud());
            if (p == null) {
                return ResponseEntity.ok(new SuccessResponse(HttpStatus.NOT_FOUND, "La solicitud indicada no existe", false));
            }else  {
                BCAnexos nuevoAnexo = new BCAnexos(a.getSolicitud(), a.getObservaciones(), a.getSobranteFaltante(), a.getUsuarioCreacion(), ManejoFechas.setTimeZoneDateGT(new Date()));
                anexosRepository.save(nuevoAnexo);
                return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Anexo creado exitosamente", nuevoAnexo));
            }

        } catch(BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}
