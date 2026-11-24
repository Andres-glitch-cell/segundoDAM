package org.example;

import java.util.List;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "centros")
public class CentroListWrapper {

    private List<Centro> centros;

    @XmlElement(name = "centro")
    public List<Centro> getCentros() {
        return centros;
    }

    public void setCentros(List<Centro> centros) {
        this.centros = centros;
    }
}
