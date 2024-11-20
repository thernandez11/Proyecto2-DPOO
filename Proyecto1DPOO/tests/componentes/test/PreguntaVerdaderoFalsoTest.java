package componentes.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import componentes.PreguntaVerdaderoFalso;
import componentes.Opcion;

class PreguntaVerdaderoFalsoTest {

    @Test
    void testConstructorAndGetters() {
        Opcion opcion1 = new Opcion("Verdadero", "Es correcto", true);
        Opcion opcion2 = new Opcion("Falso", "Es incorrecto", false);
        List<Opcion> opciones = Arrays.asList(opcion1, opcion2);

        PreguntaVerdaderoFalso pregunta = new PreguntaVerdaderoFalso("¿El cielo es azul?", opciones);

        assertEquals("¿El cielo es azul?", pregunta.getTextoPregunta());
        assertEquals(opciones, pregunta.getOpciones());
    }

    @Test
    void testSetOpciones() {
        Opcion opcion1 = new Opcion("Verdadero", "Es correcto", true);
        Opcion opcion2 = new Opcion("Falso", "Es incorrecto", false);
        List<Opcion> opcionesIniciales = Collections.singletonList(opcion1);
        List<Opcion> opcionesModificadas = Arrays.asList(opcion1, opcion2);

        PreguntaVerdaderoFalso pregunta = new PreguntaVerdaderoFalso("Pregunta inicial", opcionesIniciales);
        pregunta.setOpciones(opcionesModificadas);

        assertEquals(opcionesModificadas, pregunta.getOpciones());
    }

    @Test
    void testNullValues() {
        PreguntaVerdaderoFalso pregunta = new PreguntaVerdaderoFalso(null, null);

        assertNull(pregunta.getTextoPregunta());
        assertNull(pregunta.getOpciones());
    }

    @Test
    void testEmptyOptions() {
        PreguntaVerdaderoFalso pregunta = new PreguntaVerdaderoFalso("Pregunta vacía", Collections.emptyList());

        assertEquals(Collections.emptyList(), pregunta.getOpciones());
    }
}
