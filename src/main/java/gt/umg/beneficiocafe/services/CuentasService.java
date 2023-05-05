/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.umg.beneficiocafe.services;

import gt.umg.beneficiocafe.exceptions.BadRequestException;
import gt.umg.beneficiocafe.models.BCCuentas;
import gt.umg.beneficiocafe.payload.request.CambiarEstadoCuentaRequest;
import gt.umg.beneficiocafe.payload.request.CrearCuentaRequest;
import gt.umg.beneficiocafe.payload.response.SuccessResponse;
import gt.umg.beneficiocafe.repository.CuentasRepository;
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
public class CuentasService {
    private final CuentasRepository cuentasRepository;
    private JwtUtils jwtUtils;
    private static final Logger logger = LoggerFactory.getLogger(CuentasService.class);

    public CuentasService(CuentasRepository cuentasRepository, JwtUtils jwtUtils) {
        this.cuentasRepository = cuentasRepository;
        this.jwtUtils = jwtUtils;
    }
    
    /*
        Metodo para crear una cuenta
    */
    public ResponseEntity<?> crearCuenta(CrearCuentaRequest cuenta) throws BadRequestException{
        String respuesta;
        logger.info("La cuenta a crear es " + cuenta);
        try{
            BCCuentas nuevaCuenta = new BCCuentas(cuenta.getNoCuenta(), cuenta.getEstado(), cuenta.getSolicitud(), cuenta.getUsuarioCreacion(), ManejoFechas.setTimeZoneDateGT(new Date()));
            cuentasRepository.save(nuevaCuenta);
            return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "La cuenta se creo exitosamente", nuevaCuenta));
        } catch(BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
    
    /*
        Metodo para cambiar el estado de una cuenta
    */
    public ResponseEntity<?> cambiarEstadoCuenta(CambiarEstadoCuentaRequest cuenta) throws BadRequestException{
        String respuesta;
        logger.info("La cuenta a cambiarle el estado es " + cuenta.getIdCuenta());
        try{
            BCCuentas cuentaBuscada = null;
            cuentaBuscada = cuentasRepository.getCuentaById(cuenta.getIdCuenta());
            if (cuentaBuscada == null) {
                return ResponseEntity.ok(new SuccessResponse(HttpStatus.NOT_FOUND, "No se encontro la cuenta indicada", null));
            } else {
                cuentaBuscada.setEstado(cuenta.getNuevoEstado());
                cuentaBuscada.setUsuarioModificacion(cuenta.getUsuarioModificacion());
                cuentaBuscada.setFechaModificacion(ManejoFechas.setTimeZoneDateGT(new Date()));
                cuentasRepository.save(cuentaBuscada);
                return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Se actualizo la cuenta correctamente", cuentaBuscada));
            }
        } catch(BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
    
}
