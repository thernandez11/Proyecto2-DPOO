package componentes.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import componentes.RegistroActividad;
import componentes.RegistroLearningPath;

import org.junit.jupiter.api.BeforeEach;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class RegistroLearningPathTest {

    private RegistroLearningPath registro;

    @BeforeEach void setUp() {
        registro = new RegistroLearningPath("studentLogin", LocalDateTime.of(2023, 10, 25, 10, 30));
    }

    @Test
    void testConstructor() {
        assertEquals("studentLogin", registro.getLoginEstudiante());
        assertEquals(LocalDateTime.of(2023, 10, 25, 10, 30), registro.getFechaInscrito());
        assertNull(registro.getFechaTerminado());
        assertEquals("Iniciado", registro.getEstado());
        assertNull(registro.getRegistrosA());
    }

    @Test void testSetLoginEstudiante() {
        registro.setLoginEstudiante("newLogin");
        assertEquals("newLogin", registro.getLoginEstudiante());
    }

    @Test void testSetFechaInscrito() {
        LocalDateTime newFechaInscrito = LocalDateTime.of(2023, 11, 1, 9, 0);
        registro.setFechaInscrito(newFechaInscrito);
        assertEquals(newFechaInscrito, registro.getFechaInscrito());
    }

    @Test
    void testSetFechaTerminado() {
        LocalDateTime newFechaTerminado = LocalDateTime.of(2023, 11, 15, 17, 45);
        registro.setFechaTerminado(newFechaTerminado);
        assertEquals(newFechaTerminado, registro.getFechaTerminado());
    }

    @Test
    void testSetRegistrosA() {
        List<RegistroActividad> registros = new ArrayList<>();
        registro.setRegistrosA(registros);
        assertEquals(registros, registro.getRegistrosA());
    }

    @Test void testSetEstado() {
        registro.setEstado("Completado");
        assertEquals("Completado", registro.getEstado());
    }

    // Edge Cases

    @Test
    void testSetLoginEstudianteNull() {
        registro.setLoginEstudiante(null);
        assertNull(registro.getLoginEstudiante());
    }

    @Test
    void testSetFechaInscritoNull() {
        registro.setFechaInscrito(null);
        assertNull(registro.getFechaInscrito());
    }

    @Test
    void testSetFechaTerminadoNull() {
        registro.setFechaTerminado(null);
        assertNull(registro.getFechaTerminado());
    }

    @Test
    void testSetRegistrosANull() {
        registro.setRegistrosA(null);
        assertNull(registro.getRegistrosA());
    }

    @Test
    void testSetEstadoNull() {
        registro.setEstado(null);
        assertNull(registro.getEstado());
    }

    @Test
    void testSetEstadoEmptyString() {
        registro.setEstado("");
        assertEquals("", registro.getEstado());
    }
}
