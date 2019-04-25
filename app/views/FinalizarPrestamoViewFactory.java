package views;

import com.co.common.models.gamification.Gamification;
import com.co.common.models.prestamo.Prestamo;
import play.mvc.Result;

import java.util.ArrayList;

import static play.mvc.Results.ok;

public class FinalizarPrestamoViewFactory {

    public static Result crear(Prestamo prestamo, ArrayList<Gamification> categorias) {
        switch (prestamo.tipoPago) {
              case Efectivo:
                return ok(views.html.prestamoPago.render(prestamo, categorias));
              case Gratuito:
                return ok(views.html.prestamoGratuito.render(prestamo));
              case Tarjeta:
                return ok(views.html.prestamoPago.render(prestamo, categorias));
               default:
                return ok("Pago no encontrado");
        }
    }

}
