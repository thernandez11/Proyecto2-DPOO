package componentes.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import componentes.PreguntaAbierta;

class PreguntaAbiertaTest {

    @Test
    void testConstructorAndGetTextoPregunta() {
        PreguntaAbierta pregunta = new PreguntaAbierta("¿Qué es la programación?");

        assertEquals("¿Qué es la programación?", pregunta.getTextoPregunta());
    }

    @Test
    void testSetTextoPregunta() {
        PreguntaAbierta pregunta = new PreguntaAbierta("Pregunta inicial");
        pregunta.setTextoPregunta("Pregunta modificada");

        assertEquals("Pregunta modificada", pregunta.getTextoPregunta());
    }

    @Test
    void testNullValue() {
        PreguntaAbierta pregunta = new PreguntaAbierta(null);

        assertNull(pregunta.getTextoPregunta());
        pregunta.setTextoPregunta("Texto no nulo");
        assertEquals("Texto no nulo", pregunta.getTextoPregunta());
    }
}
