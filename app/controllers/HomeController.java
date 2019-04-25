package controllers;


import com.co.common.models.Configuracion;
import com.co.common.models.Punto;
import com.co.common.models.gamification.Gamification;
import com.co.common.models.PrestamoDTO;
import com.co.common.models.registro.Registro;
import com.co.common.models.registro.TipoRegistro;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;

import com.co.common.repository.PuntoRepository;
import com.co.common.repository.BicicletaRepository;
import com.co.common.repository.PrestamoRepository;
import com.co.common.repository.UserRepository;
import scala.collection.Seq;
import scala.collection.JavaConverters;
import views.FinalizarPrestamoViewFactory;

import javax.inject.Inject;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;


/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {


    private final UserRepository userRepository;
    private final PuntoRepository puntoRepository;
    private final PrestamoRepository prestamoRepository;
    private final BicicletaRepository bicicletaRepository;
    private final Configuracion configuracion;
    private final HttpExecutionContext httpExecutionContext;
    private final MessagesApi messagesApi;
    private final FormFactory formFactory;

    @Inject
    public HomeController(UserRepository userRepository,
                          PuntoRepository puntoRepository,
                          PrestamoRepository prestamoRepository,
                          BicicletaRepository bicicletaRepository,
                          Configuracion configuracion,
                          HttpExecutionContext httpExecutionContext,
                          MessagesApi messagesApi,
                          FormFactory formFactory) {
        this.userRepository = userRepository;
        this.puntoRepository= puntoRepository;
        this.prestamoRepository = prestamoRepository;
        this.bicicletaRepository = bicicletaRepository;
        this.configuracion = configuracion;
        this.httpExecutionContext = httpExecutionContext;
        this.messagesApi = messagesApi;
        this.formFactory = formFactory;
    }

        public Result index () {
            return ok(views.html.index.render());
        }

        public CompletionStage<Result> realizarPrestamo(Long idUsuario) {
            DynamicForm dynamicForm = formFactory.form().bindFromRequest();
            String codigoBicicleta = dynamicForm.get("codigo_bicicleta");
            return bicicletaRepository.lookupByCodigo(codigoBicicleta).thenComposeAsync(optBicicleta -> {
                if (optBicicleta.isPresent()) {
                    PrestamoDTO prestamoDTO = new PrestamoDTO();
                    prestamoDTO.fechaInicio = Instant.now();
                    prestamoDTO.idUsuario = idUsuario;
                    prestamoDTO.idBicicleta = optBicicleta.get().id;
                    return prestamoRepository.insert(prestamoDTO).thenApplyAsync(a -> {
                        configuracion.prestamo.fromDto(prestamoDTO);
                        return ok(views.html.prestamo.render(configuracion.prestamo));
                    }, httpExecutionContext.current());
                } else {
                    return CompletableFuture.completedFuture(notFound(views.html.notFound.render(idUsuario)));
                }
            }, httpExecutionContext.current());

        }

        public CompletionStage<Result> prestamoIniciar(Long idUsuario) {
            return userRepository.lookup(idUsuario).thenApplyAsync( optUser -> {
                if (optUser.isPresent()) {
                  String nombreCompleto = optUser.get().nombre + " " + optUser.get().apellidos;
                    return ok(views.html.prestamoIniciar.render(nombreCompleto, idUsuario));
                } else {
                    return notFound(views.html.notFound.render(idUsuario));
                }
            }, httpExecutionContext.current());
        }

        public Result finalizarPrestamo(Long idUsuario) {
            configuracion.prestamo.finalizarViaje();

            for(Gamification categoria : configuracion.categorias) {
                Punto punto = new Punto();
                punto.id_usuario = idUsuario;
                punto.categoria = categoria.name();
                punto.valor = Long.valueOf(configuracion.prestamo.getPuntos());
                puntoRepository.insert(punto);
            }
            return FinalizarPrestamoViewFactory.crear(configuracion.prestamo, configuracion.categorias);
        }

        public Result registro() {
            Seq<Registro> collection = JavaConverters.asScalaIteratorConverter(configuracion.registros.iterator()).asScala().toSeq();
        return ok(views.html.registro.render(collection));
        }

        public Result iniciarRegistro(TipoRegistro tipoRegistro) {
            return ok(tipoRegistro.name());
        }

        public Result gamification(){
            return  ok(views.html.loginBiciGov.render());
        }
        public CompletionStage<Result> gamificationBiciGov(Long idUsuario){
            return userRepository.lookup(idUsuario).thenApplyAsync( optUser -> {
                if (optUser.isPresent()) {
                    String nombreCompleto = optUser.get().nombre + " " + optUser.get().apellidos;
                    Integer puntos = optUser.get().puntos;
                    return ok(views.html.gamificationBiciGov.render(nombreCompleto, puntos));
                } else {
                    return notFound(views.html.notFound.render(idUsuario));
                }
            }, httpExecutionContext.current());
        }
        public CompletionStage<Result> gamificationBiciCity(Long idUsuario){
            return userRepository.lookup(idUsuario).thenApplyAsync( optUser -> {
                if (optUser.isPresent()) {
                    String nombreCompleto = optUser.get().nombre + " " + optUser.get().apellidos;
                    Integer puntos = optUser.get().puntos;
                    return ok(views.html.gamificationBiciCity.render(nombreCompleto, puntos));
                } else {
                    return notFound(views.html.notFound.render(idUsuario));
                }
            }, httpExecutionContext.current());
        }
        public Result catalogoPremios(){
            return ok(views.html.catalogoPremios.render());
        }
    }
