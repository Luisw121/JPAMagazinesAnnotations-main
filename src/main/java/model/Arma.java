package model;

import javax.persistence.*;

@Entity
@Table(name = "Datos_Armas")
public class Arma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_arma")
    private Long id;

    @Column(name = "Nombre_arma")
    private String nombre;

    @Column(name = "Damage_LMB")
    private Integer damageLMB;

    @Column(name = "Damage_RMB")
    private Integer damageRMB;

    @Column(name = "Kill_Award")
    private String killAward;

    @Column(name = "Running_Speed")
    private Double runningSpeed;

    @Column(name = "Side")
    private String side;

    public Arma(Long id, String nombre, Integer damageLMB, Integer damageRMB, String killAward, Double runningSpeed, String side) {
        this.id = id;
        this.nombre = nombre;
        this.damageLMB = damageLMB;
        this.damageRMB = damageRMB;
        this.killAward = killAward;
        this.runningSpeed = runningSpeed;
        this.side = side;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getDamageLMB() {
        return damageLMB;
    }

    public Integer getDamageRMB() {
        return damageRMB;
    }

    public String getKillAward() {
        return killAward;
    }

    public Double getRunningSpeed() {
        return runningSpeed;
    }

    public String getSide() {
        return side;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDamageLMB(Integer damageLMB) {
        this.damageLMB = damageLMB;
    }

    public void setDamageRMB(Integer damageRMB) {
        this.damageRMB = damageRMB;
    }

    public void setKillAward(String killAward) {
        this.killAward = killAward;
    }

    public void setRunningSpeed(Double runningSpeed) {
        this.runningSpeed = runningSpeed;
    }

    public void setSide(String side) {
        this.side = side;
    }
}
