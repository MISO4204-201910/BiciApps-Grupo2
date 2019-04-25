package com.co.common.models.prestamo;

import java.time.Duration;
import java.time.Instant;

public class Tarjeta extends Prestamo {

    private Double KM_POR_SEGUNDO = 10.00417;
    private Double TARIFA_POR_SEGUNDO= 1.4;
    private Double PUNTOS_POR_KM = 0.333;

    public Tarjeta() {
        this.fechaInicio = Instant.now();
        this.tipoPago = TipoPago.Tarjeta;
    }

    @Override
    public void finalizarViaje() {
        this.fechaFin = Instant.now();
        Duration between = Duration.between(fechaInicio, fechaFin);
        this.valor = between.getSeconds() * TARIFA_POR_SEGUNDO;
        this.kilometraje = between.getSeconds() * KM_POR_SEGUNDO;
        Double calculoPuntos = (this.kilometraje * PUNTOS_POR_KM);
        this.puntos= calculoPuntos.intValue();

    }
}
