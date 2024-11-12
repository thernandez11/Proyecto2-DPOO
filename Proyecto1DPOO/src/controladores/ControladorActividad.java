package controladores;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Arrays;
import java.util.Collection;

import componentes.Actividad;
import componentes.Opcion;
import componentes.PreguntaAbierta;
import componentes.PreguntaMultiple;
import componentes.PreguntaVerdaderoFalso;

public class ControladorActividad {
	
	HashMap<Integer, Actividad> actividades;
	
	public ControladorActividad() {
	    this.actividades = new HashMap<>();
	}
	
	//Consultar informacion actividades
	public Actividad getActividad(int id) {
		return actividades.get(id);
	}
	public Collection<Actividad> getActividades() {
		Collection<Actividad> as = actividades.values();
		return as;
	}
	public List<Actividad> getActividadesIds(List<Integer> ids) {
		ArrayList<Actividad> actividadesLista = new ArrayList<>();
		for (int id : ids) {
			actividadesLista.add(getActividad(id));
		}
		return actividadesLista;
	}
	public List<PreguntaAbierta> getPreguntasAbiertas(Actividad a) {
		return a.getPreguntasAbiertas();
	}
	public List<PreguntaMultiple> getPreguntasMultiples(Actividad a) {
		return a.getPreguntasMultiples();
	}
	public int getNotaMinima(Actividad a) {
		return a.getNotaMinima();
	}
	
	//Creacion Actividades
	public int crearActividad(String loginActual) {
		int id = actividades.size() + 1;
		Actividad a = new Actividad(id, loginActual);
		actividades.put(id, a);
		return id;
	}
	
	//Edicion Actividades
	public void editarTipo(int id, String tipo) {
		Actividad a = actividades.get(id);
		a.setTipo(tipo);
	}
	public void editarDescripcion(int id, String descripcion) {
		Actividad a = actividades.get(id);
		a.setDescripcion(descripcion);
	}
	public void editarObjetivos(int id, String stringObjetivos) {
		Actividad a = actividades.get(id);
		List<String> objetivos = Arrays.asList(stringObjetivos.split(","));
		a.setObjetivos(objetivos);
	}
	public void editarNivelDificultad(int id, String nivelDificultad) {
		Actividad a = actividades.get(id);
		a.setNivelDificultad(nivelDificultad);
	}
	public void editarDuracion(int id, int duracion) {
		Actividad a = actividades.get(id);
		a.setDuracion(duracion);
	}
	public void editarActividadesPrevias(int id, List<Integer> idActividades) {
		Actividad a = actividades.get(id);
		List<Actividad> actividadesPrevias = getActividadesIds(idActividades);
		a.setActividadesPrevias(actividadesPrevias);
	}
	public void editarActividadesSeguimiento(int id, List<Integer> idActividades) {
		Actividad a = actividades.get(id);
		List<Actividad> actividadesSeguimiento = getActividadesIds(idActividades);
		a.setActividadesSeguimiento(actividadesSeguimiento);
	}
	public void editarFechaLimite(int id, String stringFecha) {
		Actividad a = actividades.get(id);
		LocalDateTime fecha = LocalDateTime.parse(stringFecha);
		a.setFechaLimite(fecha);
	}
	public void editarURL(int id, String url) {
		Actividad a = actividades.get(id);
		a.setUrl(url);
	}
	public void editarPreguntasMultiples(int id, HashMap<String, HashMap<String, String>> preguntas, List<Integer> correctas) {
		Actividad a = actividades.get(id);
		List<PreguntaMultiple> preguntasMultiples = crearPreguntasMultiples(preguntas, correctas);
		a.setPreguntasMultiples(preguntasMultiples);
	}
	public void editarPreguntasVerdaderoFalso(int id, HashMap<String, HashMap<String, String>> preguntas, List<Integer> correctas) {
		Actividad a = actividades.get(id);
		List<PreguntaVerdaderoFalso> preguntasVerdaderoFalso = crearPreguntasVerdaderoFalso(preguntas, correctas);
		a.setPreguntasVerdaderoFalso(preguntasVerdaderoFalso);
	}
	public void editarPreguntasAbiertas(int id, List<String> preguntas) {
		Actividad a = actividades.get(id);
		List<PreguntaAbierta> preguntasAbiertas = crearPreguntasAbiertas(preguntas);
		a.setPreguntasAbiertas(preguntasAbiertas);
	}
	public void editarNotaMinima(int id, int nota) {
		Actividad a = actividades.get(id);
		a.setNotaMinima(nota);
	}
	
	//Creacion Preguntas
	private List<PreguntaAbierta> crearPreguntasAbiertas(List<String> preguntas) {
		ArrayList<PreguntaAbierta> preguntasAbiertas = new ArrayList<>();
		for (String pregunta : preguntas) {
			PreguntaAbierta preguntaAbierta = new PreguntaAbierta(pregunta);
			preguntasAbiertas.add(preguntaAbierta);
		}
		return preguntasAbiertas;
	}
	private List<PreguntaMultiple> crearPreguntasMultiples(HashMap<String, HashMap<String, String>> preguntas, List<Integer> correctas) {
		ArrayList<PreguntaMultiple> preguntasMultiples = new ArrayList<>();
		Set<String> stringPreguntas = preguntas.keySet();
		for (String pregunta : stringPreguntas) {
			Set<String> stringOpciones = preguntas.get(pregunta).keySet();
			ArrayList<Opcion> opciones = new ArrayList<>();
			for (String opcion : stringOpciones) {
				Opcion opc;
				if (correctas.get(preguntasMultiples.size()) == opciones.size() + 1) {
					opc = new Opcion(opcion, preguntas.get(pregunta).get(opcion), true);
				} else {
					opc = new Opcion(opcion, preguntas.get(pregunta).get(opcion), false);
				}
				opciones.add(opc);
			}
			PreguntaMultiple preguntaMultiple = new PreguntaMultiple(pregunta, opciones);
			preguntasMultiples.add(preguntaMultiple);
		}
		return preguntasMultiples;
	}
	private List<PreguntaVerdaderoFalso> crearPreguntasVerdaderoFalso(HashMap<String, HashMap<String, String>> preguntas, List<Integer> correctas) {
		ArrayList<PreguntaVerdaderoFalso> preguntasVerdaderoFalso = new ArrayList<>();
		Set<String> stringPreguntas = preguntas.keySet();
		for (String pregunta : stringPreguntas) {
			Set<String> stringOpciones = preguntas.get(pregunta).keySet();
			ArrayList<Opcion> opciones = new ArrayList<>();
			for (String opcion : stringOpciones) {
				Opcion opc;
				if (correctas.get(preguntasVerdaderoFalso.size()) == opciones.size() + 1) {
					opc = new Opcion(opcion, preguntas.get(pregunta).get(opcion), true);
				} else {
					opc = new Opcion(opcion, preguntas.get(pregunta).get(opcion), false);
				}
				opciones.add(opc);
			}
			PreguntaVerdaderoFalso preguntaVerdaderoFalso = new PreguntaVerdaderoFalso(pregunta, opciones);
			preguntasVerdaderoFalso.add(preguntaVerdaderoFalso);
		}
		return preguntasVerdaderoFalso;
	}
	
}
