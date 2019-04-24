package models;

import javax.persistence.Entity;

@Entity
public class Punto extends BaseModel{

    private static final long serialVersionUID = 1L;

    public Integer puntosKilometraje;

    public Integer puntosRecorridos;

    public Integer puntosRecomendaciones;

}