package controllers;


import models.Configuracion;
import models.prestamo.Prestamo;
import models.prestamo.TipoPago;
import play.i18n.MessagesApi;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import respository.UserRepository;

import javax.inject.Inject;


/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {


    private final UserRepository userRepository;
    private final Configuracion configuracion;
    private final HttpExecutionContext httpExecutionContext;
    private final MessagesApi messagesApi;

    @Inject
    public HomeController(UserRepository userRepository,
                          Configuracion configuracion,
                          HttpExecutionContext httpExecutionContext,
                          MessagesApi messagesApi) {
        this.userRepository = userRepository;
        this.configuracion = configuracion;
        this.httpExecutionContext = httpExecutionContext;
        this.messagesApi = messagesApi;
    }

        public Result index () {
            return ok(views.html.index.render());
        }

        public Result prestamoIniciar() {
            configuracion.prestamo.setIdBicicleta(1L);
            configuracion.prestamo.setIdUsuario(2L);
            return ok(views.html.prestamo.render(configuracion.prestamo));
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
            return ok(views.html.registro.render());
        }

        public Result iniciarRegistro(String tipoRegistro) {
            return ok(tipoRegistro);
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
    }
