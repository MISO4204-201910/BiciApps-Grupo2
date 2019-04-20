package modules;

import com.google.inject.AbstractModule;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigValue;
import models.*;
import play.Environment;

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

        TipoPago tipoPago = null;
        for (Map.Entry<String, ConfigValue> conf : prestamoConfig.entrySet()) {

            if (tipoPago == null) {
                if ((Boolean) conf.getValue().unwrapped()){
                    tipoPago = TipoPago.valueOf(conf.getKey());
                }
            }
        }

        Prestamo prestamo = null;
        try {
            prestamo = new PrestamoFactory().crear(tipoPago);
            bind(Prestamo.class)
                    .to(prestamo.getClass());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
