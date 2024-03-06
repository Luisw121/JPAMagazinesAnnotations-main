package model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "Datos_Skins")
public class Skin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_skin")
    private Long id;

    @Column(name = "Nombre_caja")
    private String caja;

    @Column(name = "Nombre_skin")
    private String nombre;
    public Skin(){

    }

    public Skin(String caja, String nombre) {
        this.caja = caja;
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCaja() {
        return caja;
    }

    public void setCaja(String caja) {
        this.caja = caja;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    @Override
    public String toString() {
        return "Skin:" +
                "id=" + id +
                ", caja='" + caja + '\'' +
                ", nombre='" + nombre + '\'' +
                '.';
    }
}
