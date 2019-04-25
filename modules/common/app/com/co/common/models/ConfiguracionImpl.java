package com.co.common.models;

import com.co.common.models.gamification.Gamification;
import com.co.common.models.prestamo.Prestamo;
import com.co.common.models.registro.Registro;

import javax.inject.Inject;
import java.util.ArrayList;

public class ConfiguracionImpl extends Configuracion {

    @Inject
    public ConfiguracionImpl(Prestamo prestamo, ArrayList<Registro> registros, ArrayList<Gamification> categorias) {
        this.prestamo = prestamo;
        this.registros = registros;
        this.categorias = categorias;
    }
}
