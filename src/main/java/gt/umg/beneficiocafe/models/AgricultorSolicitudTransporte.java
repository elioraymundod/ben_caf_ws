/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.umg.beneficiocafe.models;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
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
@Table(name = "solicitud_transporte", schema = "db_agricultor_ws")
public class AgricultorSolicitudTransporte {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "correlativo")
    private UUID correlativo;
    
    @Column(name = "id_solicitud")
    private UUID idSolicitud;
    
    @Column(name = "id_transporte")
    private UUID idTransporte;
    
    @Column(name = "licencia")
    private String licencia;

    public AgricultorSolicitudTransporte(UUID idSolicitud, UUID idTransporte, String licencia) {
        this.idSolicitud = idSolicitud;
        this.idTransporte = idTransporte;
        this.licencia = licencia;
    }
    
    
    
}
