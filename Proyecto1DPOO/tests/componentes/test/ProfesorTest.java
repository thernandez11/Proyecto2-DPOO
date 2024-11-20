package componentes.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import componentes.Profesor;

class ProfesorTest {

    @Test
    void testConstructorAndGetters() {
        Profesor profesor = new Profesor("profesor1", "password123");

        assertEquals("profesor1", profesor.getLogin());
        assertEquals("password123", profesor.getPassword());
    }

    @Test
    void testSetAndGetLogin() {
        Profesor profesor = new Profesor("profesor1", "password123");
        profesor.setLogin("nuevoProfesor");

        assertEquals("nuevoProfesor", profesor.getLogin());
    }

    @Test
    void testSetAndGetPassword() {
        Profesor profesor = new Profesor("profesor1", "password123");
        profesor.setPassword("nuevaPassword");

        assertEquals("nuevaPassword", profesor.getPassword());
    }

    @Test
    void testNullValues() {
        Profesor profesor = new Profesor(null, null);

        assertNull(profesor.getLogin());
        assertNull(profesor.getPassword());
    }

    @Test
    void testEdgeCases() {
        Profesor profesor = new Profesor("", "");

        assertEquals("", profesor.getLogin());
        assertEquals("", profesor.getPassword());

        profesor.setLogin(" ");
        profesor.setPassword(" ");

        assertEquals(" ", profesor.getLogin());
        assertEquals(" ", profesor.getPassword());
    }
}
