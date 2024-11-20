package controladores.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controladores.ControladorEstudiante;

class ControladorEstudiantePersistenciaTest {

    private ControladorEstudiante controlador;
    private final String testFileName = "testEstudiantes.txt";

    @BeforeEach
    void setUp() {
        // Configuración inicial antes de cada prueba
        controlador = new ControladorEstudiante();
    }

    @AfterEach
    void tearDown() {
        // Limpieza después de cada prueba
        controlador = null;
        File file = new File("Persistencia/" + testFileName);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testGuardarEstudiantesEnArchivo() throws IOException {
        // Arrange: Crear estudiantes
        controlador.crearEstudiante("user1", "password1");
        controlador.crearEstudiante("user2", "password2");

        // Act: Guardar estudiantes en el archivo
        controlador.guardarEstudiantesEnArchivo(testFileName);

        // Assert: Verificar que el archivo existe y tiene contenido
        File file = new File("Persistencia/" + testFileName);
        assertTrue(file.exists(), "El archivo debería existir después de guardar");
        assertTrue(file.length() > 0, "El archivo no debería estar vacío");

        // Verificar el contenido del archivo
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            List<String> lines = reader.lines().toList();
            assertEquals(2, lines.size(), "El archivo debería contener 2 líneas");
            assertEquals("user1,password1", lines.get(0), "La primera línea debería coincidir con el primer estudiante");
            assertEquals("user2,password2", lines.get(1), "La segunda línea debería coincidir con el segundo estudiante");
        }
    }

    @Test
    void testCargarEstudiantesDesdeArchivo_ValidFile() throws IOException {
        // Arrange: Escribir un archivo válido
        File file = new File("Persistencia/" + testFileName);
        file.getParentFile().mkdirs(); // Asegurarse de que el directorio exista
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.println("user1,password1");
            writer.println("user2,password2");
        }

        // Act: Cargar estudiantes desde el archivo
        controlador.cargarEstudiantesDesdeArchivo(testFileName);

        // Assert: Verificar que los estudiantes se cargaron correctamente
        assertTrue(controlador.existeEstudiante("user1"), "user1 debería existir después de cargar");
        assertTrue(controlador.existeEstudiante("user2"), "user2 debería existir después de cargar");
        assertTrue(controlador.ingresoEstudiante("user1", "password1"), "El inicio de sesión para user1 debería ser exitoso");
        assertTrue(controlador.ingresoEstudiante("user2", "password2"), "El inicio de sesión para user2 debería ser exitoso");
    }

    @Test
    void testCargarEstudiantesDesdeArchivo_EmptyFile() throws IOException {
        // Arrange: Crear un archivo vacío
        File file = new File("Persistencia/" + testFileName);
        file.getParentFile().mkdirs();
        file.createNewFile(); // Crear un archivo vacío

        // Act: Cargar estudiantes desde el archivo vacío
        controlador.cargarEstudiantesDesdeArchivo(testFileName);

        // Assert: Verificar que no se cargaron estudiantes
        assertEquals(0, controlador.ingresoEstudiante("user1", "password1"), "No deberían cargarse estudiantes de un archivo vacío");
    }
}