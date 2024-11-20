package controladores.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controladores.ControladorEstudiante;
import componentes.Estudiante;

import java.io.*;
import java.util.Collection;

class ControladorEstudianteTest {

    private ControladorEstudiante controlador;

    @BeforeEach
    void setUp() {
        // Configuración inicial antes de cada prueba
        controlador = new ControladorEstudiante();
    }

    @AfterEach
    void tearDown() {
        // Limpieza después de cada prueba
        controlador = null;
        File file = new File("Persistencia/testEstudiantes.txt");
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testCrearEstudiante() {
        // Prueba para crear un estudiante
        controlador.crearEstudiante("estudiante1", "password123");

        assertTrue(controlador.existeEstudiante("estudiante1"), "El estudiante debería existir después de ser creado");
    }

    @Test
    void testMostrarEstudiantes() {
        // Prueba para mostrar estudiantes
        controlador.crearEstudiante("estudiante1", "password123");
        controlador.crearEstudiante("estudiante2", "password456");

        // Capturar la salida de mostrarEstudiantes
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        controlador.mostrarEstudiantes();

        String output = outContent.toString();
        assertTrue(output.contains("estudiante1"), "La salida debería contener el login 'estudiante1'");
        assertTrue(output.contains("password123"), "La salida debería contener la contraseña 'password123'");
        assertTrue(output.contains("estudiante2"), "La salida debería contener el login 'estudiante2'");
        assertTrue(output.contains("password456"), "La salida debería contener la contraseña 'password456'");
        
        System.setOut(System.out); // Restablecer System.out
    }

    @Test
    void testExisteEstudiante() {
        // Prueba para verificar si un estudiante existe
        controlador.crearEstudiante("estudiante1", "password123");

        assertTrue(controlador.existeEstudiante("estudiante1"), "El estudiante debería existir");
        assertFalse(controlador.existeEstudiante("estudiante2"), "Un estudiante inexistente no debería existir");
    }

    @Test
    void testIngresoEstudiante() {
        // Prueba para el ingreso (login) de un estudiante
        controlador.crearEstudiante("estudiante1", "password123");

        assertTrue(controlador.ingresoEstudiante("estudiante1", "password123"), "El ingreso debería ser exitoso con credenciales correctas");
        assertFalse(controlador.ingresoEstudiante("estudiante1", "wrongPassword"), "El ingreso debería fallar con una contraseña incorrecta");
        assertFalse(controlador.ingresoEstudiante("nonexistent", "password123"), "El ingreso debería fallar para un estudiante inexistente");
    }

    @Test
    void testGuardarYCargarEstudiantes() throws IOException {
        // Prueba para guardar y cargar estudiantes desde un archivo
        String testFileName = "testEstudiantes.txt";

        // Crear y guardar estudiantes
        controlador.crearEstudiante("estudiante1", "password123");
        controlador.crearEstudiante("estudiante2", "password456");
        controlador.guardarEstudiantesEnArchivo(testFileName);

        // Simular la carga en una nueva instancia
        ControladorEstudiante newControlador = new ControladorEstudiante();
        newControlador.cargarEstudiantesDesdeArchivo(testFileName);

        // Verificar que los datos se cargaron correctamente
        assertTrue(newControlador.existeEstudiante("estudiante1"), "El estudiante1 debería existir después de cargar");
        assertTrue(newControlador.existeEstudiante("estudiante2"), "El estudiante2 debería existir después de cargar");

        // Verificar credenciales
        assertTrue(newControlador.ingresoEstudiante("estudiante1", "password123"), "El ingreso debería ser exitoso para el estudiante1");
        assertTrue(newControlador.ingresoEstudiante("estudiante2", "password456"), "El ingreso debería ser exitoso para el estudiante2");
    }
}
