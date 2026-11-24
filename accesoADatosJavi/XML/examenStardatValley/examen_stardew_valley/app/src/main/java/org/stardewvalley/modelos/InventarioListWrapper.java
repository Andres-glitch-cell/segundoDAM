package org.stardewvalley.modelos;


import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "inventario")
public class InventarioListWrapper {
    private List<Item> items;

    @XmlElement(name = "item")
    public List<Item> getItem() {
        return items;
    }

    public void setItem(List<Item> items) {
        this.items = items;
    }
}