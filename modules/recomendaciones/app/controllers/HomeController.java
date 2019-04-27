package controllers.recomendaciones;

import com.co.common.models.Punto;
import com.co.common.models.Configuracion;
import com.co.common.models.gamification.Gamification;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import com.co.common.repository.PuntoRepository;
import com.co.common.repository.UserRepository;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;




public class HomeController extends Controller {

    private final Configuracion configuracion;
    private final PuntoRepository puntoRepository;
    private final UserRepository userRepository;
    private final HttpExecutionContext httpExecutionContext;
    private final FormFactory formFactory;


    @Inject
    public HomeController(Configuracion configuracion,
                          PuntoRepository puntoRepository,
                          UserRepository userRepository,
                          HttpExecutionContext httpExecutionContext,
                          FormFactory formFactory){
        this.configuracion = configuracion;
        this.puntoRepository= puntoRepository;
        this.userRepository = userRepository;
        this.httpExecutionContext = httpExecutionContext;
        this.formFactory = formFactory;
    }

    public Result index(){
        if (configuracion.categorias.contains(Gamification.Recomendaciones)) {
            return ok("index");
        } else {
            return notFound();
        }
    }

    public CompletionStage<Result> mostrarPuntos(Long idUsuario){
        if (configuracion.categorias.contains(Gamification.Recomendaciones)) {
            return puntoRepository.lookupByUserId(idUsuario, "recomendaciones").thenApplyAsync(listaPuntos -> {
                Long puntosUsuario = 0L;
                for (Punto punto : listaPuntos) {
                    puntosUsuario += punto.valor;
                }
                return ok(views.html.recomendaciones.render(idUsuario, puntosUsuario));
            }, httpExecutionContext.current());
        } else {
            return CompletableFuture.completedFuture(notFound());
        }

    }

    public CompletionStage<Result> activarRecomendacion(Long idUsuario){
        if(configuracion.categorias.contains(Gamification.Recomendaciones)){
            DynamicForm dynamicForm = formFactory.form().bindFromRequest();
            String codigoUsuario = dynamicForm.get("codigo_recomendacion");
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
                    return userRepository.lookup(idUsuario).thenApplyAsync(destUser ->{
                        return destUser.map(user -> ok(views.html.activada.render(optUser.get(), user, puntoDestino)))
                                .orElseGet(() -> notFound(views.html.notFound.render(destUser.get().id)));
                    },httpExecutionContext.current());
                }else{
                    return CompletableFuture.completedFuture(notFound(views.html.notFound.render(idUsuario)));
                }
            }, httpExecutionContext.current());
        }else {
            return CompletableFuture.completedFuture(notFound());
        }
    }
}
