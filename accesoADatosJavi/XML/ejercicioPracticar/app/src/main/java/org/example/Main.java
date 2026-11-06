package org.example;

import org.*;

import jakarta.xml.bind.JAXB;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import java.io.File;

class Main {
    public static void main(String[] args) {
        File archivoXML = new File("producto.xml");
        try {
            Producto productoUno = new Producto(101, "Portátil Mac OS Ventura", 1600.0);
            JAXBContext claseProducto = JAXBContext.newInstance(Producto.class);

            Marshaller serializarXML = claseProducto.createMarshaller();

            serializarXML.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        } catch (Exception e) {
        }
    }
}
