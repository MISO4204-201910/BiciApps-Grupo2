package modules;

import com.google.inject.AbstractModule;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigValue;
import com.co.common.models.Configuracion;
import com.co.common.models.ConfiguracionImpl;
import com.co.common.models.gamification.Gamification;
import com.co.common.models.prestamo.Prestamo;
import com.co.common.models.prestamo.PrestamoFactory;
import com.co.common.models.prestamo.TipoPago;
import com.co.common.models.registro.Registro;
import com.co.common.models.registro.RegistroFactory;
import com.co.common.models.registro.TipoRegistro;
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
        final Config gamificationConfig = config.getConfig("gamification");


        try {
            Prestamo prestamo = prestamoConfiguracion(prestamoConfig);
            ArrayList<Registro> registros = registroConfiguracion(registroConfig);
            ArrayList<Gamification> categorias = gamificationConfiguracion(gamificationConfig);
            Configuracion configuracion = new ConfiguracionImpl(prestamo, registros, categorias);

            bind(Configuracion.class)
                    .toInstance(configuracion);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Gamification> gamificationConfiguracion(Config config) throws Exception {
        ArrayList<Gamification> categorias = new ArrayList<>();

        for (Map.Entry<String, ConfigValue> conf : config.entrySet()) {

            if ((Boolean) conf.getValue().unwrapped()){
                Gamification categoria = Gamification.valueOf(conf.getKey());
                categorias.add(categoria);
            }
        }

        return categorias;
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
