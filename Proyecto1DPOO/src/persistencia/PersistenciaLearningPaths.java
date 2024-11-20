package persistencia;

import componentes.Actividad;
import componentes.LearningPath;
import controladores.ControladorActividad;
import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.json.JSONArray;
import org.json.JSONObject;

import controladores.ControladorLearningPath;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

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

    public void cargarLearningPaths(String path, ControladorLearningPath controlador, ControladorActividad controladorActividades) throws IOException {
        String jsonCompleto = new String(Files.readAllBytes(new File(path).toPath()));
        JSONArray json = new JSONArray(jsonCompleto);
        loadLearningPaths(controlador, json, controladorActividades);
    }

    public void guardarLearningPaths(String path, ControladorLearningPath controlador,  ControladorActividad controladorActividades) throws IOException {
        JSONArray json = new JSONArray();
        saveLearningPaths(controlador, json);
        PrintWriter pw = new PrintWriter(path);
        json.write(pw, 2, 0);
        pw.close();
    }

    private void saveLearningPaths(ControladorLearningPath controlador, JSONArray jArrayLearningPaths) {
        Collection<LearningPath> learningPaths = controlador.getLearningPaths();
        for (LearningPath learningPath : learningPaths) {
            JSONObject jLearningPath = new JSONObject();
            saveLP(learningPath, jLearningPath);
            jArrayLearningPaths.put(jLearningPath);
        }

    }

    private List<LearningPath> loadLearningPaths(ControladorLearningPath controlador, JSONArray jArrayLearningPaths, ControladorActividad controladorActividades) {
        // TODO Auto-generated method stub

        List<LearningPath> learningPaths = new ArrayList<>();

        for (int i = 0; i < jArrayLearningPaths.length(); i++) {
            JSONObject jLearningPath = jArrayLearningPaths.getJSONObject(i);
            LearningPath learningPath = loadLP(jLearningPath, controladorActividades);
            learningPaths.add(learningPath);
        }

        return learningPaths;
    }

    private void saveLP(LearningPath learningPath, JSONObject jLearningPath) {
        jLearningPath.put(TITULO, learningPath.getTitulo());
        jLearningPath.put(DESCRIPCION_GENERAL, learningPath.getDescripcionGeneral());
        jLearningPath.put(NIVEL_DIFICULTAD, learningPath.getNivelDificultad());
        jLearningPath.put(DURACION, learningPath.getDuracion());
        jLearningPath.put(FECHA_CREACION, learningPath.getFechaCreacion().toString());
        jLearningPath.put(FECHA_MODIFICACION, learningPath.getFechaModificacion().toString());
        jLearningPath.put(VERSION, learningPath.getVersion());
        JSONArray jArrayActivities = new JSONArray();
        saveLPActivities(learningPath, jArrayActivities);
        jLearningPath.put(ACTIVIDADES, jArrayActivities);
        jLearningPath.put(LOGIN_CREADOR, learningPath.getLoginCreador());
        jLearningPath.put(ID, learningPath.getId());

    }

    private LearningPath loadLP(JSONObject jLearningPath, ControladorActividad controladorActividad) {
        LocalDateTime fechaCreacion = LocalDateTime.parse(jLearningPath.getString(FECHA_CREACION));
        LocalDateTime fechaModificacion = LocalDateTime.parse(jLearningPath.getString(FECHA_MODIFICACION));
        LearningPath learningPath = new LearningPath(jLearningPath.getInt(ID), jLearningPath.getString(LOGIN_CREADOR), jLearningPath.getInt(VERSION), fechaCreacion, fechaModificacion);

        learningPath.setTitulo(jLearningPath.getString(TITULO));
        learningPath.setDescripcionGeneral(jLearningPath.getString(DESCRIPCION_GENERAL));
        learningPath.setNivelDificultad(jLearningPath.getString(NIVEL_DIFICULTAD));
        learningPath.setDuracion(jLearningPath.getInt(DURACION));
        loadLPActivities(learningPath, jLearningPath.getJSONArray(ACTIVIDADES), controladorActividad);
        return learningPath;
    }

    private void saveLPActivities(LearningPath learningPath, JSONArray jArrayActivities) {
        Set<Entry<Actividad, Boolean>> activities = learningPath.getActividades().entrySet();
        JSONObject jActivity = new JSONObject();
        for (Entry<Actividad, Boolean> entry : activities) {
            jActivity.put("id", entry.getKey().getId());
            jActivity.put("obligatoria", entry.getValue());
            jArrayActivities.put(jActivity);
        }
    }

    private void loadLPActivities(LearningPath learningPath, JSONArray jArrayActivities, ControladorActividad controladorActividad) {

        HashMap<Actividad, Boolean> actividades = new HashMap<>();

        for (int i = 0; i < jArrayActivities.length(); i++) {
            JSONObject jActivity = jArrayActivities.getJSONObject(i);
            Actividad actividad = controladorActividad.getActividad(jActivity.getInt("id"));
            boolean obligatoria = jActivity.getBoolean("obligatoria");
            actividades.put(actividad, obligatoria);
        }

        learningPath.setActividades(actividades);
    }
    


}
