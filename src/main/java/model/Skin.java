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

    @ManyToOne
    @JoinColumn(name = "Nombre_caja")
    private Caja caja;

    @Column(name = "Nombre_skin")
    private String nombre;
    public Skin(){

    }

    public Skin(Long id, Caja caja, String nombre) {
        this.id = id;
        this.caja = caja;
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Caja getCaja() {
        return caja;
    }

    public void setCaja(Caja caja) {
        this.caja = caja;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
