package models;

import models.prestamo.Prestamo;
import models.registro.Registro;

import javax.inject.Inject;
import java.util.ArrayList;

public class ConfiguracionImpl extends Configuracion {

    @Inject
    public ConfiguracionImpl(Prestamo prestamo, ArrayList<Registro> registros) {
        this.prestamo = prestamo;
        this.registros = registros;
    }
}
