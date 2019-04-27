package com.co.common.models;

import com.co.common.models.gamification.Gamification;
import com.co.common.models.prestamo.Prestamo;
import com.co.common.models.registro.Registro;

import java.util.ArrayList;

public abstract class Configuracion {

    public Prestamo prestamo;
    public ArrayList<Registro> registros;
    public ArrayList<Gamification> categorias;

}
