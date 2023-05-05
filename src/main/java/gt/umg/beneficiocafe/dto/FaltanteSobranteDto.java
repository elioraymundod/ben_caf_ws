/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.umg.beneficiocafe.dto;

/**
 *
 * @author Elio Raymundo
 */
public class FaltanteSobranteDto {
    public Double pesoBascula;
    public Double pesoIngresado;
    public Double maximo;
    public Double minimo;
    public Boolean valido;
    public String faltanteSobrante;

    public FaltanteSobranteDto() {
    }

    public FaltanteSobranteDto(Double pesoBascula, Double pesoIngresado, Double maximo, Double minimo, Boolean valido, String faltanteSobrante) {
        this.pesoBascula = pesoBascula;
        this.pesoIngresado = pesoIngresado;
        this.maximo = maximo;
        this.minimo = minimo;
        this.valido = valido;
        this.faltanteSobrante = faltanteSobrante;
    }

   

    public Boolean getValido() {
        return valido;
    }

    public void setValido(Boolean valido) {
        this.valido = valido;
    }

    

    public Double getPesoBascula() {
        return pesoBascula;
    }

    public void setPesoBascula(Double pesoBascula) {
        this.pesoBascula = pesoBascula;
    }

    public Double getPesoIngresado() {
        return pesoIngresado;
    }

    public void setPesoIngresado(Double pesoIngresado) {
        this.pesoIngresado = pesoIngresado;
    }

    public Double getMaximo() {
        return maximo;
    }

    public void setMaximo(Double maximo) {
        this.maximo = maximo;
    }

    public Double getMinimo() {
        return minimo;
    }

    public void setMinimo(Double minimo) {
        this.minimo = minimo;
    }

    public String getFaltanteSobrante() {
        return faltanteSobrante;
    }

    public void setFaltanteSobrante(String faltanteSobrante) {
        this.faltanteSobrante = faltanteSobrante;
    }
    
    
    
}
