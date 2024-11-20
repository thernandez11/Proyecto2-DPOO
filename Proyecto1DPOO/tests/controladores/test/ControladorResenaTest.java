package controladores.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controladores.ControladorResena;
import componentes.Resena;

import java.io.*;
import java.util.ArrayList;

class ControladorResenaTest {

    private ControladorResena controlador;

    @BeforeEach
    void setUp() {
        controlador = new ControladorResena();
    }

    @AfterEach
    void tearDown() {
        controlador = null; // Reset the controller
        // Cleanup test files
        File file = new File("Persistencia/testResenas.txt");
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testCrearResena() {
        controlador.crearResena(1, "Great activity", 5, "user1", "Student");

        ArrayList<Resena> resenas = controlador.resenasActividad(1);
        assertEquals(1, resenas.size(), "There should be one resena");
        assertEquals("Great activity", resenas.get(0).getOpinion(), "Opinion should match");
    }

    @Test
    void testResenasActividad() {
        controlador.crearResena(1, "Great activity", 5, "user1", "Student");
        controlador.crearResena(2, "Good activity", 4, "user2", "Teacher");

        ArrayList<Resena> resenasActividad1 = controlador.resenasActividad(1);
        ArrayList<Resena> resenasActividad2 = controlador.resenasActividad(2);

        assertEquals(1, resenasActividad1.size(), "Activity 1 should have one resena");
        assertEquals(1, resenasActividad2.size(), "Activity 2 should have one resena");
        assertEquals("Great activity", resenasActividad1.get(0).getOpinion(), "Opinion for Activity 1 should match");
        assertEquals("Good activity", resenasActividad2.get(0).getOpinion(), "Opinion for Activity 2 should match");
    }

    @Test
    void testCalcularRating() {
        controlador.crearResena(1, "Great activity", 5, "user1", "Student");
        controlador.crearResena(1, "Good activity", 4, "user2", "Teacher");

        float rating = controlador.calcularRating(1);

        assertEquals(4.5, rating, 0.01, "Rating should be the average of all resenas for Activity 1");
    }

    @Test
    void testCalcularRatingNoResenas() {
        float rating = controlador.calcularRating(1);

        assertEquals(0, rating, "Rating should be 0 if there are no resenas for the activity");
    }
}
