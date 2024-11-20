package componentes.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import componentes.PreguntaMultiple;
import componentes.Opcion;

class PreguntaMultipleTest {

    @Test
    void testConstructorAndGetters() {
        Opcion opcion1 = new Opcion("Opción A", "Explicación A", true);
        Opcion opcion2 = new Opcion("Opción B", "Explicación B", false);
        List<Opcion> opciones = Arrays.asList(opcion1, opcion2);

        PreguntaMultiple pregunta = new PreguntaMultiple("¿Cuál es la respuesta correcta?", opciones);

        assertEquals("¿Cuál es la respuesta correcta?", pregunta.getTextoPregunta());
        assertEquals(opciones, pregunta.getOpciones());
    }

    @Test
    void testSetOpciones() {
        Opcion opcion1 = new Opcion("Opción A", "Explicación A", true);
        Opcion opcion2 = new Opcion("Opción B", "Explicación B", false);
        List<Opcion> opcionesIniciales = Collections.singletonList(opcion1);
        List<Opcion> opcionesModificadas = Arrays.asList(opcion1, opcion2);

        PreguntaMultiple pregunta = new PreguntaMultiple("Pregunta inicial", opcionesIniciales);
        pregunta.setOpciones(opcionesModificadas);

        assertEquals(opcionesModificadas, pregunta.getOpciones());
    }

    @Test
    void testNullValues() {
        PreguntaMultiple pregunta = new PreguntaMultiple(null, null);

        assertNull(pregunta.getTextoPregunta());
        assertNull(pregunta.getOpciones());
    }

    @Test
    void testEmptyOptions() {
        PreguntaMultiple pregunta = new PreguntaMultiple("Pregunta vacía", Collections.emptyList());

        assertEquals(Collections.emptyList(), pregunta.getOpciones());
    }
}
