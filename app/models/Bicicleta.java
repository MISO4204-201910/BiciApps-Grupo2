package models;

import play.data.validation.Constraints;

import javax.persistence.Entity;

@Entity
public class Bicicleta extends BaseModel {

  @Constraints.Required
  public String codigo;

}
