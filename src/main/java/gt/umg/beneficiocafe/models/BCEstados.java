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
@Table(name = "bc_estados", schema = "umg_beneficio_cafe")
public class BCEstados implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_estado")
    private UUID idEstado;
    
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

    public BCEstados(UUID idEstado, String descripcion, UUID usuarioCreacion, Date fechaCreacion, UUID usuarioModificacion, Date fechaModificacion) {
        this.idEstado = idEstado;
        this.descripcion = descripcion;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = fechaCreacion;
        this.usuarioModificacion = usuarioModificacion;
        this.fechaModificacion = fechaModificacion;
    }
}
