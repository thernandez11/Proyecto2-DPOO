package controladores;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import componentes.Actividad;
import componentes.Opcion;
import componentes.PreguntaAbierta;
import componentes.PreguntaMultiple;

public class ControladorActividad {
	
	HashMap<Integer, Actividad> actividades;
	
	public ControladorActividad() {
	    this.actividades = new HashMap<>();
	}
	
	public void imprimirActividades() {
		Set<Integer> ids = actividades.keySet();
		System.out.println("\nEstas son las actividades disponibles (El numero a su lado corresponde a su id).");
		for (int id : ids) {
			System.out.printf("%d.", id);
			System.out.printf("\n Descripcion: %s.", actividades.get(id).getDescripcion());
			System.out.printf("\n Creador: %s.", actividades.get(id).getLoginCreador());
			System.out.printf("\n Tipo: %s.", actividades.get(id).getTipo());
			System.out.printf("\n Nivel de dificultad: %s.", actividades.get(id).getNivelDificultad());
			System.out.printf("\n Duracion en minutos: %s.\n", actividades.get(id).getDuracion());
		}
	}
	
	public void imprimirActividades(ArrayList<Integer> ids) {
		System.out.println("\nEstas son las actividades disponibles en el learning path (El numero a su lado corresponde a su id).");
		for (int id : ids) {
			System.out.printf("%d.", id);
			System.out.printf("\n Descripcion: %s.", actividades.get(id).getDescripcion());
			System.out.printf("\n Creador: %s.", actividades.get(id).getLoginCreador());
			System.out.printf("\n Tipo: %s.", actividades.get(id).getTipo());
			System.out.printf("\n Nivel de dificultad: %s.", actividades.get(id).getNivelDificultad());
			System.out.printf("\n Duracion en minutos: %s.\n", actividades.get(id).getDuracion());
		}
	}
	
	public Actividad getActividad(int id) {
		return actividades.get(id);
	}
	
	public List<PreguntaAbierta> getPreguntasAbiertas(Actividad a) {
		return a.getPreguntasAbiertas();
	}
	
	public List<PreguntaMultiple> getPreguntasMultiples(Actividad a) {
		return a.getPreguntasMultiples();
	}
	
	public boolean verificarCreador(int id, String loginActual) {
		if (actividades.get(id).getLoginCreador().equals(loginActual)) {
			return true;
		}
		return false;
	}
	
	public List<Actividad> getActividades(List<Integer> ids) {
		ArrayList<Actividad> actividadesLista = new ArrayList<>();
		for (int id : ids) {
			actividadesLista.add(getActividad(id));
		}
		return actividadesLista;
	}
	
	//Creacion Actividades
	public void crearActividadExamen(String loginCreador, String tipo, String descripcion, String stringObjetivos,
			String nivelDificultad, int duracion, List<Integer> idActividadesPrevias, String fechaLimite,
			List<Integer> idActividadesSeguimiento, List<String> preguntas, float notaMinima) {
		int id = actividades.size() + 1;
		String[] objetivos = stringObjetivos.split(",");
		List<Actividad> actividadesPrevias = getActividades(idActividadesPrevias);
		List<Actividad> actividadesSeguimiento = getActividades(idActividadesSeguimiento);
		List<PreguntaAbierta> preguntasAbiertas = crearPreguntas(preguntas);
		Actividad actividad = new Actividad(loginCreador, id, tipo, descripcion, objetivos, nivelDificultad, duracion, 
				actividadesPrevias, fechaLimite, actividadesSeguimiento, null, null, preguntasAbiertas, notaMinima);
		actividades.put(id, actividad);
	}
	private List<PreguntaAbierta> crearPreguntas(List<String> preguntas) {
		ArrayList<PreguntaAbierta> preguntasAbiertas = new ArrayList<>();
		for (String pregunta : preguntas) {
			PreguntaAbierta preguntaAbierta = new PreguntaAbierta(pregunta);
			preguntasAbiertas.add(preguntaAbierta);
		}
		return preguntasAbiertas;
	}

	public void crearActividadQuiz(String loginCreador, String tipo, String descripcion, String stringObjetivos,
			String nivelDificultad, int duracion, List<Integer> idActividadesPrevias, String fechaLimite,
			List<Integer> idActividadesSeguimiento, HashMap<String, HashMap<String, String>> preguntas, 
			List<Integer> correctas, float notaMinima) {
		int id = actividades.size() + 1;
		String[] objetivos = stringObjetivos.split(",");
		List<Actividad> actividadesPrevias = getActividades(idActividadesPrevias);
		List<Actividad> actividadesSeguimiento = getActividades(idActividadesSeguimiento);
		List<PreguntaMultiple> preguntasMultiples = crearPreguntasMultiples(preguntas, correctas);
		Actividad actividad = new Actividad(loginCreador, id, tipo, descripcion, objetivos, nivelDificultad, duracion,
				actividadesPrevias, fechaLimite, actividadesSeguimiento, null, preguntasMultiples, null, notaMinima);
		actividades.put(id, actividad);
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

	public void crearActividadEncuesta(String loginCreador, String tipo, String descripcion, String stringObjetivos,
			String nivelDificultad, int duracion, List<Integer> idActividadesPrevias, String fechaLimite,
			List<Integer> idActividadesSeguimiento, List<String> preguntas) {
		int id = actividades.size() + 1;
		String[] objetivos = stringObjetivos.split(",");
		List<Actividad> actividadesPrevias = getActividades(idActividadesPrevias);
		List<Actividad> actividadesSeguimiento = getActividades(idActividadesSeguimiento);
		List<PreguntaAbierta> preguntasAbiertas = crearPreguntas(preguntas);
		Actividad actividad = new Actividad(loginCreador, id, tipo, descripcion, objetivos, nivelDificultad, duracion, 
				actividadesPrevias, fechaLimite, actividadesSeguimiento, null, null, preguntasAbiertas, 0);
		actividades.put(id, actividad);
	}
	
	public void crearActividadRecurso(String loginCreador, String tipo, String descripcion, String stringObjetivos,
			String nivelDificultad, int duracion, List<Integer> idActividadesPrevias, String fechaLimite,
			List<Integer> idActividadesSeguimiento, String url) {
		
		int id = actividades.size() + 1;
		String[] objetivos = stringObjetivos.split(",");
		List<Actividad> actividadesPrevias = getActividades(idActividadesPrevias);
		List<Actividad> actividadesSeguimiento = getActividades(idActividadesSeguimiento);
		Actividad actividad = new Actividad(loginCreador, id, tipo, descripcion, objetivos, nivelDificultad, duracion, 
				actividadesPrevias, fechaLimite, actividadesSeguimiento, url, null, null, 0);
		actividades.put(id, actividad);
	}
	
	public void crearActividadTarea(String loginCreador, String tipo, String descripcion, String stringObjetivos,
			String nivelDificultad, int duracion, List<Integer> idActividadesPrevias, String fechaLimite,
			List<Integer> idActividadesSeguimiento) {
		int id = actividades.size() + 1;
		String[] objetivos = stringObjetivos.split(",");
		List<Actividad> actividadesPrevias = getActividades(idActividadesPrevias);
		List<Actividad> actividadesSeguimiento = getActividades(idActividadesSeguimiento);
		Actividad actividad = new Actividad(loginCreador, id, tipo, descripcion, objetivos, nivelDificultad, duracion, 
				actividadesPrevias, fechaLimite, actividadesSeguimiento, null, null, null, 0);
		actividades.put(id, actividad);
	}
	
	//Edicion Actividades
	public void crearActividadExamen(String loginCreador, String tipo, String descripcion, String stringObjetivos,
			String nivelDificultad, int duracion, List<Integer> idActividadesPrevias, String fechaLimite,
			List<Integer> idActividadesSeguimiento, List<String> preguntas, float notaMinima, int idActividad) {
		int id = idActividad;
		String[] objetivos = stringObjetivos.split(",");
		List<Actividad> actividadesPrevias = getActividades(idActividadesPrevias);
		List<Actividad> actividadesSeguimiento = getActividades(idActividadesSeguimiento);
		List<PreguntaAbierta> preguntasAbiertas = crearPreguntas(preguntas);
		Actividad actividad = new Actividad(loginCreador, id, tipo, descripcion, objetivos, nivelDificultad, duracion, 
				actividadesPrevias, fechaLimite, actividadesSeguimiento, null, null, preguntasAbiertas, notaMinima);
		actividades.put(id, actividad);
	}

	public void crearActividadQuiz(String loginCreador, String tipo, String descripcion, String stringObjetivos,
			String nivelDificultad, int duracion, List<Integer> idActividadesPrevias, String fechaLimite,
			List<Integer> idActividadesSeguimiento, HashMap<String, HashMap<String, String>> preguntas,
			List<Integer> correctas, float notaMinima, int idActividad) {
		int id = idActividad;
		String[] objetivos = stringObjetivos.split(",");
		List<Actividad> actividadesPrevias = getActividades(idActividadesPrevias);
		List<Actividad> actividadesSeguimiento = getActividades(idActividadesSeguimiento);
		List<PreguntaMultiple> preguntasMultiples = crearPreguntasMultiples(preguntas, correctas);
		Actividad actividad = new Actividad(loginCreador, id, tipo, descripcion, objetivos, nivelDificultad, duracion,
				actividadesPrevias, fechaLimite, actividadesSeguimiento, null, preguntasMultiples, null, notaMinima);
		actividades.put(id, actividad);
	}

	public void crearActividadEncuesta(String loginCreador, String tipo, String descripcion, String stringObjetivos,
			String nivelDificultad, int duracion, List<Integer> idActividadesPrevias, String fechaLimite,
			List<Integer> idActividadesSeguimiento, List<String> preguntas, int idActividad) {
		int id = idActividad;
		String[] objetivos = stringObjetivos.split(",");
		List<Actividad> actividadesPrevias = getActividades(idActividadesPrevias);
		List<Actividad> actividadesSeguimiento = getActividades(idActividadesSeguimiento);
		List<PreguntaAbierta> preguntasAbiertas = crearPreguntas(preguntas);
		Actividad actividad = new Actividad(loginCreador, id, tipo, descripcion, objetivos, nivelDificultad, duracion, 
				actividadesPrevias, fechaLimite, actividadesSeguimiento, null, null, preguntasAbiertas, 0);
		actividades.put(id, actividad);
	}
	
	public void crearActividadRecurso(String loginCreador, String tipo, String descripcion, String stringObjetivos,
			String nivelDificultad, int duracion, List<Integer> idActividadesPrevias, String fechaLimite,
			List<Integer> idActividadesSeguimiento, String url, int idActividad) {
		
		int id = idActividad;
		String[] objetivos = stringObjetivos.split(",");
		List<Actividad> actividadesPrevias = getActividades(idActividadesPrevias);
		List<Actividad> actividadesSeguimiento = getActividades(idActividadesSeguimiento);
		Actividad actividad = new Actividad(loginCreador, id, tipo, descripcion, objetivos, nivelDificultad, duracion, 
				actividadesPrevias, fechaLimite, actividadesSeguimiento, url, null, null, 0);
		actividades.put(id, actividad);
	}
	
	public void crearActividadTarea(String loginCreador, String tipo, String descripcion, String stringObjetivos,
			String nivelDificultad, int duracion, List<Integer> idActividadesPrevias, String fechaLimite,
			List<Integer> idActividadesSeguimiento, int idActividad) {
		int id = idActividad;
		String[] objetivos = stringObjetivos.split(",");
		List<Actividad> actividadesPrevias = getActividades(idActividadesPrevias);
		List<Actividad> actividadesSeguimiento = getActividades(idActividadesSeguimiento);
		Actividad actividad = new Actividad(loginCreador, id, tipo, descripcion, objetivos, nivelDificultad, duracion, 
				actividadesPrevias, fechaLimite, actividadesSeguimiento, null, null, null, 0);
		actividades.put(id, actividad);
	}
}
