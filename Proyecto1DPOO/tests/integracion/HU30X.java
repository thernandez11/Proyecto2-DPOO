package integracion;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controladores.*;
import componentes.*;

import java.time.LocalDateTime;
import java.util.*;

class HU30X { 

    private ControladorProfesor profesorCtrl;
    private ControladorEstudiante estudianteCtrl;
    private ControladorLearningPath lpCtrl;
    private ControladorActividad actividadCtrl;
    private ControladorRegistros registroCtrl;

    private int lpId;
    private int actividadId1;
    private int actividadId2;

    @BeforeEach
    void setUp() {
        // Configuración inicial antes de cada prueba
        profesorCtrl = new ControladorProfesor();
        estudianteCtrl = new ControladorEstudiante();
        lpCtrl = new ControladorLearningPath();
        actividadCtrl = new ControladorActividad();
        registroCtrl = new ControladorRegistros();

        // Crear Learning Path y actividades
        lpId = lpCtrl.crearLearningPath("prof1");
        actividadId1 = actividadCtrl.crearActividad("prof1");
        actividadId2 = actividadCtrl.crearActividad("prof1");

        actividadCtrl.editarTipo(actividadId1, "Tarea");
        actividadCtrl.editarTipo(actividadId2, "Quiz");

        HashMap<Actividad, Boolean> actividades = new HashMap<>();
        actividades.put(actividadCtrl.getActividad(actividadId1), true); // Obligatoria
        actividades.put(actividadCtrl.getActividad(actividadId2), true); // Obligatoria
        lpCtrl.editarActividades(lpId, actividades);

        // Inscribir un estudiante y registrar actividades
        estudianteCtrl.crearEstudiante("student1", "pass123");
        registroCtrl.crearRegistroLpEstudiante("student1", lpCtrl.getLearningPath(lpId));
        registroCtrl.crearRegistrosActividad("student1", lpCtrl.getLearningPath(lpId));
    }

    @Test
    void testHU301_MonitorStudentProgress() {
        // Actuar: Actualizar estados de actividades
        RegistroActividad registro1 = registroCtrl.getRegistroActividad("student1", lpId, actividadId1);
        registro1.setEstado("Completada");
        registro1.setFechaInicio(LocalDateTime.now().minusDays(1));
        registro1.setFechaTerminado(LocalDateTime.now());

        RegistroActividad registro2 = registroCtrl.getRegistroActividad("student1", lpId, actividadId2);
        registro2.setEstado("En progreso");

        float progress = registroCtrl.porcentajeCompletado(lpId);

        // Afirmar: Validar progreso y detalles de actividades
        assertEquals(50.0, progress, 0.01, "El progreso debería ser del 50% después de completar una actividad");
        assertEquals("Completada", registro1.getEstado(), "La primera actividad debería marcarse como completada");
        assertEquals("En progreso", registro2.getEstado(), "La segunda actividad debería estar en progreso");
    }

    @Test
    void testHU302_EvaluateAndGradeSubmissions() {
        // Actuar: Calificar y proporcionar retroalimentación
        RegistroActividad registro = registroCtrl.getRegistroActividad("student1", lpId, actividadId1);
        registro.setEstado("Enviado");
        registro.setNota(85); // Calificación
        registroCtrl.editarEstado(registro, "Aprobado");

        // Afirmar: Validar estado y retroalimentación
        assertEquals("Aprobado", registro.getEstado(), "La actividad debería marcarse como aprobada");
        assertEquals(85, registro.getNota(), "La calificación debería coincidir con el valor asignado");
    }
}
