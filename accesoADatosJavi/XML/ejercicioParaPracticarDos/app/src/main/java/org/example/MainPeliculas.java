package org.example;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


class MainPeliculas {
    public static void main(String[] args) {
        File archivoXML = new File("Cine.xml");
        try {
            System.out.println("1. Generando el XML");
            List<Pelicula> lista = new ArrayList<>();
            lista.add(new Pelicula("Toy Story", "17-07-2003", 80));
            lista.add(new Pelicula("Pelicula Tres", "25-08-20076", 81));
            PeliculaListWrapper catalogoOriginal = new PeliculaListWrapper(lista);
            JAXBContext claseContexto = JAXBContext.newInstance(PeliculaListWrapper.class);
            Marshaller serielizacion = claseContexto.createMarshaller();
            serielizacion.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            serielizacion.marshal(catalogoOriginal, archivoXML);
            serielizacion.marshal(catalogoOriginal, System.out);
        } catch (Exception e) {
        }
    }
}
