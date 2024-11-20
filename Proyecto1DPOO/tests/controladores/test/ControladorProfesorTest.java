package controladores.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controladores.ControladorProfesor;
import componentes.Profesor;

import java.io.*;
import java.util.Collection;

class ControladorProfesorTest {

    private ControladorProfesor controlador;

    @BeforeEach
    void setUp() {
        // Configuración inicial antes de cada prueba
        controlador = new ControladorProfesor();
    }

    @AfterEach
    void tearDown() {
        // Limpieza después de cada prueba
        controlador = null;
        File file = new File("Persistencia/testProfesores.txt");
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testCrearProfesor() {
        // Prueba para crear un profesor
        controlador.crearProfesor("profesor1", "password123");

        assertTrue(controlador.existeProfesor("profesor1"), "El profesor debería existir después de ser creado");
    }

    @Test
    void testMostrarProfesores() {
        // Prueba para mostrar profesores
        controlador.crearProfesor("profesor1", "password123");
        controlador.crearProfesor("profesor2", "password456");

        // Capturar la salida de mostrarProfesores
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        controlador.mostrarProfesores();

        String output = outContent.toString();
        assertTrue(output.contains("profesor1"), "La salida debería contener el login 'profesor1'");
        assertTrue(output.contains("password123"), "La salida debería contener la contraseña 'password123'");
        assertTrue(output.contains("profesor2"), "La salida debería contener el login 'profesor2'");
        assertTrue(output.contains("password456"), "La salida debería contener la contraseña 'password456'");
        
        System.setOut(System.out); // Restablecer System.out
    }

    @Test
    void testExisteProfesor() {
        // Prueba para verificar si un profesor existe
        controlador.crearProfesor("profesor1", "password123");

        assertTrue(controlador.existeProfesor("profesor1"), "El profesor debería existir");
        assertFalse(controlador.existeProfesor("profesor2"), "Un profesor inexistente no debería existir");
    }

    @Test
    void testIngresoProfesor() {
        // Prueba para el ingreso (login) de un profesor
        controlador.crearProfesor("profesor1", "password123");

        assertTrue(controlador.ingresoProfesor("profesor1", "password123"), "El ingreso debería ser exitoso con credenciales correctas");
        assertFalse(controlador.ingresoProfesor("profesor1", "wrongPassword"), "El ingreso debería fallar con una contraseña incorrecta");
        assertFalse(controlador.ingresoProfesor("nonexistent", "password123"), "El ingreso debería fallar para un profesor inexistente");
    }

    @Test
    void testGuardarYCargarProfesores() throws IOException {
        // Prueba para guardar y cargar profesores desde un archivo
        String testFileName = "testProfesores.txt";

        // Crear y guardar profesores
        controlador.crearProfesor("profesor1", "password123");
        controlador.crearProfesor("profesor2", "password456");
        controlador.guardarProfesoresEnArchivo(testFileName);

        // Simular la carga en una nueva instancia
        ControladorProfesor newControlador = new ControladorProfesor();
        newControlador.cargarProfesoresDesdeArchivo(testFileName);

        // Verificar que los datos se cargaron correctamente
        assertTrue(newControlador.existeProfesor("profesor1"), "El profesor1 debería existir después de cargar");
        assertTrue(newControlador.existeProfesor("profesor2"), "El profesor2 debería existir después de cargar");

        // Verificar credenciales
        assertTrue(newControlador.ingresoProfesor("profesor1", "password123"), "El ingreso debería ser exitoso para el profesor1");
        assertTrue(newControlador.ingresoProfesor("profesor2", "password456"), "El ingreso debería ser exitoso para el profesor2");
    }
}
