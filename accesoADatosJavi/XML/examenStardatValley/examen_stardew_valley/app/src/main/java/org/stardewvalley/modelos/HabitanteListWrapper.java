package org.stardewvalley.modelos;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "habitantes")
public class HabitanteListWrapper {
    private List<Habitante> habitantes;

    @XmlElement(name = "habitante")
    public List<Habitante> getHabitante() {
        return habitantes;
    }

    public void setHabitante(List<Habitante> habitantes) {
        this.habitantes = habitantes;
    }
}