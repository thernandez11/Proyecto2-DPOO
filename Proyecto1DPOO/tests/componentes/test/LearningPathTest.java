package componentes.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;

import componentes.Actividad;
import componentes.LearningPath;

class LearningPathTest {

    private LearningPath learningPath;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;

    @BeforeEach
    void setUp() {
        fechaCreacion = LocalDateTime.of(2023, 1, 1, 0, 0);
        fechaModificacion = LocalDateTime.of(2023, 1, 2, 0, 0);
        learningPath = new LearningPath(1, "creador", 1, fechaCreacion, fechaModificacion);
    }

    @Test
    void testGettersYSetters() {
        learningPath.setTitulo("Java Básico");
        assertEquals("Java Básico", learningPath.getTitulo());

        learningPath.setDescripcionGeneral("Una ruta para aprender lo básico de Java.");
        assertEquals("Una ruta para aprender lo básico de Java.", learningPath.getDescripcionGeneral());

        learningPath.setNivelDificultad("Principiante");
        assertEquals("Principiante", learningPath.getNivelDificultad());

        learningPath.setDuracion(30);
        assertEquals(30, learningPath.getDuracion());

        LocalDateTime nuevaFechaCreacion = LocalDateTime.of(2023, 1, 3, 0, 0);
        learningPath.setFechaCreacion(nuevaFechaCreacion);
        assertEquals(nuevaFechaCreacion, learningPath.getFechaCreacion());

        LocalDateTime nuevaFechaModificacion = LocalDateTime.of(2023, 1, 4, 0, 0);
        learningPath.setFechaModificacion(nuevaFechaModificacion);
        assertEquals(nuevaFechaModificacion, learningPath.getFechaModificacion());

        learningPath.setVersion(2);
        assertEquals(2, learningPath.getVersion());

        HashMap<Actividad, Boolean> actividades = new HashMap<>();
        learningPath.setActividades(actividades);
        assertEquals(actividades, learningPath.getActividades());

        learningPath.setLoginCreador("autor");
        assertEquals("autor", learningPath.getLoginCreador());

        learningPath.setId(2);
        assertEquals(2, learningPath.getId());
    }

    @Test
    void testConstructorInicializacion() {
        assertEquals(1, learningPath.getId());
        assertEquals("creador", learningPath.getLoginCreador());
        assertEquals(1, learningPath.getVersion());
        assertEquals(fechaCreacion, learningPath.getFechaCreacion());
        assertEquals(fechaModificacion, learningPath.getFechaModificacion());
    }

    @Test
    void testSetTituloValorNulo() {
        learningPath.setTitulo(null);
        assertNull(learningPath.getTitulo());
    }

    @Test
    void testSetDescripcionGeneralValorNulo() {
        learningPath.setDescripcionGeneral(null);
        assertNull(learningPath.getDescripcionGeneral());
    }

    @Test
    void testSetNivelDificultadValorNulo() {
        learningPath.setNivelDificultad(null);
        assertNull(learningPath.getNivelDificultad());
    }

    @Test
    void testSetDuracionValorNegativo() {
        learningPath.setDuracion(-10);
        assertEquals(-10, learningPath.getDuracion());
    }

    @Test
    void testSetActividadesValorNulo() {
        learningPath.setActividades(null);
        assertNull(learningPath.getActividades());
    }

    @Test
    void testSetLoginCreadorValorNulo() {
        learningPath.setLoginCreador(null);
        assertNull(learningPath.getLoginCreador());
    }
}
