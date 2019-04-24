package models;

import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import java.util.Date;


@Entity
public class User extends BaseModel{

    private static final long serialVersionUID = 1L;

    @Constraints.Required
    public String numeroDocumento;

    @Constraints.Required
    public String tipoDocumento;

    @Constraints.Required
    public String nombre;

    @Constraints.Required
    public String apellidos;

    @Formats.DateTime(pattern="yyyy-MM-dd")
    public Date fechaNacimiento;

    public Integer puntos;

}
