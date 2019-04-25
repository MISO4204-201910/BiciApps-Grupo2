package models;

import models.gamification.Gamification;
import models.prestamo.Prestamo;
import models.registro.Registro;

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
