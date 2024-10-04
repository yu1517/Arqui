package org.example.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Turno {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)  //id autogenerado
    private int id;
    @Column
    private Timestamp fecha;
    @ManyToMany(mappedBy = "turnos", fetch = FetchType.EAGER)  // mappedBy indica que 'turnos' en Persona es el dueño
    private List<Persona> jugadores = new ArrayList<>();

    public Turno() {
        super();
    }

    public Turno(Timestamp fecha) {
        super();
        this.fecha = fecha;
        this.jugadores = new ArrayList<Persona>();
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public List<Persona> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<Persona> jugadores) {
        this.jugadores = jugadores;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Turno{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", jugadores=" + jugadores +
                '}';
    }
}
