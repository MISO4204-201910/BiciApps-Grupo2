package com.co.common.models.prestamo;

import java.time.Duration;
import java.time.Instant;

public class Efectivo extends Prestamo {

    private Double TARIFA_POR_SEGUNDO = 1.4;

    public Efectivo() {
        this.fechaInicio = Instant.now();
        this.tipoPago = TipoPago.Efectivo;
    }

    @Override
    public void finalizarViaje() {
        this.fechaFin = Instant.now();
        Duration between = Duration.between(fechaInicio, fechaFin);
        this.valor = between.getSeconds() * TARIFA_POR_SEGUNDO;
    }
}
