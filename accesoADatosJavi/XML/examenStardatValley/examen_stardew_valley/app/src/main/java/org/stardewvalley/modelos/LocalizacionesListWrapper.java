package org.stardewvalley.modelos;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "localizaciones")
public class LocalizacionesListWrapper {
    private List<Localizacion> localizaciones;

    @XmlElement(name = "localizacion")
    public List<Localizacion> getLocalizacion() {
        return localizaciones;
    }

    public void setLocalizacion(List<Localizacion> localizaciones) {
        this.localizaciones = localizaciones;
    }
}
