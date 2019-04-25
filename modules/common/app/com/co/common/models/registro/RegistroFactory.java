package com.co.common.models.registro;

public class RegistroFactory {

    public static Registro crear(TipoRegistro tipoRegistro) throws Exception {
        switch (tipoRegistro) {
            case Celular:
                return new RegistroCelular();
            case Correo:
                return new RegistroCorreo();
            case Facebook:
                return new RegistroFacebook();
            default:
                throw new Exception("No esta definido el tipo de registro " + tipoRegistro);
        }
    }

}
