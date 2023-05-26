/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.umg.beneficiocafe.services;

import gt.umg.beneficiocafe.exceptions.BadRequestException;
import gt.umg.beneficiocafe.models.pesocabal.BCPesajesBascula;
import gt.umg.beneficiocafe.payload.request.CrearPesoRequest;
import gt.umg.beneficiocafe.payload.response.SuccessResponse;
import gt.umg.beneficiocafe.projections.TotalPesajeProjection;
import gt.umg.beneficiocafe.repository.beneficioagricultor.ParcialidadesRepository;
import gt.umg.beneficiocafe.repository.pesocabal.PesoCabalRepository;
import gt.umg.beneficiocafe.security.jwt.JwtUtils;
import gt.umg.beneficiocafe.util.ManejoFechas;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(transactionManager = "pesoCabalTransactionManager")
    public ResponseEntity<?> registrarPeso(CrearPesoRequest peso) throws BadRequestException{
        String respuesta;
        logger.info("El peso a registrar es " + peso);
        try{
            /*BCParcialidades p = parcialidadesRepository.getParcialidadById(peso.getParcialidad());
            if (p == null) {
                return ResponseEntity.ok(new SuccessResponse(HttpStatus.NOT_FOUND, "La parcialidad indicada no existe", false));
            }else  {
                BCPesajesBascula nuevoPesaje = new BCPesajesBascula(peso.getParcialidad(), peso.getPeso(), peso.getUsuarioCreacion(), ManejoFechas.setTimeZoneDateGT(new Date()));
                pesoCabalRepository.save(nuevoPesaje);
                return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "El pesaje se registro exitosamente", nuevoPesaje));
            }*/
            BCPesajesBascula nuevoPesaje = new BCPesajesBascula(peso.getParcialidad(), peso.getPeso(), peso.getUsuarioCreacion(), ManejoFechas.setTimeZoneDateGT(new Date()));
                pesoCabalRepository.save(nuevoPesaje);
                return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "El pesaje se registro exitosamente", nuevoPesaje));

        } catch(BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
    
    /*
        Metodo para obtener la cantidad de pesajes por dia
    */
    @Transactional(transactionManager = "pesoCabalTransactionManager")
    public ResponseEntity<?> getTotalPesajeByFecha(String pFecha) throws BadRequestException{
        try{
            Integer pesajesByFecha = pesoCabalRepository.getTotalPesajeByFecha(pFecha);
                return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Se obtienen los pesajes por fecha", pesajesByFecha));

        } catch(BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
    
    
    /*
        Metodo para obtener la cantidad de pesajes por rango de fechas
    */
    @Transactional(transactionManager = "pesoCabalTransactionManager")
    public ResponseEntity<?> getTotalPesajeByRangoFechas(String pFechaInicio, String pFechaFin) throws BadRequestException, ParseException{
        try{            
            Date fechaI = ManejoFechas.stringToDate(pFechaInicio, "dd-MM-yyyy");
            Date fechaF = ManejoFechas.stringToDate(pFechaFin, "dd-MM-yyyy");
            logger.info("busqueda con fechas " + fechaI + " " + fechaF);
            Integer pesajesByFecha = 0; //new TotalPesajeProjection(); 
            pesajesByFecha = pesoCabalRepository.getTotalPesajesByRangoFechas(fechaI, fechaF);
            //logger.info("Resultado es " + pesajesByFecha.getTotalPesaje());
                return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Se obtienen los pesajes por rango de fechas", pesajesByFecha));

        } catch(BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}
