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
@Table(name = "bc_cuentas", schema = "umg_beneficio_cafe")
public class BCCuentas implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_cuenta")
    private UUID idCuenta;
    
    @Column(name = "no_cuenta")
    private String noCuenta;
    
    @Column(name = "estado")
    private UUID estado;
    
    @Column(name = "solicitud")
    private UUID solicitud;
    
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

    public BCCuentas(String noCuenta, UUID estado, UUID solicitud, UUID usuarioCreacion, Date fechaCreacion) {
        this.noCuenta = noCuenta;
        this.estado = estado;
        this.solicitud = solicitud;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = fechaCreacion;
    }
    
    
}
