package org.example;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Main {
    public static void main(String[] args) {
        File archivoXML = new File("producto.xml");
        try {
            Producto productoUno = new Producto(101, "Portátil Mac OS Ventura", 1600.0);

            Document documento = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                    .newDocument();
            Element elementoProducto = documento.createElement("Producto");
            elementoProducto.setAttribute("id", Integer.toString(productoUno.getId()));
            elementoProducto.setAttribute("nombre", productoUno.getNombre());
            elementoProducto.setAttribute("precio", Double.toString(productoUno.getPrecio()));
            documento.appendChild(elementoProducto);

            Transformer transformador = TransformerFactory.newInstance().newTransformer();
            transformador.setOutputProperty(OutputKeys.INDENT, "yes");
            transformador.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformador.transform(new DOMSource(documento), new StreamResult(archivoXML));
            System.out.println("Archivo XML creado: " + archivoXML.getAbsolutePath());
        } catch (ParserConfigurationException | TransformerException e) {
            System.err.println("No se pudo crear el archivo XML: " + e.getMessage());
        }
    }
}
