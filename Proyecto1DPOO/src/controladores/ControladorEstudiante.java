package controladores;

import componentes.Estudiante;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import persistencia.PersistenciaEstudiantes;

public class ControladorEstudiante {

	private HashMap<String, Estudiante> estudiantes;
	
	public ControladorEstudiante() {
	    this.estudiantes = new HashMap<>();
	}
	
	public void crearEstudiante(String login, String password) {
		Estudiante e = new Estudiante(login, password);
        estudiantes.put(e.getLogin(), e);
	}
	
	public void mostrarEstudiantes() {
		Set<String> logins = estudiantes.keySet();
 		Collection<Estudiante> passwords = estudiantes.values();
		for (String login : logins) {
			System.out.println(login);
		}
		for (Estudiante pass : passwords) {
			System.out.println(pass.getPassword());
		}
	}
	
	public boolean existeEstudiante(String login) {
		return estudiantes.containsKey(login);
	}
	
	public boolean ingresoEstudiante(String login, String password) {
		Estudiante estudiante = estudiantes.get(login);
		if (password.equals(estudiante.getPassword())) {
			return true;
		}
		return false;
	}
	
    public void guardarEstudiantesEnArchivo(String nombreArchivo) throws IOException {
        String directorioRelativo = "datos";
        File directorio = new File(directorioRelativo);

        if (!directorio.exists()) {
            directorio.mkdirs();
        }

        File archivo = new File(directorio, nombreArchivo);

        PersistenciaEstudiantes.guardarEstudiantes(archivo.getAbsolutePath(), this);
    }

    // Cargar estudiantes desde un archivo
    public void cargarEstudiantesDesdeArchivo(String nombreArchivo) throws IOException {
        String directorioRelativo = "datos";
        File directorio = new File(directorioRelativo);

        if (!directorio.exists()) {
            directorio.mkdir();
        }

        File archivo = new File(directorio, nombreArchivo);

        if (!archivo.exists()) {
            archivo.createNewFile();
            System.out.println("No existe el archivo " + nombreArchivo + ". Se ha creado uno nuevo");
        } else {
            PersistenciaEstudiantes.cargarEstudiantes(archivo.getAbsolutePath(), this);
        }
    }

    public List<Estudiante> getEstudiantes() {
        return new ArrayList<>(estudiantes.values());
    }
}
