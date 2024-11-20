package componentes.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import componentes.Estudiante;

class EstudianteTest {

    @Test
    void testConstructorAndGetters() {
        Estudiante estudiante = new Estudiante("estudiante1", "password123");

        assertEquals("estudiante1", estudiante.getLogin());
        assertEquals("password123", estudiante.getPassword());
    }

    @Test
    void testSetAndGetLogin() {
        Estudiante estudiante = new Estudiante("estudiante1", "password123");
        estudiante.setLogin("nuevoEstudiante");

        assertEquals("nuevoEstudiante", estudiante.getLogin());
    }

    @Test
    void testSetAndGetPassword() {
        Estudiante estudiante = new Estudiante("estudiante1", "password123");
        estudiante.setPassword("nuevaPassword");

        assertEquals("nuevaPassword", estudiante.getPassword());
    }

    @Test
    void testNullValues() {
        Estudiante estudiante = new Estudiante(null, null);

        assertNull(estudiante.getLogin());
        assertNull(estudiante.getPassword());
    }

    @Test
    void testEdgeCases() {
        Estudiante estudiante = new Estudiante("", "");

        assertEquals("", estudiante.getLogin());
        assertEquals("", estudiante.getPassword());

        estudiante.setLogin(" ");
        estudiante.setPassword(" ");

        assertEquals(" ", estudiante.getLogin());
        assertEquals(" ", estudiante.getPassword());
    }
}
