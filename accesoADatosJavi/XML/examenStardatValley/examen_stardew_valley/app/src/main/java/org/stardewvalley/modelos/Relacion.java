package org.stardewvalley.modelos;

import javax.xml.bind.annotation.XmlElement;

public class Relacion {
    private String habitante;
    private int corazones;
    private boolean casado;

    public Relacion() {
    }

    @XmlElement(name = "habitante")
    public String getHabitante() {
        return habitante;
    }

    public void setHabitante(String habitante) {
        this.habitante = habitante;
    }

    @XmlElement(name = "corazones")
    public int getCorazones() {
        return corazones;
    }

    public void setCorazones(int corazones) {
        this.corazones = corazones;
    }

    @XmlElement(name = "casado")
    public boolean getCasado() {
        return casado;
    }

    public void setCasado(boolean casado) {
        this.casado = casado;
    }

}
