package models.prestamo;

import models.PrestamoDTO;

import java.time.Instant;

public abstract class Prestamo {
     protected Long idUsuario;
     protected Long idBicicleta;
     protected Instant fechaInicio;
     protected Instant fechaFin;
     protected Double valor;
     public TipoPago tipoPago;

     public abstract void finalizarViaje();
     public void setIdUsuario(Long id) {
         this.idUsuario = id;
     }
     public void setIdBicicleta(Long id) {
        this.idBicicleta = id;
    }
     public Long getIdUsuario(){
         return this.idUsuario;
     }
     public Long getIdBicicleta(){
        return this.idBicicleta;
    }

    public Instant getFechaInicio(){
        return this.fechaInicio;
    }

    public Instant getFechaFin(){
        return this.fechaFin;
    }
    public Double getValor(){
        return this.valor;
    }

    public  void fromDto(PrestamoDTO dto) {
        setIdUsuario(dto.idUsuario);
        setIdBicicleta(dto.idBicicleta);
    }
}
