/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.umg.beneficiocafe.models;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Elio Raymundo
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "piloto", schema = "db_beneficio_ws")
public class BeneficioPiloto {
    @Id
    @Column(name = "licencia")
    private String licencia;
    
    @Column(name = "permitido")
    private boolean permitido; 
    
    @Column(name = "usuario_adiciono")
    private String usuarioAdiciono;
    
    @Column(name = "fecha_adiciono")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaAdiciono;
    
    @Column(name = "usuario_modifico")
    private String usuarioModifico;
    
    @Column(name = "fecha_modifico")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaModifico;

    public BeneficioPiloto(String licencia, boolean permitido, String usuarioAdiciono, Date fechaAdiciono, String usuarioModifico, Date fechaModifico) {
        this.licencia = licencia;
        this.permitido = permitido;
        this.usuarioAdiciono = usuarioAdiciono;
        this.fechaAdiciono = fechaAdiciono;
        this.usuarioModifico = usuarioModifico;
        this.fechaModifico = fechaModifico;
    }
    
    
    
}
