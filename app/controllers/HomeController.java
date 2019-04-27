package controllers;


import models.Configuracion;
import models.Punto;
import models.User;
import models.prestamo.Prestamo;
import models.PrestamoDTO;
import models.prestamo.TipoPago;
import models.registro.Registro;
import models.registro.TipoRegistro;
import play.Logger;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;

import respository.PuntoRepository;
import respository.BicicletaRepository;
import respository.PrestamoRepository;
import respository.UserRepository;
import scala.collection.Seq;
import scala.collection.JavaConverters;

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
                    prestamoDTO.idBicicleta = 5L;
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
            if (configuracion.prestamo.tipoPago == TipoPago.Efectivo) {
                return ok(views.html.prestamoPago.render(configuracion.prestamo));
            } else if (configuracion.prestamo.tipoPago == TipoPago.Gratuito) {
                Punto punto = new Punto();
                punto.id_usuario=idUsuario;
                punto.categoria="recorridos";
                punto.valor= Long.valueOf(configuracion.prestamo.getPuntos());
                puntoRepository.insert(punto);
                return ok(views.html.prestamoGratuito.render(configuracion.prestamo));
            } else if(configuracion.prestamo.tipoPago == TipoPago.Tarjeta) {
                Punto punto = new Punto();
                punto.id_usuario=idUsuario;
                punto.categoria="kilometraje";
                punto.valor= Long.valueOf(configuracion.prestamo.getPuntos());
                puntoRepository.insert(punto);
                puntoRepository.lookupByUserId(idUsuario).thenApplyAsync(listaPuntos ->{
                   System.out.println(listaPuntos.get(0).valor);
                   return null;
                }, httpExecutionContext.current());
                return ok(views.html.prestamoPago.render(configuracion.prestamo));
            }else{
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

        public Result gamification(){
            Seq<Registro> collection = JavaConverters.asScalaIteratorConverter(configuracion.registros.iterator()).asScala().toSeq();
            return  ok(views.html.loginBiciGov.render(collection));
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
