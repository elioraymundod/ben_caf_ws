/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.umg.beneficiocafe.dto;

import java.io.Serializable;

/**
 *
 * @author Elio Raymundo
 */


public class SolicitudesDto implements Serializable {
    private String estado;
    private String nombrePiloto;
    private String placa;
    private String piloto;
    private String descripcion;

    public SolicitudesDto() {
    }

    public SolicitudesDto(String estado, String nombrePiloto, String placa, String piloto, String descripcion) {
        this.estado = estado;
        this.nombrePiloto = nombrePiloto;
        this.placa = placa;
        this.piloto = piloto;
        this.descripcion = descripcion;
    }
    
    

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombrePiloto() {
        return nombrePiloto;
    }

    public void setNombrePiloto(String nombrePiloto) {
        this.nombrePiloto = nombrePiloto;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getPiloto() {
        return piloto;
    }

    public void setPiloto(String piloto) {
        this.piloto = piloto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
    
}
