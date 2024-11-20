package persistencia;

import componentes.RegistroActividad;
import controladores.ControladorRegistros;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.time.LocalDateTime;

import org.json.JSONArray;
import org.json.JSONObject;

import componentes.RegistroLearningPath;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class PersistenciaRegistros {


    private static final String NOMBRE_ARCHIVO = "registros.json";
    private static final String FECHA_INICIO = "fechaInicio";
    private static final String FECHA_FIN = "fechaFin";
    private static final String ESTADO = "estado";
    private static final String RESPUESTAS = "respuestas";
    private static final String OBLIGATORIA = "obligatoria";
    private static final String NOTA = "nota";

    private static final String LOGIN_ESTUDIANTE = "loginEstudiante";
    private static final String FECHA_INSCRITO = "fechaInscrito";
    private static final String FECHA_TERMINADO = "fechaTerminado";
    private static final String ESTADO_LP = "estadoLP";
    private static final String REGISTROS_A = "registrosA";

    public void cargarRegistros(String path, ControladorRegistros controlador, ControladorActividad controladorActividades) throws IOException {
        String jsonCompleto = new String(Files.readAllBytes(new File(path).toPath()));
        JSONArray json = new JSONArray(jsonCompleto);
        loadRegistros(controlador, json, controladorActividades);
    }

    public void guardarRegistros(String path, ControladorRegistros controlador) throws IOException {
        JSONArray json = new JSONArray();
        saveRegistros(controlador, json);
        PrintWriter pw = new PrintWriter(path);
        json.write(pw, 2, 0);
        pw.close();
    }

    private void saveRegistros(ControladorRegistros controlador, JSONArray jArrayRegistros) { 
        
        HashMap<Integer, ArrayList<RegistroLearningPath>> registros = controlador.getRegistrosLp();
        Set<Integer> keys = registros.keySet();
        JSONObject jObjectRegistro = new JSONObject();

        for (Integer integer : keys) {
            jObjectRegistro.put("id", integer);
            ArrayList<RegistroLearningPath> registrosLp = registros.get(integer);
            JSONArray jArrayRegistrosLp = new JSONArray();
            saveLPRegisters(registrosLp, jArrayRegistrosLp);
            jObjectRegistro.put("registrosLp", jArrayRegistrosLp);
        }


    }

    private void chargeRegistros(ControladorRegistros controlador, JSONArray jArrayRegistros) {
        for (int i = 0; i < jArrayRegistros.length(); i++) {
            JSONObject jObjectRegistro = jArrayRegistros.getJSONObject(i);
            int id = jObjectRegistro.getInt("id");
            JSONArray jArrayRegistrosLp = jObjectRegistro.getJSONArray("registrosLp");
            ArrayList<RegistroLearningPath> registrosLp = new ArrayList<>();
            chargeLPRegisters(registrosLp, jArrayRegistrosLp);
            controlador.getRegistrosLp().put(id, registrosLp);
        }

    }



    private void saveLPRegisters(ArrayList<RegistroLearningPath> registrosLp, JSONArray jArrayRegistrosLp) {
        JSONObject jObjectRegistroLp = new JSONObject();
        for (RegistroLearningPath registroLearningPath : registrosLp) {
            jObjectRegistroLp.put(LOGIN_ESTUDIANTE, registroLearningPath.getLoginEstudiante());
            jObjectRegistroLp.put(FECHA_INSCRITO, registroLearningPath.getFechaInscrito().toString());
            jObjectRegistroLp.put(FECHA_TERMINADO, registroLearningPath.getFechaTerminado().toString());
            jObjectRegistroLp.put(ESTADO_LP, registroLearningPath.getEstado());
            JSONArray jArrayRegistrosA = new JSONArray();
            saveRegistersA(registroLearningPath.getRegistrosA(), jArrayRegistrosA);
            jObjectRegistroLp.put(REGISTROS_A, jArrayRegistrosA);
            jArrayRegistrosLp.put(jObjectRegistroLp);
        }
    }
    private void chargeLPRegisters(ArrayList<RegistroLearningPath> registrosLp, JSONArray jArrayRegistrosLp) {
        for (int i = 0; i < jArrayRegistrosLp.length(); i++) {
            JSONObject jObjectRegistroLp = jArrayRegistrosLp.getJSONObject(i);
            String loginEstudiante = jObjectRegistroLp.getString(LOGIN_ESTUDIANTE);
            LocalDateTime fechaInscrito = LocalDateTime.parse(jObjectRegistroLp.getString(FECHA_INSCRITO));
            RegistroLearningPath registroLearningPath = new RegistroLearningPath(loginEstudiante, fechaInscrito);

            registrosLp.add(registroLearningPath);

            if (!jObjectRegistroLp.getString(FECHA_TERMINADO).isEmpty()) {
                registroLearningPath.setFechaTerminado(LocalDateTime.parse(jObjectRegistroLp.getString(FECHA_TERMINADO)));
            }

            if (!jObjectRegistroLp.getString(ESTADO_LP).isEmpty()) {
                registroLearningPath.setEstado(jObjectRegistroLp.getString(ESTADO_LP));
            }

            if (!jObjectRegistroLp.getJSONArray(REGISTROS_A).length() == 0) {
                JSONArray jArrayRegistrosA = jObjectRegistroLp.getJSONArray(REGISTROS_A);
                ArrayList<RegistroActividad> registrosA = new ArrayList<>();
                chargeRegistersA(registrosA, jArrayRegistrosA);
                registroLearningPath.setRegistrosA(registrosA);
            }
        }
    }
   


    private void saveRegistersA(ArrayList<RegistroActividad> registrosA, JSONArray jArrayRegistrosA) {
        JSONObject jObjectRegistroA = new JSONObject();
        for (RegistroActividad registroActividad : registrosA) {
            jObjectRegistroA.put(FECHA_INICIO, registroActividad.getFechaInicio().toString());
            jObjectRegistroA.put(FECHA_FIN, registroActividad.getFechaTerminado().toString());
            jObjectRegistroA.put(ESTADO, registroActividad.getEstado());
            jObjectRegistroA.put(OBLIGATORIA, registroActividad.isObligatoria());
            jObjectRegistroA.put(NOTA, registroActividad.getNota());
            JSONObject jObjectRespuestas = new JSONObject();
            saveRespuestas(registroActividad.getRespuestas(), jObjectRespuestas);
            jObjectRegistroA.put(RESPUESTAS, jObjectRespuestas);
            jArrayRegistrosA.put(jObjectRegistroA);
        }
    }

    private void chargeRegistersA(ArrayList<RegistroActividad> registrosA, JSONArray jArrayRegistrosA) {
        for (int i = 0; i < jArrayRegistrosA.length(); i++) {
            JSONObject jObjectRegistroA = jArrayRegistrosA.getJSONObject(i);
            int idActividad = jObjectRegistroA.getInt("idActividad");
            boolean obligatoria = jObjectRegistroA.getBoolean(OBLIGATORIA);
            RegistroActividad registroActividad = new RegistroActividad(idActividad, obligatoria);
            registrosA.add(registroActividad);

            if (!jObjectRegistroA.getString(FECHA_INICIO).isEmpty()) {
                registroActividad.setFechaInicio(LocalDateTime.parse(jObjectRegistroA.getString(FECHA_INICIO)));
            }

            if (!jObjectRegistroA.getString(FECHA_FIN).isEmpty()) {
                registroActividad.setFechaTerminado(LocalDateTime.parse(jObjectRegistroA.getString(FECHA_FIN)));
            }

            if (!jObjectRegistroA.getString(ESTADO).isEmpty()) {
                registroActividad.setEstado(jObjectRegistroA.getString(ESTADO));
            }

            if (!jObjectRegistroA.getString(NOTA).isEmpty()) {
                registroActividad.setNota(jObjectRegistroA.getInt(NOTA));
            }

            if (!jObjectRegistroA.getJSONObject(RESPUESTAS).length() == 0) {
                JSONObject jObjectRespuestas = jObjectRegistroA.getJSONObject(RESPUESTAS);
                HashMap<String, String> respuestas = new HashMap<>();
                chargeRespuestas(respuestas, jObjectRespuestas);
                registroActividad.setRespuestas(respuestas);
            }

            
        }
    }
    
    private void saveRespuestas(HashMap<String, String> respuestas, JSONObject jObjectRespuestas) {

        Set<String> keys = respuestas.keySet();
        for (String key : keys) {
            jObjectRespuestas.put(key, respuestas.get(key));
        }

    }

    private void chargeRespuestas(HashMap<String, String> respuestas, JSONObject jObjectRespuestas) {
        Set<String> keys = jObjectRespuestas.keySet();
        for (String key : keys) {
            respuestas.put(key, jObjectRespuestas.getString(key));
        }
    }




}
