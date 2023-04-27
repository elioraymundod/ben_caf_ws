/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.umg.beneficiocafe.services;

import gt.umg.beneficiocafe.exceptions.BadRequestException;
import gt.umg.beneficiocafe.models.BCImpresionesBascula;
import gt.umg.beneficiocafe.models.BCPesajesBascula;
import gt.umg.beneficiocafe.payload.request.CrearRegistroImpresionRequest;
import gt.umg.beneficiocafe.payload.response.SuccessResponse;
import gt.umg.beneficiocafe.repository.ImpresionBasculaRepository;
import gt.umg.beneficiocafe.repository.PesoCabalRepository;
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
public class ImpresionesService {
    private final ImpresionBasculaRepository impresionesBasculaRepository;
    private final PesoCabalRepository pesoCabalRepository;
    private JwtUtils jwtUtils;
    private static final Logger logger = LoggerFactory.getLogger(ImpresionesService.class);

    public ImpresionesService(ImpresionBasculaRepository impresionesBasculaRepository, PesoCabalRepository pesoCabalRepository, JwtUtils jwtUtils) {
        this.impresionesBasculaRepository = impresionesBasculaRepository;
        this.pesoCabalRepository = pesoCabalRepository;
        this.jwtUtils = jwtUtils;
    }
    
    /*
        Metodo para crear bitacora de los usuarios que realizan impresiones de pesos
    */
    public ResponseEntity<?> registrarBitacoraImpresion(CrearRegistroImpresionRequest i) throws BadRequestException{
        String respuesta;
        logger.info("El dato a registrar es " + i);
        try{
            BCPesajesBascula p = pesoCabalRepository.getPesajeById(i.getPesaje());
            if (p == null) {
                return ResponseEntity.ok(new SuccessResponse(HttpStatus.NOT_FOUND, "El pesaje indicado es incorrecto, por lo tanto no se puede imprimir", false));
            }else  {
                BCImpresionesBascula nuevaImpresion = new BCImpresionesBascula(i.getPesaje(), i.getUsuarioImprimio(), i.getUsuarioCreacion(), ManejoFechas.setTimeZoneDateGT(new Date()));
                impresionesBasculaRepository.save(nuevaImpresion);
                return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Se creo bitacora de impresion exitosamente", nuevaImpresion));
            }

        } catch(BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}
