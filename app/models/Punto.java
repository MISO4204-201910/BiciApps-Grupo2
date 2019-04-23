package models;

import javax.persistence.Entity;

@Entity
public class Punto extends BaseModel{

    private static final long serialVersionUID = 1L;

    public String puntosKilometraje;

    public String puntosRecorridos;

    public String puntosRecomendaciones;

}