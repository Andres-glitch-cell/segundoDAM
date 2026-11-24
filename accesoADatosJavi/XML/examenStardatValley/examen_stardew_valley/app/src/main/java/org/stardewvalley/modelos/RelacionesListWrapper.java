package org.stardewvalley.modelos;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "relaciones")
public class RelacionesListWrapper {
    private List<Relacion> relaciones;

    @XmlElement(name = "relacion")
    public List<Relacion> getRelacion() {
        return relaciones;
    }

    public void setRelacion(List<Relacion> relaciones) {
        this.relaciones = relaciones;
    }
}
