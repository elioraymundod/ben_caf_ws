/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.umg.beneficiocafe.models;

import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Elio Raymundo
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "bc_solicitudes", schema = "umg_beneficio_cafe")
public class BCSolicitudes {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_solicitud", columnDefinition = "UUID")
    private UUID idSolicitud;
    
    @Column(name = "estado_solicitud")
    private UUID estadoSolicitud;
    
    @Column(name = "placa")
    private String placa;
    
    @Column(name = "cantidad_parcialidades")
    private Integer cantidadParcialidades;
    
    @Column(name = "piloto")
    private String piloto;
    
    @Column(name = "descripcion")
    private String descripcion;
    
    @Column(name = "usuario_creacion")
    private UUID usuarioCreacion;
    
    @Column(name = "fecha_creacion")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    
    @Column(name = "usuario_modificacion")
    private UUID usuarioModificacion;
    
    @Column(name = "fecha_modificacion")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaModificacion;

    public BCSolicitudes(UUID estadoSolicitud, String placa, Integer cantidadParcialidades, String piloto, UUID usuarioCreacion, Date fechaCreacion,String descripcion) {
        this.estadoSolicitud = estadoSolicitud;
        this.descripcion = descripcion;
        this.placa = placa;
        this.cantidadParcialidades = cantidadParcialidades;
        this.piloto = piloto;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = fechaCreacion;
    }

    public BCSolicitudes() {
    }
    
    
    
}
