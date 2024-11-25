package controladores.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import componentes.Actividad;
import componentes.LearningPath;
import controladores.ControladorActividad;
import controladores.ControladorLearningPath;

class ControladorLearningPathTest {

    private ControladorLearningPath controlador;
    private ControladorActividad AC;
    private final String testFilename = "Persistencia/test_lp_data.txt";

    @BeforeEach
    void setUp() {
        // Configuración inicial antes de cada prueba
    	AC = new ControladorActividad();
        controlador = new ControladorLearningPath(AC);
    }

    @AfterEach
    void tearDown() {
        // Limpieza después de cada prueba
        File file = new File(testFilename);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testCrearLearningPath() {
        // Prueba para crear un Learning Path
        String login = "user123";
        int id = controlador.crearLearningPath(login);
        LearningPath lp = controlador.getLearningPath(id);

        assertNotNull(lp);
        assertEquals(id, lp.getId());
        assertEquals(login, lp.getLoginCreador());
        assertEquals(0, lp.getVersion());
    }
    
    @Test
    void testGetLearningPaths() {
        // Prueba para obtener todos los Learning Paths

        int id1 = controlador.crearLearningPath("user1");
        int id2 = controlador.crearLearningPath("user2");
        int id3 = controlador.crearLearningPath("user3");

        Collection<LearningPath> allLearningPaths = controlador.getLearningPaths();

        assertNotNull(allLearningPaths, "La colección no debería ser nula");
        assertEquals(3, allLearningPaths.size(), "La colección debería contener 3 Learning Paths");

        assertTrue(allLearningPaths.stream().anyMatch(lp -> lp.getId() == id1), "El Learning Path con ID 1 debería existir");
        assertTrue(allLearningPaths.stream().anyMatch(lp -> lp.getId() == id2), "El Learning Path con ID 2 debería existir");
        assertTrue(allLearningPaths.stream().anyMatch(lp -> lp.getId() == id3), "El Learning Path con ID 3 debería existir");
    }

    @Test
    void testEditarTitulo() {
        // Prueba para editar el título de un Learning Path
        int id = controlador.crearLearningPath("user123");
        String newTitle = "Título Actualizado";

        controlador.editarTitulo(id, newTitle);

        LearningPath lp = controlador.getLearningPath(id);
        assertEquals(newTitle, lp.getTitulo());
    }
    
    @Test
    void testEditarDescripcionGeneral() {
        // Prueba para editar la descripción general
        int id = controlador.crearLearningPath("user123");
        String newDescription = "Descripción Actualizada";

        controlador.editarDescripcionGeneral(id, newDescription);

        LearningPath lp = controlador.getLearningPath(id);
        assertEquals(newDescription, lp.getDescripcionGeneral());
    }

    @Test
    void testEditarNivelDificultad() {
        // Prueba para editar el nivel de dificultad
        int id = controlador.crearLearningPath("user123");
        String newDifficulty = "Avanzado";

        controlador.editarNivelDificultad(id, newDifficulty);

        LearningPath lp = controlador.getLearningPath(id);
        assertEquals(newDifficulty, lp.getNivelDificultad());
    }

    @Test
    void testEditarDuracion() {
        // Prueba para editar la duración
        int id = controlador.crearLearningPath("user123");
        int newDuration = 120;

        controlador.editarDuracion(id, newDuration);

        LearningPath lp = controlador.getLearningPath(id);
        assertEquals(newDuration, lp.getDuracion());
    }

    @Test
    void testEditarVersion() {
        // Prueba para editar la versión
        int id = controlador.crearLearningPath("user123");
        LearningPath lp = controlador.getLearningPath(id);
        int originalVersion = lp.getVersion();

        controlador.editarVersion(id);

        assertEquals(originalVersion + 1, controlador.getLearningPath(id).getVersion(), "La versión debería incrementarse en 1");
    }

    @Test
    void testEditarFechaModificacion() {
        // Prueba para editar la fecha de modificación
        int id = controlador.crearLearningPath("user123");
        LearningPath lpBefore = controlador.getLearningPath(id);
        LocalDateTime beforeEdit = lpBefore.getFechaModificacion();

        try {
            Thread.sleep(1000);  // Esperar 1 segundo
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        controlador.editarFechaModificacion(id);

        LocalDateTime afterEdit = controlador.getLearningPath(id).getFechaModificacion();
        assertTrue(beforeEdit.isBefore(afterEdit), "La fecha de modificación debería actualizarse a una fecha posterior");
        assertEquals(1, ChronoUnit.SECONDS.between(beforeEdit, afterEdit), "La fecha de modificación debería reflejar el tiempo de la edición");
    }
    
    @Test
    void testGetLearningPathsPropios() {
        // Prueba para obtener los Learning Paths propios de un usuario
        controlador.crearLearningPath("user1");
        controlador.crearLearningPath("user2");
        controlador.crearLearningPath("user1");

        Collection<LearningPath> propios = controlador.getLearningPathsPropios("user1");

        assertEquals(2, propios.size());
    }

    @Test
    void testGetIdsActividadesLP() {
        // Prueba para obtener los IDs de las actividades de un Learning Path
        int id = controlador.crearLearningPath("user123");

        // Simular actividades
        HashMap<Actividad, Boolean> actividades = new HashMap<>();
        actividades.put(new Actividad(1, "user123"), true);
        actividades.put(new Actividad(2, "user123"), false);

        controlador.editarActividades(id, actividades);

        ArrayList<Integer> ids = controlador.getIdsActividadesLP(id);

        assertEquals(2, ids.size());
        assertTrue(ids.contains(1));
        assertTrue(ids.contains(2));
    }
}
