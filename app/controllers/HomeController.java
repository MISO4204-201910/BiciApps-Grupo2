package controllers;


import models.Prestamo;
import models.TipoPago;
import play.i18n.MessagesApi;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import respository.UserRepository;

import javax.inject.Inject;


/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {


    private final UserRepository userRepository;
    private final Prestamo prestamo;
    private final HttpExecutionContext httpExecutionContext;
    private final MessagesApi messagesApi;

    @Inject
    public HomeController(UserRepository userRepository,
                          Prestamo prestamo,
                          HttpExecutionContext httpExecutionContext,
                          MessagesApi messagesApi) {
        this.userRepository = userRepository;
        this.prestamo = prestamo;
        this.httpExecutionContext = httpExecutionContext;
        this.messagesApi = messagesApi;
    }

        public Result index () {
            return ok(views.html.index.render());
        }

        public Result prestamoIniciar() {
            prestamo.setIdBicicleta(1L);
            prestamo.setIdUsuario(2L);
            return ok(views.html.prestamo.render(prestamo));
        }

        public Result finalizarPrestamo() {
        prestamo.finalizarViaje();
        if (prestamo.tipoPago == TipoPago.Efectivo) {
            return ok(views.html.prestamoEfectivo.render(prestamo));
        } else if (prestamo.tipoPago == TipoPago.Gratuito) {
            return ok(views.html.prestamoGratuito.render(prestamo));
        } else {
            return ok("Pago no encontrado");
        }
        }
    }
