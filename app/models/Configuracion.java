package models;

import models.prestamo.Prestamo;
import models.registro.Registro;

import java.util.ArrayList;

public class Configuracion {

    public Prestamo prestamo;
    public ArrayList<Registro> registros;

    public Configuracion(Prestamo prestamo, ArrayList<Registro> registros) {
        this.prestamo = prestamo;
        this.registros = registros;
    }
}
