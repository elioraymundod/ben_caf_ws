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
@Table(name = "bc_impresiones_bascula", schema = "umg_beneficio_cafe")
public class BCImpresionesBascula implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_impresion")
    private UUID idImpresion;
    
    @Column(name = "pesaje")
    private UUID pesaje;
    
    @Column(name = "usuario_imprimio")
    private UUID usuarioImprimio;
    
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

    public BCImpresionesBascula(UUID pesaje, UUID usuarioImprimio, UUID usuarioCreacion, Date fechaCreacion) {
        //this.idImpresion = idImpresion;
        this.pesaje = pesaje;
        this.usuarioImprimio = usuarioImprimio;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = fechaCreacion;
    }
    
    
}
