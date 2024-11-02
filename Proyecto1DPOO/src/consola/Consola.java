package consola;
import controladores.*;
import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Consola {
	private ControladorActividad AC;
	private ControladorEstudiante EC;
	private ControladorLearningPath LPC;
	private ControladorProfesor PC;
	private ControladorRegistros RGC;
	private ControladorResena RC;
	private Scanner input;
	private String loginActual;
	private String rolActual;
	
	public Consola() {
		this.AC = new ControladorActividad();
        this.EC = new ControladorEstudiante();
        this.LPC = new ControladorLearningPath();
        this.PC = new ControladorProfesor();
        this.RGC = new ControladorRegistros();
        this.RC = new ControladorResena();
        this.input = new Scanner(System.in);
    }
	
	public static void main(String[] args) throws IOException {
		
		Consola c = new Consola();
		c.consolaRegistro();
		c.input.close();
	}
	
	private void consolaRegistro() throws IOException {
		int respuesta;
		do {
			System.out.println("\nDigite el numero de la opcion que quiere usar.\n"
					+ "1. Ingresar como estudiante\n"
					+ "2. Ingresar como profesor\n"
					+ "3. Registrarse como estudiante\n"
					+ "4. Registrarse como profesor\n"
					+ "5. Cargar datos");
			respuesta = input.nextInt();
			input.nextLine();
			if (respuesta < 1 || respuesta > 5) {
				System.out.println("El numero que ha ingresado no esta en las opciones disponibles. Intente de nuevo.");
			}
			switch (respuesta) {
				case 1:
					ingresarEstudiante();
					break;
				case 2:
					ingresarProfesor();
					break;
				case 3:
					registrarEstudiante();
					break;
				case 4:
					registrarProfesor();
					break;
				case 5:
					cargarDatos();
					break;
			}
		} while (rolActual == null);
		
		do {
			System.out.println("\nDigite el numero de la opcion que quiere usar.\n"
					+ "1. Crear actividad\n"
					+ "2. Editar actividad\n"
					+ "3. Crear learning path\n"
					+ "4. Editar learning path\n"
					+ "5. Crear reseña\n"
					+ "6. Ver actividades\n"
					+ "7. Ver reseñas de una actividad\n"
					+ "8. Ver learning paths\n"
					+ "9. Inscribir learning path\n"
					+ "10. Desarrollar actividad\n"
					+ "11. Revisar progreso estudiante\n"
					+ "12. Salvar datos\n"
					+ "13. Ingresar como estudiante\n"
					+ "14. Ingresar como profesor\n"
					+ "15. Salir");
			respuesta = input.nextInt();
			input.nextLine();
			if (respuesta < 1 || respuesta > 15) {
				System.out.println("El numero que ha ingresado no esta en las opciones disponibles. Intente de nuevo.");
			}
			switch (respuesta) {
				case 1:
					crearActividad();
					break;
				case 2:
					editarActividad();
					break;
				case 3:
					crearLearningPath();
					break;
				case 4:
					editarLearningPath();
					break;
				case 5:
					crearResena();
					break;
				case 6:
					verActividades();
					break;
				case 7:
					verResenasActividad();
					break;
				case 8:
					verLearningPaths();
					break;
				case 9:
					inscribirLearningPath();
					break;
				case 10:
					desarrollarActividad();
					break;
				case 11 :
					revisarProgreso();
					break;
				case 12:
					salvarDatos();
					break;
				case 13:
					ingresarEstudiante();
					break;
				case 14:
					ingresarProfesor();
					break;
			}
		} while (respuesta != 15);
	}
	

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
	private void registrarProfesor() {
		
		System.out.println("Ingrese su login:");
		String login = input.nextLine();
		System.out.println("Ingrese su password:");
		String password = input.nextLine();
		
		if (PC.existeProfesor(login)) {
			System.out.println("Ya existe otro profesor con este usuario.");
		} else {
			PC.crearProfesor(login, password);
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
	private void ingresarProfesor() {
		
		System.out.println("\nIngrese su login:");
		String login = input.nextLine();
		
		System.out.println("Ingrese su password:");
		String password = input.nextLine();
		
		if (!(PC.existeProfesor(login))) {
			System.out.println("El login ingresado no esta registrado.");
		} else {
			if (PC.ingresoProfesor(login, password)) {
				 this.loginActual = login;
				 this.rolActual = "Profesor";
				 System.out.println("Bienvenido.");
			} else {
				 System.out.println("Contraseña incorrecta.");
			}
		}
	}

	private void crearActividad() {
		if (!(rolActual.equals("Profesor"))) {
			System.out.println("\nNo tiene permisos para realizar esta accion");
		} else {
			
			System.out.println("\nIngrese la descripcion de la actividad:");
			String descripcion = input.nextLine();
			
			System.out.println("\nIngrese los objetivos separados por comas:");
			String objetivoString = input.nextLine();
			
			System.out.println("\nIngrese el nivel de dificultad de la actividad:");
			String nivelDificultad = input.nextLine();
			
			System.out.println("\nIngrese la duracion de la actividad en minutos:");
			int duracion = input.nextInt();
			input.nextLine();
			
			System.out.println("\nIngrese la fecha limite de la actividad:");
			String fechaLimite = input.nextLine();
			
			String tipo = seleccionadorTipo();
			
			System.out.println("\nIngrese las actividades previas de la actividad:");
			ArrayList<Integer> idActividadesPrevias = seleccionadorActividades();
			System.out.println("\nIngrese las actividades de seguimiento de la actividad:");
			ArrayList<Integer> idActividadesSeguimiento = seleccionadorActividades();
			
			switch (tipo) {
				case "Tarea":
					crearActividadTarea(descripcion, objetivoString, nivelDificultad, duracion, fechaLimite, tipo, idActividadesPrevias, idActividadesSeguimiento);
					break;
				case "RecursoEducativo":
					crearActividadRecurso(descripcion, objetivoString, nivelDificultad, duracion, fechaLimite, tipo, idActividadesPrevias, idActividadesSeguimiento);
					break;
				case "Encuesta":
					crearActividadEncuesta(descripcion, objetivoString, nivelDificultad, duracion, fechaLimite, tipo, idActividadesPrevias, idActividadesSeguimiento);
					break;
				case "Quiz":
					crearActividadQuiz(descripcion, objetivoString, nivelDificultad, duracion, fechaLimite, tipo, idActividadesPrevias, idActividadesSeguimiento);
					break;
				case "Examen":
					crearActividadExamen(descripcion, objetivoString, nivelDificultad, duracion, fechaLimite, tipo, idActividadesPrevias, idActividadesSeguimiento);
					break;
			}
			
		}
	}
	private void crearLearningPath() {
		if (!(rolActual.equals("Profesor"))) {
			System.out.println("\nNo tiene permisos para realizar esta accion");
		} else {
			
			System.out.println("\nIngrese el titulo del Learning Path:");
			String titulo = input.nextLine();
			
			System.out.println("\nIngrese la descripcion del Learning Path:");
			String descripcion = input.nextLine();
			
			System.out.println("\nIngrese el nivel de dificultad del Learning Path:");
			String nivelDificultad = input.nextLine();
			
			System.out.println("\nIngrese la duracion del Learning Path en minutos:");
			int duracion = input.nextInt();
			input.nextLine();
			
			System.out.println("\nIngrese las actividades que quiere incluir en el Learning Path:");
			HashMap<Integer, Boolean> idActividades = seleccionadorActividadesLearningPath();
			
			LPC.crearLearningPath(titulo, descripcion, nivelDificultad, duracion, idActividades, AC, loginActual);
			System.out.println("Learning Path Creado exitosamente!");
		}
	}
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
	
	private HashMap<Integer, Boolean> seleccionadorActividadesLearningPath() {
		int id;
		Boolean obligatoria;
		HashMap<Integer, Boolean> ids = new HashMap<>();
		System.out.println("Ingrese las id de las actividades que quiere seleccionar, ingrese 0 para terminar.");
		do {
			System.out.println("Ingrese id: ");
			id = input.nextInt();
			input.nextLine();
			if (id != 0) {
				System.out.println("Digite 1 si la actividad es obligatoria y 0 si no lo es.");
				int resp = input.nextInt();
				if (resp == 1) {
					obligatoria = true;
				} else {
					obligatoria = false;
				}
				ids.put(id, obligatoria);
			}
		} while (id != 0);
		
		return ids;
	}
	private ArrayList<Integer> seleccionadorActividades() {
		int id;
		ArrayList<Integer> ids = new ArrayList<>();
		System.out.println("Ingrese las id de las actividades que quiere seleccionar, ingrese 0 para terminar.");
		do {
			id = input.nextInt();
			input.nextLine();
			if (id != 0) {
				ids.add(id);
			}
		} while (id != 0);
		
		return ids;
	}
	private String seleccionadorTipo() {
		int respuesta;
		do {
			System.out.println("\nDigite el numero del tipo de actividad que quiere crear.\n"
					+ "1. ActividadTarea\n"
					+ "2. ActividadRecursoEducativo\n"
					+ "3. ActividadEncuesta\n"
					+ "4. ActividadQuiz\n"
					+ "5. ActividadExamen");
			respuesta = input.nextInt();
			input.nextLine();
			if (respuesta < 1 || respuesta > 5) {
				System.out.println("El numero que ha ingresado no esta en las opciones disponibles. Intente de nuevo.");
			}
		} while (respuesta < 1 || respuesta > 5);
		
		switch (respuesta) {
			case 1:
				return "Tarea";
			case 2:
				return "RecursoEducativo";
			case 3:
				return "Encuesta";
			case 4:
				return "Quiz";
			case 5:
				return "Examen";
		}
		return "";
	}
	
	private void editarActividad() {
		if (!(rolActual.equals("Profesor"))) {
			System.out.println("\nNo tiene permisos para realizar esta accion");
		} else {
			System.out.println("Digite la id de la actividad que quiere editar (Solo puede editarla si usted es el creador.)");
			int idActividad = input.nextInt();
			input.nextLine();
			if (AC.verificarCreador(idActividad, loginActual)) {
			
				System.out.println("\nIngrese la descripcion de la actividad:");
				String descripcion = input.nextLine();
				
				System.out.println("\nIngrese los objetivos separados por comas:");
				String objetivoString = input.nextLine();
				
				System.out.println("\nIngrese el nivel de dificultad de la actividad:");
				String nivelDificultad = input.nextLine();
				
				System.out.println("\nIngrese la duracion de la actividad en minutos:");
				int duracion = input.nextInt();
				input.nextLine();
				
				System.out.println("\nIngrese la fecha limite de la actividad:");
				String fechaLimite = input.nextLine();
				
				String tipo = seleccionadorTipo();
				
				System.out.println("\nIngrese las actividades previas de la actividad:");
				ArrayList<Integer> idActividadesPrevias = seleccionadorActividades();
				System.out.println("\nIngrese las actividades de seguimiento de la actividad:");
				ArrayList<Integer> idActividadesSeguimiento = seleccionadorActividades();
				
				switch (tipo) {
					case "Tarea":
						editarActividadTarea(descripcion, objetivoString, nivelDificultad, duracion, fechaLimite, tipo, idActividadesPrevias, idActividadesSeguimiento, idActividad);
						break;
					case "RecursoEducativo":
						editarActividadRecurso(descripcion, objetivoString, nivelDificultad, duracion, fechaLimite, tipo, idActividadesPrevias, idActividadesSeguimiento, idActividad);
						break;
					case "Encuesta":
						editarActividadEncuesta(descripcion, objetivoString, nivelDificultad, duracion, fechaLimite, tipo, idActividadesPrevias, idActividadesSeguimiento, idActividad);
						break;
					case "Quiz":
						editarActividadQuiz(descripcion, objetivoString, nivelDificultad, duracion, fechaLimite, tipo, idActividadesPrevias, idActividadesSeguimiento, idActividad);
						break;
					case "Examen":
						editarActividadExamen(descripcion, objetivoString, nivelDificultad, duracion, fechaLimite, tipo, idActividadesPrevias, idActividadesSeguimiento, idActividad);
						break;
				}
			} else {
				System.out.println("Usted no es el creador de esta actividad, no puede editarla a menos que la clone");
			}
		}
	}
	private void editarLearningPath() {
		System.out.println("Digite el id del learning path que quiere editar (Usted debe ser el creador de esta actividad)");
		int id = input.nextInt();
		input.nextLine();
		
		if (!(rolActual.equals("Profesor"))) {
			System.out.println("\nNo tiene permisos para realizar esta accion");
		} else {
			
			System.out.println("\nIngrese el titulo del Learning Path:");
			String titulo = input.nextLine();
			
			System.out.println("\nIngrese la descripcion del Learning Path:");
			String descripcion = input.nextLine();
			
			System.out.println("\nIngrese el nivel de dificultad del Learning Path:");
			String nivelDificultad = input.nextLine();
			
			System.out.println("\nIngrese la duracion del Learning Path en minutos:");
			int duracion = input.nextInt();
			input.nextLine();
			
			System.out.println("\nIngrese las actividades que quiere incluir en el Learning Path:");
			HashMap<Integer, Boolean> idActividades = seleccionadorActividadesLearningPath();
			
			LPC.crearLearningPath(titulo, descripcion, nivelDificultad, duracion, idActividades, AC, loginActual, id);
			System.out.println("Learning Path editado exitosamente!");
		}
	}
	
	private void verActividades() {
		AC.imprimirActividades();
	}
	private void verResenasActividad() {
		System.out.println("Digite la id de la actividad que quiere revisar: ");
		int id = input.nextInt();
		input.nextLine();
		
		RC.revisarResenas(id);
	}
	private void verLearningPaths() {
		LPC.imprimirLearningPaths();
	}
	
	private void crearActividadExamen(String descripcion, String objetivoString, String nivelDificultad, int duracion,
			String fechaLimite, String tipo, List<Integer> idActividadesPrevias, List<Integer>idActividadesSeguimiento) {
		String pregunta;
		List<String> preguntas = new ArrayList<>();
		System.out.println("Ingrese la nota minima de la actividad");
		float notaMinima = input.nextFloat();
		input.nextLine();
		System.out.println("Ingrese las preguntas que quiere realizar en el examen una por una, escriba N cuando quiera parar.");
		do {
			pregunta = input.nextLine();
			if (!(pregunta.equals("N"))) {
				preguntas.add(pregunta);
			}
			
		} while (!(pregunta.equals("N")));
		AC.crearActividadExamen(loginActual, tipo, descripcion, objetivoString, nivelDificultad, duracion, idActividadesPrevias, fechaLimite, idActividadesSeguimiento, preguntas, notaMinima);
		System.out.println("Actividad creada exitosamente!");
	}
	private void crearActividadQuiz(String descripcion, String objetivoString, String nivelDificultad, int duracion,
			String fechaLimite, String tipo, List<Integer> idActividadesPrevias, List<Integer>idActividadesSeguimiento) {
		
		String pregunta, opcion1, opcion2, opcion3, opcion4, explicacion1, explicacion2, explicacion3, explicacion4;
		int correcta;
		
		HashMap<String, HashMap<String, String>> preguntas = new HashMap<>();
		ArrayList<Integer> correctas = new ArrayList<>();
		System.out.println("Ingrese la nota minima de la actividad");
		float notaMinima = input.nextFloat();
		input.nextLine();
		System.out.println("Ingrese las preguntas que quiere realizar en el examen una por una, escriba N cuando quiera parar.");
		do {
			System.out.println("Ingrese la pregunta:");
			pregunta = input.nextLine();
			if (!(pregunta.equals("N"))) {
				HashMap<String, String> opciones = new HashMap<>();
				
				System.out.println("Ingrese la primera opcion:");
				opcion1 = input.nextLine();
				
				System.out.println("Ingrese la explicacion de la primera opcion:");
				explicacion1 = input.nextLine();
				opciones.put(opcion1, explicacion1);
				
				System.out.println("Ingrese la segunda opcion:");
				opcion2 = input.nextLine();
				
				System.out.println("Ingrese la explicacion de la segunda opcion:");
				explicacion2 = input.nextLine();
				opciones.put(opcion2, explicacion2);

				System.out.println("Ingrese la tercera opcion:");
				opcion3 = input.nextLine();
				
				System.out.println("Ingrese la explicacion de la tercera opcion:");
				explicacion3 = input.nextLine();
				opciones.put(opcion3, explicacion3);
				
				System.out.println("Ingrese la cuarta opcion:");
				opcion4 = input.nextLine();
				
				System.out.println("Ingrese la explicacion de la cuarta opcion:");
				explicacion4 = input.nextLine();
				opciones.put(opcion4, explicacion4);
				
				System.out.println("Ingrese el numero de la opcion correcta:");
				correcta = input.nextInt();
				input.nextLine();
				correctas.add(correcta);
				
				preguntas.put(pregunta, opciones);
			}
		} while (!(pregunta.equals("N")));
		AC.crearActividadQuiz(loginActual, tipo, descripcion, objetivoString, nivelDificultad, duracion, idActividadesPrevias, 
				fechaLimite, idActividadesSeguimiento, preguntas, correctas, notaMinima);
		System.out.println("Actividad creada exitosamente!");
	}
	private void crearActividadEncuesta(String descripcion, String objetivoString, String nivelDificultad, int duracion,
			String fechaLimite, String tipo, List<Integer> idActividadesPrevias, List<Integer>idActividadesSeguimiento) {
		
		String pregunta;
		List<String> preguntas = new ArrayList<>();
		System.out.println("Ingrese las preguntas que quiere realizar en el examen una por una, escriba N cuando quiera parar.");
		do {
			pregunta = input.nextLine();
			if (!(pregunta.equals("N"))) {
				preguntas.add(pregunta);
			}
			
		} while (!(pregunta.equals("N")));
		AC.crearActividadEncuesta(loginActual, tipo, descripcion, objetivoString, nivelDificultad, duracion, idActividadesPrevias, fechaLimite, idActividadesSeguimiento, preguntas);
		System.out.println("Actividad creada exitosamente!");
	}
	private void crearActividadRecurso(String descripcion, String objetivoString, String nivelDificultad, int duracion,
			String fechaLimite, String tipo, List<Integer> idActividadesPrevias, List<Integer>idActividadesSeguimiento) {
		
		System.out.println("Digite la url del recurso que quiere mostrar");
		String url = input.nextLine();
		
		AC.crearActividadRecurso(loginActual, tipo, descripcion, objetivoString, nivelDificultad, duracion, idActividadesPrevias, fechaLimite, idActividadesSeguimiento, url);
		System.out.println("Actividad creada exitosamente!");
	}
	private void crearActividadTarea(String descripcion, String objetivoString, String nivelDificultad, int duracion,
			String fechaLimite, String tipo, List<Integer> idActividadesPrevias, List<Integer>idActividadesSeguimiento) {
		AC.crearActividadTarea(loginActual, tipo, descripcion, objetivoString, nivelDificultad, duracion, idActividadesPrevias, fechaLimite, idActividadesSeguimiento);
		System.out.println("Actividad creada exitosamente!");
	}

	private void editarActividadExamen(String descripcion, String objetivoString, String nivelDificultad, int duracion,
			String fechaLimite, String tipo, List<Integer> idActividadesPrevias, List<Integer>idActividadesSeguimiento, int idActividad) {
		String pregunta;
		List<String> preguntas = new ArrayList<>();
		System.out.println("Ingrese la nota minima de la actividad");
		float notaMinima = input.nextFloat();
		input.nextLine();
		System.out.println("Ingrese las preguntas que quiere realizar en el examen una por una, escriba N cuando quiera parar.");
		do {
			pregunta = input.nextLine();
			if (!(pregunta.equals("N"))) {
				preguntas.add(pregunta);
			}
			
		} while (!(pregunta.equals("N")));
		AC.crearActividadExamen(loginActual, tipo, descripcion, objetivoString, nivelDificultad, duracion, idActividadesPrevias, fechaLimite, idActividadesSeguimiento, preguntas, notaMinima);
		System.out.println("Actividad creada exitosamente!");
	}
	private void editarActividadQuiz(String descripcion, String objetivoString, String nivelDificultad, int duracion,
			String fechaLimite, String tipo, List<Integer> idActividadesPrevias, List<Integer>idActividadesSeguimiento, int idActividad) {
		
		String pregunta, opcion1, opcion2, opcion3, opcion4, explicacion1, explicacion2, explicacion3, explicacion4;
		int correcta;
		
		HashMap<String, HashMap<String, String>> preguntas = new HashMap<>();
		ArrayList<Integer> correctas = new ArrayList<>();
		System.out.println("Ingrese la nota minima de la actividad");
		float notaMinima = input.nextFloat();
		input.nextLine();
		System.out.println("Ingrese las preguntas que quiere realizar en el examen una por una, escriba N cuando quiera parar.");
		do {
			System.out.println("Ingrese la pregunta:");
			pregunta = input.nextLine();
			if (!(pregunta.equals("N"))) {
				HashMap<String, String> opciones = new HashMap<>();
				
				System.out.println("Ingrese la primera opcion:");
				opcion1 = input.nextLine();
				
				System.out.println("Ingrese la explicacion de la primera opcion:");
				explicacion1 = input.nextLine();
				opciones.put(opcion1, explicacion1);
				
				System.out.println("Ingrese la segunda opcion:");
				opcion2 = input.nextLine();
				
				System.out.println("Ingrese la explicacion de la segunda opcion:");
				explicacion2 = input.nextLine();
				opciones.put(opcion2, explicacion2);

				System.out.println("Ingrese la tercera opcion:");
				opcion3 = input.nextLine();
				
				System.out.println("Ingrese la explicacion de la tercera opcion:");
				explicacion3 = input.nextLine();
				opciones.put(opcion3, explicacion3);
				
				System.out.println("Ingrese la cuarta opcion:");
				opcion4 = input.nextLine();
				
				System.out.println("Ingrese la explicacion de la cuarta opcion:");
				explicacion4 = input.nextLine();
				opciones.put(opcion4, explicacion4);
				
				System.out.println("Ingrese el numero de la opcion correcta:");
				correcta = input.nextInt();
				input.nextLine();
				correctas.add(correcta);
				
				preguntas.put(pregunta, opciones);
			}
		} while (!(pregunta.equals("N")));
		AC.crearActividadQuiz(loginActual, tipo, descripcion, objetivoString, nivelDificultad, duracion, idActividadesPrevias, fechaLimite, idActividadesSeguimiento, preguntas, correctas, notaMinima);
		System.out.println("Actividad creada exitosamente!");
	}
	private void editarActividadEncuesta(String descripcion, String objetivoString, String nivelDificultad, int duracion,
			String fechaLimite, String tipo, List<Integer> idActividadesPrevias, List<Integer>idActividadesSeguimiento, int idActividad) {
		
		String pregunta;
		List<String> preguntas = new ArrayList<>();
		System.out.println("Ingrese las preguntas que quiere realizar en el examen una por una, escriba N cuando quiera parar.");
		do {
			pregunta = input.nextLine();
			if (!(pregunta.equals("N"))) {
				preguntas.add(pregunta);
			}
			
		} while (!(pregunta.equals("N")));
		AC.crearActividadEncuesta(loginActual, tipo, descripcion, objetivoString, nivelDificultad, duracion, idActividadesPrevias, fechaLimite, idActividadesSeguimiento, preguntas);
		System.out.println("Actividad creada exitosamente!");
	}
	private void editarActividadRecurso(String descripcion, String objetivoString, String nivelDificultad, int duracion,
			String fechaLimite, String tipo, List<Integer> idActividadesPrevias, List<Integer>idActividadesSeguimiento, int idActividad) {
		
		System.out.println("Digite la url del recurso que quiere mostrar");
		String url = input.nextLine();
		
		AC.crearActividadRecurso(loginActual, tipo, descripcion, objetivoString, nivelDificultad, duracion, idActividadesPrevias, fechaLimite, idActividadesSeguimiento, url);
		System.out.println("Actividad creada exitosamente!");
	}
	private void editarActividadTarea(String descripcion, String objetivoString, String nivelDificultad, int duracion,
			String fechaLimite, String tipo, List<Integer> idActividadesPrevias, List<Integer>idActividadesSeguimiento, int idActividad) {
		AC.crearActividadTarea(loginActual, tipo, descripcion, objetivoString, nivelDificultad, duracion, idActividadesPrevias, fechaLimite, idActividadesSeguimiento);
		System.out.println("Actividad creada exitosamente!");
	}
	
	private void inscribirLearningPath() {
		System.out.println("Inserte la id del learning path que quiere inscribir.");
		int idLP = input.nextInt();
		input.nextLine();
		RGC.inscribirEstudiante(idLP, loginActual, AC, LPC);
		System.out.println("Se ha inscrito exitosamente!");
	}
	
	private void desarrollarActividad() {
		if (!(rolActual.equals("Estudiante"))) {
			System.out.println("\nUsted es un profesor, no tiene porque realizar actividades");
		} else {
			System.out.println("Ingrese la id del learning path al cual pertenece la actividad");
			int idLP = input.nextInt();
			input.nextLine();
			AC.imprimirActividades(LPC.getActividadesLP(idLP));
			System.out.println("Digite el id de la actividad que quiere realizar");
			int idA = input.nextInt();
			input.nextLine();
			String tipo = AC.getActividad(idA).getTipo();
			RGC.desarrollarActividad(idLP, idA, tipo, loginActual, input, AC);
		}
	}
	private void revisarProgreso() {
		String login;
		if (rolActual.equals("Estudiante")) {
			login = loginActual;
		} else {
			System.out.println("Ingrese el login del estudiante que quiere revisar: ");
			login = input.nextLine();
		}
		System.out.println("Ingrese el id del learning path que quiere revisar");
		int idLP = input.nextInt();
		input.nextLine();
		RGC.mostrarProgreso(idLP, login);
	}
}