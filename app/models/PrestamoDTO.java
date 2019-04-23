package models;

import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name="prestamo")
public class PrestamoDTO extends BaseModel {

    private static final long serialVersionUID = 1L;

    @Constraints.Required
    public Long idUsuario;

    @Constraints.Required
    public Long idBicicleta;

    @Constraints.Required
    public Instant fechaInicio;

    public Instant fechaFin;

    public String tipoPago;

    public Double valor;
}
