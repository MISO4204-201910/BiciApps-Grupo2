package models;

import models.prestamo.Prestamo;
import models.registro.Registro;

import javax.inject.Inject;
import java.util.ArrayList;

public abstract class Configuracion {

    public Prestamo prestamo;
    public ArrayList<Registro> registros;

}
