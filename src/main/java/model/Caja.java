package model;

import javax.persistence.*;

@Entity
@Table(name = "Nombre_Cajas")
public class Caja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_caja")
    private Long id;

    @Column(name = "Nombre_caja")
    private String nombre;

    public Caja(){}

    public Caja(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
