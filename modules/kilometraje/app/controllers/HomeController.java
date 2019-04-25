package controllers.kilometraje;

import com.co.common.models.Punto;
import com.co.common.repository.PuntoRepository;
import play.api.Configuration;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import javax.inject.Inject;
import java.util.concurrent.CompletionStage;


public class HomeController extends Controller {

    private final Configuration configuracion;
    private final PuntoRepository puntoRepository;
    private final HttpExecutionContext httpExecutionContext;


    @Inject
    public HomeController(Configuration configuracion,
                          PuntoRepository puntoRepository,
                          HttpExecutionContext httpExecutionContext){
        this.configuracion = configuracion;
        this.puntoRepository= puntoRepository;
        this.httpExecutionContext = httpExecutionContext;
    }

    public Result index(){return ok("index");}

    public CompletionStage<Result> mostrarPuntos(Long idUsuario){
        return puntoRepository.lookupByUserId(idUsuario,"kilometraje").thenApplyAsync(listaPuntos ->{
            Long puntosUsuario = 0L;
            for (Punto punto : listaPuntos) {
                puntosUsuario+=punto.valor;
            }
            return ok(views.html.kilometraje.render(idUsuario,puntosUsuario));
        }, httpExecutionContext.current());

    }
}
