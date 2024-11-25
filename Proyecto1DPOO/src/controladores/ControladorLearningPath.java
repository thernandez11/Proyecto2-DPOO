package controladores;

import componentes.Actividad;
import componentes.LearningPath;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import persistencia.PersistenciaLearningPaths;

public class ControladorLearningPath {

	HashMap<Integer, LearningPath> learningPaths;
	PersistenciaLearningPaths persistenciaLearningPaths;
	ControladorActividad controladorActividades;
	
	public ControladorLearningPath(ControladorActividad controladorActividades) {
	    this.learningPaths = new HashMap<>();
		this.persistenciaLearningPaths = new PersistenciaLearningPaths();
		this.controladorActividades = controladorActividades;
	}
	
	//Consultar informacion learningPaths
	public LearningPath getLearningPath(int idLp) {
		LearningPath lp = learningPaths.get(idLp);
		return lp;
	}
	public Collection<LearningPath> getLearningPaths() {
		Collection<LearningPath> lps = learningPaths.values();
		return lps;
	}
	public Collection<LearningPath> getLearningPathsPropios(String loginActual) {
		ArrayList<LearningPath> propios = new ArrayList<>();
		Collection<LearningPath> lps = learningPaths.values();
		for (LearningPath lp : lps) {
			if (lp.getLoginCreador().equals(loginActual)) {
				propios.add(lp);
			}
		}
		return propios;
	}
	public ArrayList<Integer> getIdsActividadesLP(int idLP) {
		ArrayList<Integer> ids = new ArrayList<>();
		LearningPath lp = learningPaths.get(idLP);
		Set<Actividad> actividades = lp.getActividades().keySet();
		for (Actividad a : actividades) {
			ids.add(a.getId());
		}
		return ids;
	}
	
	//Crear learning path
	public int crearLearningPath(String loginActual) {
		LocalDateTime fecha = LocalDateTime.now();
		int id = learningPaths.size() + 1;
		LearningPath lp = new LearningPath(id, loginActual, 0, fecha, fecha);
		learningPaths.put(id, lp);
		return id;
	}
	
	//Editar atributos learning path
	public void editarTitulo(int id, String titulo) {
		LearningPath lp = learningPaths.get(id);
		lp.setTitulo(titulo);
	}
	public void editarDescripcionGeneral(int id, String descripcion) {
		LearningPath lp = learningPaths.get(id);
		lp.setDescripcionGeneral(descripcion);
	}
	public void editarNivelDificultad(int id, String nivelDificultad) {
		LearningPath lp = learningPaths.get(id);
		lp.setNivelDificultad(nivelDificultad);
	}
	public void editarDuracion(int id, int duracion) {
		LearningPath lp = learningPaths.get(id);
		lp.setDuracion(duracion);
	}
	public void editarActividades(int id, HashMap<Actividad, Boolean> actividades) {
		LearningPath lp = learningPaths.get(id);
		lp.setActividades(actividades);
	}
	public void editarVersion(int id) {
		LearningPath lp = learningPaths.get(id);
		lp.setVersion(lp.getVersion() + 1);
	}
	public void editarFechaModificacion(int id) {
		LearningPath lp = learningPaths.get(id);
		LocalDateTime fecha = LocalDateTime.now();
		lp.setFechaModificacion(fecha);
	}
		
	//Persistencia learning paths
	public void guardarLPEnArchivo(String nombreArchivo) throws IOException {
        String directorioRelativo = "Persistencia"; 
        File directorio = new File(directorioRelativo);
        
        if (!directorio.exists()) {
            directorio.mkdirs(); 
        }

        File archivo = new File(directorio, nombreArchivo); 

        try (PrintWriter writer = new PrintWriter(new FileWriter(archivo, true))) { 
            for (LearningPath learningPath : learningPaths.values()) {
                writer.println(learningPath.getTitulo() + "," + learningPath.getDescripcionGeneral() + "," + learningPath.getNivelDificultad()+ "," + 
                				learningPath.getDuracion()+ "," + learningPath.getFechaCreacion()+ "," + learningPath.getFechaModificacion()+ "," + 
                				learningPath.getVersion() + "," + learningPath.getActividades() + "," + learningPath.getLoginCreador() ); // Guarda como login,password
            }
            System.out.println("Datos guardados exitosamente en " + archivo.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error al guardar los datos: " + e.getMessage());
            throw e;
        }
    }
    public void cargarLPDesdeArchivo(String nombreArchivo) throws IOException {
        if (learningPaths == null) {
        	learningPaths = new HashMap<>();
        }

        String directorioRelativo = "Persistencia"; 
        File archivo = new File(directorioRelativo, nombreArchivo);

        
        persistenciaLearningPaths.cargarLearningPaths(nombreArchivo, this, controladorActividades);
    }
	
}
