package integracion;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controladores.*;
import componentes.*;

import java.util.*;
import java.time.LocalDateTime;

class HU20X {

    private ControladorProfesor profesorCtrl;
    private ControladorEstudiante estudianteCtrl;
    private ControladorLearningPath lpCtrl;
    private ControladorActividad actividadCtrl;
    private ControladorResena resenaCtrl;
    private ControladorRegistros registroCtrl;

    @BeforeEach
    void setUp() {
        // Configuración inicial antes de cada prueba
        profesorCtrl = new ControladorProfesor();
        estudianteCtrl = new ControladorEstudiante();
        lpCtrl = new ControladorLearningPath();
        actividadCtrl = new ControladorActividad();
        resenaCtrl = new ControladorResena();
        registroCtrl = new ControladorRegistros();
    }
    
    @Test
    void testHU201_ExplorarLearningPaths() {
        // Arrange: Crear Learning Paths
        int lp1 = lpCtrl.crearLearningPath("prof1");
        int lp2 = lpCtrl.crearLearningPath("prof2");

        lpCtrl.editarTitulo(lp1, "Java Básico");
        lpCtrl.editarDescripcionGeneral(lp1, "Introducción a la programación en Java");
        lpCtrl.editarNivelDificultad(lp1, "Principiante");
        lpCtrl.editarDuracion(lp1, 5);

        lpCtrl.editarTitulo(lp2, "Java Avanzado");
        lpCtrl.editarDescripcionGeneral(lp2, "Profundización en conceptos de Java");
        lpCtrl.editarNivelDificultad(lp2, "Avanzado");
        lpCtrl.editarDuracion(lp2, 15);

        // Act: Recuperar y filtrar Learning Paths
        Collection<LearningPath> learningPaths = lpCtrl.getLearningPaths();
        LearningPath selectedLp = lpCtrl.getLearningPath(lp1);

        // Assert: Verificar filtrado y recuperación
        assertEquals(2, learningPaths.size(), "Deberían haber 2 Learning Paths");
        assertNotNull(selectedLp, "El Learning Path seleccionado no debería ser nulo");
        assertEquals("Java Básico", selectedLp.getTitulo(), "El título del Learning Path debería coincidir");
        assertEquals("Principiante", selectedLp.getNivelDificultad(), "El nivel de dificultad debería coincidir");
    }

    @Test
    void testHU202_InscribirseEnLearningPath() {
        // Arrange: Crear un Learning Path
        int lpId = lpCtrl.crearLearningPath("prof1");

        // Añadir actividades al Learning Path
        int actividadId1 = actividadCtrl.crearActividad("prof1");
        actividadCtrl.editarDescripcion(actividadId1, "Introducción a Java");
        actividadCtrl.editarTipo(actividadId1, "Clase");

        int actividadId2 = actividadCtrl.crearActividad("prof1");
        actividadCtrl.editarDescripcion(actividadId2, "Quiz sobre fundamentos de Java");
        actividadCtrl.editarTipo(actividadId2, "Quiz");

        HashMap<Actividad, Boolean> actividades = new HashMap<>();
        actividades.put(actividadCtrl.getActividad(actividadId1), true); // Obligatoria
        actividades.put(actividadCtrl.getActividad(actividadId2), false); // Opcional
        lpCtrl.editarActividades(lpId, actividades);

        // Estudiante se inscribe en el Learning Path
        estudianteCtrl.crearEstudiante("student1", "pass123");
        registroCtrl.crearRegistroLpEstudiante("student1", lpCtrl.getLearningPath(lpId));

        // Act: Recuperar el Learning Path y actividades del estudiante
        RegistroLearningPath registro = registroCtrl.getRegistroLp("student1", lpId);

        // Assert
        assertNotNull(registro, "El estudiante debería estar inscrito en el Learning Path");
        assertEquals(2, lpCtrl.getIdsActividadesLP(lpId).size(), "El Learning Path debería tener 2 actividades");
    }

    @Test
    void testHU203_RealizarActividadesYVerAvance() {
        // Arrange: Crear un Learning Path y inscribir a un estudiante
        int lpId = lpCtrl.crearLearningPath("prof1");
        estudianteCtrl.crearEstudiante("student1", "pass123");
        registroCtrl.crearRegistroLpEstudiante("student1", lpCtrl.getLearningPath(lpId));

        // Añadir una actividad al Learning Path con configuración completa
        int actividadId = actividadCtrl.crearActividad("prof1");
        actividadCtrl.editarDescripcion(actividadId, "Introducción a Java");
        actividadCtrl.editarDuracion(actividadId, 60); // Duración (ejemplo: 60 minutos)
        actividadCtrl.editarNivelDificultad(actividadId, "Principiante"); // Nivel de dificultad
        actividadCtrl.editarObjetivos(actividadId, "Entender los fundamentos de Java,Aprender sintaxis"); // Objetivos
        actividadCtrl.editarNotaMinima(actividadId, 5); // Nota mínima para aprobar
        actividadCtrl.editarTipo(actividadId, "Tarea"); // Tipo de actividad
        
        HashMap<Actividad, Boolean> actividades = new HashMap<>();
        actividades.put(actividadCtrl.getActividad(actividadId), true); // Obligatoria
        lpCtrl.editarActividades(lpId, actividades);

        // Crear registro de actividad
        registroCtrl.crearRegistrosActividad("student1", lpCtrl.getLearningPath(lpId));

        // Act: Marcar actividad como completada y calcular avance
        RegistroActividad registroActividad = registroCtrl.getRegistroActividad("student1", lpId, actividadId);
        registroActividad.setEstado("Completada");
        registroActividad.setFechaInicio(LocalDateTime.now().minusHours(1));
        registroActividad.setFechaTerminado(LocalDateTime.now());

        float progress = registroCtrl.porcentajeCompletado(lpId);

        // Assert
        assertEquals("Completada", registroActividad.getEstado(), "La actividad debería marcarse como completada");
        assertEquals(100.0, progress, 0.01, "El progreso debería ser del 100% tras completar la actividad");
    }
    
    @Test
    void testHU204_RecibirFeedbackQuizzes() {
        // Arrange: Crear una actividad tipo Quiz
        int actividadId = actividadCtrl.crearActividad("prof1");
        actividadCtrl.editarDescripcion(actividadId, "Quiz sobre fundamentos de Java");
        actividadCtrl.editarTipo(actividadId, "Quiz");

        HashMap<String, String> opciones = new HashMap<>();
        opciones.put("A", "Respuesta Correcta");
        opciones.put("B", "Respuesta Incorrecta");
        HashMap<String, HashMap<String, String>> preguntas = new HashMap<>();
        preguntas.put("¿Qué es Java?", opciones);
        actividadCtrl.editarPreguntasMultiples(actividadId, preguntas, List.of(1));

        // Act: Simular que un estudiante realiza el quiz
        RegistroActividad registro = new RegistroActividad(actividadId, false);
        HashMap<String, String> respuestas = new HashMap<>();
        respuestas.put("¿Qué es Java?", "A");
        registro.setRespuestas(respuestas);
        registro.setEstado("Completada");

        // Assert: Validar retroalimentación del quiz
        assertEquals("Completada", registro.getEstado(), "El quiz debería marcarse como completado");
        assertTrue(registro.getRespuestas().containsKey("¿Qué es Java?"), "El quiz debería incluir la pregunta");
        assertEquals("A", registro.getRespuestas().get("¿Qué es Java?"), "La respuesta debería guardarse correctamente");
    }

    @Test
    void testHU205_CalificarYComentarActividades() {
        // Arrange: Crear una actividad y añadir una reseña
        int actividadId = actividadCtrl.crearActividad("prof1");
        actividadCtrl.editarDescripcion(actividadId, "Introducción a Java");
        resenaCtrl.crearResena(actividadId, "¡Excelente actividad!", 5, "student1", "Estudiante");

        // Act: Recuperar reseñas y calcular calificación
        ArrayList<Resena> resenas = resenaCtrl.resenasActividad(actividadId);
        float rating = resenaCtrl.calcularRating(actividadId);

        // Assert
        assertEquals(1, resenas.size(), "Debería haber una reseña");
        assertEquals("¡Excelente actividad!", resenas.get(0).getOpinion(), "El contenido de la reseña debería coincidir");
        assertEquals(5.0, rating, 0.01, "La calificación promedio debería coincidir con la calificación de la reseña");
    }
}
