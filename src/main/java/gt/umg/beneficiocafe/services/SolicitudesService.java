/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.umg.beneficiocafe.services;

import gt.umg.beneficiocafe.dto.FaltanteSobranteDto;
import gt.umg.beneficiocafe.dto.SolicitudesDto;
import gt.umg.beneficiocafe.exceptions.BadRequestException;
import gt.umg.beneficiocafe.models.beneficioagricultor.BCParcialidades;
import gt.umg.beneficiocafe.models.pesocabal.BCPesajesBascula;
import gt.umg.beneficiocafe.models.beneficioagricultor.BCSolicitudes;
import gt.umg.beneficiocafe.payload.request.CambiarEstadoSolicitudRequest;
import gt.umg.beneficiocafe.payload.request.CrearSolicitudRequest;
import gt.umg.beneficiocafe.payload.request.EstadoRequest;
import gt.umg.beneficiocafe.payload.request.UsuarioRequest;
import gt.umg.beneficiocafe.payload.request.ValidarSolicitudRequest;
import gt.umg.beneficiocafe.payload.response.SuccessResponse;
import gt.umg.beneficiocafe.projections.BestClientsProjection;
import gt.umg.beneficiocafe.projections.ClientsWitthSobrantesFaltantes;
import gt.umg.beneficiocafe.projections.SolicitudValidaProjection;
import gt.umg.beneficiocafe.projections.SolicitudesDetalleProjection;
import gt.umg.beneficiocafe.repository.beneficioagricultor.ParcialidadesRepository;
import gt.umg.beneficiocafe.repository.pesocabal.PesoCabalRepository;
import gt.umg.beneficiocafe.repository.beneficioagricultor.SolicitudesRepository;
import gt.umg.beneficiocafe.security.jwt.JwtUtils;
import gt.umg.beneficiocafe.util.ManejoFechas;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
public class SolicitudesService {
    
    private final SolicitudesRepository solicitudesRepository;
    private final ParcialidadesRepository parcialidadesRepository;
    private final PesoCabalRepository pesoCabalRepository;
    private double pesosIndicados; 
    private double pesosBascula;
    private JwtUtils jwtUtils;
    private static final Logger logger = LoggerFactory.getLogger(UsuariosService.class);

    public SolicitudesService(SolicitudesRepository solicitudesRepository, ParcialidadesRepository parcialidadesRepository,JwtUtils jwtUtils, PesoCabalRepository pesoCabalRepository) {
        this.solicitudesRepository = solicitudesRepository;
        this.parcialidadesRepository = parcialidadesRepository;
        this.jwtUtils = jwtUtils;
        this.pesoCabalRepository = pesoCabalRepository;
        this.pesosBascula = 0;
        this.pesosIndicados = 0;
    }
    
    /*
        Metodo para crear una solicitud
    */
    @Transactional(transactionManager = "beneficioagricultorTransactionManager")
    public ResponseEntity<?> registrarSolicitud(CrearSolicitudRequest solicitud) throws BadRequestException{
        String respuesta;
        logger.info("La solicitud a crear es " + solicitud);
        try{
            //Validar que el piloto no este asignado a otra cuenta
            List<BCSolicitudes> pilotoConsulta = new ArrayList<>();
            pilotoConsulta = solicitudesRepository.consultarPilotoAsignado(solicitud.getPiloto());
            System.out.println(pilotoConsulta.toString()); 
            System.out.println("permitido es " + pilotoConsulta.isEmpty());
            if (pilotoConsulta.isEmpty()) {
            BCSolicitudes nuevaSolicitud = new BCSolicitudes(solicitud.getEstadoSolicitiud(), solicitud.getPlaca(), solicitud.getCantidadParcialidades(),
                            solicitud.getPiloto(), solicitud.getUsuarioCreacion(), ManejoFechas.setTimeZoneDateGT(new Date()), solicitud.getTotalPesaje());
            solicitudesRepository.save(nuevaSolicitud);
            return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "La solicitud se creo exitosamente", nuevaSolicitud));     
            }  else {
                return ResponseEntity.ok(new SuccessResponse(HttpStatus.CONFLICT, "El piloto esta asignado a una cuenta", null));
            }
                
            
           
        } catch(BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
    
    /*
        Metodo para obtener solicitudes en base a un usuario
    */
    @Transactional(transactionManager = "beneficioagricultorTransactionManager")
    public List<SolicitudesDetalleProjection> getSolicitudesByUsuario(UsuarioRequest usuario) throws BadRequestException{
        
        logger.info("Se consulta solicitudes del usuario " + usuario.getUsuario());
        try{
            List<SolicitudesDetalleProjection> respuesta = new ArrayList<>();
            respuesta = solicitudesRepository.solicitudesByUser(usuario.getUsuario());
            return respuesta;
            //return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, ""));
        } catch(BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
    
    /*
        Metodo para obtener solicitudes por estado
    */
    @Transactional(transactionManager = "beneficioagricultorTransactionManager")
    public List<SolicitudesDetalleProjection> getSolicitudesByEstado(EstadoRequest estado) throws BadRequestException{
        
        logger.info("Se consulta solicitudes en estado " + estado.getEstado());
        try{
            List<SolicitudesDetalleProjection> respuesta = new ArrayList<>();
            respuesta = solicitudesRepository.solicitudesByEstado(estado.getEstado());
            return respuesta;
            //return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, ""));
        } catch(BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
    
    /*
        Metodo para validar si una solicitud es valida o no es valida para poder proceder
    */
    @Transactional(transactionManager = "beneficioagricultorTransactionManager")
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
    @Transactional(transactionManager = "beneficioagricultorTransactionManager")
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
    
    /*
        Metodo para validar Faltantes y Sobrantes
    */
    @Transactional(transactionManager = "pesoCabalTransactionManager")
    public ResponseEntity<?> validarFaltantesSobrantes(ValidarSolicitudRequest solicitud) throws BadRequestException{
        this.pesosBascula= 0;
        this.pesosIndicados = 0;
       Boolean respuesta = false;
        logger.info("La solicitud a validar es " + solicitud.getSolicitud());
        try{
           
           List<BCParcialidades> parcialidadesSolicitud = parcialidadesRepository.getParcialidadesBySolicitud(solicitud.getSolicitud());
           parcialidadesSolicitud.forEach(parcialidad -> {
               this.pesosIndicados += parcialidad.getPesoEnviado();
               BCPesajesBascula pesajeBascula = pesoCabalRepository.getPesajeByParcialidad(parcialidad.getIdParcialidad());
               this.pesosBascula += pesajeBascula.getPeso();
           });
            double maximo = this.pesosIndicados * 1.05;
            double minimo = this.pesosIndicados * 0.95;
            FaltanteSobranteDto resultado = new FaltanteSobranteDto() ;
            resultado.setMaximo(maximo);
            resultado.setMinimo(minimo);
            resultado.setPesoBascula(this.pesosBascula);
            resultado.setPesoIngresado(this.pesosIndicados);
            resultado.setFaltanteSobrante("");
            if (this.pesosBascula >= minimo && this.pesosBascula <= maximo) {
                resultado.setValido(true);
                return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Faltantes sobrantes validos", resultado));
            } else {
                resultado.setValido(false);
                if (this.pesosBascula < minimo) {
                    resultado.setFaltanteSobrante("F");
                    return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Faltantes ", resultado));
                }  if (this.pesosBascula > maximo) {
                    resultado.setFaltanteSobrante("S");
                    return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Sobrante", resultado));
                }
                return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "cantidad invalida", false));
            }
            
        } catch(BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
    
    /*
        Metodo para obtener el reporte de los tres mejores clientes
    */
    @Transactional(transactionManager = "beneficioagricultorTransactionManager")
    public ResponseEntity<?> getBestClients() throws BadRequestException{
       List<BestClientsProjection> respuesta = new ArrayList<>();
        try{
            respuesta = solicitudesRepository.getBestClients();
                return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Se meustra la lista de los mejores clientes", respuesta));
        } catch(BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
    
    /*
        Metodo para obtener los clientes con sofrantes y faltantes
    */
    @Transactional(transactionManager = "beneficioagricultorTransactionManager")
    public ResponseEntity<?> getClientsWithSobrantesFaltantes() throws BadRequestException{
       List<ClientsWitthSobrantesFaltantes> respuesta = new ArrayList<>();
        try{
            respuesta = solicitudesRepository.getClientsWithSobrantesFaltantes();
                return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Se meustra la lista de los clientes con faltantes", respuesta));
        } catch(BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
    
}
