package org.example;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        File archivoXML = new File("producto.xml");
        try {
            Producto productoUno = new Producto(101, "Portátil Mac OS Ventura", 1600.0);
            JAXBContext claseProducto = JAXBContext.newInstance(Producto.class);

            Marshaller serializarXML = claseProducto.createMarshaller();
            serializarXML.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            serializarXML.marshal(productoUno, archivoXML);
            System.out.println("Archivo XML creado: " + archivoXML.getAbsolutePath());
        } catch (JAXBException e) {
            System.err.println("No se pudo crear el archivo XML: " + e.getMessage());
        }
    }
}
