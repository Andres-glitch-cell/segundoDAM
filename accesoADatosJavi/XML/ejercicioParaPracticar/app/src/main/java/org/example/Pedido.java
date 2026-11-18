package org.example;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "pedido")
public class Pedido {

    private int id;
    private String fecha;
    private String cliente;
    private List<Item> items = new ArrayList<>();

    public Pedido() {
    }

    public Pedido(int id, String fecha, String cliente, List<Item> items) {
        this.id = id;
        this.fecha = fecha;
        this.cliente = cliente;
        this.items = items;
    }

    @XmlElement(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlElement(name = "fecha")
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @XmlElement(name = "cliente")
    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    @XmlElement(name = "item")
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "\nPedido Id=" + id + "\nFecha=" + fecha + "\nCliente=" + cliente + "\nItems=" + items;
    }
}
