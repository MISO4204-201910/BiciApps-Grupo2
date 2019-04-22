package modules;

import com.google.inject.AbstractModule;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigValue;
import models.Configuracion;
import models.prestamo.Prestamo;
import models.prestamo.PrestamoFactory;
import models.prestamo.TipoPago;
import models.registro.Registro;
import models.registro.RegistroFactory;
import models.registro.TipoRegistro;
import play.Environment;

import java.util.ArrayList;
import java.util.Map;

public class Module extends AbstractModule {

    private final Environment environment;
    private final Config config;

    public Module(
            Environment environment,
            Config config) {
        this.environment = environment;
        this.config = config;
    }

    protected void configure() {

        final Config prestamoConfig = config.getConfig("prestamo");
        final Config registroConfig = config.getConfig("registro");

        try {
            Prestamo prestamo = prestamoConfiguracion(prestamoConfig);
            ArrayList<Registro> registros = registroConfiguracion(registroConfig);

            Configuracion configuracion = new Configuracion(prestamo, registros);

            bind(Configuracion.class)
                    .to(configuracion.getClass());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Registro> registroConfiguracion(Config config) throws Exception {
        ArrayList<Registro> registros = new ArrayList<>();

        for (Map.Entry<String, ConfigValue> conf : config.entrySet()) {

                if ((Boolean) conf.getValue().unwrapped()){
                    Registro registro = RegistroFactory.crear(TipoRegistro.valueOf(conf.getKey()));
                    registros.add(registro);
                }
        }

        return registros;
    }

    private Prestamo prestamoConfiguracion(Config config) throws Exception {
        Prestamo prestamo = null;
        for (Map.Entry<String, ConfigValue> conf : config.entrySet()) {

            if (prestamo == null) {
                if ((Boolean) conf.getValue().unwrapped()){
                    prestamo = PrestamoFactory.crear(TipoPago.valueOf(conf.getKey()));
                }
            }
        }
        return prestamo;
    }
}
