package componentes.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import componentes.Resena;

public class ResenaTest {

    private Resena resena;

    @BeforeEach
    public void setUp() {
        resena = new Resena(1, "Buena actividad", 4, "juanperez", "estudiante");
    }

    @Test
    public void testConstructor() {
        Resena nuevaResena = new Resena(2, "Excelente", 5, "mariagomez", "profesor");
        assertEquals(2, nuevaResena.getIdActividad());
        assertEquals("Excelente", nuevaResena.getOpinion());
        assertEquals(5, nuevaResena.getRating());
        assertEquals("mariagomez", nuevaResena.getLoginAutor());
        assertEquals("profesor", nuevaResena.getRolAutor());
    }

    @Test
    public void testGetters() {
        assertEquals(1, resena.getIdActividad());
        assertEquals("Buena actividad", resena.getOpinion());
        assertEquals(4, resena.getRating());
        assertEquals("juanperez", resena.getLoginAutor());
        assertEquals("estudiante", resena.getRolAutor());
    }

    @Test
    public void testSetters() {
        resena.setIdActividad(3);
        resena.setOpinion("Regular");
        resena.setRating(3);
        resena.setLoginAutor("carlossanchez");
        resena.setRolAutor("profesor");

        assertEquals(3, resena.getIdActividad());
        assertEquals("Regular", resena.getOpinion());
        assertEquals(3, resena.getRating());
        assertEquals("carlossanchez", resena.getLoginAutor());
        assertEquals("profesor", resena.getRolAutor());
    }

    @Test
    public void testRatingInvalido() {
        assertThrows(IllegalArgumentException.class, () -> {
            resena.setRating(-1);
        });
    }

    @Test
    public void testOpinionNull() {
        resena.setOpinion(null);
        assertNull(resena.getOpinion());
    }

    @Test
    public void testLoginAutorVacio() {
        resena.setLoginAutor("");
        assertEquals("", resena.getLoginAutor());
    }

    @Test
    public void testRolAutorNull() {
        resena.setRolAutor(null);
        assertNull(resena.getRolAutor());
    }
}
