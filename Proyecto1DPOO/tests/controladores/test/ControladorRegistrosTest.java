package controladores.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import controladores.ControladorRegistros;
import componentes.*;

class ControladorRegistrosTest {

    private ControladorRegistros controlador;
    private LearningPath learningPath;
    private RegistroLearningPath registroLearningPath;
    private Actividad actividad;

    @BeforeEach
    public void setUp() {
        controlador = new ControladorRegistros();

        // Set up LearningPath
        learningPath = new LearningPath(1, "user1", 1, LocalDateTime.now(), LocalDateTime.now());
        learningPath.setActividades(new HashMap<>());

        // Set up RegistroLearningPath
        registroLearningPath = new RegistroLearningPath("student1", LocalDateTime.now());
        registroLearningPath.setRegistrosA(new ArrayList<>());

        // Set up Actividad
        actividad = new Actividad(1, "user1");
        actividad.setTipo("Tarea");
        learningPath.getActividades().put(actividad, false);
    }
    
    @AfterEach
    public void tearDown() {
        controlador = null; // Clear the reference to ensure a fresh instance
    }

    @Test
    public void testCrearRegistroLpEstudiante() {
        controlador.crearRegistroLpEstudiante("student1", learningPath);

        RegistroLearningPath registro = controlador.getRegistroLp("student1", 1);
        assertNotNull(registro);
        assertEquals("student1", registro.getLoginEstudiante());
    }
    
    @Test
    public void testCrearRegistroLpEstudiante_ExistingIdLp() {
        // Arrange: Create a RegistroLearningPath with an existing idLp
        controlador.crearRegistroLpEstudiante("student1", learningPath);

        // Act: Add another RegistroLearningPath for the same idLp
        controlador.crearRegistroLpEstudiante("student2", learningPath);

        // Assert: Verify that both students are registered under the same idLp
        ArrayList<RegistroLearningPath> registros = controlador.getRegistrosLp().get(learningPath.getId());
        assertNotNull(registros);
        assertEquals(2, registros.size());
        assertEquals("student1", registros.get(0).getLoginEstudiante());
        assertEquals("student2", registros.get(1).getLoginEstudiante());
    }
    
    @Test
    public void testCrearRegistrosMultiplesActividades() {
        // Configurar el LearningPath con múltiples tipos de actividad
        Actividad tarea = new Actividad(1, "user1");
        tarea.setTipo("Tarea");
        Actividad recursoEducativo = new Actividad(2, "user1");
        recursoEducativo.setTipo("RecursoEducativo");
        Actividad encuesta = new Actividad(3, "user1");
        encuesta.setTipo("Encuesta");
        encuesta.setPreguntasAbiertas(List.of(new PreguntaAbierta("Pregunta abierta")));
        Actividad examen = new Actividad(4, "user1");
        examen.setTipo("Examen");
        examen.setPreguntasAbiertas(List.of(new PreguntaAbierta("Pregunta de examen")));
        Actividad quizMultiple = new Actividad(5, "user1");
        quizMultiple.setTipo("QuizMultiple");
        quizMultiple.setPreguntasMultiples(List.of(new PreguntaMultiple("Pregunta múltiple", new ArrayList<>())));
        Actividad quizVerdaderoFalso = new Actividad(6, "user1");
        quizVerdaderoFalso.setTipo("QuizVerdaderoFalso");
        quizVerdaderoFalso.setPreguntasVerdaderoFalso(List.of(new PreguntaVerdaderoFalso("Pregunta VF", new ArrayList<>())));

        // Añadir actividades al LearningPath
        learningPath.getActividades().put(tarea, false);
        learningPath.getActividades().put(recursoEducativo, false);
        learningPath.getActividades().put(encuesta, false);
        learningPath.getActividades().put(examen, false);
        learningPath.getActividades().put(quizMultiple, false);
        learningPath.getActividades().put(quizVerdaderoFalso, false);

        // Crear los registros de actividad
        controlador.crearRegistroLpEstudiante("student1", learningPath);
        controlador.crearRegistrosActividad("student1", learningPath);

        // Verificar que cada actividad tenga un registro asociado y que sea correcto
        RegistroActividad registroTarea = controlador.getRegistroActividad("student1", 1, 1);
        assertNotNull(registroTarea);
        assertEquals("Tarea", tarea.getTipo());

        RegistroActividad registroRecurso = controlador.getRegistroActividad("student1", 1, 2);
        assertNotNull(registroRecurso);
        assertEquals("RecursoEducativo", recursoEducativo.getTipo());

        RegistroActividad registroEncuesta = controlador.getRegistroActividad("student1", 1, 3);
        assertNotNull(registroEncuesta);
        assertEquals("Encuesta", encuesta.getTipo());
        assertNotNull(registroEncuesta.getRespuestas()); // Debe haberse inicializado respuestas

        RegistroActividad registroExamen = controlador.getRegistroActividad("student1", 1, 4);
        assertNotNull(registroExamen);
        assertEquals("Examen", examen.getTipo());
        assertNotNull(registroExamen.getRespuestas()); // Debe haberse inicializado respuestas

        RegistroActividad registroQuizMultiple = controlador.getRegistroActividad("student1", 1, 5);
        assertNotNull(registroQuizMultiple);
        assertEquals("QuizMultiple", quizMultiple.getTipo());
        assertNotNull(registroQuizMultiple.getRespuestas()); // Debe haberse inicializado respuestas

        RegistroActividad registroQuizVF = controlador.getRegistroActividad("student1", 1, 6);
        assertNotNull(registroQuizVF);
        assertEquals("QuizVerdaderoFalso", quizVerdaderoFalso.getTipo());
        assertNotNull(registroQuizVF.getRespuestas()); // Debe haberse inicializado respuestas
    }

    @Test
    public void testGetActividadesPendientes() {
        controlador.crearRegistroLpEstudiante("student1", learningPath);
        controlador.crearRegistrosActividad("student1", learningPath);

        List<Integer> pendientes = controlador.getActividadesPendientes("student1", 1);
        assertEquals(1, pendientes.size());
        assertTrue(pendientes.contains(1));
    }
    
    @Test
    public void testGetActividadesEnviadasLp_NoneSent() {
        controlador.crearRegistroLpEstudiante("student1", learningPath);
        controlador.crearRegistrosActividad("student1", learningPath);

        List<RegistroActividad> enviadas = controlador.getActividadesEnviadasLp(1);
        assertTrue(enviadas.isEmpty());
    }

    @Test
    public void testEditarRespuestasAbiertas() {
        RegistroActividad registroActividad = new RegistroActividad(1, false);
        Actividad actividadWithQuestions = new Actividad(2, "user1");
        actividadWithQuestions.setTipo("Encuesta");
        actividadWithQuestions.setPreguntasAbiertas(List.of(new PreguntaAbierta("Opiniones?")));

        controlador.editarRespuestasAbiertas(registroActividad, actividadWithQuestions);

        assertNotNull(registroActividad.getRespuestas());
        assertTrue(registroActividad.getRespuestas().containsKey("Opiniones?"));
    }

    @Test
    public void testTiempoDedicadoPorActividad() {
        controlador.crearRegistroLpEstudiante("student1", learningPath);
        controlador.crearRegistrosActividad("student1", learningPath);

        RegistroActividad registroActividad = controlador.getRegistroActividad("student1", 1, 1);
        registroActividad.setFechaInicio(LocalDateTime.now().minusHours(1));
        registroActividad.setFechaTerminado(LocalDateTime.now());
        registroActividad.setEstado("Completada");

        float tiempoDedicado = controlador.tiempoDedicadoPorActividad(1);
        assertTrue(tiempoDedicado > 0);
    }

    @Test
    public void testPorcentajeCompletado() {
        controlador.crearRegistroLpEstudiante("student1", learningPath);

        registroLearningPath.setEstado("Completada");
        controlador.getRegistrosLp().put(1, new ArrayList<>(List.of(registroLearningPath)));

        float porcentaje = controlador.porcentajeCompletado(1);
        assertEquals(100.0, porcentaje, 0.01);
    }

    @Test
    public void testPorcentajeCompletadoWithNoCompletions() {
        controlador.crearRegistroLpEstudiante("student1", learningPath);

        registroLearningPath.setEstado("Pending");
        controlador.getRegistrosLp().put(1, new ArrayList<>(List.of(registroLearningPath)));

        float porcentaje = controlador.porcentajeCompletado(1);
        assertEquals(0.0, porcentaje, 0.01);
    }

    @Test
    public void testRevisarEstadoRLP() {
        registroLearningPath.setEstado("Completada");
        assertTrue(controlador.revisarEstadoRLP(registroLearningPath));

        registroLearningPath.setEstado("Pending");
        assertFalse(controlador.revisarEstadoRLP(registroLearningPath));
    }
    
    @Test
    public void testEditarFechaInicio() {
        RegistroActividad registro = new RegistroActividad(1, true);

        controlador.editarFechaInicio(registro);

        assertNotNull(registro.getFechaInicio());
        assertTrue(registro.getFechaInicio().isBefore(LocalDateTime.now()) || 
                   registro.getFechaInicio().isEqual(LocalDateTime.now()));
    }

    @Test
    public void testEditarFechaTerminado() {
        RegistroActividad registro = new RegistroActividad(1, true);

        controlador.editarFechaTerminado(registro);

        assertNotNull(registro.getFechaTerminado());
        assertTrue(registro.getFechaTerminado().isBefore(LocalDateTime.now()) || 
                   registro.getFechaTerminado().isEqual(LocalDateTime.now()));
    }

    @Test
    public void testEditarEstado() {
        RegistroActividad registro = new RegistroActividad(1, true);
        String nuevoEstado = "Completado";

        controlador.editarEstado(registro, nuevoEstado);
        assertNotNull(registro.getEstado());
        assertEquals(nuevoEstado, registro.getEstado());
    }
    
    @Test
    public void testEditarNota() {
        RegistroActividad registro = new RegistroActividad(1, true);
        int nuevaNota = 85;

        controlador.editarNota(registro, nuevaNota);
        assertEquals(nuevaNota, registro.getNota());
    }
}
