package models.prestamo;

public class PrestamoFactory {

    public static Prestamo crear(TipoPago tipoPago) throws Exception {
        switch (tipoPago) {
            case Efectivo:
                return new Efectivo();
            case Gratuito:
                return new Gratuito();
            default:
                throw new Exception("No esta definido el tipo de pago " + tipoPago);
        }
    }

}
