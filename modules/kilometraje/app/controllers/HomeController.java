package controllers.kilometraje;

import com.co.common.models.Punto;
import com.co.common.repository.PuntoRepository;
import com.co.common.repository.UserRepository;
import play.api.Configuration;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;


public class HomeController extends Controller {

    private final Configuration configuracion;
    private final PuntoRepository puntoRepository;
    private final UserRepository userRepository;
    private final HttpExecutionContext httpExecutionContext;


    @Inject
    public HomeController(Configuration configuracion,
                          PuntoRepository puntoRepository,
                          UserRepository userRepository,
                          HttpExecutionContext httpExecutionContext){
        this.configuracion = configuracion;
        this.puntoRepository= puntoRepository;
        this.userRepository = userRepository;
        this.httpExecutionContext = httpExecutionContext;
    }

    public Result index(){return ok("index");}

    public CompletionStage<Result> mostrarPuntos(Long idUsuario){
        return puntoRepository.lookupByUserId(idUsuario,"Kilometraje").thenComposeAsync(listaPuntos ->{
            return userRepository.lookup(idUsuario).thenApplyAsync(optUser ->{
                if(optUser.isPresent()){
                    Long puntosUsuario = 0L;
                    for (Punto punto : listaPuntos) {
                        puntosUsuario+=punto.valor;
                    }
                    return ok(views.html.kilometraje.render(optUser.get(),puntosUsuario));
                }else{
                    return ok(com.co.common.views.html.notFound.render(optUser.get().id));
                }
            }, httpExecutionContext.current());

        }, httpExecutionContext.current());

    }
}
