package persistencia;


import controladores.ControladorActividad;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import componentes.Actividad;
import componentes.Opcion;
import componentes.PreguntaAbierta;
import componentes.PreguntaMultiple;
import componentes.PreguntaVerdaderoFalso;

import java.util.Collection;


public class PersistenciaActividades {

    
    private static final String NOMBRE_ARCHIVO = "actividades.json";

    private static final String LOGIN_CREDOR = "loginCreador";
    private static final String ID = "id";
    private static final String TIPO = "tipo";
    private static final String DESCRIPCION = "descripcion";
    private static final String OBJETIVOS = "objetivos";
    private static final String ACTIVIDADES_PREVIAS = "actividadesPrevias";
    private static final String ACTIVIDADES_SIGUIENTES = "actividadesSiguientes";
    private static final String FECHA_LIMITE = "fechaLimite";
    private static final String URL = "url";
    private static final String PREGUNTAS = "preguntas";
    private static final String NOTA_MINIMA = "notaMinima";
    
    public void cargarActividades(String archivo, ControladorActividad controladorActividad) throws  IOException{
        String jsonCompleto = new String(Files.readAllBytes(new File(archivo).toPath()));
        JSONArray json = new JSONArray(jsonCompleto);
    }
    public void guardarActividades(String archivo, ControladorActividad controladorActividad) throws  IOException{

        JSONArray jsonArray = new JSONArray();

        saveActivities(controladorActividad, jsonArray);

        PrintWriter pw = new PrintWriter(archivo);
        jsonArray.write(pw, 2, 0);
        pw.close();

    }

    private void saveActivities(ControladorActividad controladorActividad, JSONArray jArray)

    {
        
        Collection<Actividad> actividades = controladorActividad.getActividades();
        for (Actividad actividad : actividades) {
            JSONObject jActividad = new JSONObject();

            jActividad.put("loginCreador", actividad.getLoginCreador());
            jActividad.put("id", actividad.getId());
            jActividad.put("tipo", actividad.getTipo());
            jActividad.put("descripcion", actividad.getDescripcion());

            JSONArray jObjetivos = new JSONArray();
            for (String objetivo : actividad.getObjetivos()) {
                jObjetivos.put(objetivo);
            }
            jActividad.put("objetivos", jObjetivos);

            saveNextActivities(actividad, jActividad);
            savePreviousActivities(actividad, jActividad);
            jActividad.put("fechaLimite", actividad.getFechaLimite().toString());
            jActividad.put("url", actividad.getUrl());
            
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
            jActividad.put("preguntas", jPreguntas);
            jActividad.put("notaMinima", actividad.getNotaMinima());
        }

    }

    private void savePreviousActivities(Actividad actividad, JSONObject jActividad) {
        JSONArray jActividadesPrevias = new JSONArray();
        for (Actividad actividadPrevia : actividad.getActividadesPrevias()) {
            jActividadesPrevias.put(actividadPrevia.getId());
        }
        jActividad.put("actividadesPrevias", jActividadesPrevias);
    }

    private void saveNextActivities(Actividad actividad, JSONObject jActividad) {
        JSONArray jActividadesSiguientes = new JSONArray();
        for (Actividad actividadSiguiente : actividad.getActividadesSeguimiento()) {
            jActividadesSiguientes.put(actividadSiguiente.getId());
        }
        jActividad.put("actividadesSiguientes", jActividadesSiguientes);
    }

    private void saveQuestion(Pregunta pregunta, JSONObject jPregunta){

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

}
