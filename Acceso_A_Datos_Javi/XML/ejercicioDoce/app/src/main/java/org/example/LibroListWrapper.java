
package org.example;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "biblioteca")
public class LibroListWrapper {

    private List<Libro> libros = new ArrayList<>();

    public LibroListWrapper() {
    }

    public LibroListWrapper(List<Libro> libros) {
        this.libros = libros;
    }

    @XmlElement(name = "libro")
    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }
}
