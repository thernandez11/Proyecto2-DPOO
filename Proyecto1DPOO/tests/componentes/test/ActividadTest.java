package componentes.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import componentes.Actividad;
import componentes.PreguntaAbierta;
import componentes.PreguntaMultiple;
import componentes.PreguntaVerdaderoFalso;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


class ActividadTest {

    @Test void testConstructorAndGetters() {
        Actividad actividad = new Actividad(1, "usuario1");

        assertEquals(1, actividad.getId());
        assertEquals("usuario1", actividad.getLoginCreador());
    }
    

    @Test void testSetAndGetTipo() {
        Actividad actividad = new Actividad(1, "usuario1");
        actividad.setTipo("practica");

        assertEquals("practica", actividad.getTipo());
    }

    @Test
    void testSetAndGetDescripcion() {
        Actividad actividad = new Actividad(1, "usuario1");
        actividad.setDescripcion("Descripcion de prueba");

        assertEquals("Descripcion de prueba", actividad.getDescripcion());
    }

    @Test void testSetAndGetObjetivos() {
        Actividad actividad = new Actividad(1, "usuario1");
        List<String> objetivos = Arrays.asList("Objetivo1", "Objetivo2");
        actividad.setObjetivos(objetivos);

        assertEquals(objetivos, actividad.getObjetivos());
    }

    @Test
    void testSetAndGetNivelDificultad() {
        Actividad actividad = new Actividad(1, "usuario1");
        actividad.setNivelDificultad("Media");

        assertEquals("Media", actividad.getNivelDificultad());
    }

    @Test void testSetAndGetDuracion() {
        Actividad actividad = new Actividad(1, "usuario1");
        actividad.setDuracion(120);

        assertEquals(120, actividad.getDuracion());
    }

    @Test
    void testsetLoginCreador() {
        Actividad actividad = new Actividad(1, "usuario1");
        actividad.setLoginCreador("usuario2");

        assertEquals("usuario2", actividad.getLoginCreador());
    }

    @Test
    void testSetId() {
        Actividad actividad = new Actividad(1, "usuario1");
        actividad.setId(99);

        assertEquals(99, actividad.getId());
    }

    
    @Test void testSetAndGetActividadesPrevias() {
        Actividad actividad = new Actividad(1, "usuario1");
        List<Actividad> actividadesPrevias = Arrays.asList(new Actividad(2, "usuario2"), new Actividad(3, "usuario3"));
        actividad.setActividadesPrevias(actividadesPrevias);

        assertEquals(actividadesPrevias, actividad.getActividadesPrevias());
    }

    @Test
    void testSetAndGetFechaLimite() {
        Actividad actividad = new Actividad(1, "usuario1");
        LocalDateTime fechaLimite = LocalDateTime.now();
        actividad.setFechaLimite(fechaLimite);

        assertEquals(fechaLimite, actividad.getFechaLimite());
    }

    @Test
    void testSetAndGetActividadesSeguimiento() {
        Actividad actividad = new Actividad(1, "usuario1");
        List<Actividad> actividadesSeguimiento = Arrays.asList(new Actividad(4, "usuario4"), new Actividad(5, "usuario5"));
        actividad.setActividadesSeguimiento(actividadesSeguimiento);

        assertEquals(actividadesSeguimiento, actividad.getActividadesSeguimiento());
    }

    @Test void testSetAndGetUrl() {
        Actividad actividad = new Actividad(1, "usuario1");
        actividad.setUrl("http://example.com");

        assertEquals("http://example.com", actividad.getUrl());
    }

    @Test
    void testSetAndGetPreguntasMultiples() {
        Actividad actividad = new Actividad(1, "usuario1");
        List<PreguntaMultiple> preguntasMultiples = Collections.emptyList();
        actividad.setPreguntasMultiples(preguntasMultiples);

        assertEquals(preguntasMultiples, actividad.getPreguntasMultiples());
    }

    @Test void testSetAndGetPreguntasVerdaderoFalso() {
        Actividad actividad = new Actividad(1, "usuario1");
        List<PreguntaVerdaderoFalso> preguntasVerdaderoFalso = Collections.emptyList();
        actividad.setPreguntasVerdaderoFalso(preguntasVerdaderoFalso);

        assertEquals(preguntasVerdaderoFalso, actividad.getPreguntasVerdaderoFalso());
    }

    @Test void testSetAndGetPreguntasAbiertas() {
        Actividad actividad = new Actividad(1, "usuario1");
        List<PreguntaAbierta> preguntasAbiertas = Collections.emptyList();
        actividad.setPreguntasAbiertas(preguntasAbiertas);

        assertEquals(preguntasAbiertas, actividad.getPreguntasAbiertas());
    }

    @Test void testSetAndGetNotaMinima() {
        Actividad actividad = new Actividad(1, "usuario1");
        actividad.setNotaMinima(75);

        assertEquals(75, actividad.getNotaMinima());
    }

    @Test
    void testEdgeCasesDuracionNegative() {
        Actividad actividad = new Actividad(1, "usuario1");
        actividad.setDuracion(-1);

        assertEquals(-1, actividad.getDuracion());
    }

    @Test
    void testEdgeCasesNotaMinimaNegative() {
        Actividad actividad = new Actividad(1, "usuario1");
        actividad.setNotaMinima(-1);

        assertEquals(-1, actividad.getNotaMinima());
    }

    @Test
    void testNullValues() {
        Actividad actividad = new Actividad(1, "usuario1");
        actividad.setTipo(null);
        actividad.setDescripcion(null);
        actividad.setObjetivos(null);
        actividad.setNivelDificultad(null);
        actividad.setActividadesPrevias(null);
        actividad.setActividadesSeguimiento(null);
        actividad.setUrl(null);
        actividad.setPreguntasMultiples(null);
        actividad.setPreguntasVerdaderoFalso(null);
        actividad.setPreguntasAbiertas(null);

        assertNull(actividad.getTipo());
        assertNull(actividad.getDescripcion());
        assertNull(actividad.getObjetivos());
        assertNull(actividad.getNivelDificultad());
        assertNull(actividad.getActividadesPrevias());
        assertNull(actividad.getActividadesSeguimiento());
        assertNull(actividad.getUrl());
        assertNull(actividad.getPreguntasMultiples());
        assertNull(actividad.getPreguntasVerdaderoFalso());
        assertNull(actividad.getPreguntasAbiertas());
    }
}

