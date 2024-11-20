package persistencia;

import java.io.File;
import java.nio.file.Files;

import org.json.JSONArray;

import controladores.ControladorLearningPath;
import java.io.IOException;
import java.io.PrintWriter;

public class PersistenciaLearningPaths {

    private static final String PATH = "learningPaths.json";

    private static final String TITULO = "titulo";
    private static final String DESCRIPCION_GENERAL = "descripcionGeneral";
    private static final String NIVEL_DIFICULTAD = "nivelDificultad";
    private static final String DURACION = "duracion";
    private static final String FECHA_CREACION = "fechaCreacion";
    private static final String FECHA_MODIFICACION = "fechaModificacion";
    private static final String VERSION = "version";
    private static final String ACTIVIDADES = "actividades";
    private static final String LOGIN_CREADOR = "loginCreador";
    private static final String ID = "id";

    public void cargarLearningPaths(String path, ControladorLearningPath controlador) throws IOException {
        String jsonCompleto = new String(Files.readAllBytes(new File(path).toPath()));
        JSONArray json = new JSONArray(jsonCompleto);
        loadLearningPath();

    }

    public void guardarLearningPaths(String path, ControladorLearningPath controlador) throws IOException {
        JSONArray json = new JSONArray();
        saveLearningPath();
        PrintWriter pw = new PrintWriter(path);
        json.write(pw, 2, 0);
        pw.close();

        
    }

    private void saveLearningPath() {
        // TODO Auto-generated method stub

    }

    private void loadLearningPath() {
        // TODO Auto-generated method stub

    }
    


}
