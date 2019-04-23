package controllers;


import models.Configuracion;
import models.User;
import models.prestamo.Prestamo;
import models.prestamo.TipoPago;
import models.registro.Registro;
import models.registro.TipoRegistro;
import play.i18n.MessagesApi;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import respository.PuntoRepository;
import respository.UserRepository;
import scala.collection.Seq;
import scala.collection.JavaConverters;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;


/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {


    private final UserRepository userRepository;
    private final PuntoRepository puntoRepository;
    private final Configuracion configuracion;
    private final HttpExecutionContext httpExecutionContext;
    private final MessagesApi messagesApi;

    @Inject
    public HomeController(UserRepository userRepository,
                          PuntoRepository puntoRepository,
                          Configuracion configuracion,
                          HttpExecutionContext httpExecutionContext,
                          MessagesApi messagesApi) {
        this.userRepository = userRepository;
        this.puntoRepository= puntoRepository;
        this.configuracion = configuracion;
        this.httpExecutionContext = httpExecutionContext;
        this.messagesApi = messagesApi;
    }

        public Result index () {
            return ok(views.html.index.render());
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

        public Result finalizarPrestamo() {
            configuracion.prestamo.finalizarViaje();
            if (configuracion.prestamo.tipoPago == TipoPago.Efectivo) {
                return ok(views.html.prestamoEfectivo.render(configuracion.prestamo));
            } else if (configuracion.prestamo.tipoPago == TipoPago.Gratuito) {
                return ok(views.html.prestamoGratuito.render(configuracion.prestamo));
            } else {
                return ok("Pago no encontrado");
            }
        }

        public Result registro() {
            Seq<Registro> collection = JavaConverters.asScalaIteratorConverter(configuracion.registros.iterator()).asScala().toSeq();
        return ok(views.html.registro.render(collection));
        }

        public Result iniciarRegistro(TipoRegistro tipoRegistro) {
            return ok(tipoRegistro.name());
        }

        /*public Result registroFacebook() {
            return ok("Registro facebook");
        }

        public Result registroTelefono() {
            return ok("Registro telefono");
        }

        public Result registroCorreo() {
           return ok("Registro correo");
        }*/

        public CompletionStage<Result> gamificationBiciGov(Long idUsuario){
            return puntoRepository.lookupByUserId(idUsuario).thenApplyAsync( listaPuntos -> {
                return ok(views.html.gamificationBiciGov.render(idUsuario, listaPuntos));
            }, httpExecutionContext.current());
            //return ok(views.html.gamificationBiciGov.render());
        }
        public Result gamificationBiciCity(){
            //User usuario = new User("12345678","CC","Alejandro","Martinez",1986-03-10);

            return ok(views.html.gamificationBiciCity.render());
        }
        public Result catalogoPremios(){
            return ok(views.html.catalogoPremios.render());
        }
    }
