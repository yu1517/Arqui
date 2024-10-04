package org.example.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable=false)
    private String nombre;
    @Column(name="anios")
    private int edad;
    @ManyToOne
    @JoinColumn(name = "direccion_id")  // Definir la columna de la FK
    private Direccion domicilio;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "turno_persona",  // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "persona_id"),
            inverseJoinColumns = @JoinColumn(name = "turno_id"))
    private List<Turno> turnos = new ArrayList<>();

    public Persona() {
        super();
    }
    public Persona(String nombre, int edad, Direccion domicilio) {
        super();
        this.nombre = nombre;
        this.edad = edad;
        this.domicilio = domicilio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public Direccion getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Direccion domicilio) {
        if (this.domicilio != null) {
            this.domicilio.getHabitante().remove(this);
        }
        domicilio.getHabitante().add(this);
        this.domicilio = domicilio;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", domicilio=" + domicilio +
                '}';
    }
}
