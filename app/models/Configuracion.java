package models;

import models.gamification.Gamification;
import models.prestamo.Prestamo;
import models.registro.Registro;

import java.util.ArrayList;

public abstract class Configuracion {

    public Prestamo prestamo;
    public ArrayList<Registro> registros;
    public ArrayList<Gamification> categorias;

}
