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
					+ "5. Revisar progreso estudiante\n"
					+ "6. Salir");
			respuesta = input.nextInt();
			input.nextLine();
			if (respuesta < 1 || respuesta > 7) {
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
					revisarProgreso();
					break;
			}
		} while (respuesta != 7);
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
	
	//Revisar progreso de un estudiante
	private void revisarProgreso() {
		System.out.println("Ingrese el id del learning path que quiere revisar");
		int idLP = input.nextInt();
		input.nextLine();
		
		System.out.println("Ingrese el login del estudiante que quiere revisar");
		String login = input.nextLine();
		
		RGC.mostrarProgreso(idLP, login);
	}
}