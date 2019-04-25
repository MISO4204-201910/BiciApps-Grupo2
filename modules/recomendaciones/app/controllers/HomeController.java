package controllers.recomendaciones;

import com.co.common.models.Punto;
import play.api.Configuration;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import repository.PuntoRepository;
import repository.UserRepository;

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
                          UserRepository userRepository, HttpExecutionContext httpExecutionContext){
        this.configuracion = configuracion;
        this.puntoRepository= puntoRepository;
        this.userRepository = userRepository;
        this.httpExecutionContext = httpExecutionContext;
    }

    public Result index(){return ok("index");}

    public CompletionStage<Result> mostrarPuntos(Long idUsuario){
        return puntoRepository.lookupByUserId(idUsuario,"recomendaciones").thenApplyAsync(listaPuntos ->{
            Long puntosUsuario=0L;
            for (Punto punto : listaPuntos) {
                puntosUsuario+=punto.valor;
            }
            return ok(views.html.recomendaciones.render(idUsuario,puntosUsuario));
        }, httpExecutionContext.current());

    }

    public Result activarRecomendacion(Long idUsuario, String codigoUsuario){
        if(configuracion.gamification.recomendaciones == true){
            return userRepository.lookupCode(codigoUsuario).thenComposeAsync(optUser ->{
                if(optUser.isPresent()){
                    Punto puntoOrigen = new Punto();
                    puntoOrigen.id_usuario=optUser.get().id;
                    puntoOrigen.categoria="recomendaciones";
                    puntoOrigen.valor=20L;
                    puntoRepository.insert(puntoOrigen);
                    Punto puntoDestino = new Punto();
                    puntoDestino.id_usuario=idUsuario;
                    puntoDestino.categoria="recomendaciones";
                    puntoDestino.valor=20L;
                    puntoRepository.insert(puntoDestino);
                    userRepository.lookup(idUsuario).thenApplyAsync(destUser ->{
                        if(destUser.isPresent()){
                            return ok(views.html.activada.render(optUser,destUser,puntoDestino));
                        }else{
                            return notFound(views.html.notFound.render(destUser.get().id));
                        }
                    },httpExecutionContext.current());

                }else{
                    return notFound(views.html.notFound.render(idUsuario));
                }
            }, httpExecutionContext.current());
        }else {
            return ok("Modulo no disponible");
        }
    }
}
