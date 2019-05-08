package models;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.co.common.models.prestamo.*;
import org.junit.Test;

public class PrestamoSpec {


    @Test
    public void testPrestamoEfectivo() throws Exception {
        Prestamo pretamo = PrestamoFactory.crear(TipoPago.Efectivo);
        assertTrue(pretamo instanceof Efectivo );
    }

    @Test
    public void testPrestamoTarjeta() throws Exception {
        Prestamo pretamo = PrestamoFactory.crear(TipoPago.Tarjeta);
        assertTrue(pretamo instanceof Tarjeta);
    }

    @Test
    public void testPrestamoGratuito() throws Exception {
        Prestamo pretamo = PrestamoFactory.crear(TipoPago.Gratuito);
        assertTrue(pretamo instanceof Gratuito);
    }

    @Test
    public void testPrestamoGratuitoValor() throws Exception {
        Prestamo pretamo = PrestamoFactory.crear(TipoPago.Gratuito);
        pretamo.finalizarViaje();
        assertEquals(0D, pretamo.getValor(), 0D);
    }

    @Test
    public void testPrestamoEfectivoValor() throws Exception {
        Prestamo pretamo = PrestamoFactory.crear(TipoPago.Efectivo);
        Thread.sleep(2000);
        pretamo.finalizarViaje();
        assertTrue(pretamo.getValor() > 0D);
    }

    @Test
    public void testPrestamoTarjetaValor() throws Exception {
        Prestamo pretamo = PrestamoFactory.crear(TipoPago.Tarjeta);
        Thread.sleep(2000);
        pretamo.finalizarViaje();
        assertTrue(pretamo.getValor() < 0D);
    }

}
