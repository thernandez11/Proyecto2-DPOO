package persistencia;


import controladores.ControladorActividad;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import componentes.Actividad;
import componentes.Opcion;
import componentes.Pregunta;
import componentes.PreguntaAbierta;
import componentes.PreguntaMultiple;
import componentes.PreguntaVerdaderoFalso;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;


public class PersistenciaActividades {

    

    private static final String LOGIN_CREADOR = "loginCreador";
    private static final String ID = "id";
    private static final String TIPO = "tipo";
    private static final String DESCRIPCION = "descripcion";
    private static final String OBJETIVOS = "objetivos";
    private static final String NIVEL_DIFICULTAD = "nivelDificultad";
    private static final String DURACION = "duracion";
    private static final String ACTIVIDADES_PREVIAS = "actividadesPrevias";
    private static final String ACTIVIDADES_SIGUIENTES = "actividadesSiguientes";
    private static final String FECHA_LIMITE = "fechaLimite";
    private static final String URL = "url";
    private static final String PREGUNTAS = "preguntas";
    private static final String NOTA_MINIMA = "notaMinima";
    
    public static void cargarActividades(String archivo, ControladorActividad controladorActividad) throws  IOException{
        String jsonCompleto = new String(Files.readAllBytes(new File(archivo).toPath()));
        JSONArray json = new JSONArray(jsonCompleto);
        chargeActivities(controladorActividad, json);
    }
    public static void guardarActividades(String archivo, ControladorActividad controladorActividad) throws  IOException{

        JSONArray jsonArray = new JSONArray();

        saveActivities(controladorActividad, jsonArray);

        PrintWriter pw = new PrintWriter(archivo);
        jsonArray.write(pw, 2, 0);
        pw.close();

    }

    private static void saveActivities(ControladorActividad controladorActividad, JSONArray jArray)

    {
        
        Collection<Actividad> actividades = controladorActividad.getActividades();
        for (Actividad actividad : actividades) {
            JSONObject jActividad = new JSONObject();

            jActividad.put(LOGIN_CREADOR, actividad.getLoginCreador());
            jActividad.put(ID, actividad.getId());
            jActividad.put(TIPO, actividad.getTipo());
            jActividad.put(DESCRIPCION, actividad.getDescripcion());

            JSONArray jObjetivos = new JSONArray();
            for (String objetivo : actividad.getObjetivos()) {
                jObjetivos.put(objetivo);
            }
            jActividad.put(OBJETIVOS, jObjetivos);
            jActividad.put(NIVEL_DIFICULTAD, actividad.getNivelDificultad());
            jActividad.put(DURACION, actividad.getDuracion());

            saveNextActivities(actividad, jActividad);
            savePreviousActivities(actividad, jActividad);
            jActividad.put(FECHA_LIMITE, actividad.getFechaLimite().toString());
            jActividad.put(URL, actividad.getUrl());
            
            JSONArray jPreguntas = new JSONArray();
            for (PreguntaAbierta pregunta : actividad.getPreguntasAbiertas()) {
                JSONObject jPregunta = new JSONObject();
                saveQuestion(pregunta, jPregunta);
                jPreguntas.put(jPregunta);
            }
            for (PreguntaMultiple pregunta : actividad.getPreguntasMultiples()) {
                JSONObject jPregunta = new JSONObject();
                saveQuestion(pregunta, jPregunta);
                jPreguntas.put(jPregunta);
            }
            for (PreguntaVerdaderoFalso pregunta : actividad.getPreguntasVerdaderoFalso()) {
                JSONObject jPregunta = new JSONObject();
                saveQuestion(pregunta, jPregunta);
                jPreguntas.put(jPregunta);
            }
            jActividad.put(PREGUNTAS, jPreguntas);
            jActividad.put(NOTA_MINIMA, actividad.getNotaMinima());
        }

    }

    private static void chargeActivities(ControladorActividad controladorActividad, JSONArray jActividades) throws IOException {

        HashMap<Integer, Actividad> controladorActividades = controladorActividad.actividades;

        HashMap<Integer, List<Integer>> actividadesPrevias = new HashMap<>();
        HashMap<Integer, List<Integer>> actividadesSiguientes = new HashMap<>();
        
        int amountActivities = jActividades.length();
        for (int index = 0; index < amountActivities; index++) {
            JSONObject jActividad = jActividades.getJSONObject(index);
            String loginCreador = jActividad.getString(LOGIN_CREADOR);
            Actividad actividad = new Actividad(Integer.parseInt(ID), loginCreador);
            actividad.setTipo(jActividad.getString(TIPO));
            actividad.setDescripcion(jActividad.getString(DESCRIPCION));
            JSONArray jObjetivos = jActividad.getJSONArray(OBJETIVOS);
            List<String> objetivos = new ArrayList<>();

            for (int i = 0; i < jObjetivos.length(); i++) {
                objetivos.add(jObjetivos.getString(i));
            }
            actividad.setObjetivos(objetivos);
            actividad.setNivelDificultad(jActividad.getString(NIVEL_DIFICULTAD));
            actividad.setDuracion(jActividad.getInt(DURACION));
            actividadesPrevias.put(actividad.getId(), new ArrayList<>());
            actividadesSiguientes.put(actividad.getId(), new ArrayList<>());
            JSONArray jActividadesPrevias = jActividad.getJSONArray(ACTIVIDADES_PREVIAS);
            for (int i = 0; i < jActividadesPrevias.length(); i++) {
                actividadesPrevias.get(actividad.getId()).add(jActividadesPrevias.getInt(i));
            }
            JSONArray jActividadesSiguientes = jActividad.getJSONArray(ACTIVIDADES_SIGUIENTES);
            for (int i = 0; i < jActividadesSiguientes.length(); i++) {
                actividadesSiguientes.get(actividad.getId()).add(jActividadesSiguientes.getInt(i));
            }

            String fechaLimite = jActividad.getString(FECHA_LIMITE);
            actividad.setFechaLimite(LocalDateTime.parse(fechaLimite));
            actividad.setUrl(jActividad.getString(URL));
            chargeQuestion(jActividad.getJSONArray(PREGUNTAS), actividad);
            actividad.setNotaMinima(jActividad.getInt(NOTA_MINIMA));
            controladorActividades.put(actividad.getId(), actividad);
        }

        chargePreviousActivities(actividadesPrevias, controladorActividades);
        chargeNextActivities(actividadesSiguientes, controladorActividades);

    }



    private static  void savePreviousActivities(Actividad actividad, JSONObject jActividad) {
        JSONArray jActividadesPrevias = new JSONArray();
        for (Actividad actividadPrevia : actividad.getActividadesPrevias()) {
            jActividadesPrevias.put(actividadPrevia.getId());
        }
        jActividad.put(ACTIVIDADES_PREVIAS, jActividadesPrevias);
    }

    private static void chargePreviousActivities(Map<Integer, List<Integer>> actividadesPrevias, Map<Integer, Actividad> controladorActividades) {
        
        Set<Integer> ids = actividadesPrevias.keySet();

        for (Integer integer : ids) {

            Actividad actividadPrincipal = controladorActividades.get(integer);
            List<Integer> previous = actividadesPrevias.get(integer);
            List<Actividad> previousActivities = new ArrayList<>();
            for (Integer id : previous) {
                Actividad actividadPrevia = controladorActividades.get(id);
                previousActivities.add(actividadPrevia);
            }
            actividadPrincipal.setActividadesPrevias(previousActivities);
            
        }

    }


    private static void saveNextActivities(Actividad actividad, JSONObject jActividad) {
        JSONArray jActividadesSiguientes = new JSONArray();
        for (Actividad actividadSiguiente : actividad.getActividadesSeguimiento()) {
            jActividadesSiguientes.put(actividadSiguiente.getId());
        }
        jActividad.put(ACTIVIDADES_SIGUIENTES, jActividadesSiguientes);
    }

    private static void chargeNextActivities(Map<Integer, List<Integer>> actividadesSiguientes, Map<Integer, Actividad> controladorActividades) {
        
        Set<Integer> ids = actividadesSiguientes.keySet();

        for (Integer integer : ids) {

            Actividad actividadPrincipal = controladorActividades.get(integer);
            List<Integer> next = actividadesSiguientes.get(integer);
            List<Actividad> nextActivities = new ArrayList<>();
            for (Integer id : next) {
                Actividad actividadSiguiente = controladorActividades.get(id);
                nextActivities.add(actividadSiguiente);
            }
            actividadPrincipal.setActividadesSeguimiento(nextActivities);
            
        }

    }

    private static void saveQuestion(Pregunta pregunta, JSONObject jPregunta){

        jPregunta.put("textoPregunta", pregunta.getTextoPregunta());

        if (pregunta instanceof PreguntaAbierta) {
            jPregunta.put("tipo", "PreguntaAbierta");
        } else if (pregunta instanceof PreguntaMultiple preguntaMultiple) {
            jPregunta.put("tipo", "PreguntaMultiple");
            JSONArray jOpciones = new JSONArray();
            for (Opcion opcion : preguntaMultiple.getOpciones()) {
                JSONObject jOpcion = new JSONObject();
                jOpcion.put("textoOpcion", opcion.getTextoOpcion());
                jOpcion.put("correcta", opcion.getCorrecta());
                jOpcion.put("explicacion", opcion.getExplicacion());
                jOpciones.put(jOpcion);
            }
            jPregunta.put("opciones", jOpciones);
        } else if (pregunta instanceof PreguntaVerdaderoFalso preguntaVerdaderoFalso) {
            jPregunta.put("tipo", "PreguntaVerdaderoFalso");
            JSONArray jOpciones = new JSONArray();
            for (Opcion opcion : preguntaVerdaderoFalso.getOpciones()) {
                JSONObject jOpcion = new JSONObject();
                jOpcion.put("textoOpcion", opcion.getTextoOpcion());
                jOpcion.put("correcta", opcion.getCorrecta());
                jOpcion.put("explicacion", opcion.getExplicacion());
                jOpciones.put(jOpcion);
            }
            jPregunta.put("opciones", jOpciones);
            
        }

    }

    private static void chargeQuestion(JSONArray jPreguntas, Actividad actividad) {
        
        int amountQuestions = jPreguntas.length();
        List<PreguntaMultiple> preguntasMultiples = new ArrayList<>();
        List<PreguntaVerdaderoFalso> preguntasVerdaderoFalso = new ArrayList<>();
        List<PreguntaAbierta> preguntasAbiertas = new ArrayList<>();

        for (int index = 0; index < amountQuestions; index++) {

            JSONObject jPregunta = jPreguntas.getJSONObject(index);
            String tipoPregunta = jPregunta.getString("tipo");

            switch (tipoPregunta) {
                case "PreguntaAbierta" -> {
                    PreguntaAbierta preguntaAbierta = new PreguntaAbierta(jPregunta.getString("textoPregunta"));
                    preguntasAbiertas.add(preguntaAbierta);
                }
                case "PreguntaMultiple" ->{
                        PreguntaMultiple preguntaMultiple = new PreguntaMultiple(jPregunta.getString("textoPregunta"), new ArrayList<Opcion>());
                        JSONArray jOpciones = jPregunta.getJSONArray("opciones");
                        List<Opcion> opciones = new ArrayList<>();
                        for (int i = 0; i < jOpciones.length(); i++) {
                            JSONObject jOpcion = jOpciones.getJSONObject(i);
                            Opcion opcion = new Opcion(jOpcion.getString("textoOpcion"), jOpcion.getString("explicacion"), jOpcion.getBoolean("correcta"));
                            opciones.add(opcion);
                        }       preguntaMultiple.setOpciones(opciones);
                        preguntasMultiples.add(preguntaMultiple);
                    }
                case "PreguntaVerdaderoFalso" ->{
                        PreguntaVerdaderoFalso preguntaVerdaderoFalso = new PreguntaVerdaderoFalso(jPregunta.getString("textoPregunta"), new ArrayList<Opcion>());
                        JSONArray jOpciones = jPregunta.getJSONArray("opciones");
                        List<Opcion> opciones = new ArrayList<>();
                        for (int i = 0; i < jOpciones.length(); i++) {
                            JSONObject jOpcion = jOpciones.getJSONObject(i);
                            Opcion opcion = new Opcion(jOpcion.getString("textoOpcion"), jOpcion.getString("explicacion"), jOpcion.getBoolean("correcta"));
                            opciones.add(opcion);
                        }       preguntaVerdaderoFalso.setOpciones(opciones);
                        preguntasVerdaderoFalso.add(preguntaVerdaderoFalso);
                    }
            }
        }

        actividad.setPreguntasAbiertas(preguntasAbiertas);
        actividad.setPreguntasMultiples(preguntasMultiples);
        actividad.setPreguntasVerdaderoFalso(preguntasVerdaderoFalso);

    }
}