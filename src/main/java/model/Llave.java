package model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "Datos_Llaves")
public class Llave {
    @Id
    @Column(name = "Nombre_llave")
    private String nombre;

    @Column(name = "Precio_llave")
    private Float precio;

    @Column(name = "Caja_que_abre")
    private String cajasQueAbre;

    public Llave() {

    }
    public Llave(String nombre, Float precio, String cajasQueAbre) {
        this.nombre = nombre;
        this.precio = precio;
        this.cajasQueAbre = cajasQueAbre;
    }

    public String getNombre() {
        return nombre;
    }

    public Float getPrecio() {
        return precio;
    }

    public String getCajasQueAbre() {
        return cajasQueAbre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public void setCajasQueAbre(String cajasQueAbre) {
        this.cajasQueAbre = cajasQueAbre;
    }
}
