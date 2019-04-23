package models.registro;

public abstract class Registro {

    protected TipoRegistro tiopoRegistro;

    public abstract void registrar();

    public TipoRegistro getRegistro() {
        return this.tiopoRegistro;
    }

}
