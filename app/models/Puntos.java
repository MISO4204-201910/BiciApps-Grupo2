package models;

import javax.persistence.Entity;

@Entity
public class Puntos extends BaseModel{

    private static final long serialVersionUID = 1L;

    public String puntosKilometraje;

    public String puntosRecorridos;

    public String puntosRecomendaciones;

}