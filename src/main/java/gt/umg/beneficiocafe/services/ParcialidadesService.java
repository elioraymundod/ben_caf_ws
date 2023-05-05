/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.umg.beneficiocafe.services;

import gt.umg.beneficiocafe.exceptions.BadRequestException;
import gt.umg.beneficiocafe.models.BCCuentas;
import gt.umg.beneficiocafe.models.BCParcialidades;
import gt.umg.beneficiocafe.models.BCSolicitudes;
import gt.umg.beneficiocafe.payload.request.AtenderParcialidadRequest;
import gt.umg.beneficiocafe.payload.request.AtendidoRequest;
import gt.umg.beneficiocafe.payload.request.CrearParcialidadesRequest;
import gt.umg.beneficiocafe.payload.request.EstadoRequest;
import gt.umg.beneficiocafe.payload.request.SolicitudRequest;
import gt.umg.beneficiocafe.payload.response.SuccessResponse;
import gt.umg.beneficiocafe.projections.CantidadParcialidadesProjection;
import gt.umg.beneficiocafe.repository.CuentasRepository;
import gt.umg.beneficiocafe.repository.ParcialidadesRepository;
import gt.umg.beneficiocafe.repository.SolicitudesRepository;
import gt.umg.beneficiocafe.security.jwt.JwtUtils;
import gt.umg.beneficiocafe.util.ManejoFechas;
import java.util.Date;
import java.util.List;
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
public class ParcialidadesService {

    private final ParcialidadesRepository parcialidadesRepository;
    private final SolicitudesRepository solicitudesRepository;
    private final CuentasRepository cuentasRepository;
    private JwtUtils jwtUtils;
    private static final Logger logger = LoggerFactory.getLogger(ParcialidadesService.class);

    public ParcialidadesService(ParcialidadesRepository parcialidadesRepository, JwtUtils jwtUtils, SolicitudesRepository solicitudesRepository, CuentasRepository cuentasRepository) {
        this.parcialidadesRepository = parcialidadesRepository;
        this.solicitudesRepository = solicitudesRepository;
        this.cuentasRepository = cuentasRepository;
        this.jwtUtils = jwtUtils;
    }

    /*
        Metodo para enviar una parcialidad
     */
    public ResponseEntity<?> enviarParcialidad(CrearParcialidadesRequest parcialidad) throws BadRequestException {
        String respuesta;
        logger.info("La parcialidad a crear es " + parcialidad);
        try {
            BCSolicitudes solicitud = solicitudesRepository.getSolicitudById(parcialidad.getSolicitud());
            if (solicitud == null) {
                return ResponseEntity.ok(new SuccessResponse(HttpStatus.NOT_FOUND, "La solicitud indicada no existe", parcialidad.getSolicitud()));
            } else {
                CantidadParcialidadesProjection c = parcialidadesRepository.getCantidadParcialidades(parcialidad.getSolicitud());
                if (solicitud.getCantidadParcialidades() <= c.getCantidadParcialidades()) {
                    return ResponseEntity.ok(new SuccessResponse(HttpStatus.EXPECTATION_FAILED, "Ya no es posible enviar mas parcialidades para esta solicitud porque ya fueron enviadas todas las parcialidades indicadas en la creacion de la solicitud", false));
                }

                if (!solicitud.getPlaca().equals(parcialidad.getPlaca())) {
                    return ResponseEntity.ok(new SuccessResponse(HttpStatus.EXPECTATION_FAILED, "La placa indicada no es la que fue aprobada al crear la solicitud", false));
                }

                if (!solicitud.getPiloto().equals(parcialidad.getPiloto())) {
                    return ResponseEntity.ok(new SuccessResponse(HttpStatus.EXPECTATION_FAILED, "El piloto indicado no es el que fue aprobado al crear la solicitud", false));
                }

                BCParcialidades nuevaParcialidad = new BCParcialidades(parcialidad.getSolicitud(), parcialidad.getPesoEnviado(), parcialidad.getPlaca(), parcialidad.getPiloto(), false, parcialidad.getUsuarioCreacion(), ManejoFechas.setTimeZoneDateGT(new Date()));
                parcialidadesRepository.save(nuevaParcialidad);
                return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "La parcialidad se creo exitosamente", nuevaParcialidad));
            }
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    /*
        Metodo para obtener las parcialidades
     */
    public ResponseEntity<?> getCantidadParcialidades(SolicitudRequest solicitudRequest) throws BadRequestException {

        try {
            BCSolicitudes solicitud = solicitudesRepository.getSolicitudById(solicitudRequest.getSolicitud());
            BCCuentas cuenta = cuentasRepository.getCuentaBySolicitud(solicitudRequest.getSolicitud());
            if (solicitud == null) {
                return ResponseEntity.ok(new SuccessResponse(HttpStatus.NOT_FOUND, "La solicitud indicada no existe", solicitudRequest.getSolicitud()));
            } else {
                CantidadParcialidadesProjection c = parcialidadesRepository.getCantidadParcialidades(solicitudRequest.getSolicitud());
                if (solicitud.getCantidadParcialidades() <= c.getCantidadParcialidades()) {
                    return ResponseEntity.ok(new SuccessResponse(HttpStatus.EXPECTATION_FAILED, String.valueOf(cuenta.getIdCuenta()), false));
                } else {
                    return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, String.valueOf(c.getCantidadParcialidades()), true));
                }
            }

        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    /*
        Metodo para obtener parcialidades a ingresar al beneficio del cafe
     */
    public List<BCParcialidades> getParcialidadesPendientes(AtendidoRequest estado) throws BadRequestException {

        try {
            List<BCParcialidades> parcialidades = parcialidadesRepository.getParcialidadesPendientes(estado.getEstado());

            return  parcialidades;

        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
    
    /*
        Metodo para marcar como atendida una parcialidad
    */
    public ResponseEntity<?> atenderParcialidad(AtenderParcialidadRequest estado) throws BadRequestException{
         String respuesta;
        try{
            BCParcialidades parcialidad = null;
            parcialidad = parcialidadesRepository.getParcialidadById(estado.getIdParcialidad());
            if (parcialidad == null) {
                return ResponseEntity.ok(new SuccessResponse(HttpStatus.NOT_FOUND, "No se encontro la parcialidad indicada", null));
            } else {
                parcialidad.setAtendido(estado.getNuevoEstado());
                parcialidad.setUsuarioModificacion(estado.getUsuarioModificacion());
                parcialidad.setFechaModificacion(ManejoFechas.setTimeZoneDateGT(new Date()));
                parcialidadesRepository.save(parcialidad);
                return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Se actualizo la parcialidad correctamente", parcialidad));
            }
        } catch(BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

}
