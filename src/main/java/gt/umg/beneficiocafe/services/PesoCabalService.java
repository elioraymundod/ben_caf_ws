/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.umg.beneficiocafe.services;

import gt.umg.beneficiocafe.exceptions.BadRequestException;
import gt.umg.beneficiocafe.models.BCParcialidades;
import gt.umg.beneficiocafe.models.BCPesajesBascula;
import gt.umg.beneficiocafe.payload.request.CrearPesoRequest;
import gt.umg.beneficiocafe.payload.response.SuccessResponse;
import gt.umg.beneficiocafe.repository.ParcialidadesRepository;
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
public class PesoCabalService {
    private final PesoCabalRepository pesoCabalRepository;
    private final ParcialidadesRepository parcialidadesRepository;
    private JwtUtils jwtUtils;
    private static final Logger logger = LoggerFactory.getLogger(PesoCabalService.class);

    public PesoCabalService(PesoCabalRepository pesoCabalRepository, JwtUtils jwtUtils, ParcialidadesRepository parcialidadesRepository) {
        this.pesoCabalRepository = pesoCabalRepository;
        this.parcialidadesRepository = parcialidadesRepository;
        this.jwtUtils = jwtUtils;
    }
    
    /*
        Metodo para registrar el peso de una parcialidad que ingresa a peso cabal
    */
    public ResponseEntity<?> registrarPeso(CrearPesoRequest peso) throws BadRequestException{
        String respuesta;
        logger.info("El peso a registrar es " + peso);
        try{
            BCParcialidades p = parcialidadesRepository.getParcialidadById(peso.getParcialidad());
            if (p == null) {
                return ResponseEntity.ok(new SuccessResponse(HttpStatus.NOT_FOUND, "La parcialidad indicada no existe", false));
            }else  {
                BCPesajesBascula nuevoPesaje = new BCPesajesBascula(peso.getParcialidad(), peso.getPeso(), peso.getUsuarioCreacion(), ManejoFechas.setTimeZoneDateGT(new Date()));
                pesoCabalRepository.save(nuevoPesaje);
                return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "El pesaje se registro exitosamente", nuevoPesaje));
            }

        } catch(BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}
