package com.co.common.models.prestamo;

import java.time.Duration;
import java.time.Instant;

public class Gratuito extends Prestamo {

    private Double PUNTOS_POR_RECORRIDO = 10.0834;

    public Gratuito() {
        this.fechaInicio = Instant.now();
        this.valor = 0D;
        this.tipoPago = TipoPago.Gratuito;
    }

    @Override
    public void finalizarViaje() {

        this.fechaFin = Instant.now();
        Duration between = Duration.between(fechaInicio, fechaFin);
        Double puntos = (between.getSeconds() * PUNTOS_POR_RECORRIDO);
        this.puntos=puntos.intValue();
    }
}
