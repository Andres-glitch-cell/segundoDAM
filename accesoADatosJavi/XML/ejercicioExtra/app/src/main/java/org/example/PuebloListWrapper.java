package org.example;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "pueblos")
public class PuebloListWrapper {

    private List<Pueblo> pueblos;

    @XmlElement(name = "pueblo")
    public List<Pueblo> getPueblos() {
        return pueblos;
    }

    public void setPueblos(List<Pueblo> pueblos) {
        this.pueblos = pueblos;
    }
}
