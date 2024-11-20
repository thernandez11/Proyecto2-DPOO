package componentes.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import componentes.RegistroActividad;

import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDateTime;
import java.util.HashMap;

class RegistroActividadTest {

    private RegistroActividad registro;

    @BeforeEach void setUp() {
        registro = new RegistroActividad(1, true);
    }

    @Test
    void testConstructor() {
        assertEquals(1, registro.getIdActividad());
        assertTrue(registro.isObligatoria());
    }

    @Test
    void testSetAndGetIdActividad() {
        registro.setIdActividad(100);
        assertEquals(100, registro.getIdActividad());
    }

    @Test
    void testSetAndGetFechaInicio() {
        LocalDateTime now = LocalDateTime.now();
        registro.setFechaInicio(now);
        assertEquals(now, registro.getFechaInicio());
    }

    @Test
    void testSetAndGetFechaTerminado() {
        LocalDateTime now = LocalDateTime.now();
        registro.setFechaTerminado(now);
        assertEquals(now, registro.getFechaTerminado());
    }

    @Test void testSetAndGetEstado() {
        registro.setEstado("Iniciado");
        assertEquals("Iniciado", registro.getEstado());
    }

    @Test void testSetAndGetRespuestas() {
        HashMap<String, String> respuestas = new HashMap<>();
        respuestas.put("Pregunta1", "Respuesta1");
        registro.setRespuestas(respuestas);
        assertNotNull(registro.getRespuestas());
        assertEquals("Respuesta1", registro.getRespuestas().get("Pregunta1"));
    }

    @Test
    void testSetAndGetNota() {
        registro.setNota(85);
        assertEquals(85, registro.getNota());
    }

    @Test
    void testObligatoriaFlag() {
        registro.setObligatoria(false);
        assertFalse(registro.isObligatoria());
    }
}

