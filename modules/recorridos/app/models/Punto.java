package models;

import com.co.common.models.BaseModel;

import javax.persistence.Entity;

@Entity
public class Punto extends BaseModel {

    private static final long serialVersionUID = 1L;

    public Long id_usuario;

    public Long valor;

    public String categoria;
    
}