/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.umg.beneficiocafe.models;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Elio Raymundo
 */

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "bc_parcialidades", schema = "umg_beneficio_cafe")
public class BCParcialidades implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_parcialidad")
    private UUID idParcialidad;
    
    @Column(name = "solicitud")
    private UUID solicitud;
    
    @Column(name = "peso_enviado")
    private Double pesoEnviado;
    
    @Column(name = "placa")
    private String placa;
    
    @Column(name = "piloto")
    private String piloto;
    
    @Column(name = "atendido")
    private Boolean atendido;
    
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

    public BCParcialidades(UUID solicitud, Double pesoEnviado, String placa, String piloto, Boolean atendido, UUID usuarioCreacion, Date fechaCreacion) {
        this.solicitud = solicitud;
        this.pesoEnviado = pesoEnviado;
        this.placa = placa;
        this.piloto = piloto;
        this.atendido = atendido;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = fechaCreacion;
    }

 
}
