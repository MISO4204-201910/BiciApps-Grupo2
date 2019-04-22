package models.prestamo;

import java.time.Instant;

public class Gratuito extends Prestamo {

    public Gratuito() {
        this.fechaInicio = Instant.now();
        this.valor = 0D;
        this.tipoPago = TipoPago.Gratuito;
    }

    @Override
    public void finalizarViaje() {
        this.fechaFin = Instant.now();
    }
}
