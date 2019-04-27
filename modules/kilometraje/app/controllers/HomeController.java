package controllers.kilometraje;

import com.co.common.models.Configuracion;
import com.co.common.models.Punto;
import com.co.common.models.gamification.Gamification;
import com.co.common.repository.PuntoRepository;
import com.co.common.repository.UserRepository;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;


public class HomeController extends Controller {

    private final Configuracion configuracion;
    private final PuntoRepository puntoRepository;
    private final UserRepository userRepository;
    private final HttpExecutionContext httpExecutionContext;


    @Inject
    public HomeController(Configuracion configuracion,
                          PuntoRepository puntoRepository,
                          UserRepository userRepository,
                          HttpExecutionContext httpExecutionContext){
        this.configuracion = configuracion;
        this.puntoRepository= puntoRepository;
        this.userRepository = userRepository;
        this.httpExecutionContext = httpExecutionContext;
    }

    public Result index(){
        if (configuracion.categorias.contains(Gamification.Kilometraje)) {
            return ok("index");
        } else {
            return notFound();
        }
    }

    public CompletionStage<Result> mostrarPuntos(Long idUsuario){
        if (configuracion.categorias.contains(Gamification.Kilometraje)) {
            return puntoRepository.lookupByUserId(idUsuario,"recomendaciones").thenComposeAsync(listaPuntos ->{
                return userRepository.lookup(idUsuario).thenApplyAsync(optUser ->{
                    if(optUser.isPresent()){
                        Long puntosUsuario = 0L;
                        for (Punto punto : listaPuntos) {
                            puntosUsuario+=punto.valor;
                        }
                        return ok(views.html.kilometraje.render(optUser.get(),puntosUsuario,configuracion.prestamo.tipoPago));
                    }else{
                        return ok(com.co.common.views.html.notFound.render(optUser.get().id));
                    }
                }, httpExecutionContext.current());

            }, httpExecutionContext.current());
        } else {
            return CompletableFuture.completedFuture(notFound());
        }
    }

    public Result redimirPuntos(){
        return ok(views.html.redimirKm.render());
    }

    public Result premios(){
        return ok(views.html.premios.render());
    }

}
