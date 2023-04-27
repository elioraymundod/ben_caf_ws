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
@Table(name = "bc_pilotos", schema = "umg_beneficio_cafe")
public class BCPilotos implements Serializable {
    @Id
    @Column(name = "licencia_piloto")
    private String licenciaPiloto;
    
    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "celular")
    private String celular;
    
    @Column(name = "correo")
    private String correo;
    
    @Column(name = "permitido_en_beneficio")
    private Boolean permitidoEnBeneficio;
    
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

    public BCPilotos(String licenciaPiloto, String nombre, String celular, String correo, UUID usuarioCreacion, Date fechaCreacion, Boolean permitidoEnBeneficio) {
        this.licenciaPiloto = licenciaPiloto;
        this.nombre = nombre;
        this.celular = celular;
        this.correo = correo;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = fechaCreacion;
        this.permitidoEnBeneficio = permitidoEnBeneficio;
    }
    
}
