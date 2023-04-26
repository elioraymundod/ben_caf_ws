/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.umg.beneficiocafe.models;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
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
@Table(name = "bc_usuarios", uniqueConstraints = {@UniqueConstraint(columnNames = "correo_usuario"), @UniqueConstraint(columnNames = "username")}, schema = "umg_beneficio_cafe")
public class BCUsuarios {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_usuario")
    private UUID idUsuario;
    
    @Column(name = "username")
    private String username;
    
    @Column(name = "correo_usuario")
    private String correoUsuario;
    
    @Column(name = "password_usuario")
    private String passwordUsuario;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "bc_usuarios_roles", schema = "umg_beneficio_cafe",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_rol"))
    private Set<BCRoles> roles = new HashSet<>();

    public BCUsuarios(String correoUsuario, String passwordUsuario, String username) {
        this.correoUsuario = correoUsuario;
        this.passwordUsuario = passwordUsuario;
        this.username = username;
    }
    
}
