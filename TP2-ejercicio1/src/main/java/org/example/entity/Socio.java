package org.example.entity;

import jakarta.persistence.*;

@Entity
public class Socio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;  //pk y fk
    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    private Persona persona;
    @Column
    private String tipo;

    public Socio() {
        super();
    }

    public Socio(Persona persona, String tipo) {
        super();

        this.persona = persona;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Socio{" +
                "persona=" + persona +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
