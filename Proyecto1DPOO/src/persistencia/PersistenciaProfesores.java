package persistencia;

import controladores.ControladorProfesor;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;

import componentes.Profesor;

public class PersistenciaProfesores {

    private static final String RUTA_ARCHIVO = "profesores.json";

    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";

    public static void cargarProfesores(String RUTA_ARCHIVO, ControladorProfesor controladorProfesor) throws IOException {
        String jsonCompleto  = new String(Files.readAllBytes(new File(RUTA_ARCHIVO).toPath()));
        JSONArray json = new JSONArray(jsonCompleto);
        loadProfesores(controladorProfesor, json);

    }

    public static void guardarProfesores(String RUTA_ARCHIVO, ControladorProfesor controladorProfesor) throws IOException {
        JSONArray json = new JSONArray();
        saveProfesores(controladorProfesor, json);
        PrintWriter pw = new PrintWriter(RUTA_ARCHIVO);
        json.write(pw, 2, 0);
        pw.close();
    }

    private static void saveProfesores(ControladorProfesor controladorProfesor, JSONArray jArrayProfesores) {
        ArrayList<Profesor> profesores = controladorProfesor.getProfesores();
        for (Profesor profesor : profesores) {
            JSONObject jObjectProfesor = new JSONObject();
            jObjectProfesor.put(LOGIN, profesor.getLogin());
            jObjectProfesor.put(PASSWORD, profesor.getPassword());
            jArrayProfesores.put(jObjectProfesor);
        }
    }

    private static void loadProfesores(ControladorProfesor controladorProfesor, JSONArray json) {
        for (int i = 0; i < json.length(); i++) {
            JSONObject jObjectProfesor = json.getJSONObject(i);
            String login = jObjectProfesor.getString(LOGIN);
            String password = jObjectProfesor.getString(PASSWORD);
            controladorProfesor.crearProfesor(login, password);
        }
    }

}
