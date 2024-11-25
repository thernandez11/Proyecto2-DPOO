package integracion;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controladores.*;
import componentes.*;

import java.util.*;

class HU10X {

    private ControladorProfesor profesorCtrl;
    private ControladorEstudiante estudianteCtrl;
    private ControladorLearningPath lpCtrl;
    private ControladorActividad actividadCtrl;
    private ControladorResena resenaCtrl;

    @BeforeEach
    void setUp() {
        // Configuración inicial antes de cada prueba
        profesorCtrl = new ControladorProfesor();
        estudianteCtrl = new ControladorEstudiante();
        actividadCtrl = new ControladorActividad();
        lpCtrl = new ControladorLearningPath(actividadCtrl);
        
        resenaCtrl = new ControladorResena();
    }

    @Test
    void testProfessorCreatesLearningPathAndStudentInteracts() {
        // Paso 1: El profesor crea una cuenta
        profesorCtrl.crearProfesor("prof1", "securePass");
        assertTrue(profesorCtrl.existeProfesor("prof1"), "El profesor debería existir");

        // Paso 2: El profesor inicia sesión y crea un LearningPath
        assertTrue(profesorCtrl.ingresoProfesor("prof1", "securePass"), "El inicio de sesión del profesor debería tener éxito");
        int lpId = lpCtrl.crearLearningPath("prof1");
        assertNotNull(lpCtrl.getLearningPath(lpId), "El LearningPath debería ser creado");

        // Paso 3: El profesor añade actividades al LearningPath
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

        assertEquals(2, lpCtrl.getIdsActividadesLP(lpId).size(), "El LearningPath debería tener 2 actividades");

        // Paso 4: El estudiante crea una cuenta y se inscribe en el LearningPath
        estudianteCtrl.crearEstudiante("student1", "studentPass");
        assertTrue(estudianteCtrl.ingresoEstudiante("student1", "studentPass"), "El inicio de sesión del estudiante debería tener éxito");

        ControladorRegistros registroCtrl = new ControladorRegistros();
        registroCtrl.crearRegistroLpEstudiante("student1", lpCtrl.getLearningPath(lpId));

        assertNotNull(registroCtrl.getRegistroLp("student1", lpId), "El estudiante debería estar inscrito en el LearningPath");

        // Paso 5: El estudiante interactúa con el LearningPath (completa actividades)
        RegistroActividad registroActividad1 = new RegistroActividad(actividadId1, false);
        registroActividad1.setEstado("Completada");
        registroCtrl.editarEstado(registroActividad1, "Completada");

        assertEquals("Completada", registroActividad1.getEstado(), "La actividad 1 debería marcarse como completada");

        // Paso 6: El estudiante deja una reseña
        resenaCtrl.crearResena(actividadId1, "¡Excelente clase!", 5, "student1", "Estudiante");
        ArrayList<Resena> resenas = resenaCtrl.resenasActividad(actividadId1);
        assertEquals(1, resenas.size(), "Debería haber una reseña");
        assertEquals("¡Excelente clase!", resenas.get(0).getOpinion(), "El contenido de la reseña debería coincidir");
        assertEquals(5, resenaCtrl.calcularRating(actividadId1), "La calificación debería calcularse correctamente");
    }
}
