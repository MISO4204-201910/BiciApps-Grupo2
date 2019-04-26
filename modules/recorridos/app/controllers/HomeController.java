package controllers.recorridos;

import com.co.common.models.Configuracion;
import com.co.common.models.Punto;
import com.co.common.models.gamification.Gamification;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import com.co.common.repository.PuntoRepository;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;




public class HomeController extends Controller {

    private final Configuracion configuracion;
    private final PuntoRepository puntoRepository;
    private final HttpExecutionContext httpExecutionContext;


    @Inject
    public HomeController(Configuracion configuracion,
                          PuntoRepository puntoRepository,
                          HttpExecutionContext httpExecutionContext){
        this.configuracion = configuracion;
        this.puntoRepository= puntoRepository;
        this.httpExecutionContext = httpExecutionContext;
    }

    public Result index(){
        if (configuracion.categorias.contains(Gamification.Recorrido)) {
            return ok("index");
        } else {
            return notFound();
        }
    }

    public CompletionStage<Result> mostrarPuntos(Long idUsuario){
        if (configuracion.categorias.contains(Gamification.Recorrido)) {
            return puntoRepository.lookupByUserId(idUsuario, "Recorrido").thenApplyAsync(listaPuntos -> {
                Long puntosUsuario = 0L;
                for (Punto punto : listaPuntos) {
                    puntosUsuario += punto.valor;
                }
                return ok(views.html.recorridos.render(idUsuario, puntosUsuario));
            }, httpExecutionContext.current());
        } else {
            return CompletableFuture.completedFuture(notFound());
        }
    }
}
