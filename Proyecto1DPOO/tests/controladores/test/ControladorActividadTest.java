
package controladores.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import componentes.Actividad;
import componentes.Opcion;
import componentes.PreguntaAbierta;
import componentes.PreguntaMultiple;
import componentes.PreguntaVerdaderoFalso;
import controladores.ControladorActividad;

class ControladorActividadTest {

    private ControladorActividad controlador;

    @BeforeEach
    void setUp() {
        controlador = new ControladorActividad();
    }

    @Test
    void testCrearActividad() {
        int id1 = controlador.crearActividad("user1");
        int id2 = controlador.crearActividad("user2");
        assertEquals(1, id1);
        assertEquals(2, id2);
    }

    @Test
    void testGetActividad() {
        int id = controlador.crearActividad("user1");
        Actividad actividad = controlador.getActividad(id);

        assertNotNull(actividad);
        assertEquals(id, actividad.getId());
    }

    @Test
    void testGetActividadNonExistent() {
        Actividad actividad = controlador.getActividad(999);
        assertNull(actividad);
    }

    @Test
    void testGetActividades() {
        controlador.crearActividad("user1");
        controlador.crearActividad("user2");
        Collection<Actividad> actividades = controlador.getActividades();

        assertEquals(2, actividades.size());
    }

    @Test void testGetPreguntasAbiertas() {
        int id = controlador.crearActividad("user1");
        controlador.editarPreguntasAbiertas(id, Arrays.asList("Describe tu experiencia"));
        
        List<PreguntaAbierta> preguntas = controlador.getPreguntasAbiertas(controlador.getActividad(id));
        assertEquals(1, preguntas.size());
        assertEquals("Describe tu experiencia", preguntas.get(0).getTextoPregunta());
    }

    @Test void testEditarTipo() {
        int id = controlador.crearActividad("user1");
        controlador.editarTipo(id, "Laboratorio");

        Actividad actividad = controlador.getActividad(id);
        assertEquals("Laboratorio", actividad.getTipo());
    }

    @Test
    void testEditarDescripcion() {
        int id = controlador.crearActividad("user1");
        controlador.editarDescripcion(id, "Actividad sobre programación");

        Actividad actividad = controlador.getActividad(id);
        assertEquals("Actividad sobre programación", actividad.getDescripcion());
    }

    @Test void testEditarObjetivos() {
        int id = controlador.crearActividad("user1");
        controlador.editarObjetivos(id, "Aprender Java,Practicar tests");

        Actividad actividad = controlador.getActividad(id);
        assertEquals(Arrays.asList("Aprender Java", "Practicar tests"), actividad.getObjetivos());
    }

    @Test void testEditarNivelDificultad() {
        int id = controlador.crearActividad("user1");
        controlador.editarNivelDificultad(id, "Medio");

        Actividad actividad = controlador.getActividad(id);
        assertEquals("Medio", actividad.getNivelDificultad());
    }

    @Test
    void testEditarDuracion() {
        int id = controlador.crearActividad("user1");
        controlador.editarDuracion(id, 120);

        Actividad actividad = controlador.getActividad(id);
        assertEquals(120, actividad.getDuracion());
    }

    @Test
    void testEditarActividadesPrevias() {
        int id1 = controlador.crearActividad("user1");
        int id2 = controlador.crearActividad("user2");
 controlador.editarActividadesPrevias(id2, List.of(id1));
        Actividad actividad = controlador.getActividad(id2);
        assertEquals(1, actividad.getActividadesPrevias().size());
        assertEquals(id1, actividad.getActividadesPrevias().get(0).getId());
    }

    @Test void testEditarFechaLimite() {
        int id = controlador.crearActividad("user1");
        controlador.editarFechaLimite(id, "2023-12-31T23:59");

        Actividad actividad = controlador.getActividad(id);
        assertEquals(LocalDateTime.of(2023, 12, 31, 23, 59), actividad.getFechaLimite());
    }

    @Test
    void testEditarURL() {
        int id = controlador.crearActividad("user1");
        controlador.editarURL(id, "https://example.com");

        Actividad actividad = controlador.getActividad(id);
        assertEquals("https://example.com", actividad.getUrl());
    }

    @Test void testEditarPreguntasMultiples() {
        int id = controlador.crearActividad("user1");
        HashMap<String, HashMap<String, String>> preguntas = new HashMap<>();
        HashMap<String, String> opciones = new HashMap<>();
        opciones.put("A", "ResA");
        opciones.put("B", "ResB");
        preguntas.put("Pregunta?", opciones);

        controlador.editarPreguntasMultiples(id, preguntas, List.of(1));

        Actividad actividad = controlador.getActividad(id);
        List<PreguntaMultiple> pregMultiples = actividad.getPreguntasMultiples();
        assertEquals(1, pregMultiples.size());
        assertEquals("Pregunta?", pregMultiples.get(0).getTextoPregunta());
    }

    @Test void testEditarPreguntasVerdaderoFalso() {
        int id = controlador.crearActividad("user1");
        HashMap<String, HashMap<String, String>> preguntas = new HashMap<>();
        HashMap<String, String> opciones = new HashMap<>();
        opciones.put("Verdadero", "True");
        opciones.put("Falso", "False");
        preguntas.put("Esta declaración es verdadera?", opciones);

        controlador.editarPreguntasVerdaderoFalso(id, preguntas, List.of(1));

        Actividad actividad = controlador.getActividad(id);
        List<PreguntaVerdaderoFalso> pregVF = actividad.getPreguntasVerdaderoFalso();
        assertEquals(1, pregVF.size());
        assertEquals("Esta declaración es verdadera?", pregVF.get(0).getTextoPregunta());
    }

    @Test void testEditarNotaMinima() {
        int id = controlador.crearActividad("user1");
        controlador.editarNotaMinima(id, 5);

        Actividad actividad = controlador.getActividad(id);
        assertEquals(5, actividad.getNotaMinima());
    }
}
