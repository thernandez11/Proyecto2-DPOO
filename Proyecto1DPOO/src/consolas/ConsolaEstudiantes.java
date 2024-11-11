package consolas;
import controladores.*;
import componentes.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.io.IOException;
import java.time.LocalDateTime;

public class ConsolaEstudiantes {
	private ControladorActividad AC;
	private ControladorEstudiante EC;
	private ControladorLearningPath LPC;
	private ControladorProfesor PC;
	private ControladorRegistros RGC;
	private ControladorResena RC;
	private Scanner input;
	private String loginActual;
	private String rolActual;
	
	public ConsolaEstudiantes() {
		this.AC = new ControladorActividad();
        this.EC = new ControladorEstudiante();
        this.LPC = new ControladorLearningPath();
        this.PC = new ControladorProfesor();
        this.RGC = new ControladorRegistros();
        this.RC = new ControladorResena();
        this.input = new Scanner(System.in);
    }
	
	//Main y consola principal estudiantes
	public static void main(String[] args) throws IOException {
		
		ConsolaEstudiantes c = new ConsolaEstudiantes();
		c.consolaRegistro();
		c.input.close();
	}
	private void consolaRegistro() throws IOException {
		int respuesta;
		cargarDatos();
		do {
			System.out.println("\nDigite el numero de la opcion que quiere usar.\n"
					+ "1. Ingresar como estudiante\n"
					+ "2. Registrarse como estudiante");
			respuesta = input.nextInt();
			input.nextLine();
			if (respuesta < 1 || respuesta > 2) {
				System.out.println("El numero que ha ingresado no esta en las opciones disponibles. Intente de nuevo.");
			}
			switch (respuesta) {
				case 1:
					ingresarEstudiante();
					break;
				case 2:
					registrarEstudiante();
					break;
			}
		} while (rolActual == null);
		
		do {
			System.out.println("\nDigite el numero de la opcion que quiere usar.\n"
					+ "1. Ver learning paths\n"
					+ "2. Ver actividades\n"
					+ "3. Ver reseñas de una actividad\n"
					+ "4. Crear reseña\n"
					+ "5. Inscribir learning path\n"
					+ "6. Desarrollar actividad\n"
					+ "7. Revisar progreso learning path\n"
					+ "8. Salir");
			respuesta = input.nextInt();
			input.nextLine();
			if (respuesta < 1 || respuesta > 8) {
				System.out.println("El numero que ha ingresado no esta en las opciones disponibles. Intente de nuevo.");
			}
			switch (respuesta) {
				case 1:
					verLearningPaths();
					break;
				case 2:
					verActividades();
					break;
				case 3:
					verResenasActividad();
					break;
				case 4:
					crearResena();
					break;
				case 5:
					inscribirLearningPath();
					break;
				case 6:
					desarrollarActividad();
					break;
				case 7:
					revisarProgreso();
					break;
			}
		} while (respuesta != 8);
		salvarDatos();
	}
	
	//Salvar y cargar datos
	private void salvarDatos() throws IOException {
		EC.guardarEstudiantesEnArchivo("estudiantes.txt");
		PC.guardarProfesoresEnArchivo("profesores.txt");
		LPC.guardarLPEnArchivo("learningPaths.txt");
		RC.guardarResenasEnArchivo("resenas.txt");
	}
	private void cargarDatos() throws IOException {
		EC.cargarEstudiantesDesdeArchivo("estudiantes.txt");
		PC.cargarProfesoresDesdeArchivo("profesores.txt");
		LPC.cargarLPDesdeArchivo("learningPaths.txt");
		RC.cargarResenasDesdeArchivo("resenas.txt");
	}

	//Registrar y ingresar estudiantes
	private void registrarEstudiante() {
		
		System.out.println("Ingrese su login:");
		String login = input.nextLine();
		System.out.println("Ingrese su password:");
		String password = input.nextLine();
		
		if (EC.existeEstudiante(login)) {
			System.out.println("Ya existe otro estudiante con este usuario.");
		} else {
			EC.crearEstudiante(login, password);
			System.out.println("Usuario registrado exitosamente!");
		}

	}
	private void ingresarEstudiante() {
		
		System.out.println("Ingrese su login:");
		String login = input.nextLine();
		System.out.println("Ingrese su password:");
		String password = input.nextLine();
		
		if (!(EC.existeEstudiante(login))) {
			System.out.println("El login ingresado no esta registrado.");
		} else {
			if (EC.ingresoEstudiante(login, password)) {
				 this.loginActual = login;
				 this.rolActual = "Estudiante";
				 System.out.println("Bienvenido.");
			} else {
				 System.out.println("Contraseña incorrecta.");
			}
		}
	}

	//Crear reseñas de actividades
	private void crearResena() {
		System.out.println("Ingrese el id de la actividad que quiere reseñar");
		int id = input.nextInt();
		input.nextLine();
		
		System.out.println("Cual fue su opinion acerca de la actividad?");
		String opinion = input.nextLine();
		
		System.out.println("Que calificacion le da a esta actividad? (Ingrese un numero del 1 al 5)");
		int rating = input.nextInt();
		input.nextLine();
		
		RC.crearResena(id, opinion, rating, loginActual, rolActual);
		System.out.println("Reseña creada de manera exitosa!");
	}
	
	//Consultar componentes Learning Paths
	private void verActividades() {
		Collection<Actividad> actividades = AC.getActividades();
		System.out.println("\nEstas son las actividades disponibles (El numero a su lado corresponde a su id).");
		for (Actividad a : actividades) {
			System.out.printf("%d.", a.getId());
			System.out.printf("\n Descripcion: %s.", a.getDescripcion());
			System.out.printf("\n Creador: %s.", a.getLoginCreador());
			System.out.printf("\n Tipo: %s.", a.getTipo());
			System.out.printf("\n Nivel de dificultad: %s.", a.getNivelDificultad());
			System.out.printf("\n Duracion en minutos: %s.\n", a.getDuracion());
		}
	}
	private void verActividadesLp(List<Integer> ids) {
		Collection<Actividad> actividades = AC.getActividadesIds(ids);
		System.out.println("\nEstas son las actividades disponibles (El numero a su lado corresponde a su id).");
		for (Actividad a : actividades) {
			System.out.printf("%d.", a.getId());
			System.out.printf("\n Descripcion: %s.", a.getDescripcion());
			System.out.printf("\n Creador: %s.", a.getLoginCreador());
			System.out.printf("\n Tipo: %s.", a.getTipo());
			System.out.printf("\n Nivel de dificultad: %s.", a.getNivelDificultad());
			System.out.printf("\n Duracion en minutos: %s.\n", a.getDuracion());
		}
	}
	private void verResenasActividad() {
		System.out.println("Digite la id de la actividad que quiere revisar: ");
		int id = input.nextInt();
		input.nextLine();
		
		ArrayList<Resena> listaResenas = RC.resenasActividad(id);
		System.out.println("Las reseñas de la actividad son:");
		for (Resena resena : listaResenas) {
			System.out.printf("\n Login del autor: %s.", resena.getLoginAutor());
			System.out.printf("\n Rol del autor: %s.", resena.getRolAutor());
			System.out.printf("\n Opinion: %s.", resena.getOpinion());
			System.out.printf("\n Rating: %s. \n", resena.getRating());
		}
		System.out.printf("La actividad tiene un rating promedio de: %f.", RC.calcularRating(id));
	}
	private void verLearningPaths() {
		Collection<LearningPath> learningPaths = LPC.getLearningPaths();
		System.out.println("\nEstos son los learning paths disponibles (El numero a su lado corresponde a su id).");
		for (LearningPath lp : learningPaths) {
			System.out.printf("%d.", lp.getId());
			System.out.printf("\n Titulo: %s.", lp.getTitulo());
			System.out.printf("\n Descripcion: %s.", lp.getDescripcionGeneral());
			System.out.printf("\n Creador: %s.", lp.getLoginCreador());
			System.out.printf("\n FechaCreacion: %s.", lp.getFechaCreacion());
			System.out.printf("\n FechaModificacion: %s.", lp.getFechaModificacion());
			System.out.printf("\n Nivel de dificultad: %s.", lp.getNivelDificultad());
			System.out.printf("\n Duracion en minutos: %s.\n", lp.getDuracion());
			System.out.printf("\n Version: %s.", lp.getVersion());
		}
	}
	
	//Inscribir Learning Paths
	private void inscribirLearningPath() {
		System.out.println("Inserte la id del learning path que quiere inscribir.");
		int idLP = input.nextInt();
		input.nextLine();
		LearningPath lp = LPC.getLearningPath(idLP);
		RGC.crearRegistroLpEstudiante(loginActual, lp);
		RGC.crearRegistrosActividad(loginActual, lp);
		System.out.println("Se ha inscrito exitosamente!");
	}
	
	//Consultar y desarrollar actividades de learning path
	private void desarrollarActividad() {
		System.out.println("Ingrese la id del learning path al cual pertenece la actividad");
		int idLP = input.nextInt();
		input.nextLine();
		
		verActividadesLp(LPC.getIdsActividadesLP(idLP));
		System.out.println("Digite el id de la actividad que quiere realizar");
		int idA = input.nextInt();
		input.nextLine();
		
		Actividad a = AC.getActividad(idA);
		RegistroActividad ra = RGC.getRegistroActividad(loginActual, idLP, idA);
		
		switch (a.getTipo()) {
			case "Tarea":
				desarrollarActividadTarea(a, ra);
				break;
			case "RecursoEducativo":
				desarrollarActividadRecursoEducativo(a, ra);
				break;
			case "Encuesta":
				desarrollarActividadEncuesta(a, ra);
				break;
			case "Examen":
				desarrollarActividadExamen(a, ra);
				break;
			case "QuizMultiple":
				desarrollarActividadQuizMultiple(a, ra);
				break;
			case "QuizVerdaderoFalso":
				desarrollarActividadQuizVerdaderoFalso(a, ra);
				break;
		}
		System.out.println("Actividad desarrollada exitosamente");
	}
	public void desarrollarActividadTarea(Actividad a, RegistroActividad ra) {
		String respuesta;
		ra.setFechaInicio(LocalDateTime.now());
		System.out.println("Lea la descripcion de la tarea, cuando ya la haya enviado al profesor, ingrese Y");
		do {
			respuesta = input.nextLine();
		} while (respuesta.equals("Y"));
		ra.setEstado("Enviada");
	}
	public void desarrollarActividadRecursoEducativo(Actividad a, RegistroActividad ra) {
		String respuesta;
		ra.setFechaInicio(LocalDateTime.now());
		System.out.println("Ingrese al url, cuando ya lo haya consultado, ingrese Y");
		System.out.printf("URL: %s. \n", a.getUrl());
		do {
			respuesta = input.nextLine();
		} while (!(respuesta.equals("Y")));
		ra.setEstado("Completada");
		ra.setFechaTerminado(LocalDateTime.now());
	}
	public void desarrollarActividadEncuesta(Actividad a, RegistroActividad ra) {
		String respuesta;
		ra.setFechaInicio(LocalDateTime.now());
		HashMap<String, String> respuestas = ra.getRespuestas();
		Set<String> preguntas = respuestas.keySet();
		for (String pregunta : preguntas) {
			System.out.println(pregunta);
			System.out.println("Ingrese su respuesta: ");
			respuesta = input.nextLine();
			respuestas.put(pregunta, respuesta);
		}
		ra.setEstado("Completada");
		ra.setFechaTerminado(LocalDateTime.now());
	}
	public void desarrollarActividadExamen(Actividad a, RegistroActividad ra) {
		String respuesta;
		ra.setFechaInicio(LocalDateTime.now());
		HashMap<String, String> respuestas = ra.getRespuestas();
		Set<String> preguntas = respuestas.keySet();
		for (String pregunta : preguntas) {
			System.out.println(pregunta);
			System.out.println("Ingrese su respuesta: ");
			respuesta = input.nextLine();
			respuestas.put(pregunta, respuesta);
		}
		ra.setEstado("Enviada");
		ra.setFechaTerminado(LocalDateTime.now());
	}
	public void desarrollarActividadQuizMultiple(Actividad a, RegistroActividad ra) {
		String respuesta;
		ra.setFechaInicio(LocalDateTime.now());
		String textoA = null, textoB = null, textoC = null, textoD = null;
		HashMap<String, String> respuestas = ra.getRespuestas();
		List<PreguntaMultiple> opciones = a.getPreguntasMultiples();
		for (PreguntaMultiple pregunta : opciones) {
			System.out.println(pregunta.getTextoPregunta());
			List<Opcion> variantes = pregunta.getOpciones();
			textoA = variantes.get(0).getTextoOpcion();
			System.out.printf("A. %s\n", textoA);
			
			textoB = variantes.get(1).getTextoOpcion();
			System.out.printf("B. %s\n", textoB);
			
			textoC = variantes.get(2).getTextoOpcion();
			System.out.printf("C. %s\n", textoC);
			
			textoD = variantes.get(3).getTextoOpcion();
			System.out.printf("D. %s\n", textoD);
			
			System.out.println("Ingrese la letra de la opcion que quiere elegir: ");
			do {
			respuesta = input.nextLine();
			} while (!(respuesta.equals("A") || respuesta.equals("B") || respuesta.equals("C") || respuesta.equals("D")));
			switch (respuesta) {
				case "A":
				respuestas.put(pregunta.getTextoPregunta(), textoA);
					break;
				case "B":
					respuestas.put(pregunta.getTextoPregunta(), textoB);
					break;
				case "C":
					respuestas.put(pregunta.getTextoPregunta(), textoC);
					break;
				case "D":
					respuestas.put(pregunta.getTextoPregunta(), textoD);
					break;
			}
		}
		ra.setEstado("Completada");
		ra.setFechaTerminado(LocalDateTime.now());
	}
	public void desarrollarActividadQuizVerdaderoFalso(Actividad a, RegistroActividad ra) {
		String respuesta;
		ra.setFechaInicio(LocalDateTime.now());
		String textoA = null, textoB = null;
		HashMap<String, String> respuestas = ra.getRespuestas();
		List<PreguntaVerdaderoFalso> opciones = a.getPreguntasVerdaderoFalso();
		for (PreguntaVerdaderoFalso pregunta : opciones) {
			System.out.println(pregunta.getTextoPregunta());
			List<Opcion> variantes = pregunta.getOpciones();
			textoA = variantes.get(0).getTextoOpcion();
			System.out.printf("V. %s\n", textoA);
			
			textoB = variantes.get(1).getTextoOpcion();
			System.out.printf("F. %s\n", textoB);
			
			System.out.println("Ingrese la letra de la opcion que quiere elegir: ");
			do {
			respuesta = input.nextLine();
			} while (!(respuesta.equals("V") || respuesta.equals("F")));
			switch (respuesta) {
				case "V":
				respuestas.put(pregunta.getTextoPregunta(), textoA);
					break;
				case "F":
					respuestas.put(pregunta.getTextoPregunta(), textoB);
					break;
			}
		}
		ra.setEstado("Completada");
		ra.setFechaTerminado(LocalDateTime.now());
	}
	
	//Revisar progeso de lp
	private void revisarProgreso() {
		String login = loginActual;
		System.out.println("Ingrese el id del learning path que quiere revisar");
		int idLP = input.nextInt();
		input.nextLine();
		RGC.mostrarProgreso(idLP, login);
	}
}