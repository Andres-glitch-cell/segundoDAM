package org.example;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

public class MainPedido {

    public static void main(String[] args) {
        File archivoXML = new File("pedido.xml");
        try {
            System.out.println("1 Guardamos objeto de Java a XML");
            Item itemUno = new Item("Teclado Mecánico", 45.50);
            Item itemDos = new Item("Monitor", 129.99);
            List<Item> listaItems = new ArrayList<>();
            listaItems.add(itemUno);
            listaItems.add(itemDos);
            Pedido pedidoOriginal = new Pedido(2001, "2026-11-17", "Laura Gómez", listaItems);
            JAXBContext contextoClase = JAXBContext.newInstance(Pedido.class);
            Marshaller serielizacion = contextoClase.createMarshaller();
            serielizacion.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            serielizacion.setProperty(Marshaller.JAXB_FRAGMENT, true);
            serielizacion.marshal((pedidoOriginal), archivoXML);
            serielizacion.marshal(pedidoOriginal, System.out);
            System.out.println();
            System.out.println("\n2. Leyendo archivo XML a Obvjeto Java");
            Unmarshaller deserielizacion = contextoClase.createUnmarshaller();
            Pedido pedidoLeido = (Pedido) deserielizacion.unmarshal(archivoXML);
            System.out.println("Pedido realizado " + pedidoLeido);
        } catch (Exception e) {
            System.err.println("Error " + e.getMessage());
            e.printStackTrace();
        }
    }
}
