package consolas;
import controladores.*;
import componentes.*;
import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class ConsolaProfesoresRevisores {
	private ControladorActividad AC;
	private ControladorEstudiante EC;
	private ControladorLearningPath LPC;
	private ControladorProfesor PC;
	private ControladorRegistros RGC;
	private ControladorResena RC;
	private Scanner input;
	private String loginActual;
	private String rolActual;
	
	public ConsolaProfesoresRevisores() {
		this.AC = new ControladorActividad();
        this.EC = new ControladorEstudiante();
        this.LPC = new ControladorLearningPath();
        this.PC = new ControladorProfesor();
        this.RGC = new ControladorRegistros();
        this.RC = new ControladorResena();
        this.input = new Scanner(System.in);
    }
	
	public static void main(String[] args) throws IOException {
		
		ConsolaProfesoresRevisores c = new ConsolaProfesoresRevisores();
		c.consolaRegistro();
		c.input.close();
	}
	
	private void consolaRegistro() throws IOException {
		cargarDatos();
		int respuesta;
		do {
			System.out.println("\nDigite el numero de la opcion que quiere usar.\n"
					+ "1. Ingresar como profesor\n"
					+ "2. Registrarse como profesor");
			respuesta = input.nextInt();
			input.nextLine();
			if (respuesta < 1 || respuesta > 2) {
				System.out.println("El numero que ha ingresado no esta en las opciones disponibles. Intente de nuevo.");
			}
			switch (respuesta) {
				case 1:
					ingresarProfesor();
					break;
				case 2:
					registrarProfesor();
					break;
			}
		} while (rolActual == null);
		
		do {
			System.out.println("\nDigite el numero de la opcion que quiere usar.\n"
					+ "1. Ver learning paths\n"
					+ "2. Ver actividades\n"
					+ "3. Ver reseñas de una actividad\n"
					+ "4. Crear learning path\n"
					+ "5. Crear actividad\n"
					+ "6. Crear reseña\n"
					+ "7. Editar learning path\n"
					+ "8. Editar actividad\n"
					+ "9. Revisar progreso estudiante\n"
					+ "10. Salir");
			respuesta = input.nextInt();
			input.nextLine();
			if (respuesta < 1 || respuesta > 10) {
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
					crearLearningPath();
					break;
				case 5:
					crearActividad();
					break;
				case 6:
					crearResena();
					break;
				case 7:
					editarLearningPath();
					break;
				case 8:
					editarActividad();
					break;
				case 9:
					revisarProgreso();
					break;
			}
		} while (respuesta != 10);
		salvarDatos();
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
		int idA = AC.crearActividad(loginActual);
				
		System.out.println("\nIngrese la descripcion de la actividad:");
		String descripcion = input.nextLine();
		AC.editarDescripcion(idA, descripcion);
		
		System.out.println("\nIngrese los objetivos separados por comas:");
		String objetivoString = input.nextLine();
		AC.editarObjetivos(idA, objetivoString);
		
		System.out.println("\nIngrese el nivel de dificultad de la actividad:");
		String nivelDificultad = input.nextLine();
		AC.editarNivelDificultad(idA, nivelDificultad);
		
		System.out.println("\nIngrese la duracion de la actividad en minutos:");
		int duracion = input.nextInt();
		input.nextLine();
		AC.editarDuracion(idA, duracion);
		
		System.out.println("\nIngrese la fecha limite de la actividad en formato (yyyy-MM-ddThh:mm:ss), si no quiere poner una, ingrese 0:");
		String fechaLimite = input.nextLine();
		if (!(fechaLimite.equals("0"))) {
			AC.editarFechaLimite(idA, fechaLimite);
		}
		
		System.out.println("\nIngrese las actividades previas de la actividad:");
		ArrayList<Integer> idActividadesPrevias = seleccionadorActividadesids();
		AC.editarActividadesPrevias(idA, idActividadesPrevias);
		
		System.out.println("\nIngrese las actividades de seguimiento de la actividad:");
		ArrayList<Integer> idActividadesSeguimiento = seleccionadorActividadesids();
		AC.editarActividadesSeguimiento(idA, idActividadesSeguimiento);
			
		String tipo = seleccionadorTipo();
		AC.editarTipo(idA, tipo);
		
		switch (tipo) {
			case "Tarea":
				//Tarea no tiene nada especial, no posee su propio metodo de creacion.
				break;
			case "RecursoEducativo":
				crearActividadRecurso(idA);
				break;
			case "Encuesta":
				crearActividadEncuesta(idA);
				break;
			case "QuizMultiple":
				crearActividadQuiz(idA);
				break;
			case "QuizVerdaderoFalso":
				crearActividadQuizVerdaderoFalso(idA);
				break;
			case "Examen":
				crearActividadExamen(idA);
				break;
		}
		System.out.println("Actividad creada exitosamente!");
	}
	private void crearLearningPath() {
		int idLp = LPC.crearLearningPath(loginActual);
		
		System.out.println("\nIngrese el titulo del Learning Path:");
		String titulo = input.nextLine();
		LPC.editarTitulo(idLp, titulo);
			
		System.out.println("\nIngrese la descripcion del Learning Path:");
		String descripcion = input.nextLine();
		LPC.editarDescripcionGeneral(idLp, descripcion);
			
		System.out.println("\nIngrese el nivel de dificultad del Learning Path:");
		String nivelDificultad = input.nextLine();
		LPC.editarNivelDificultad(idLp, nivelDificultad);
			
		System.out.println("\nIngrese la duracion del Learning Path en minutos:");
		int duracion = input.nextInt();
		input.nextLine();
		LPC.editarDuracion(idLp, duracion);
			
		System.out.println("\nIngrese las actividades que quiere incluir en el Learning Path:");
		HashMap<Actividad, Boolean> actividades = seleccionadorActividadesLearningPath();
		LPC.editarActividades(idLp, actividades);
			
		System.out.println("Learning Path Creado exitosamente!");
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
	
	private HashMap<Actividad, Boolean> seleccionadorActividadesLearningPath() {
		int id;
		Boolean obligatoria;
		HashMap<Actividad, Boolean> as = new HashMap<>();
		System.out.println("Ingrese las id de las actividades que quiere seleccionar, ingrese 0 para terminar.");
		do {
			System.out.println("Ingrese id: ");
			id = input.nextInt();
			input.nextLine();
			Actividad actividad = AC.getActividad(id);
			if (id != 0) {
				System.out.println("Digite 1 si la actividad es obligatoria y 0 si no lo es.");
				int resp = input.nextInt();
				if (resp == 1) {
					obligatoria = true;
				} else {
					obligatoria = false;
				}
				as.put(actividad, obligatoria);
			}
		} while (id != 0);
		
		return as;
	}
	private ArrayList<Integer> seleccionadorActividadesids() {
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
					+ "4. ActividadQuizMultiple\n"
					+ "5. ActividadQuizVerdaderoFalso\n"
					+ "6. ActividadExamen");
			respuesta = input.nextInt();
			input.nextLine();
			if (respuesta < 1 || respuesta > 6) {
				System.out.println("El numero que ha ingresado no esta en las opciones disponibles. Intente de nuevo.");
			}
		} while (respuesta < 1 || respuesta > 6);
		
		switch (respuesta) {
			case 1:
				return "Tarea";
			case 2:
				return "RecursoEducativo";
			case 3:
				return "Encuesta";
			case 4:
				return "QuizMultiple";
			case 5:
				return "QuizVerdaderoFalso";
			case 6:
				return "Examen";
		}
		return "";
	}
	
	//Edicion actividades
	private void editarActividad() {
		System.out.println("Digite la id de la actividad que quiere editar (Solo puede editarla si usted es el creador.)");
		int idActividad = input.nextInt();
		input.nextLine();
		Actividad a = AC.getActividad(idActividad);
		if (a.getLoginCreador().equals(loginActual)) {
			String tipo = a.getTipo();
			menuEdicionActividad(idActividad);
			switch (tipo) {
			case "RecursoEducativo":
				menuRecurso(idActividad);
				break;
			case "Encuesta":
				menuEncuesta(idActividad);
				break;
			case "QuizMultiple":
				menuQuiz(idActividad);
				break;
			case "QuizVerdaderoFalso":
				menuQuizVerdaderoFalso(idActividad);
				break;
			case "Examen":
				menuExamen(idActividad);
				break;
			}
			System.out.println("Actividad editada exitosamente!");
		} else {
			System.out.println("Usted no es el creador de esta actividad, si quiere editarla, clonela.");
		}
	}
	
	private void menuEdicionActividad(int idA) {
		int respuesta;
		do {
			System.out.println("\nDigite la opcion que quiere usar.\n"
					+ "1. Editar descripcion\n"
					+ "2. Editar objetivos\n"
					+ "3. Editar nivel dificultad\n"
					+ "4. Editar duracion\n"
					+ "5. Editar actividades previas\n"
					+ "6. Editar actividades de seguimiento\n"
					+ "7. Editar fecha limite\n"
					+ "8. Salir");
			respuesta = input.nextInt();
			input.nextLine();
			if (respuesta < 1 || respuesta > 8) {
				System.out.println("El numero que ha ingresado no esta en las opciones disponibles. Intente de nuevo.");
			}
			switch (respuesta) {
				case 1:
					System.out.println("\nIngrese la descripcion de la actividad:");
					String descripcion = input.nextLine();
					AC.editarDescripcion(idA, descripcion);
					break;
				case 2:
					System.out.println("\nIngrese los objetivos separados por comas:");
					String objetivoString = input.nextLine();
					AC.editarObjetivos(idA, objetivoString);
					break;
				case 3:
					System.out.println("\nIngrese el nivel de dificultad de la actividad:");
					String nivelDificultad = input.nextLine();
					AC.editarNivelDificultad(idA, nivelDificultad);
					break;
				case 4:
					System.out.println("\nIngrese la duracion de la actividad en minutos:");
					int duracion = input.nextInt();
					input.nextLine();
					AC.editarDuracion(idA, duracion);
					break;
				case 5:
					System.out.println("\nIngrese la fecha limite de la actividad en formato (yyyy-MM-ddThh:mm:ss):");
					String fechaLimite = input.nextLine();
					AC.editarFechaLimite(idA, fechaLimite);
					break;
				case 6:
					System.out.println("\nIngrese las actividades previas de la actividad:");
					ArrayList<Integer> idActividadesPrevias = seleccionadorActividadesids();
					AC.editarActividadesPrevias(idA, idActividadesPrevias);
					break;
				case 7:
					System.out.println("\nIngrese las actividades de seguimiento de la actividad:");
					ArrayList<Integer> idActividadesSeguimiento = seleccionadorActividadesids();
					AC.editarActividadesSeguimiento(idA, idActividadesSeguimiento);
					break;
			}
		} while (respuesta != 8);
	}
	private void menuRecurso(int idA) {
		int respuesta;
		do {
			System.out.println("\nDigite la opcion que quiere usar.\n"
					+ "1. Editar url\n"
					+ "2. Salir");
			respuesta = input.nextInt();
			input.nextLine();
			if (respuesta < 1 || respuesta > 2) {
				System.out.println("El numero que ha ingresado no esta en las opciones disponibles. Intente de nuevo.");
			}
			switch (respuesta) {
				case 1:
					System.out.println("Digite la url del recurso que quiere mostrar");
					String url = input.nextLine();
					AC.editarURL(idA, url);
					break;
			}
		} while (respuesta != 2);
	}
	private void menuQuiz(int idA) {
		int respuesta;
		do {
			System.out.println("\nDigite la opcion que quiere usar.\n"
					+ "1. Editar preguntas\n"
					+ "2. Editar notaMinima\n"
					+ "3. Salir");
			respuesta = input.nextInt();
			input.nextLine();
			if (respuesta < 1 || respuesta > 3) {
				System.out.println("El numero que ha ingresado no esta en las opciones disponibles. Intente de nuevo.");
			}
			switch (respuesta) {
				case 1:
					String pregunta, opcion1, opcion2, opcion3, opcion4, explicacion1, explicacion2, explicacion3, explicacion4;
					int correcta;
					HashMap<String, HashMap<String, String>> preguntas = new HashMap<>();
					ArrayList<Integer> correctas = new ArrayList<>();
					System.out.println("Ingrese las preguntas que quiere realizar en el quiz una por una, escriba N cuando quiera parar.");
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
							
							do {
								System.out.println("Ingrese el numero de la opcion correcta:");
								correcta = input.nextInt();
								input.nextLine();
								if (correcta < 1 || correcta > 4) {
									System.out.println("El numero que ha ingresado no esta en las opciones disponibles. Intente de nuevo.");
								}
							} while (correcta < 1 || correcta > 4);
							correctas.add(correcta);
							preguntas.put(pregunta, opciones);
						}
					} while (!(pregunta.equals("N")));
					AC.editarPreguntasMultiples(idA, preguntas, correctas);
					break;
				case 2:
					System.out.println("Ingrese la nota minima de la actividad sobre 100");
					int notaMinima = input.nextInt();
					input.nextLine();
					AC.editarNotaMinima(idA, notaMinima);
					break;
			}
		} while (respuesta != 3);
	}
	private void menuQuizVerdaderoFalso(int idA) {
		int respuesta;
		do {
			System.out.println("\nDigite la opcion que quiere usar.\n"
					+ "1. Editar preguntas\n"
					+ "2. Editar nota minima\n"
					+ "3. Salir");
			respuesta = input.nextInt();
			input.nextLine();
			if (respuesta < 1 || respuesta > 3) {
				System.out.println("El numero que ha ingresado no esta en las opciones disponibles. Intente de nuevo.");
			}
			switch (respuesta) {
				case 1:
					String pregunta, opcion1, opcion2, explicacion1, explicacion2;
					int correcta;
					HashMap<String, HashMap<String, String>> preguntas = new HashMap<>();
					ArrayList<Integer> correctas = new ArrayList<>();
					System.out.println("Ingrese las preguntas que quiere realizar en el quiz una por una, escriba N cuando quiera parar.");
					do {
						System.out.println("Ingrese la pregunta:");
						pregunta = input.nextLine();
						if (!(pregunta.equals("N"))) {
							HashMap<String, String> opciones = new HashMap<>();
							
							System.out.println("La primera opcion es verdadero:");
							opcion1 = "Verdadero";
							
							System.out.println("Ingrese la explicacion de la primera opcion:");
							explicacion1 = input.nextLine();
							opciones.put(opcion1, explicacion1);
							
							System.out.println("La segunda opcion es falso:");
							opcion2 = "Falso";
							
							System.out.println("Ingrese la explicacion de la segunda opcion:");
							explicacion2 = input.nextLine();
							opciones.put(opcion2, explicacion2);
							
							do {
								System.out.println("Ingrese el numero de la opcion correcta 1 verdadero, 2 falso:");
								correcta = input.nextInt();
								input.nextLine();
								if (correcta < 1 || correcta > 2) {
									System.out.println("El numero que ha ingresado no esta en las opciones disponibles. Intente de nuevo.");
								}
							} while (correcta < 1 || correcta > 2);
							correctas.add(correcta);
							preguntas.put(pregunta, opciones);
						}
					} while (!(pregunta.equals("N")));
					AC.editarPreguntasMultiples(idA, preguntas, correctas);
					break;
				case 2:
					System.out.println("Ingrese la nota minima de la actividad sobre 100");
					int notaMinima = input.nextInt();
					input.nextLine();
					AC.editarNotaMinima(idA, notaMinima);
					break;
			}
		} while (respuesta != 3);
	}
	private void menuExamen(int idA) {
		int respuesta;
		do {
			System.out.println("\nDigite la opcion que quiere usar.\n"
					+ "1. Editar preguntas\n"
					+ "2. Editar nota minima\n"
					+ "3. Salir");
			respuesta = input.nextInt();
			input.nextLine();
			if (respuesta < 1 || respuesta > 3) {
				System.out.println("El numero que ha ingresado no esta en las opciones disponibles. Intente de nuevo.");
			}
			switch (respuesta) {
				case 1:
					String pregunta;
					List<String> preguntas = new ArrayList<>();
					System.out.println("Ingrese las preguntas que quiere realizar en la encuesta una por una, escriba N cuando quiera parar.");
					do {
						pregunta = input.nextLine();
						if (!(pregunta.equals("N"))) {
							preguntas.add(pregunta);
						}
						
					} while (!(pregunta.equals("N")));
					AC.editarPreguntasAbiertas(idA, preguntas);
					break;
				case 2:
					System.out.println("Ingrese la nota minima de la actividad sobre 100");
					int notaMinima = input.nextInt();
					input.nextLine();
					AC.editarNotaMinima(idA, notaMinima);
					break;
			}
		} while (respuesta != 3);
	}	
	private void menuEncuesta(int idA) {
		int respuesta;
		do {
			System.out.println("\nDigite la opcion que quiere usar.\n"
					+ "1. Editar preguntas\n"
					+ "2. Salir");
			respuesta = input.nextInt();
			input.nextLine();
			if (respuesta < 1 || respuesta > 2) {
				System.out.println("El numero que ha ingresado no esta en las opciones disponibles. Intente de nuevo.");
			}
			switch (respuesta) {
				case 1:
					String pregunta;
					List<String> preguntas = new ArrayList<>();
					System.out.println("Ingrese las preguntas que quiere realizar en la encuesta una por una, escriba N cuando quiera parar.");
					do {
						pregunta = input.nextLine();
						if (!(pregunta.equals("N"))) {
							preguntas.add(pregunta);
						}
						
					} while (!(pregunta.equals("N")));
					AC.editarPreguntasAbiertas(idA, preguntas);
					break;
			}
		} while (respuesta != 2);
	}

	//Edicion learning paths
	private void editarLearningPath() {
		System.out.println("Digite el id del learning path que quiere editar (Usted debe ser el creador de este)");
		int idLp = input.nextInt();
		input.nextLine();
		LearningPath lp = LPC.getLearningPath(idLp);
		if (lp.getLoginCreador().equals(loginActual)) {
			menuEdicionLearningPath(idLp);
			System.out.println("Learning path modificado exitosamente");
		} else {
			System.out.println("Usted no es el creador de este learning path");
		}
	}
	
	private void menuEdicionLearningPath(int idLp) {
		int respuesta;
		do {
			System.out.println("\nDigite la opcion que quiere usar.\n"
					+ "1. Editar titulo\n"
					+ "2. Editar descripcion\n"
					+ "3. Editar nivel dificultad\n"
					+ "4. Editar duracion\n"
					+ "5. Editar actividades\n"
					+ "6. Salir");
			respuesta = input.nextInt();
			input.nextLine();
			if (respuesta < 1 || respuesta > 6) {
				System.out.println("El numero que ha ingresado no esta en las opciones disponibles. Intente de nuevo.");
			}
			switch (respuesta) {
				case 1:
					System.out.println("\nIngrese el titulo del Learning Path:");
					String titulo = input.nextLine();
					LPC.editarTitulo(idLp, titulo);
					break;
				case 2:
					System.out.println("\nIngrese la descripcion del Learning Path:");
					String descripcion = input.nextLine();
					LPC.editarDescripcionGeneral(idLp, descripcion);
					break;
				case 3:
					System.out.println("\nIngrese el nivel de dificultad del Learning Path:");
					String nivelDificultad = input.nextLine();
					LPC.editarNivelDificultad(idLp, nivelDificultad);
					break;
				case 4:
					System.out.println("\nIngrese la duracion del Learning Path en minutos:");
					int duracion = input.nextInt();
					input.nextLine();
					LPC.editarDuracion(idLp, duracion);
					break;
				case 5:
					System.out.println("\nIngrese las actividades que quiere incluir en el Learning Path:");
					HashMap<Actividad, Boolean> actividades = seleccionadorActividadesLearningPath();
					LPC.editarActividades(idLp, actividades);
					break;
			}
		} while (respuesta != 6);
		LPC.editarVersion(idLp);
		LPC.editarFechaModificacion(idLp);
	}

	private void verActividades() {
		Collection<Actividad> actividades = AC.getActividades();
		System.out.println("\nEstas son las actividades disponibles (El numero a su lado corresponde a su id).");
		for (Actividad a : actividades) {
			System.out.printf("ID: %d.", a.getId());
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
			System.out.printf("ID: %d.", lp.getId());
			System.out.printf("\n Titulo: %s.", lp.getTitulo());
			System.out.printf("\n Descripcion: %s.", lp.getDescripcionGeneral());
			System.out.printf("\n Creador: %s.", lp.getLoginCreador());
			System.out.printf("\n FechaCreacion: %s.", lp.getFechaCreacion());
			System.out.printf("\n FechaModificacion: %s.", lp.getFechaModificacion());
			System.out.printf("\n Nivel de dificultad: %s.", lp.getNivelDificultad());
			System.out.printf("\n Duracion en minutos: %s.", lp.getDuracion());
			System.out.printf("\n Version: %s. \n", lp.getVersion());
		}
	}
	
	private void crearActividadExamen(int idA) {
		String pregunta;
		List<String> preguntas = new ArrayList<>();
		System.out.println("Ingrese la nota minima de la actividad sobre 100");
		int notaMinima = input.nextInt();
		input.nextLine();
		System.out.println("Ingrese las preguntas que quiere realizar en el examen una por una, escriba N cuando quiera parar.");
		do {
			pregunta = input.nextLine();
			if (!(pregunta.equals("N"))) {
				preguntas.add(pregunta);
			}
			
		} while (!(pregunta.equals("N")));
		AC.editarNotaMinima(idA, notaMinima);
		AC.editarPreguntasAbiertas(idA, preguntas);
		System.out.println("Actividad creada exitosamente!");
	}
	private void crearActividadQuiz(int idA) {
		String pregunta, opcion1, opcion2, opcion3, opcion4, explicacion1, explicacion2, explicacion3, explicacion4;
		int correcta;
		
		HashMap<String, HashMap<String, String>> preguntas = new HashMap<>();
		ArrayList<Integer> correctas = new ArrayList<>();
		System.out.println("Ingrese la nota minima de la actividad sobre 100");
		int notaMinima = input.nextInt();
		input.nextLine();
		System.out.println("Ingrese las preguntas que quiere realizar en el quiz una por una, escriba N cuando quiera parar.");
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
				
				do {
					System.out.println("Ingrese el numero de la opcion correcta:");
					correcta = input.nextInt();
					input.nextLine();
					if (correcta < 1 || correcta > 4) {
						System.out.println("El numero que ha ingresado no esta en las opciones disponibles. Intente de nuevo.");
					}
				} while (correcta < 1 || correcta > 4);
				correctas.add(correcta);
				preguntas.put(pregunta, opciones);
			}
		} while (!(pregunta.equals("N")));
		AC.editarNotaMinima(idA, notaMinima);
		AC.editarPreguntasMultiples(idA, preguntas, correctas);
	}
	private void crearActividadQuizVerdaderoFalso(int idA) {
		String pregunta, opcion1, opcion2, explicacion1, explicacion2;
		int correcta;
		
		HashMap<String, HashMap<String, String>> preguntas = new HashMap<>();
		ArrayList<Integer> correctas = new ArrayList<>();
		System.out.println("Ingrese la nota minima de la actividad sobre 100");
		int notaMinima = input.nextInt();
		input.nextLine();
		System.out.println("Ingrese las preguntas que quiere realizar en el quiz una por una, escriba N cuando quiera parar.");
		do {
			System.out.println("Ingrese la pregunta:");
			pregunta = input.nextLine();
			if (!(pregunta.equals("N"))) {
				HashMap<String, String> opciones = new HashMap<>();
				
				System.out.println("La primera opcion es verdadero:");
				opcion1 = "Verdadero";
				
				System.out.println("Ingrese la explicacion de la primera opcion:");
				explicacion1 = input.nextLine();
				opciones.put(opcion1, explicacion1);
				
				System.out.println("La segunda opcion es falso:");
				opcion2 = "Falso";
				
				System.out.println("Ingrese la explicacion de la segunda opcion:");
				explicacion2 = input.nextLine();
				opciones.put(opcion2, explicacion2);
				
				do {
					System.out.println("Ingrese el numero de la opcion correcta 1 verdadero, 2 falso:");
					correcta = input.nextInt();
					input.nextLine();
					if (correcta < 1 || correcta > 2) {
						System.out.println("El numero que ha ingresado no esta en las opciones disponibles. Intente de nuevo.");
					}
				} while (correcta < 1 || correcta > 2);
				correctas.add(correcta);
				preguntas.put(pregunta, opciones);
			}
		} while (!(pregunta.equals("N")));
		AC.editarNotaMinima(idA, notaMinima);
		AC.editarPreguntasVerdaderoFalso(idA, preguntas, correctas);
	}
	private void crearActividadEncuesta(int idA) {
		String pregunta;
		List<String> preguntas = new ArrayList<>();
		System.out.println("Ingrese las preguntas que quiere realizar en la encuesta una por una, escriba N cuando quiera parar.");
		do {
			pregunta = input.nextLine();
			if (!(pregunta.equals("N"))) {
				preguntas.add(pregunta);
			}
			
		} while (!(pregunta.equals("N")));
		AC.editarPreguntasAbiertas(idA, preguntas);
	}
	private void crearActividadRecurso(int idA) {
		System.out.println("Digite la url del recurso que quiere mostrar");
		String url = input.nextLine();
		AC.editarURL(idA, url);
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