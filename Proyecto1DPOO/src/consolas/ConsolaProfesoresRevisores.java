package consolas;
import controladores.*;
import componentes.*;
import java.util.Scanner;
import java.util.Set;
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
	
	//Main y consola principal profesores revisores
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
					+ "4. Ver learning paths propios\n"
					+ "5. Ver estadisticas de learning path propio\n"
					+ "5. Revisar actividad estudiante\n"
					+ "7. Revisar progreso estudiante\n"
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
					verLearningPathsPropios();
					break;
				case 5:
					verEstadisticasLearningPathPropio();
					break;
				case 6:
					revisarActividadEnviada();
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
	
	//Registrar y ingresar profesores
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

	//Consultar componentes Learning Paths
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
	
	//Consultar informacion learning paths propios
	private void verLearningPathsPropios() {
		Collection<LearningPath> learningPaths = LPC.getLearningPathsPropios(loginActual);
		System.out.println("\nEstos son los learning paths creados por usted (El numero a su lado corresponde a su id).");
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
	private void verEstadisticasLearningPathPropio() {
		System.out.println("Ingrese el id del learning path que quiere revisar");
		int idLP = input.nextInt();
		input.nextLine();
		LearningPath lp = LPC.getLearningPath(idLP);
		if (lp.getLoginCreador().equals(loginActual)) {
			
		} else {
			System.out.println("El learning path con la id que ingreso no es suyo.");
		}
	}
	
	//Revisar actividades y progreso de un estudiante
	private void revisarActividadEnviada() {
		System.out.println("Ingrese el id del learning path que quiere revisar");
		int idLP = input.nextInt();
		input.nextLine();
		LearningPath lp = LPC.getLearningPath(idLP);
		if (lp.getLoginCreador().equals(loginActual)) {
			List<RegistroActividad> ras = RGC.getActividadesEnviadasLp(idLP);
			for (int i = 0; i < ras.size(); i++) {
				RegistroActividad ra = ras.get(i);
				System.out.printf("\n%d. \n", i);
				System.out.printf(" ID actividad: %d.\n", ra.getIdActividad());
				System.out.printf(" Fecha iniciado: %s.\n", ra.getFechaInicio().toString());
			}
			System.out.println("Ingrese el numero de la actividad que quiere revisar");
			int index = input.nextInt();
			input.nextLine();
			if (index > ras.size() + 1 && index >= 0) {
				if (ras.get(index).getRespuestas() == null) {
					revisarActividadTarea(ras.get(index));
				} else {
					revisarActividadExamen(ras.get(index));
				}
			} else {
				System.out.println("El numero que ha puesto no esta dentro de las opciones.");
			}
		} else {
			System.out.println("Este learning path no es suyo, no puede revisarlo");
		}
	}
	private void revisarActividadTarea(RegistroActividad ra) {
		String respuesta;
		System.out.println("Revise su correo y busque la tarea del estudiante, ingrese Y si la aprueba y N si no lo hace.");
		do {
			respuesta = input.nextLine();
		} while (!(respuesta.equals("Y") || respuesta.equals("N")));
		switch (respuesta) {
			case "Y":
				RGC.editarEstado(ra, "Completada");
				break;
			case "N":
				RGC.editarEstado(ra, "Desaprovado");
				break;
		}
	}
	private void revisarActividadExamen(RegistroActividad ra) {
		HashMap<String, String> respuestas = ra.getRespuestas();
		Set<String> preguntas = respuestas.keySet();
		for (String pregunta : preguntas ) {
			System.out.printf("\nPregunta: %s", pregunta);
			System.out.printf("\nRespuesta: %s", respuestas.get(pregunta));
		}
		System.out.println("Ingrese la nota que le quiere asignar al examen");
		int nota = input.nextInt();
		input.nextLine();
		Actividad a = AC.getActividad(ra.getIdActividad());
		if (nota >= a.getNotaMinima()) {
			RGC.editarEstado(ra, "Completada");
		} else {
			RGC.editarEstado(ra, "Desaprovado");
		}
		RGC.editarNota(ra, nota);
	}
	private void revisarProgreso() {
		System.out.println("Ingrese el id del learning path que quiere revisar");
		int idLP = input.nextInt();
		input.nextLine();
		
		System.out.println("Ingrese el login del estudiante que quiere revisar");
		String login = input.nextLine();
		
		RegistroLearningPath rlp = RGC.getRegistroLp(login, idLP);
		System.out.println("Esta es la informacion para el estudiante: ");
		System.out.printf("Estado: %s\n", rlp.getEstado());
		System.out.printf("Fecha de inscripcion: %s\n", rlp.getFechaInscrito());
		System.out.printf("Login: %s\n", rlp.getLoginEstudiante());
		for (RegistroActividad ra : rlp.getRegistrosA()) {
			System.out.printf("Actividad: %s\n", ra.getIdActividad());
			System.out.printf("Estado: %s\n", ra.getEstado());
			if (ra.getRespuestas() != null) {
				System.out.println("Respuestas:");
				HashMap<String, String> respuestas = ra.getRespuestas();
				Set<String> preguntas = respuestas.keySet();
				if (!(ra.getEstado().equals("No enviado"))) {
					for (String pregunta : preguntas) {
						System.out.printf("\nPregunta: %s\n", pregunta);
						System.out.printf("Respuesta: %s\n", respuestas.get(pregunta));
					}
				} else {
					System.out.println("No hay preguntas respondidas");
				}
			}
		}
	}
	
}