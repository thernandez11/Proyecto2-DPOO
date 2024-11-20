package componentes.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import componentes.Opcion;

class OpcionTest {

    @Test
    void testConstructorYGetters() {
        Opcion opcion = new Opcion("Texto de opción", "Esta es una explicación.", true);

        assertEquals("Texto de opción", opcion.getTextoOpcion());
        assertEquals("Esta es una explicación.", opcion.getExplicacion());
        assertTrue(opcion.getCorrecta());
    }

    @Test
    void testSetYGetTextoOpcion() {
        Opcion opcion = new Opcion("Texto inicial", "Explicación inicial", false);
        opcion.setTextoOpcion("Texto modificado");

        assertEquals("Texto modificado", opcion.getTextoOpcion());
    }

    @Test
    void testSetYGetExplicacion() {
        Opcion opcion = new Opcion("Texto inicial", "Explicación inicial", false);
        opcion.setExplicacion("Explicación modificada");

        assertEquals("Explicación modificada", opcion.getExplicacion());
    }

    @Test
    void testSetYGetCorrecta() {
        Opcion opcion = new Opcion("Texto inicial", "Explicación inicial", false);
        opcion.setCorrecta(true);

        assertTrue(opcion.getCorrecta());
    }

    @Test
    void testValoresNulos() {
        Opcion opcion = new Opcion(null, null, null);

        assertNull(opcion.getTextoOpcion());
        assertNull(opcion.getExplicacion());
        assertNull(opcion.getCorrecta());
    }

    @Test
    void testCasosBorde() {
        Opcion opcion = new Opcion("", "", true);

        assertEquals("", opcion.getTextoOpcion());
        assertEquals("", opcion.getExplicacion());
        assertTrue(opcion.getCorrecta());

        opcion.setTextoOpcion(" ");
        opcion.setExplicacion(" ");
        opcion.setCorrecta(false);

        assertEquals(" ", opcion.getTextoOpcion());
        assertEquals(" ", opcion.getExplicacion());
        assertFalse(opcion.getCorrecta());
    }
}
