package persistencia;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import componentes.Estudiante;
import controladores.ControladorEstudiante;

public class PersistenciaEstudiantes {


    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";

    
    public static  void cargarEstudiantes(String RUTA_ARCHIVO, ControladorEstudiante controladorEstudiante ) throws IOException {
        String jsonCompleto = new String(Files.readAllBytes(new File(RUTA_ARCHIVO).toPath()));

        JSONArray json = new JSONArray(jsonCompleto);
        if (json.length() == 0) {
            return;
        }
        loadEstudiantes(controladorEstudiante, json);
    }

    public static void guardarEstudiantes(String RUTA_ARCHIVO, ControladorEstudiante controladorEstudiante) throws IOException {
        JSONArray jArrayEstudiantes = new JSONArray();
        saveEstudiantes(controladorEstudiante, jArrayEstudiantes);
        PrintWriter pw = new PrintWriter(RUTA_ARCHIVO);
        jArrayEstudiantes.write(pw, 2, 0);
        pw.close();
    }

    private static void loadEstudiantes(ControladorEstudiante controladorEstudiante, JSONArray json) {

        for (int index = 0; index < json.length(); index++) {
            JSONObject jObjectEstudiante = json.getJSONObject(index);
            String login = jObjectEstudiante.getString(LOGIN);
            String password = jObjectEstudiante.getString(PASSWORD);
            controladorEstudiante.crearEstudiante(login, password);
        }

    }

    private static void saveEstudiantes(ControladorEstudiante controladorEstudiante, JSONArray jArrayEstudiantes) {
        List<Estudiante> estudiantes = controladorEstudiante.getEstudiantes();
        for (Estudiante estudiante : estudiantes) {
            JSONObject jObjectEstudiante = new JSONObject();
            jObjectEstudiante.put(LOGIN, estudiante.getLogin());
            jObjectEstudiante.put(PASSWORD, estudiante.getPassword());
            jArrayEstudiantes.put(jObjectEstudiante);
        }
    }


}

