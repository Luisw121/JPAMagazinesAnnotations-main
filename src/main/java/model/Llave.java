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
    private BigDecimal precio;

    @ElementCollection
    @CollectionTable(name = "Caja_que_abre", joinColumns = @JoinColumn(name = "Nombre_llave"))
    @Column(name = "Caja")
    private List<String> cajasQueAbre;

    public Llave(String nombre, BigDecimal precio, List<String> cajasQueAbre) {
        this.nombre = nombre;
        this.precio = precio;
        this.cajasQueAbre = cajasQueAbre;
    }

    public String getNombre() {
        return nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public List<String> getCajasQueAbre() {
        return cajasQueAbre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public void setCajasQueAbre(List<String> cajasQueAbre) {
        this.cajasQueAbre = cajasQueAbre;
    }
}
