package controladores;

import componentes.Profesor;
import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import persistencia.PersistenciaProfesores;

public class ControladorProfesor {

	HashMap<String, Profesor> profesores;
	
	public ControladorProfesor() {
	    this.profesores = new HashMap<>();
	}
	
	public void crearProfesor(String login, String password) {
		Profesor p = new Profesor(login, password);
		profesores.put(p.getLogin(), p);
	}
	
	public void mostrarProfesores() {
		Set<String> logins = profesores.keySet();
		Collection<Profesor> passwords = profesores.values();
		for (String login : logins) {
			System.out.println(login);
		}
		for (Profesor pass : passwords) {
			System.out.println(pass.getPassword());
		}
	}
	
	public boolean existeProfesor(String login) {
		return profesores.containsKey(login);
	}
	
	public boolean ingresoProfesor(String login, String password) {
		Profesor profesor = profesores.get(login);
		if (password.equals(profesor.getPassword())) {
			return true;
		}
		return false;
	}
	
	// Guardar profesores en un archivo
    public void guardarProfesoresEnArchivo(String nombreArchivo) throws IOException {
        String directorioRelativo = "datos";
        File directorio = new File(directorioRelativo);

        if (!directorio.exists()) {
            directorio.mkdirs();
        }

        File archivo = new File(directorio, nombreArchivo);

        PersistenciaProfesores.guardarProfesores(archivo.getAbsolutePath(), this);
    }

    // Cargar profesores desde un archivo
    public void cargarProfesoresDesdeArchivo(String nombreArchivo) throws IOException {
        if (profesores == null) {
            profesores = new HashMap<>();
        }

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
            PersistenciaProfesores.cargarProfesores(archivo.getAbsolutePath(), this);
        }
    }

    public Collection<Profesor> getProfesores() {
        return profesores.values();
    }

}
