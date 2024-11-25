package persistencia;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import componentes.Resena;
import controladores.ControladorResena;

public class PersistenciaResenias {

    private static final String ID_ACTIVIDAD = "idActividad";
    private static final String OPINION = "opinion";
    private static final String RATING = "rating";
    private static final String LOGIN_AUTOR = "loginAutor";
    private static final String ROL_AUTOR = "rolAutor";

    public static void cargarResenias(String RUTA_ARCHIVO, ControladorResena controladorResena) throws IOException {

        String jsonCompleto = new String(Files.readAllBytes(new File(RUTA_ARCHIVO).toPath()));
        // Comentario
        JSONArray json = new JSONArray(jsonCompleto);
        loadResenias(controladorResena, json); 

    }

    public static void guardarResenias(String RUTA_ARCHIVO, ControladorResena controladorResena) throws IOException {

        JSONArray jArrayResenias = new JSONArray();
        saveResenias(controladorResena, jArrayResenias);
        PrintWriter pw = new PrintWriter(RUTA_ARCHIVO);
        jArrayResenias.write(pw, 2, 0);
        pw.close();

    }


    private static void saveResenias(ControladorResena controladorResena, JSONArray jArrayResenias) {
        List<Resena> resenias = controladorResena.getResenas();
        for (Resena resena : resenias) {
            JSONObject jObjectResenia = new JSONObject();
            jObjectResenia.put(ID_ACTIVIDAD, resena.getIdActividad());
            jObjectResenia.put(OPINION, resena.getOpinion());
            jObjectResenia.put(RATING, resena.getRating());
            jObjectResenia.put(LOGIN_AUTOR, resena.getLoginAutor());
            jObjectResenia.put(ROL_AUTOR, resena.getRolAutor());
            jArrayResenias.put(jObjectResenia);
        }
        
    }

    private static void loadResenias(ControladorResena controladorResena, JSONArray json) {
        for (int i = 0; i < json.length(); i++) {
            JSONObject jObjectResenia = json.getJSONObject(i);
            int idActividad = jObjectResenia.getInt(ID_ACTIVIDAD);
            String opinion = jObjectResenia.getString(OPINION);
            int rating = jObjectResenia.getInt(RATING);
            String loginAutor = jObjectResenia.getString(LOGIN_AUTOR);
            String rolAutor = jObjectResenia.getString(ROL_AUTOR);
            controladorResena.crearResena(idActividad, opinion, rating, loginAutor, rolAutor);
        }
    }
}
